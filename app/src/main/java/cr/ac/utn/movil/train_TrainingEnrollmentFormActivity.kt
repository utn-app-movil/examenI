package cr.ac.utn.movil

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.movil.R
import cr.ac.utn.movil.data.MemoryDataManager
import identities.train_Course
import identities.train_Person
import identities.train_TrainingEnrollment
import java.text.SimpleDateFormat
import java.util.*

class train_TrainingEnrollmentFormActivity : AppCompatActivity() {


    private lateinit var txtPerson: EditText
    private lateinit var txtSelectedCourses: TextView
    private lateinit var datePicker: DatePicker


    private val allCourses = listOf(
        train_Course("Android Basics"),
        train_Course("Kotlin Intermediate"),
        train_Course("Advanced Android"),
        train_Course("UI/UX Design")
    )
    private val selectedCourses = mutableListOf<train_Course>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_train_training_enrollment_form)

        txtPerson = findViewById(R.id.txtTrainPerson)
        txtSelectedCourses = findViewById(R.id.txtTrainSelectedCourses)
        datePicker = findViewById(R.id.selectTrainDate)


        val calendar = Calendar.getInstance()
        datePicker.minDate = calendar.timeInMillis


        txtSelectedCourses.setOnClickListener { showCourseSelectionDialog() }
        findViewById<android.widget.Button>(R.id.btnTrainSelectCourses).setOnClickListener { showCourseSelectionDialog() }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_crud, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnu_save -> {
                saveEnrollment()
                true
            }
            R.id.mnu_delete -> {
                deleteEnrollment()
                true
            }
            R.id.mnu_cancel -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun showCourseSelectionDialog() {
        val courseNames = allCourses.map { it.name }.toTypedArray()
        val checkedItems = BooleanArray(allCourses.size) { selectedCourses.contains(allCourses[it]) }

        AlertDialog.Builder(this)
            .setTitle("Select Courses")
            .setMultiChoiceItems(courseNames, checkedItems) { _, which, isChecked ->
                if (isChecked) {
                    selectedCourses.add(allCourses[which])
                } else {
                    selectedCourses.remove(allCourses[which])
                }
            }
            .setPositiveButton("OK") { _, _ ->
                txtSelectedCourses.text = if (selectedCourses.isEmpty()) {
                    "No courses selected"
                } else {
                    selectedCourses.joinToString { it.name }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }


    private fun saveEnrollment() {
        val personName = txtPerson.text.toString().trim()
        if (personName.isEmpty()) {
            Toast.makeText(this, "Please enter the person's name", Toast.LENGTH_SHORT).show()
            return
        }
        if (selectedCourses.isEmpty()) {
            Toast.makeText(this, "Please select at least one course", Toast.LENGTH_SHORT).show()
            return
        }

        val calendar = Calendar.getInstance()
        calendar.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
        val selectedDate = calendar.time
        val today = Date()
        if (selectedDate.before(today)) {
            Toast.makeText(this, "Selected date cannot be before today", Toast.LENGTH_SHORT).show()
            return
        }


        val person = train_Person(personName)
        val enrollment = train_TrainingEnrollment(
            personName = personName,
            courses = selectedCourses.map { it.name },
            date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate)
        )


        MemoryDataManager.add(enrollment)
        Toast.makeText(this, "Enrollment saved", Toast.LENGTH_SHORT).show()


        val intent = Intent(this, train_TrainingEnrollmentListActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }


    private fun deleteEnrollment() {
        val personName = txtPerson.text.toString().trim()
        if (personName.isEmpty()) {
            Toast.makeText(this, "Enter a person's name to delete", Toast.LENGTH_SHORT).show()
            return
        }
        val enrollment = MemoryDataManager.getAll()
            .find { it.FullName.equals(personName, ignoreCase = true) }
        if (enrollment != null) {
            MemoryDataManager.remove(enrollment.ID)
            Toast.makeText(this, "Enrollment deleted", Toast.LENGTH_SHORT).show()
            clearForm()
        } else {
            Toast.makeText(this, "Enrollment not found", Toast.LENGTH_SHORT).show()
        }
    }


    private fun clearForm() {
        txtPerson.text.clear()
        selectedCourses.clear()
        txtSelectedCourses.text = "No courses selected"

        val calendar = Calendar.getInstance()
        datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    }
}