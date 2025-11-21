package cr.ac.utn.movil

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import controller.fli_ReservasController
import cr.ac.utn.movil.util.EXTRA_ID
import cr.ac.utn.movil.util.util
import identities.fli_Reserva
import java.time.LocalDateTime
import java.util.Calendar
import java.util.regex.Pattern

class fli_BookingsRegister : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    // UI Components
    private lateinit var txtID: EditText
    private lateinit var txtFlyNumber: EditText
    private lateinit var spnOriginCountry: Spinner
    private lateinit var spnDestinationCountry: Spinner
    private lateinit var lblDateTime: TextView
    private lateinit var txtCountry: EditText
    private lateinit var txtName: EditText
    private lateinit var txtFirstLastName: EditText
    private lateinit var txtSecondLastName: EditText
    private lateinit var txtPhone: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtAddress: EditText

    // Controller
    private lateinit var reservasController: fli_ReservasController

    // State
    private var isEditMode: Boolean = false
    private var selectedDateTime: LocalDateTime? = null
    private lateinit var menuItemDelete: MenuItem

    // Date and Time selection
    private var day: Int = 0
    private var month: Int = 0
    private var year: Int = 0
    private var hour: Int = 0
    private var minute: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fli_bookings_register)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize controller
        reservasController = fli_ReservasController(this)

        // Initialize UI components
        initializeComponents()

        // Setup spinners
        setupCountrySpinners()

        // Reset date to current
        resetDateTime()

        // Check if editing existing booking
        val bookingId = intent.getStringExtra(EXTRA_ID)
        if (bookingId != null && bookingId.trim().isNotEmpty()) {
            searchBooking(bookingId)
        }

        // Setup button listeners
        setupButtonListeners()
    }

    private fun initializeComponents() {
        txtID = findViewById(R.id.fli_txtID)
        txtFlyNumber = findViewById(R.id.fli_txtFlyNumber)
        spnOriginCountry = findViewById(R.id.fli_spnOriginCountry)
        spnDestinationCountry = findViewById(R.id.fli_spnDestinationCountry)
        lblDateTime = findViewById(R.id.fli_lblDateTime)
        txtCountry = findViewById(R.id.fli_txtCountry)
        txtName = findViewById(R.id.fli_txtName)
        txtFirstLastName = findViewById(R.id.fli_txtFirstLastName)
        txtSecondLastName = findViewById(R.id.fli_txtSecondLastName)
        txtPhone = findViewById(R.id.fli_txtPhone)
        txtEmail = findViewById(R.id.fli_txtEmail)
        txtAddress = findViewById(R.id.fli_txtAddress)
    }

    private fun setupCountrySpinners() {
        // ✅ Usar el array con prefijo fli_
        val countries = resources.getStringArray(R.array.fli_countries_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countries)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spnOriginCountry.adapter = adapter
        spnDestinationCountry.adapter = adapter
    }

    private fun setupButtonListeners() {
        val btnSelectDate = findViewById<ImageButton>(R.id.fli_btnSelectDate)
        btnSelectDate.setOnClickListener {
            showDatePickerDialog()
        }

        val btnSearch = findViewById<ImageButton>(R.id.fli_btnSearchID)
        btnSearch.setOnClickListener {
            searchBooking(txtID.text.trim().toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_crud, menu)
        menuItemDelete = menu!!.findItem(R.id.mnu_delete)
        menuItemDelete.isVisible = isEditMode
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnu_save -> {
                if (isEditMode) {
                    util.showDialogCondition(
                        this,
                        getString(R.string.TextSaveActionQuestion)
                    ) { saveBooking() }
                } else {
                    saveBooking()
                }
                true
            }
            R.id.mnu_delete -> {
                util.showDialogCondition(
                    this,
                    getString(R.string.TextDeleteActionQuestion)
                ) { deleteBooking() }
                true
            }
            R.id.mnu_cancel -> {
                cleanScreen()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun resetDateTime() {
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        hour = calendar.get(Calendar.HOUR_OF_DAY)
        minute = calendar.get(Calendar.MINUTE)
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this, this,
            year, month, day
        )
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        this.year = year
        this.month = month
        this.day = dayOfMonth

        // Now show time picker
        showTimePickerDialog()
    }

    private fun showTimePickerDialog() {
        val timePickerDialog = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                hour = selectedHour
                minute = selectedMinute
                updateDateTimeLabel()
            },
            hour, minute, true
        )
        timePickerDialog.show()
    }

    private fun updateDateTimeLabel() {
        val formattedDate = getDateTimeFormatString(day, month + 1, year, hour, minute)
        lblDateTime.text = formattedDate
        selectedDateTime = LocalDateTime.of(year, month + 1, day, hour, minute)
    }

    private fun getDateTimeFormatString(
        dayOfMonth: Int,
        monthValue: Int,
        yearValue: Int,
        hourValue: Int,
        minuteValue: Int
    ): String {
        return "${if (dayOfMonth < 10) "0" else ""}$dayOfMonth/" +
                "${if (monthValue < 10) "0" else ""}$monthValue/$yearValue " +
                "${if (hourValue < 10) "0" else ""}$hourValue:" +
                "${if (minuteValue < 10) "0" else ""}$minuteValue"
    }

    private fun searchBooking(id: String) {
        try {
            val reserva = reservasController.getById(id)
            if (reserva != null) {
                isEditMode = true
                txtID.setText(reserva.ID)
                txtID.isEnabled = false
                txtFlyNumber.setText(reserva.FlyNumber)

                // Set origin country
                val originPosition = getCountryPosition(reserva.OriginCountry)
                spnOriginCountry.setSelection(originPosition)

                // Set destination country
                val destPosition = getCountryPosition(reserva.DestinationCountry)
                spnDestinationCountry.setSelection(destPosition)

                // Set date and time
                year = reserva.Date.year
                month = reserva.Date.monthValue - 1
                day = reserva.Date.dayOfMonth
                hour = reserva.Date.hour
                minute = reserva.Date.minute
                selectedDateTime = reserva.Date
                updateDateTimeLabel()

                // Set personal data
                txtCountry.setText(reserva.Country)
                txtName.setText(reserva.Name)
                txtFirstLastName.setText(reserva.FLastName)
                txtSecondLastName.setText(reserva.SLastName)
                txtPhone.setText(reserva.Phone.toString())  // Int → String
                txtEmail.setText(reserva.Email)
                txtAddress.setText(reserva.Address)

                invalidateOptionsMenu()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.MsgDataNoFound),
                    Toast.LENGTH_LONG
                ).show()
            }
        } catch (e: Exception) {
            cleanScreen()
            Toast.makeText(
                this,
                e.message.toString(),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun getCountryPosition(countryName: String): Int {
        // ✅ Usar el array con prefijo fli_
        val countries = resources.getStringArray(R.array.fli_countries_array)
        return countries.indexOf(countryName).takeIf { it >= 0 } ?: 0
    }

    private fun isValidationData(): Boolean {
        // Check all fields are filled
        if (txtID.text.trim().isEmpty() ||
            txtFlyNumber.text.trim().isEmpty() ||
            spnOriginCountry.selectedItemPosition == 0 ||
            spnDestinationCountry.selectedItemPosition == 0 ||
            selectedDateTime == null ||
            txtCountry.text.trim().isEmpty() ||
            txtName.text.trim().isEmpty() ||
            txtFirstLastName.text.trim().isEmpty() ||
            txtSecondLastName.text.trim().isEmpty() ||
            txtPhone.text.trim().isEmpty() ||
            txtEmail.text.trim().isEmpty() ||
            txtAddress.text.trim().isEmpty()
        ) {
            // ✅ Usar string con prefijo fli_
            Toast.makeText(this, getString(R.string.fli_ErrorIncompleteData), Toast.LENGTH_LONG).show()
            return false
        }

        // Validate fly number format (e.g., AB1234)
        if (!isValidFlyNumber(txtFlyNumber.text.toString())) {
            // ✅ Usar string con prefijo fli_
            Toast.makeText(this, getString(R.string.fli_ErrorInvalidFlyNumber), Toast.LENGTH_LONG).show()
            return false
        }

        // Validate date is in the future
        if (selectedDateTime != null && selectedDateTime!!.isBefore(LocalDateTime.now())) {
            // ✅ Usar string con prefijo fli_
            Toast.makeText(this, getString(R.string.fli_ErrorDateInPast), Toast.LENGTH_LONG).show()
            return false
        }

        // Validate origin and destination are different
        if (spnOriginCountry.selectedItem.toString() == spnDestinationCountry.selectedItem.toString()) {
            // ✅ Usar string con prefijo fli_
            Toast.makeText(this, getString(R.string.fli_ErrorSameCountries), Toast.LENGTH_LONG).show()
            return false
        }

        // Validate phone is a valid number and at least 8 digits
        val phoneText = txtPhone.text.trim().toString()
        if (phoneText.toIntOrNull() == null || phoneText.length < 8) {
            // ✅ Usar string con prefijo fli_
            Toast.makeText(this, getString(R.string.fli_ErrorInvalidPhone), Toast.LENGTH_LONG).show()
            return false
        }

        // Validate email format
        if (!isValidEmail(txtEmail.text.toString())) {
            // ✅ Usar string con prefijo fli_
            Toast.makeText(this, getString(R.string.fli_ErrorInvalidEmail), Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun isValidFlyNumber(flyNumber: String): Boolean {
        // Format: 2 letters followed by 4 digits (e.g., AB1234)
        val pattern = Pattern.compile("^[A-Z]{2}\\d{4}$")
        return pattern.matcher(flyNumber.uppercase()).matches()
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        return pattern.matcher(email).matches()
    }

    private fun saveBooking() {
        try {
            if (isValidationData()) {
                // Check for duplicates only in add mode
                if (reservasController.getById(txtID.text.toString().trim()) != null && !isEditMode) {
                    Toast.makeText(
                        this,
                        getString(R.string.MsgDuplicateDate),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val reserva = fli_Reserva()
                    reserva.ID = txtID.text.toString().trim()
                    reserva.FlyNumber = txtFlyNumber.text.toString().uppercase().trim()
                    reserva.OriginCountry = spnOriginCountry.selectedItem.toString()
                    reserva.DestinationCountry = spnDestinationCountry.selectedItem.toString()
                    reserva.Date = selectedDateTime!!
                    reserva.Country = txtCountry.text.toString().trim()
                    reserva.Name = txtName.text.toString().trim()
                    reserva.FLastName = txtFirstLastName.text.toString().trim()
                    reserva.SLastName = txtSecondLastName.text.toString().trim()
                    reserva.Phone = txtPhone.text.toString().toInt()  // String → Int
                    reserva.Email = txtEmail.text.toString().trim()
                    reserva.Address = txtAddress.text.toString().trim()

                    if (!isEditMode) {
                        reservasController.addBooking(reserva)
                    } else {
                        reservasController.updatePerson(reserva)
                    }

                    cleanScreen()

                    Toast.makeText(
                        this,
                        getString(R.string.MsgSaveSuccess),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(
                this,
                e.message.toString(),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun deleteBooking() {
        try {
            reservasController.remove(txtID.text.toString())
            cleanScreen()
            Toast.makeText(
                this,
                getString(R.string.MsgDeleteSuccess),
                Toast.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            Toast.makeText(
                this,
                e.message.toString(),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun cleanScreen() {
        resetDateTime()
        isEditMode = false
        txtID.isEnabled = true
        txtID.setText("")
        txtFlyNumber.setText("")
        spnOriginCountry.setSelection(0)
        spnDestinationCountry.setSelection(0)
        lblDateTime.text = ""
        selectedDateTime = null
        txtCountry.setText("")
        txtName.setText("")
        txtFirstLastName.setText("")
        txtSecondLastName.setText("")
        txtPhone.setText("")
        txtEmail.setText("")
        txtAddress.setText("")
        invalidateOptionsMenu()
    }
}