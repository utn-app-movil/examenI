package cr.ac.utn.movil

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.movil.adapter.LicenseAdapter
import cr.ac.utn.movil.controller.LicenseController
import cr.ac.utn.movil.identities.LicenseRenewal
import cr.ac.utn.movil.util.EXTRA_ID
import cr.ac.utn.movil.util.util

class LicenseListActivity : AppCompatActivity() {

    private lateinit var rvLicenseList: RecyclerView
    private lateinit var tvEmptyList: TextView
    private lateinit var btnAddNew: Button
    private lateinit var licenseAdapter: LicenseAdapter

    private val licenseController = LicenseController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lic_list)

        supportActionBar?.title = getString(R.string.lic_title_list)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initializeViews()
        setupRecyclerView()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        loadLicenseList()
    }

    private fun initializeViews() {
        rvLicenseList = findViewById(R.id.lic_rvLicenseList)
        tvEmptyList = findViewById(R.id.lic_tvEmptyList)
        btnAddNew = findViewById(R.id.lic_btnAddNew)
    }

    private fun setupRecyclerView() {
        licenseAdapter = LicenseAdapter(
            licenses = mutableListOf(),
            onItemClick = { license ->
                // Open edit activity
                util.openActivity(
                    this,
                    LicenseRenewalActivity::class.java,
                    EXTRA_ID,
                    license.ID
                )
            }
        )

        rvLicenseList.apply {
            layoutManager = LinearLayoutManager(this@LicenseListActivity)
            adapter = licenseAdapter
        }
    }

    private fun setupListeners() {
        btnAddNew.setOnClickListener {
            util.openActivity(this, LicenseRenewalActivity::class.java)
        }
    }

    private fun loadLicenseList() {
        val licenses = licenseController.getAll()

        if (licenses.isEmpty()) {
            rvLicenseList.visibility = View.GONE
            tvEmptyList.visibility = View.VISIBLE
        } else {
            rvLicenseList.visibility = View.VISIBLE
            tvEmptyList.visibility = View.GONE
            licenseAdapter.updateList(licenses)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
