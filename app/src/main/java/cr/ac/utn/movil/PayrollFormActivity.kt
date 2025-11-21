package cr.ac.utn.movil

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cr.ac.utn.movil.controller.PayrollController
import cr.ac.utn.movil.identities.Payroll
import cr.ac.utn.movil.util.EXTRA_ID
import cr.ac.utn.movil.util.util

class PayrollFormActivity : AppCompatActivity() {

    private lateinit var tvFormTitle: TextView
    private lateinit var etId: EditText
    private lateinit var etName: EditText
    private lateinit var etFirstLastName: EditText
    private lateinit var etSecondLastName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText
    private lateinit var etEmployeeNumber: EditText
    private lateinit var etPosition: EditText
    private lateinit var etSalary: EditText
    private lateinit var etIban: EditText
    private lateinit var spMonth: Spinner
    private lateinit var etYear: EditText
    private lateinit var spBank: Spinner

    private val controller = PayrollController()
    private var isEditMode = false
    private var currentPayrollId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payroll_form)

        initializeViews()
        setupSpinners()

        currentPayrollId = intent.getStringExtra(EXTRA_ID)
        if (currentPayrollId != null) {
            isEditMode = true
            loadPayrollData(currentPayrollId!!)
            tvFormTitle.text = getString(R.string.pay_form_title_edit)
            etId.isEnabled = false
        } else {
            tvFormTitle.text = getString(R.string.pay_form_title_add)
        }
    }

    private fun initializeViews() {
        tvFormTitle = findViewById(R.id.tvFormTitle_pay)
        etId = findViewById(R.id.etId_pay)
        etName = findViewById(R.id.etName_pay)
        etFirstLastName = findViewById(R.id.etFirstLastName_pay)
        etSecondLastName = findViewById(R.id.etSecondLastName_pay)
        etPhone = findViewById(R.id.etPhone_pay)
        etEmail = findViewById(R.id.etEmail_pay)
        etEmployeeNumber = findViewById(R.id.etEmployeeNumber_pay)
        etPosition = findViewById(R.id.etPosition_pay)
        etSalary = findViewById(R.id.etSalary_pay)
        etIban = findViewById(R.id.etIban_pay)
        spMonth = findViewById(R.id.spMonth_pay)
        etYear = findViewById(R.id.etYear_pay)
        spBank = findViewById(R.id.spBank_pay)
    }

    private fun setupSpinners() {
        val months = resources.getStringArray(R.array.pay_months_array)
        val monthsWithPrompt = arrayOf(getString(R.string.pay_select_month)) + months
        val monthAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, monthsWithPrompt)
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spMonth.adapter = monthAdapter

        val banks = resources.getStringArray(R.array.pay_banks_array)
        val banksWithPrompt = arrayOf(getString(R.string.pay_select_bank)) + banks
        val bankAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, banksWithPrompt)
        bankAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spBank.adapter = bankAdapter
    }

    private fun loadPayrollData(id: String) {
        val payroll = controller.getById(id)
        if (payroll != null) {
            etId.setText(payroll.ID)
            etName.setText(payroll.Name)
            etFirstLastName.setText(payroll.FirstLastName)
            etSecondLastName.setText(payroll.SecondLastName)
            etPhone.setText(payroll.Phone)
            etEmail.setText(payroll.Email)
            etEmployeeNumber.setText(payroll.EmployeeNumber)
            etPosition.setText(payroll.Position)
            etSalary.setText(payroll.Salary.toString())
            etIban.setText(payroll.IbanAccount)
            etYear.setText(payroll.PaymentYear.toString())

            if (payroll.PaymentMonth in 1..12) {
                spMonth.setSelection(payroll.PaymentMonth)
            }

            val banks = resources.getStringArray(R.array.pay_banks_array)
            val bankIndex = banks.indexOf(payroll.BankName)
            if (bankIndex >= 0) {
                spBank.setSelection(bankIndex + 1)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_payroll, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnu_save_pay -> {
                util.showDialogCondition(this, getString(R.string.pay_dialog_save_message)) {
                    savePayroll()
                }
                true
            }
            R.id.mnu_delete_pay -> {
                if (isEditMode) {
                    util.showDialogCondition(this, getString(R.string.pay_dialog_delete_message)) {
                        deletePayroll()
                    }
                } else {
                    Toast.makeText(this, getString(R.string.MsgDataNoFound), Toast.LENGTH_SHORT).show()
                }
                true
            }
            R.id.mnu_cancel_pay -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun validateForm(): Boolean {
        val id = etId.text.toString().trim()
        val name = etName.text.toString().trim()
        val firstLastName = etFirstLastName.text.toString().trim()
        val employeeNumber = etEmployeeNumber.text.toString().trim()
        val position = etPosition.text.toString().trim()
        val salaryText = etSalary.text.toString().trim()
        val iban = etIban.text.toString().trim()
        val yearText = etYear.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val phone = etPhone.text.toString().trim()

        if (id.isEmpty()) {
            etId.error = getString(R.string.pay_error_empty_id)
            etId.requestFocus()
            return false
        }

        if (name.isEmpty()) {
            etName.error = getString(R.string.pay_error_empty_name)
            etName.requestFocus()
            return false
        }

        if (firstLastName.isEmpty()) {
            etFirstLastName.error = getString(R.string.pay_error_empty_first_last_name)
            etFirstLastName.requestFocus()
            return false
        }

        if (employeeNumber.isEmpty()) {
            etEmployeeNumber.error = getString(R.string.pay_error_empty_employee_number)
            etEmployeeNumber.requestFocus()
            return false
        }

        if (position.isEmpty()) {
            etPosition.error = getString(R.string.pay_error_empty_position)
            etPosition.requestFocus()
            return false
        }

        if (salaryText.isEmpty()) {
            etSalary.error = getString(R.string.pay_error_empty_salary)
            etSalary.requestFocus()
            return false
        }

        val salary = salaryText.toDoubleOrNull()
        if (salary == null || salary <= 0) {
            etSalary.error = getString(R.string.pay_error_invalid_salary)
            etSalary.requestFocus()
            return false
        }

        if (iban.isEmpty()) {
            etIban.error = getString(R.string.pay_error_empty_iban)
            etIban.requestFocus()
            return false
        }

        if (!controller.isValidIban(iban)) {
            etIban.error = getString(R.string.pay_error_invalid_iban)
            etIban.requestFocus()
            return false
        }

        if (spMonth.selectedItemPosition == 0) {
            Toast.makeText(this, getString(R.string.pay_error_empty_month), Toast.LENGTH_SHORT).show()
            return false
        }

        if (yearText.isEmpty()) {
            etYear.error = getString(R.string.pay_error_empty_year)
            etYear.requestFocus()
            return false
        }

        val year = yearText.toIntOrNull()
        val currentYear = java.time.Year.now().value
        if (year == null || year > currentYear) {
            etYear.error = getString(R.string.pay_error_invalid_year)
            etYear.requestFocus()
            return false
        }

        val month = spMonth.selectedItemPosition
        if (!controller.isValidMonth(month, year)) {
            Toast.makeText(this, getString(R.string.pay_error_invalid_month), Toast.LENGTH_SHORT).show()
            return false
        }

        if (spBank.selectedItemPosition == 0) {
            Toast.makeText(this, getString(R.string.pay_error_empty_bank), Toast.LENGTH_SHORT).show()
            return false
        }

        if (email.isNotEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = getString(R.string.pay_error_invalid_email)
            etEmail.requestFocus()
            return false
        }

        if (phone.isNotEmpty() && !phone.matches(Regex("^[0-9]{8}$"))) {
            etPhone.error = getString(R.string.pay_error_invalid_phone)
            etPhone.requestFocus()
            return false
        }

        if (!isEditMode && controller.getById(id) != null) {
            etId.error = getString(R.string.pay_error_duplicate_id)
            etId.requestFocus()
            return false
        }

        val excludeId = if (isEditMode) currentPayrollId else null
        if (controller.isDuplicateEmployee(employeeNumber, excludeId)) {
            etEmployeeNumber.error = getString(R.string.pay_error_duplicate_employee)
            etEmployeeNumber.requestFocus()
            return false
        }

        return true
    }

    private fun savePayroll() {
        if (!validateForm()) return

        val payroll = Payroll().apply {
            ID = etId.text.toString().trim()
            Name = etName.text.toString().trim()
            FirstLastName = etFirstLastName.text.toString().trim()
            SecondLastName = etSecondLastName.text.toString().trim()
            Phone = etPhone.text.toString().trim()
            Email = etEmail.text.toString().trim()
            EmployeeNumber = etEmployeeNumber.text.toString().trim()
            Position = etPosition.text.toString().trim()
            Salary = etSalary.text.toString().trim().toDouble()
            IbanAccount = etIban.text.toString().trim().uppercase()
            PaymentMonth = spMonth.selectedItemPosition
            PaymentYear = etYear.text.toString().trim().toInt()
            BankName = spBank.selectedItem.toString()
        }

        val success = if (isEditMode) {
            controller.update(payroll)
        } else {
            controller.add(payroll)
        }

        if (success) {
            val message = if (isEditMode) {
                getString(R.string.pay_success_update)
            } else {
                getString(R.string.pay_success_add)
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            finish()
        } else {
            val errorMessage = if (isEditMode) {
                getString(R.string.ErrorMsgUpdate)
            } else {
                getString(R.string.ErrorMsgAdd)
            }
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun deletePayroll() {
        currentPayrollId?.let { id ->
            if (controller.remove(id)) {
                Toast.makeText(this, getString(R.string.pay_success_delete), Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, getString(R.string.ErrorMsgRemove), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
