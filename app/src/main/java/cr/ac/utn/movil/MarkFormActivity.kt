package cr.ac.utn.movil

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.movil.controller.mark_MarketingController
import cr.ac.utn.movil.identities.mark_MarketingCampaign
import cr.ac.utn.movil.util.util
import java.text.SimpleDateFormat
import java.util.*

class MarkFormActivity : AppCompatActivity() {

    private var currentCampaign: mark_MarketingCampaign? = null
    private var isNew = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mark_form)

        setSupportActionBar(findViewById(R.id.mark_toolbar_form))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupSpinners()
        setupDatePickers()

        val id = intent.getStringExtra("cr.ac.utn.appmovil.Id")
        if (id != null) loadCampaign(id) else newCampaign()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_crud, menu)
        menu?.findItem(R.id.mnu_delete)?.isVisible = !isNew
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnu_save    -> { saveCampaign(); true }
            R.id.mnu_delete  -> { deleteCampaign(); true }
            R.id.mnu_cancel  -> { finish(); true }
            android.R.id.home -> { finish(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupSpinners() {
        findViewById<android.widget.Spinner>(R.id.mark_spinner_channel).adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.mark_channels))

        findViewById<android.widget.Spinner>(R.id.mark_spinner_province).adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.mark_provinces))
    }

    private fun setupDatePickers() {
        val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val picker = { et: android.widget.EditText ->
            val c = Calendar.getInstance()
            if (et.text.isNotEmpty()) try { c.time = fmt.parse(et.text.toString())!! } catch (e: Exception) {}
            DatePickerDialog(this, { _, y, m, d -> et.setText(String.format("%d-%02d-%02d", y, m + 1, d)) },
                c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
        }
        findViewById<android.widget.EditText>(R.id.mark_et_start_date).setOnClickListener { picker(it as android.widget.EditText) }
        findViewById<android.widget.EditText>(R.id.mark_et_end_date).setOnClickListener { picker(it as android.widget.EditText) }
    }

    private fun newCampaign() {
        isNew = true
        currentCampaign = mark_MarketingCampaign().apply { ID = mark_MarketingController.generateNextId() }
        findViewById<android.widget.EditText>(R.id.mark_et_code).setText(currentCampaign!!.ID)
        invalidateOptionsMenu()
    }

    private fun loadCampaign(id: String) {
        isNew = false
        currentCampaign = mark_MarketingController.getById(id) ?: run { finish(); return }
        with(currentCampaign!!) {
            findViewById<android.widget.EditText>(R.id.mark_et_code).setText(ID)
            findViewById<android.widget.EditText>(R.id.mark_et_name).setText(campaignName)
            findViewById<android.widget.EditText>(R.id.mark_et_budget).setText(budget.toString())
            findViewById<android.widget.EditText>(R.id.mark_et_start_date).setText(startDate)
            findViewById<android.widget.EditText>(R.id.mark_et_end_date).setText(endDate)
            findViewById<android.widget.EditText>(R.id.mark_et_leader).setText(leaderFullName)
            findViewById<android.widget.Spinner>(R.id.mark_spinner_channel).setSelection(
                resources.getStringArray(R.array.mark_channels).indexOf(channel).coerceAtLeast(0))
            findViewById<android.widget.Spinner>(R.id.mark_spinner_province).setSelection(
                resources.getStringArray(R.array.mark_provinces).indexOf(province).coerceAtLeast(0))
        }
        invalidateOptionsMenu()
    }

    private fun saveCampaign() {
        val name = findViewById<android.widget.EditText>(R.id.mark_et_name).text.toString().trim()
        val budget = findViewById<android.widget.EditText>(R.id.mark_et_budget).text.toString().toDoubleOrNull() ?: 0.0
        val start = findViewById<android.widget.EditText>(R.id.mark_et_start_date).text.toString()
        val end = findViewById<android.widget.EditText>(R.id.mark_et_end_date).text.toString()

        if (name.isEmpty() || budget <= 0 || start.isEmpty() || end.isEmpty() || start > end) {
            Toast.makeText(this, "Check all fields", Toast.LENGTH_LONG).show()
            return
        }

        currentCampaign!!.apply {
            campaignName = name
            this.budget = budget
            startDate = start
            endDate = end
            channel = findViewById<android.widget.Spinner>(R.id.mark_spinner_channel).selectedItem.toString()
            province = findViewById<android.widget.Spinner>(R.id.mark_spinner_province).selectedItem.toString()
            leaderFullName = findViewById<android.widget.EditText>(R.id.mark_et_leader).text.toString()
        }

        if (isNew) mark_MarketingController.add(currentCampaign!!)
        else mark_MarketingController.update(currentCampaign!!)

        Toast.makeText(this, "Correct saved ", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun deleteCampaign() {
        util.showDialogCondition(this, "You probably want to delete this campaign?") {
            mark_MarketingController.delete(currentCampaign!!.ID)
            Toast.makeText(this@MarkFormActivity, "Deleted campaign", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}