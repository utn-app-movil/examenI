package cr.ac.utn.movil

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import cr.ac.utn.movil.controller.LicenseController
import cr.ac.utn.movil.identities.LicenseRenewal
import cr.ac.utn.movil.util.EXTRA_ID
import cr.ac.utn.movil.util.util
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.UUID

class LicenseRenewalActivity : AppCompatActivity() {

    private lateinit var etUserId: TextInputEditText
    private lateinit var etUserName: TextInputEditText
    private lateinit var etUserEmail: TextInputEditText
    private lateinit var etUserPhone: TextInputEditText
    private lateinit var spLicenseType: Spinner
    private lateinit var etMedicalOpinionCode: TextInputEditText
    private lateinit var etCurrentScore: TextInputEditText
    private lateinit var btnSelectDate: Button
    private lateinit var btnSelectTime: Button
    private lateinit var tvSelectedDate: TextView
    private lateinit var tvSelectedTime: TextView
    private lateinit var btnSave: Button
    private lateinit var btnClear: Button

    private val licenseController = LicenseController()
    private var selectedDate: String = ""
    private var selectedTime: String = ""
    private var currentLicenseId: String? = null
    private var isEditMode: Boolean = false
    private lateinit var licenseTypes: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lic_renewal)

        supportActionBar?.title = getString(R.string.lic_title_renewal)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        licenseTypes = resources.getStringArray(R.array.lic_license_types_array)

        initializeViews()
        setupSpinner()
        setupListeners()

        // Check if editing existing record
        val licenseId = intent.getStringExtra(EXTRA_ID)
        if (!licenseId.isNullOrEmpty()) {
            loadLicenseData(licenseId)
        }
    }

    private fun initializeViews() {
        etUserId = findViewById(R.id.lic_etUserId)
        etUserName = findViewById(R.id.lic_etUserName)
        etUserEmail = findViewById(R.id.lic_etUserEmail)
        etUserPhone = findViewById(R.id.lic_etUserPhone)
        spLicenseType = findViewById(R.id.lic_spLicenseType)
        etMedicalOpinionCode = findViewById(R.id.lic_etMedicalOpinionCode)
        etCurrentScore = findViewById(R.id.lic_etCurrentScore)
        btnSelectDate = findViewById(R.id.lic_btnSelectDate)
        btnSelectTime = findViewById(R.id.lic_btnSelectTime)
        tvSelectedDate = findViewById(R.id.lic_tvSelectedDate)
        tvSelectedTime = findViewById(R.id.lic_tvSelectedTime)
        btnSave = findViewById(R.id.lic_btnSave)
        btnClear = findViewById(R.id.lic_btnClear)
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, licenseTypes.toList())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spLicenseType.adapter = adapter
    }

    private fun setupListeners() {
        btnSelectDate.setOnClickListener {
            showDatePicker()
        }

        btnSelectTime.setOnClickListener {
            showTimePicker()
        }

        btnSave.setOnClickListener {
            saveLicenseRenewal()
        }

        btnClear.setOnClickListener {
            util.showDialogCondition(this, getString(R.string.lic_confirm_clear)) {
                clearFields()
            }
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                selectedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                tvSelectedDate.text = selectedDate
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                tvSelectedTime.text = selectedTime
            },
            hour, minute, true
        )
        timePickerDialog.show()
    }

    private fun loadLicenseData(licenseId: String) {
        val license = licenseController.getById(licenseId)
        if (license != null) {
            isEditMode = true
            currentLicenseId = license.ID

            etUserId.setText(license.UserId)
            etUserName.setText(license.UserName)
            etUserEmail.setText(license.UserEmail)
            etUserPhone.setText(license.UserPhone)
            etMedicalOpinionCode.setText(license.MedicalOpinionCode)
            etCurrentScore.setText(license.CurrentScore.toString())

            // Set spinner selection
            val typeIndex = licenseTypes.toList().indexOf(license.LicenseType)
            if (typeIndex >= 0) {
                spLicenseType.setSelection(typeIndex)
            }

            selectedDate = license.RenewalDate
            selectedTime = license.RenewalTime
            tvSelectedDate.text = selectedDate
            tvSelectedTime.text = selectedTime

            btnSave.text = getString(R.string.lic_btn_update)
        }
    }

    private fun saveLicenseRenewal() {
        // Validate fields
        val userId = etUserId.text.toString().trim()
        val userName = etUserName.text.toString().trim()
        val userEmail = etUserEmail.text.toString().trim()
        val userPhone = etUserPhone.text.toString().trim()
        val licenseType = spLicenseType.selectedItem?.toString() ?: ""
        val medicalOpinionCode = etMedicalOpinionCode.text.toString().trim()
        val scoreText = etCurrentScore.text.toString().trim()

        // Validation
        if (userId.isEmpty()) {
            showError(R.string.lic_error_empty_user_id)
            return
        }

        if (userName.isEmpty()) {
            showError(R.string.lic_error_empty_user_name)
            return
        }

        if (userEmail.isEmpty()) {
            showError(R.string.lic_error_empty_user_email)
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            showError(R.string.lic_error_invalid_email)
            return
        }

        if (userPhone.isEmpty()) {
            showError(R.string.lic_error_empty_user_phone)
            return
        }

        if (licenseType.isEmpty()) {
            showError(R.string.lic_error_empty_license_type)
            return
        }

        if (medicalOpinionCode.isEmpty()) {
            showError(R.string.lic_error_empty_medical_code)
            return
        }

        if (scoreText.isEmpty()) {
            showError(R.string.lic_error_empty_score)
            return
        }

        val currentScore = scoreText.toIntOrNull()
        if (currentScore == null || currentScore < 0 || currentScore > 100) {
            showError(R.string.lic_error_invalid_score)
            return
        }

        // Validate score is greater than 65
        if (currentScore <= 65) {
            showError(R.string.lic_error_score_too_low)
            return
        }

        if (selectedDate.isEmpty()) {
            showError(R.string.lic_error_empty_date)
            return
        }

        if (selectedTime.isEmpty()) {
            showError(R.string.lic_error_empty_time)
            return
        }

        // Validate date and time are not in the future
        if (!validateDateTimeNotFuture()) {
            showError(R.string.lic_error_future_date)
            return
        }

        // Check for duplicate user ID (only if not editing the same record)
        val excludeId = if (isEditMode) currentLicenseId ?: "" else ""
        if (licenseController.existsByUserId(userId, excludeId)) {
            showError(R.string.lic_error_duplicate_user)
            return
        }

        // Create or update license renewal
        val licenseId = currentLicenseId ?: UUID.randomUUID().toString()

        val licenseRenewal = LicenseRenewal(
            id = licenseId,
            userId = userId,
            userName = userName,
            userEmail = userEmail,
            userPhone = userPhone,
            licenseType = licenseType,
            medicalOpinionCode = medicalOpinionCode,
            currentScore = currentScore,
            renewalDate = selectedDate,
            renewalTime = selectedTime
        )

        if (isEditMode) {
            licenseController.update(licenseRenewal)
            showSuccess(R.string.lic_success_updated)
        } else {
            licenseController.add(licenseRenewal)
            showSuccess(R.string.lic_success_saved)
        }

        finish()
    }

    private fun validateDateTimeNotFuture(): Boolean {
        try {
            val dateTimeString = "$selectedDate $selectedTime"
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val selectedDateTime = LocalDateTime.parse(dateTimeString, formatter)
            val now = LocalDateTime.now()
            return !selectedDateTime.isAfter(now)
        } catch (e: Exception) {
            return false
        }
    }

    private fun clearFields() {
        etUserId.text?.clear()
        etUserName.text?.clear()
        etUserEmail.text?.clear()
        etUserPhone.text?.clear()
        spLicenseType.setSelection(0)
        etMedicalOpinionCode.text?.clear()
        etCurrentScore.text?.clear()
        selectedDate = ""
        selectedTime = ""
        tvSelectedDate.text = ""
        tvSelectedTime.text = ""

        if (isEditMode) {
            isEditMode = false
            currentLicenseId = null
            btnSave.text = getString(R.string.lic_btn_save)
        }
    }

    private fun deleteLicenseRenewal() {
        if (currentLicenseId != null) {
            util.showDialogCondition(this, getString(R.string.lic_confirm_delete)) {
                licenseController.delete(currentLicenseId!!)
                showSuccess(R.string.lic_success_deleted)
                finish()
            }
        }
    }

    private fun showError(messageResId: Int) {
        Toast.makeText(this, getString(messageResId), Toast.LENGTH_LONG).show()
    }

    private fun showSuccess(messageResId: Int) {
        Toast.makeText(this, getString(messageResId), Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_crud, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.mnu_save -> {
                saveLicenseRenewal()
                true
            }
            R.id.mnu_delete -> {
                if (isEditMode) {
                    deleteLicenseRenewal()
                } else {
                    Toast.makeText(this, getString(R.string.lic_error_no_record_selected), Toast.LENGTH_SHORT).show()
                }
                true
            }
            R.id.mnu_cancel -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
