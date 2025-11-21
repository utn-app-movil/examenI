package cr.ac.utn.movil

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.movil.controller.med_MedCheckingController
import cr.ac.utn.movil.identities.med_NursingControl
import cr.ac.utn.movil.util.EXTRA_ID
import cr.ac.utn.movil.util.util
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class med_MedCheckingActivity : AppCompatActivity() {

    private lateinit var controller: med_MedCheckingController

    private lateinit var txtId: EditText
    private lateinit var txtPatientName: EditText
    private lateinit var txtBloodPressure: EditText
    private lateinit var txtWeight: EditText
    private lateinit var txtHeight: EditText
    private lateinit var txtOxygenation: EditText
    private lateinit var txtDate: EditText
    private lateinit var txtTime: EditText
    private lateinit var btnSearch: Button

    private var isEditMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.med_med_checking)

        controller = med_MedCheckingController()

        txtId = findViewById(R.id.med_txtId)
        txtPatientName = findViewById(R.id.med_txtPatientName)
        txtBloodPressure = findViewById(R.id.med_txtBloodPressure)
        txtWeight = findViewById(R.id.med_txtWeight)
        txtHeight = findViewById(R.id.med_txtHeight)
        txtOxygenation = findViewById(R.id.med_txtOxygenation)
        txtDate = findViewById(R.id.med_txtDate)
        txtTime = findViewById(R.id.med_txtTime)
        btnSearch = findViewById(R.id.med_btnSearch)

        btnSearch.setOnClickListener {
            val idValue = txtId.text.toString().trim()
            if (idValue.isEmpty()) {
                txtId.error = getString(R.string.med_error_required_id)
                return@setOnClickListener
            }

            val entity = controller.getById(idValue)
            if (entity == null) {
                Toast.makeText(this, getString(R.string.MsgDataNoFound), Toast.LENGTH_LONG).show()
                clearForm(keepId = true)
                isEditMode = false
            } else {
                fillForm(entity)
                isEditMode = true
            }
        }

        val idFromIntent = intent.getStringExtra(EXTRA_ID)
        if (!idFromIntent.isNullOrBlank()) {
            txtId.setText(idFromIntent)
            controller.getById(idFromIntent)?.let {
                fillForm(it)
                isEditMode = true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_crud, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnu_save -> {
                util.showDialogCondition(this, getString(R.string.TextSaveActionQuestion)) {
                    saveRecord()
                }
                true
            }
            R.id.mnu_delete -> {
                util.showDialogCondition(this, getString(R.string.TextDeleteActionQuestion)) {
                    deleteRecord()
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


    private fun saveRecord() {
        if (!validateForm()) {
            return
        }

        val entity = buildEntityFromForm()
        val visit = entity.VisitDateTime

        if (visit == null) {
            txtDate.error = getString(R.string.med_error_invalid_datetime)
            txtTime.error = getString(R.string.med_error_invalid_datetime)
            Toast.makeText(
                this,
                getString(R.string.med_error_invalid_datetime),
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (visit.isAfter(LocalDateTime.now())) {
            txtDate.error = getString(R.string.med_error_future_datetime)
            txtTime.error = getString(R.string.med_error_future_datetime)
            Toast.makeText(
                this,
                getString(R.string.med_error_future_datetime),
                Toast.LENGTH_LONG
            ).show()
            return
        }

        val existing = controller.getById(entity.ID)

        if (existing == null) {
            val added = controller.add(entity)
            if (!added) {
                Toast.makeText(this, getString(R.string.MsgDuplicateDate), Toast.LENGTH_LONG).show()
                return
            }
        } else {
            if (!isEditMode) {
                Toast.makeText(this, getString(R.string.MsgDuplicateDate), Toast.LENGTH_LONG).show()
                return
            }
            controller.update(entity)
        }

        Toast.makeText(this, getString(R.string.MsgSaveSuccess), Toast.LENGTH_LONG).show()
        clearForm()
        isEditMode = false
    }

    private fun deleteRecord() {
        val idValue = txtId.text.toString().trim()
        if (idValue.isEmpty()) {
            txtId.error = getString(R.string.med_error_required_id)
            return
        }

        val deleted = controller.remove(idValue)
        if (!deleted) {
            Toast.makeText(this, getString(R.string.MsgDataNoFound), Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, getString(R.string.MsgDeleteSuccess), Toast.LENGTH_LONG).show()
            clearForm()
            isEditMode = false
        }
    }


    private fun validateForm(): Boolean {
        var isValid = true

        if (txtId.text.toString().trim().isEmpty()) {
            txtId.error = getString(R.string.med_error_required_id)
            isValid = false
        }

        if (txtPatientName.text.toString().trim().isEmpty()) {
            txtPatientName.error = getString(R.string.med_error_required_patient_name)
            isValid = false
        }

        val bp = txtBloodPressure.text.toString().trim()
        if (bp.isEmpty()) {
            txtBloodPressure.error = getString(R.string.med_error_required_blood_pressure)
            isValid = false
        } else if (!isValidBloodPressure(bp)) {
            txtBloodPressure.error = getString(R.string.med_error_invalid_blood_pressure)
            isValid = false
        }

        if (txtDate.text.toString().trim().isEmpty()) {
            txtDate.error = getString(R.string.med_error_required_date)
            isValid = false
        }

        if (txtTime.text.toString().trim().isEmpty()) {
            txtTime.error = getString(R.string.med_error_required_time)
            isValid = false
        }

        return isValid
    }

    private fun buildEntityFromForm(): med_NursingControl {
        val entity = med_NursingControl()

        entity.ID = txtId.text.toString().trim()
        entity.PatientName = txtPatientName.text.toString().trim()
        entity.BloodPressure = txtBloodPressure.text.toString().trim()

        val weightValue = txtWeight.text.toString().trim()
        entity.WeightKg = if (weightValue.isEmpty()) 0.0 else weightValue.toDouble()

        val heightValue = txtHeight.text.toString().trim()
        entity.HeightCm = if (heightValue.isEmpty()) 0.0 else heightValue.toDouble()

        val oxygenValue = txtOxygenation.text.toString().trim()
        entity.Oxygenation = if (oxygenValue.isEmpty()) 0 else oxygenValue.toInt()

        val pattern = "dd/MM/yyyy HH:mm"
        val dateTimeText = txtDate.text.toString().trim() + " " + txtTime.text.toString().trim()
        val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
        entity.VisitDateTime = try {
            LocalDateTime.parse(dateTimeText, formatter)
        } catch (e: Exception) {
            null
        }

        return entity
    }

    private fun fillForm(entity: med_NursingControl) {
        txtId.setText(entity.ID)
        txtPatientName.setText(entity.PatientName)
        txtBloodPressure.setText(entity.BloodPressure)
        txtWeight.setText(if (entity.WeightKg == 0.0) "" else entity.WeightKg.toString())
        txtHeight.setText(if (entity.HeightCm == 0.0) "" else entity.HeightCm.toString())
        txtOxygenation.setText(if (entity.Oxygenation == 0) "" else entity.Oxygenation.toString())


        txtDate.error = null
        txtTime.error = null

        entity.VisitDateTime?.let {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.getDefault())
            val dateTimeText = it.format(formatter)
            val parts = dateTimeText.split(" ")
            if (parts.size == 2) {
                txtDate.setText(parts[0])
                txtTime.setText(parts[1])
            }
        }
    }

    private fun clearForm(keepId: Boolean = false) {
        if (!keepId) {
            txtId.setText("")
        }
        txtPatientName.setText("")
        txtBloodPressure.setText("")
        txtWeight.setText("")
        txtHeight.setText("")
        txtOxygenation.setText("")
        txtDate.setText("")
        txtTime.setText("")
        txtDate.error = null
        txtTime.error = null
    }

    private fun isValidBloodPressure(value: String): Boolean {
        val regex = Regex("^\\d{2,3}/\\d{2,3}\$")
        return regex.matches(value)
    }
}
