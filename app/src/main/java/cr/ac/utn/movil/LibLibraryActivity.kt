package cr.ac.utn.movil

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import cr.ac.utn.movil.controller.LibReservationController
import cr.ac.utn.movil.identities.LibReservation
import cr.ac.utn.movil.util.EXTRA_ID
import cr.ac.utn.movil.util.util
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import java.util.Calendar

class LibLibraryActivity : AppCompatActivity() {


    private lateinit var studentDetailsEt: EditText
    private lateinit var reservedBooksTv: TextView
    private lateinit var reservationDateTv: TextView
    private lateinit var returnDateTv: TextView
    private lateinit var branchSpinner: Spinner


    private val reservationController = LibReservationController()
    private var currentReservationId: String? = null

    private var reservedBooksList: MutableList<String> = mutableListOf()
    private var reservationDateTime: LocalDateTime? = null
    private var returnDateTime: LocalDateTime? = null

    private val DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm"
    private val DATE_FORMAT = "dd/MM/yyyy"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lib_library)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeComponents()
        setupListeners()
        loadData()
    }

    private fun initializeComponents() {

        studentDetailsEt = findViewById(R.id.lib_student_details_et)
        reservedBooksTv = findViewById(R.id.lib_reserved_books_selected_tv)
        reservationDateTv = findViewById(R.id.lib_reservation_date_tv)
        returnDateTv = findViewById(R.id.lib_return_date_tv)
        branchSpinner = findViewById(R.id.lib_library_branch_sp)


        ArrayAdapter.createFromResource(
            this,
            R.array.lib_branches_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            branchSpinner.adapter = adapter
        }
    }

    private fun setupListeners() {
        reservedBooksTv.setOnClickListener { showBookMultiSelectDialog() }


        reservationDateTv.setOnClickListener { showDateTimePicker { dateTime ->
            reservationDateTime = dateTime
            reservationDateTv.text = dateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
        } }


        returnDateTv.setOnClickListener { showDatePicker { date ->

            returnDateTime = date.atStartOfDay()
            returnDateTv.text = date.format(DateTimeFormatter.ofPattern(DATE_FORMAT))
        } }
    }

    private fun loadData() {

        currentReservationId = intent.getStringExtra(EXTRA_ID)
        if (currentReservationId != null) {
            val reservation = reservationController.getReservationById(currentReservationId!!)
            if (reservation != null) {

                studentDetailsEt.setText(reservation.studentDetails)
                reservedBooksList = reservation.reservedBooks.toMutableList()
                reservationDateTime = reservation.reservationDateTime
                returnDateTime = reservation.returnDate


                updateReservedBooksTextView()
                reservationDateTv.text = reservationDateTime?.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
                returnDateTv.text = returnDateTime?.format(DateTimeFormatter.ofPattern(DATE_FORMAT))


                val branches = resources.getStringArray(R.array.lib_branches_array)
                branchSpinner.setSelection(branches.indexOf(reservation.libraryBranch))

            } else {
                Toast.makeText(this, R.string.MsgDataNoFound, Toast.LENGTH_LONG).show()
                currentReservationId = null
            }
        }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_crud, menu)


        if (currentReservationId == null) {
            menu.findItem(R.id.mnu_delete)?.isVisible = false
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnu_save -> {

                util.showDialogCondition(this, getString(R.string.TextSaveActionQuestion)) {
                    saveReservation()
                }
                true
            }
            R.id.mnu_delete -> {
                util.showDialogCondition(this, getString(R.string.TextDeleteActionQuestion)) {
                    deleteReservation()
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



    private fun collectData(): LibReservation? {
        val student = studentDetailsEt.text.toString().trim()
        val branch = branchSpinner.selectedItem.toString()


        if (student.isEmpty() || reservedBooksList.isEmpty() || reservationDateTime == null || returnDateTime == null) {
            Toast.makeText(this, getString(R.string.lib_validation_required_fields_error), Toast.LENGTH_LONG).show()
            return null
        }

        val reservation = LibReservation().apply {
            ID = currentReservationId ?: UUID.randomUUID().toString()
            studentDetails = student
            reservedBooks = reservedBooksList
            reservationDateTime = this@LibLibraryActivity.reservationDateTime
            returnDate = this@LibLibraryActivity.returnDateTime
            libraryBranch = branch
        }


        if (!reservationController.validateDates(reservation)) {
            Toast.makeText(this, getString(R.string.lib_validation_date_error), Toast.LENGTH_LONG).show()
            return null
        }

        return reservation
    }

    private fun saveReservation() {
        val reservation = collectData() ?: return

        val success = if (currentReservationId == null) {
            reservationController.addReservation(reservation)
        } else {
            reservationController.updateReservation(reservation)
        }

        if (success) {
            Toast.makeText(this, R.string.MsgSaveSuccess, Toast.LENGTH_LONG).show()
            finish()
        } else {

            Toast.makeText(this, R.string.lib_data_duplication_error, Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteReservation() {
        val id = currentReservationId
        if (id != null) {
            reservationController.removeReservation(id)
            Toast.makeText(this, R.string.MsgDeleteSuccess, Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(this, R.string.MsgDataNoFound, Toast.LENGTH_LONG).show()
        }
    }



    private fun showDatePicker(callback: (java.time.LocalDate) -> Unit) {
        val c = Calendar.getInstance()
        val dpd = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val localDate = java.time.LocalDate.of(selectedYear, selectedMonth + 1, selectedDay)
            callback(localDate)
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
        dpd.show()
    }

    private fun showDateTimePicker(callback: (LocalDateTime) -> Unit) {
        val c = Calendar.getInstance()
        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                val localDateTime = LocalDateTime.of(selectedYear, selectedMonth + 1, selectedDay, selectedHour, selectedMinute)
                callback(localDateTime)
            }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show()
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun showBookMultiSelectDialog() {
        val books = resources.getStringArray(R.array.lib_books_array)
        val checkedItems = BooleanArray(books.size) { i -> reservedBooksList.contains(books[i]) }

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.lib_reserved_books_label))
            .setMultiChoiceItems(books, checkedItems) { _, which, isChecked ->
                val book = books[which]
                if (isChecked) {
                    reservedBooksList.add(book)
                } else {
                    reservedBooksList.remove(book)
                }
            }
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                updateReservedBooksTextView()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.TextCancel_mnu) { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun updateReservedBooksTextView() {
        reservedBooksTv.text = if (reservedBooksList.isNotEmpty()) {
            reservedBooksList.joinToString(", ")
        } else {
            getString(R.string.lib_book_selection_hint)
        }
    }
}