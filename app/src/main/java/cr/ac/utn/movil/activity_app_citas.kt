package cr.ac.utn.movil

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import controllers.AppointmentsController
import identities.AppointmentEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class activity_app_citas : AppCompatActivity() {

    private lateinit var idInput: EditText
    private lateinit var nameInput: EditText
    private lateinit var contactInput: EditText
    private lateinit var doctorInput: EditText
    private lateinit var dateInput: EditText
    private lateinit var timeInput: EditText
    private lateinit var specialtySpinner: Spinner
    private lateinit var saveButton: Button
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button
    private lateinit var listAppointments: ListView

    private val controller = AppointmentsController()
    private var selectedId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_app_citas)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        idInput = findViewById(R.id.ID_app_ins)
        nameInput = findViewById(R.id.name_app_ins)
        contactInput = findViewById(R.id.contact_app_ins)
        doctorInput = findViewById(R.id.doctorname_app_insert)
        dateInput = findViewById(R.id.date_app_ins)
        timeInput = findViewById(R.id.time_app_ins)
        specialtySpinner = findViewById(R.id.sSpeciality_app_spinner)
        saveButton = findViewById(R.id.button)
        updateButton = findViewById(R.id.app_btn_update)
        deleteButton = findViewById(R.id.app_btn_delete)
        listAppointments = findViewById(R.id.app_citas_listAppointments)


        val specialties = listOf("Cardiology", "Dermatology", "Pediatrics", "Neurology")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, specialties)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        specialtySpinner.adapter = adapter


        saveButton.setOnClickListener { saveAppointment() }
        updateButton.setOnClickListener { updateAppointment() }
        deleteButton.setOnClickListener { deleteAppointment() }

        listAppointments.setOnItemClickListener { _, _, position, _ ->
            val appointment = controller.getAppointments()[position]
            selectedId = appointment.ID
            idInput.setText(appointment.ID)
            nameInput.setText(appointment.patientName)
            contactInput.setText(appointment.contact)
            doctorInput.setText(appointment.doctorName)
            dateInput.setText(appointment.dateTime.toLocalDate().toString())
            timeInput.setText(appointment.dateTime.toLocalTime().toString())
            specialtySpinner.setSelection((specialtySpinner.adapter as ArrayAdapter<String>).getPosition(appointment.specialty))
        }

        loadAppointments()
    }

    private fun saveAppointment() {
        val id = idInput.text.toString().trim()
        val name = nameInput.text.toString().trim()
        val contact = contactInput.text.toString().trim()
        val doctor = doctorInput.text.toString().trim()
        val date = dateInput.text.toString().trim()
        val time = timeInput.text.toString().trim()
        val specialty = specialtySpinner.selectedItem.toString()

        if (id.isEmpty() || name.isEmpty() || contact.isEmpty() || doctor.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, getString(R.string.app_msg_fill_fields), Toast.LENGTH_SHORT).show()
            return
        }

        val appointmentDateTime = parseDateTime(date, time) ?: return
        if (appointmentDateTime.isBefore(LocalDateTime.now())) {
            Toast.makeText(this, getString(R.string.app_msg_invalid_date), Toast.LENGTH_SHORT).show()
            return
        }

        val appointment = AppointmentEntity(id, name, contact, doctor, specialty, appointmentDateTime)
        if (!controller.addAppointment(appointment)) {
            Toast.makeText(this, getString(R.string.app_msg_duplicate), Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(this, getString(R.string.app_msg_saved), Toast.LENGTH_SHORT).show()
        clearFields()
        loadAppointments()
    }

    private fun updateAppointment() {
        if (selectedId == null) return
        val dateTime = parseDateTime(dateInput.text.toString(), timeInput.text.toString()) ?: return

        val appointment = AppointmentEntity(
            selectedId!!,
            nameInput.text.toString(),
            contactInput.text.toString(),
            doctorInput.text.toString(),
            specialtySpinner.selectedItem.toString(),
            dateTime
        )

        controller.updateAppointment(appointment)
        Toast.makeText(this, getString(R.string.app_msg_updated), Toast.LENGTH_SHORT).show()
        clearFields()
        loadAppointments()
    }

    private fun deleteAppointment() {
        if (selectedId == null) return
        controller.deleteAppointment(selectedId!!)
        Toast.makeText(this, getString(R.string.app_msg_deleted), Toast.LENGTH_SHORT).show()
        clearFields()
        loadAppointments()
    }

    private fun loadAppointments() {
        val appointments = controller.getAppointments()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, appointments.map { it.toString() })
        listAppointments.adapter = adapter
    }

    private fun parseDateTime(date: String, time: String): LocalDateTime? {
        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            LocalDateTime.parse("$date $time", formatter)
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.app_msg_invalid_format), Toast.LENGTH_SHORT).show()
            null
        }
    }

    private fun clearFields() {
        idInput.text.clear()
        nameInput.text.clear()
        contactInput.text.clear()
        doctorInput.text.clear()
        dateInput.text.clear()
        timeInput.text.clear()
        specialtySpinner.setSelection(0)
        selectedId = null
    }
}