package cr.ac.utn.movil.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.app.AlertDialog
import android.widget.ArrayAdapter
import android.widget.Toast
import cr.ac.utn.movil.R
import cr.ac.utn.movil.controller.RentController
import cr.ac.utn.movil.identities.RentEntity

class RentActivity : AppCompatActivity() {

    private val controller = RentController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_main)

        val btnAdd = findViewById<Button>(R.id.rent_btn_add)
        val btnShow = findViewById<Button>(R.id.rent_btn_show)

        btnAdd.setOnClickListener {
            openAddDialog()
        }

        btnShow.setOnClickListener {
            openListDialog()
        }
    }

    // CREATE
    private fun openAddDialog() {

        val dialogView = layoutInflater.inflate(R.layout.dialog_rent_form, null)

        val inputName = dialogView.findViewById<EditText>(R.id.rent_input_client_name)
        val inputId = dialogView.findViewById<EditText>(R.id.rent_input_client_id)
        val spinnerType = dialogView.findViewById<Spinner>(R.id.rent_spinner_vehicle_type)
        val inputPlate = dialogView.findViewById<EditText>(R.id.rent_input_plate)
        val inputMileage = dialogView.findViewById<EditText>(R.id.rent_input_mileage)
        val inputLicense = dialogView.findViewById<EditText>(R.id.rent_input_license)

        val vehicleTypes = listOf(
            getString(R.string.rent_type_hatchback),
            getString(R.string.rent_type_sedan),
            getString(R.string.rent_type_suv),
            getString(R.string.rent_type_microbus)
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            vehicleTypes
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerType.adapter = adapter

        val dialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.rent_title_form))
            .setView(dialogView)
            .setPositiveButton(getString(R.string.rent_btn_save)) { _, _ ->

                val rent = RentEntity()
                rent.ID = System.currentTimeMillis().toString()
                rent.ClientName = inputName.text.toString()
                rent.ClientId = inputId.text.toString()
                rent.VehicleType = spinnerType.selectedItem.toString()
                rent.Plate = inputPlate.text.toString()
                rent.Mileage = inputMileage.text.toString().toIntOrNull() ?: 0
                rent.License = inputLicense.text.toString()

                controller.addRent(rent)

                Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(getString(R.string.rent_btn_cancel), null)
            .create()

        dialog.show()
    }

    // READ - LISTA DE RENTAS
    private fun openListDialog() {

        val rents = controller.getAllRents()

        if (rents.isEmpty()) {
            Toast.makeText(this, getString(R.string.rent_empty_list), Toast.LENGTH_SHORT).show()
            return
        }

        val rentDescriptions = rents.map { it.FullDescription }

        val dialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.rent_title_list))
            .setItems(rentDescriptions.toTypedArray()) { _, index ->
                val rent = rents[index] as RentEntity
                openEditDialog(rent)
            }
            .setNegativeButton(getString(R.string.rent_btn_cancel), null)
            .create()

        dialog.show()
    }

    // UPDATE & DELETE - EDITAR UNA RENTA EXISTENTE

    private fun openEditDialog(rent: RentEntity) {

        val dialogView = layoutInflater.inflate(R.layout.dialog_rent_form, null)

        val inputName = dialogView.findViewById<EditText>(R.id.rent_input_client_name)
        val inputId = dialogView.findViewById<EditText>(R.id.rent_input_client_id)
        val spinnerType = dialogView.findViewById<Spinner>(R.id.rent_spinner_vehicle_type)
        val inputPlate = dialogView.findViewById<EditText>(R.id.rent_input_plate)
        val inputMileage = dialogView.findViewById<EditText>(R.id.rent_input_mileage)
        val inputLicense = dialogView.findViewById<EditText>(R.id.rent_input_license)

        // Llenar datos existentes
        inputName.setText(rent.ClientName)
        inputId.setText(rent.ClientId)
        inputPlate.setText(rent.Plate)
        inputMileage.setText(rent.Mileage.toString())
        inputLicense.setText(rent.License)

        val vehicleTypes = listOf(
            getString(R.string.rent_type_hatchback),
            getString(R.string.rent_type_sedan),
            getString(R.string.rent_type_suv),
            getString(R.string.rent_type_microbus)
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            vehicleTypes
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerType.adapter = adapter

        val selectedIndex = vehicleTypes.indexOf(rent.VehicleType)
        if (selectedIndex >= 0) spinnerType.setSelection(selectedIndex)

        val dialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.rent_btn_update))
            .setView(dialogView)
            .setPositiveButton(getString(R.string.rent_btn_update)) { _, _ ->

                rent.ClientName = inputName.text.toString()
                rent.ClientId = inputId.text.toString()
                rent.VehicleType = spinnerType.selectedItem.toString()
                rent.Plate = inputPlate.text.toString()
                rent.Mileage = inputMileage.text.toString().toIntOrNull() ?: 0
                rent.License = inputLicense.text.toString()

                controller.updateRent(rent)

                Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(getString(R.string.rent_btn_delete)) { _, _ ->

                controller.deleteRent(rent.ID)

                Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show()
            }
            .setNeutralButton(getString(R.string.rent_btn_cancel), null)
            .create()

        dialog.show()
    }
}
