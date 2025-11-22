package cr.ac.utn.movil.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.movil.R
import cr.ac.utn.movil.controller.PayenInsuranceController
import cr.ac.utn.movil.entities.PayenInsuranceRequest
import cr.ac.utn.movil.util.util
import java.util.Calendar

class PayenInsuranceFormActivity : AppCompatActivity() {

    private val controller = PayenInsuranceController()
    private var currentId: String? = null
    private var selectedMedicines = mutableListOf<String>()

    private val medicinesOptions = arrayOf(
        "Ibuprofen",
        "Acetaminophen",
        "Amoxicillin",
        "Cough Syrup",
        "Antiallergic"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payen_insurance_form)


        val txtPersonId = findViewById<EditText>(R.id.payen_txt_person_id)
        val txtPersonName = findViewById<EditText>(R.id.payen_txt_person_name)
        val txtPersonEmail = findViewById<EditText>(R.id.payen_txt_person_email)
        val spnSpecialty = findViewById<Spinner>(R.id.payen_spn_specialty)
        val txtAppointmentDate = findViewById<EditText>(R.id.payen_txt_appointment_date)
        val txtAppointmentTime = findViewById<EditText>(R.id.payen_txt_appointment_time)
        val txtRequestDate = findViewById<EditText>(R.id.payen_txt_request_date)
        val txtRequestTime = findViewById<EditText>(R.id.payen_txt_request_time)
        val txtAppointmentAmount = findViewById<EditText>(R.id.payen_txt_appointment_amount)
        val txtMedicinesAmount = findViewById<EditText>(R.id.payen_txt_medicines_amount)

        val btnSelectMedicines = findViewById<Button>(R.id.payen_btn_select_medicines)
        val txtRefund = findViewById<TextView>(R.id.payen_txt_refund_result)


        val specialties = arrayOf("Cardiology", "Dermatology", "Neurology", "General Medicine")
        spnSpecialty.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, specialties)


        currentId = intent.getStringExtra("id")

        var currentRequest: PayenInsuranceRequest? = null

        if (currentId != null) {
            currentRequest = controller.getById(currentId!!)
            if (currentRequest != null) {
                txtPersonId.setText(currentRequest.personId)
                txtPersonName.setText(currentRequest.personName)
                txtPersonEmail.setText(currentRequest.personEmail)

                txtAppointmentDate.setText(currentRequest.appointmentDate)
                txtAppointmentTime.setText(currentRequest.appointmentTime)
                txtRequestDate.setText(currentRequest.requestDate)
                txtRequestTime.setText(currentRequest.requestTime)

                txtAppointmentAmount.setText(currentRequest.appointmentAmount.toString())
                txtMedicinesAmount.setText(currentRequest.medicinesTotalAmount.toString())

                selectedMedicines = currentRequest.medicinesList
            }
        }



        txtAppointmentDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, y, m, d ->
                txtAppointmentDate.setText("$d/${m + 1}/$y")
            }, year, month, day).show()
        }

        txtRequestDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, y, m, d ->
                txtRequestDate.setText("$d/${m + 1}/$y")
            }, year, month, day).show()
        }



        txtAppointmentTime.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            TimePickerDialog(this, { _, h, m ->
                txtAppointmentTime.setText(String.format("%02d:%02d", h, m))
            }, hour, minute, true).show()
        }

        txtRequestTime.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            TimePickerDialog(this, { _, h, m ->
                txtRequestTime.setText(String.format("%02d:%02d", h, m))
            }, hour, minute, true).show()
        }


        btnSelectMedicines.setOnClickListener {
            val checkedItems = BooleanArray(medicinesOptions.size)

            selectedMedicines.forEach { selected ->
                val index = medicinesOptions.indexOf(selected)
                if (index >= 0) checkedItems[index] = true
            }

            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Select Medicines")

            builder.setMultiChoiceItems(medicinesOptions, checkedItems) { _, which, isChecked ->
                if (isChecked) {
                    selectedMedicines.add(medicinesOptions[which])
                } else {
                    selectedMedicines.remove(medicinesOptions[which])
                }
            }

            builder.setPositiveButton("OK") { _, _ -> }
            builder.show()
        }


        val btnSave = findViewById<Button>(R.id.payen_btn_update)
        val btnDelete = findViewById<Button>(R.id.payen_btn_delete)

        btnSave.setOnClickListener {


            if (txtPersonId.text.isEmpty() ||
                txtPersonName.text.isEmpty() ||
                txtPersonEmail.text.isEmpty()
            ) {
                Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val appointmentAmount = txtAppointmentAmount.text.toString().toDoubleOrNull() ?: 0.0
            val medicinesAmount = txtMedicinesAmount.text.toString().toDoubleOrNull() ?: 0.0

            val refund = (appointmentAmount + medicinesAmount) * 0.80
            txtRefund.text = "₡${String.format("%.2f", refund)}"

            val request = currentRequest ?: PayenInsuranceRequest().apply {
                ID = System.currentTimeMillis().toString()
            }

            request.personId = txtPersonId.text.toString()
            request.personName = txtPersonName.text.toString()
            request.personEmail = txtPersonEmail.text.toString()

            request.specialty = spnSpecialty.selectedItem.toString()

            request.appointmentDate = txtAppointmentDate.text.toString()
            request.appointmentTime = txtAppointmentTime.text.toString()
            request.requestDate = txtRequestDate.text.toString()
            request.requestTime = txtRequestTime.text.toString()

            request.appointmentAmount = appointmentAmount
            request.medicinesTotalAmount = medicinesAmount
            request.medicinesList = selectedMedicines

            if (currentId == null) {
                controller.add(request)
                Toast.makeText(this, "Saved.", Toast.LENGTH_SHORT).show()
            } else {
                controller.update(request)
                Toast.makeText(this, "Updated.", Toast.LENGTH_SHORT).show()
            }

            finish()
        }

        btnDelete.setOnClickListener {
            if (currentId != null) {
                controller.delete(currentId!!)
                Toast.makeText(this, "Deleted.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
