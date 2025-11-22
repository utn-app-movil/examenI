package cr.ac.utn.movil.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.movil.R
import cr.ac.utn.movil.controllers.vac_SaveResult
import cr.ac.utn.movil.controllers.vac_VaccineController
import cr.ac.utn.movil.identities.vac_Vaccine
import cr.ac.utn.movil.util.EXTRA_ID
import cr.ac.utn.movil.util.util
import java.time.format.DateTimeFormatter

class vac_VaccineActivity : AppCompatActivity() {

    private lateinit var vac_controller: vac_VaccineController

    private lateinit var vac_txtPatientName: EditText
    private lateinit var vac_txtPatientId: EditText
    private lateinit var vac_txtVaccineType: EditText
    private lateinit var vac_txtVaccineSite: EditText
    private lateinit var vac_txtVaccineDateTime: EditText
    private lateinit var vac_lstVaccines: ListView

    private lateinit var vac_adapter: vac_VaccineAdapter

    private var vac_currentId: String? = null

    private val vac_formatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vac_vaccine)

        title = getString(R.string.vac_title)

        vac_controller = vac_VaccineController()

        vac_txtPatientName = findViewById(R.id.vac_txt_patient_name)
        vac_txtPatientId = findViewById(R.id.vac_txt_patient_id)
        vac_txtVaccineType = findViewById(R.id.vac_txt_vaccine_type)
        vac_txtVaccineSite = findViewById(R.id.vac_txt_vaccine_site)
        vac_txtVaccineDateTime = findViewById(R.id.vac_txt_vaccine_datetime)
        vac_lstVaccines = findViewById(R.id.vac_lst_vaccines)

        // Configurar el adaptador
        vac_adapter = vac_VaccineAdapter(this, emptyList())
        vac_lstVaccines.adapter = vac_adapter

        // Configurar el click en los items de la lista
        vac_lstVaccines.setOnItemClickListener { _, _, position, _ ->
            val vaccine = vac_adapter.getItem(position) as vac_Vaccine
            vac_LoadDataToForm(vaccine)
        }

        // Cargar la lista de vacunas
        vac_LoadVaccinesList()

        vac_currentId = intent.getStringExtra(EXTRA_ID)
        if (!vac_currentId.isNullOrBlank()) {
            vac_LoadData(vac_currentId!!)
        }
    }

    private fun vac_LoadVaccinesList() {
        val vaccines = vac_controller.vac_GetAll()
        vac_adapter.updateData(vaccines)
    }

    private fun vac_LoadData(id: String) {
        val data = vac_controller.vac_GetById(id)
        if (data != null) {
            vac_LoadDataToForm(data)
        } else {
            Toast.makeText(this, getString(R.string.MsgDataNoFound), Toast.LENGTH_LONG).show()
        }
    }

    private fun vac_LoadDataToForm(vaccine: vac_Vaccine) {
        vac_currentId = vaccine.ID
        vac_txtPatientName.setText(vaccine.vac_PatientName)
        vac_txtPatientId.setText(vaccine.vac_PatientIdNumber)
        vac_txtVaccineType.setText(vaccine.vac_VaccineType)
        vac_txtVaccineSite.setText(vaccine.vac_VaccineSite)
        vac_txtVaccineDateTime.setText(
            vaccine.vac_VaccineDateTime?.format(vac_formatter) ?: ""
        )
    }

    private fun vac_ClearForm() {
        vac_currentId = null
        vac_txtPatientName.setText("")
        vac_txtPatientId.setText("")
        vac_txtVaccineType.setText("")
        vac_txtVaccineSite.setText("")
        vac_txtVaccineDateTime.setText("")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_crud, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnu_save -> {
                util.showDialogCondition(
                    this,
                    getString(R.string.TextSaveActionQuestion)
                ) {
                    vac_SaveData()
                }
                true
            }

            R.id.mnu_delete -> {
                util.showDialogCondition(
                    this,
                    getString(R.string.TextDeleteActionQuestion)
                ) {
                    vac_DeleteData()
                }
                true
            }

            R.id.mnu_cancel -> {
                vac_ClearForm()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun vac_SaveData() {
        try {
            val dateTimeString = vac_txtVaccineDateTime.text.toString().trim()
            val dateTime = util.parseStringToDateTimeModern(
                dateTimeString,
                "dd/MM/yyyy HH:mm"
            )

            val vaccine = vac_Vaccine().apply {
                ID = vac_currentId ?: ""
                vac_PatientName = vac_txtPatientName.text.toString().trim()
                vac_PatientIdNumber = vac_txtPatientId.text.toString().trim()
                vac_VaccineType = vac_txtVaccineType.text.toString().trim()
                vac_VaccineSite = vac_txtVaccineSite.text.toString().trim()
                vac_VaccineDateTime = dateTime
            }

            val result = vac_controller.vac_Save(vaccine)

            when (result) {
                vac_SaveResult.Success -> {
                    Toast.makeText(
                        this,
                        getString(R.string.MsgSaveSuccess),
                        Toast.LENGTH_LONG
                    ).show()
                    vac_ClearForm()
                    vac_LoadVaccinesList()
                }

                vac_SaveResult.InvalidDateTime -> {
                    Toast.makeText(
                        this,
                        getString(R.string.vac_error_invalid_datetime),
                        Toast.LENGTH_LONG
                    ).show()
                }

                vac_SaveResult.FutureDateTime -> {
                    Toast.makeText(
                        this,
                        getString(R.string.vac_error_future_datetime),
                        Toast.LENGTH_LONG
                    ).show()
                }

                vac_SaveResult.Duplicated -> {
                    Toast.makeText(
                        this,
                        getString(R.string.MsgDuplicateDate),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } catch (ex: Exception) {
            if (vac_currentId.isNullOrBlank()) {
                Toast.makeText(this, getString(R.string.ErrorMsgAdd), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, getString(R.string.ErrorMsgUpdate), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun vac_DeleteData() {
        try {
            val id = vac_currentId
            if (id.isNullOrBlank()) {
                Toast.makeText(
                    this,
                    getString(R.string.MsgDataNoFound),
                    Toast.LENGTH_LONG
                ).show()
                return
            }

            val result = vac_controller.vac_Delete(id)
            if (result) {
                Toast.makeText(
                    this,
                    getString(R.string.MsgDeleteSuccess),
                    Toast.LENGTH_LONG
                ).show()
                vac_ClearForm()
                vac_LoadVaccinesList()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.ErrorMsgRemove),
                    Toast.LENGTH_LONG
                ).show()
            }
        } catch (ex: Exception) {
            Toast.makeText(this, getString(R.string.ErrorMsgRemove), Toast.LENGTH_LONG).show()
        }
    }
}