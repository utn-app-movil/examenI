package cr.ac.utn.movil

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.movil.data.MemoryDataManager
import cr.ac.utn.movil.identities.Ens_Policy
import cr.ac.utn.movil.util.util
import cr.ac.utn.movil.util.EXTRA_ID
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Ens_Activity : AppCompatActivity() {

    private lateinit var edtPolicyNumber: EditText
    private lateinit var spnCompany: Spinner
    private lateinit var spnInsuranceType: Spinner
    private lateinit var edtStartDate: EditText
    private lateinit var edtEndDate: EditText
    private lateinit var edtPremium: EditText
    private lateinit var lvPolicies: ListView
    private lateinit var policyAdapter: ArrayAdapter<String>

    private var currentPolicy: Ens_Policy? = null
    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    private val companies = arrayOf(
        "INS", "Mapfre", "Qualitas", "Seguros del Magisterio", "Pan American Life", "ASSA"
    )

    private val insuranceTypes = arrayOf(
        "Responsabilidad Civil", "Todo Riesgo", "Incendio y Líneas Aliadas",
        "Transporte", "Vida Grupo", "Salud Colectivo", "Fidelidad"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ens)

        initViews()
        loadSpinners()
        setupDatePickers()
        setupPolicyList()

        val id = intent.getStringExtra(EXTRA_ID)
        if (id != null && id.isNotEmpty()) {
            loadPolicy(id)
        } else {
            loadPolicyList()
        }
    }

    private fun initViews() {
        edtPolicyNumber = findViewById(R.id.edt_ens_policyNumber)
        spnCompany = findViewById(R.id.spn_ens_company)
        spnInsuranceType = findViewById(R.id.spn_ens_type)
        edtStartDate = findViewById(R.id.edt_ens_startDate)
        edtEndDate = findViewById(R.id.edt_ens_endDate)
        edtPremium = findViewById(R.id.edt_ens_premium)
        lvPolicies = findViewById(R.id.lv_ens_policies)
    }

    private fun loadSpinners() {
        ArrayAdapter(this, android.R.layout.simple_spinner_item, companies).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spnCompany.adapter = it
        }
        ArrayAdapter(this, android.R.layout.simple_spinner_item, insuranceTypes).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spnInsuranceType.adapter = it
        }
    }

    private fun setupDatePickers() {
        val today = LocalDate.now()

        val startDateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val selected = LocalDate.of(year, month + 1, day)
            edtStartDate.setText(selected.format(dateFormatter))
        }

        val endDateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val selected = LocalDate.of(year, month + 1, day)
            edtEndDate.setText(selected.format(dateFormatter))
        }

        edtStartDate.setOnClickListener {
            val current = if (edtStartDate.text.isNotEmpty())
                LocalDate.parse(edtStartDate.text.toString(), dateFormatter)
            else today

            DatePickerDialog(this, startDateListener, current.year, current.monthValue - 1, current.dayOfMonth).show()
        }

        edtEndDate.setOnClickListener {
            val current = if (edtEndDate.text.isNotEmpty())
                LocalDate.parse(edtEndDate.text.toString(), dateFormatter)
            else today

            DatePickerDialog(this, endDateListener, current.year, current.monthValue - 1, current.dayOfMonth).show()
        }
    }
    private var isStartDateFocused = true
    private fun showDatePicker(default: LocalDate, listener: DatePickerDialog.OnDateSetListener) {
        isStartDateFocused = (edtStartDate.hasFocus() || edtStartDate.text.isNotEmpty() && edtEndDate.text.isEmpty())
        val date = if ((isStartDateFocused && edtStartDate.text.isNotEmpty()) ||
            (!isStartDateFocused && edtEndDate.text.isNotEmpty())) {
            LocalDate.parse((if (isStartDateFocused) edtStartDate else edtEndDate).text.toString(), dateFormatter)
        } else default

        DatePickerDialog(this, listener, date.year, date.monthValue - 1, date.dayOfMonth).show()
    }

    private fun setupPolicyList() {
        policyAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1)
        lvPolicies.adapter = policyAdapter
        loadPolicyList()
    }

    private fun loadPolicyList() {
        policyAdapter.clear()
        MemoryDataManager.getAll()
            .filterIsInstance<Ens_Policy>()
            .forEach {
                policyAdapter.add("${it.policyNumber} - ${it.company} (${it.startDate} → ${it.endDate}) - ₡${it.premium}")
            }
    }

    private fun loadPolicy(id: String) {
        val policy = MemoryDataManager.getById(id) as? Ens_Policy
        if (policy != null) {
            currentPolicy = policy
            edtPolicyNumber.setText(policy.policyNumber)
            edtPolicyNumber.isEnabled = false
            spnCompany.setSelection(companies.indexOf(policy.company))
            spnInsuranceType.setSelection(insuranceTypes.indexOf(policy.insuranceType))
            edtStartDate.setText(policy.startDate)
            edtEndDate.setText(policy.endDate)
            edtPremium.setText(policy.premium.toString())
        }
        loadPolicyList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.ens_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnu_save -> { savePolicy(); true }
            R.id.mnu_delete -> { deletePolicy(); true }
            R.id.mnu_cancel -> { finish(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun savePolicy() {
        if (!validateFields()) return

        val policy = currentPolicy ?: Ens_Policy().apply { ID = generateId() }

        policy.policyNumber = edtPolicyNumber.text.toString().trim()
        policy.company = spnCompany.selectedItem.toString()
        policy.insuranceType = spnInsuranceType.selectedItem.toString()
        policy.startDate = edtStartDate.text.toString()
        policy.endDate = edtEndDate.text.toString()
        policy.premium = edtPremium.text.toString().toDouble()

        if (currentPolicy == null) {
            MemoryDataManager.add(policy)
            Toast.makeText(this, R.string.ens_save_success, Toast.LENGTH_SHORT).show()
        } else {
            MemoryDataManager.update(policy)
            Toast.makeText(this, R.string.ens_updated_success, Toast.LENGTH_SHORT).show()
        }

        clearFields()
        loadPolicyList()
    }

    private fun deletePolicy() {
        if (currentPolicy == null) {
            Toast.makeText(this, R.string.ens_nothing_to_delete, Toast.LENGTH_SHORT).show()
            return
        }

        util.showDialogCondition(this, getString(R.string.ens_confirm_delete)) {
            MemoryDataManager.remove(currentPolicy!!.ID)
            Toast.makeText(this, R.string.ens_deleted, Toast.LENGTH_SHORT).show()
            clearFields()
            loadPolicyList()
        }
    }

    private fun clearFields() {
        currentPolicy = null
        edtPolicyNumber.text.clear()
        edtPolicyNumber.isEnabled = true
        spnCompany.setSelection(0)
        spnInsuranceType.setSelection(0)
        edtStartDate.text.clear()
        edtEndDate.text.clear()
        edtPremium.text.clear()
    }

    private fun validateFields(): Boolean {
        val policyNum = edtPolicyNumber.text.toString().trim()
        if (policyNum.isEmpty()) {
            Toast.makeText(this, R.string.ens_error_policy_empty, Toast.LENGTH_LONG).show()
            return false
        }

        if (currentPolicy == null) {
            val exists = MemoryDataManager.getAll().any {
                (it as? Ens_Policy)?.policyNumber.equals(policyNum, ignoreCase = true)
            }
            if (exists) {
                Toast.makeText(this, R.string.ens_error_duplicate, Toast.LENGTH_LONG).show()
                return false
            }
        }

        if (edtStartDate.text.isEmpty() || edtEndDate.text.isEmpty()) {
            Toast.makeText(this, R.string.ens_select_dates, Toast.LENGTH_LONG).show()
            return false
        }

        val start = util.parseStringToDateModern(edtStartDate.text.toString(), "dd/MM/yyyy")
        val end = util.parseStringToDateModern(edtEndDate.text.toString(), "dd/MM/yyyy")

        if (start == null || end == null) {
            Toast.makeText(this, R.string.ens_error_date_format, Toast.LENGTH_LONG).show()
            return false
        }

        if (!end.isAfter(start)) {
            Toast.makeText(this, R.string.ens_error_end_before_start, Toast.LENGTH_LONG).show()
            return false
        }

        val premium = edtPremium.text.toString().toDoubleOrNull() ?: 0.0
        if (premium <= 0) {
            Toast.makeText(this, R.string.ens_premium_zero, Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun generateId() = System.currentTimeMillis().toString()

    override fun onResume() {
        super.onResume()
        loadPolicyList()
    }
}