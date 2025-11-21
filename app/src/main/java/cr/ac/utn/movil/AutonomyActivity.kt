package cr.ac.utn.movil.autonomy

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.movil.R
import cr.ac.utn.movil.controllers.AutonomyController
import cr.ac.utn.movil.identities.Autonomy

class AutonomyActivity : AppCompatActivity() {

    lateinit var txtVehicleId: EditText
    lateinit var txtChargeDate: EditText
    lateinit var txtRange: EditText
    lateinit var txtBatteryInitial: EditText
    lateinit var txtBatteryFinal: EditText
    lateinit var txtBrand: EditText
    lateinit var txtOwner: EditText
    lateinit var spinnerType: Spinner

    lateinit var btnSave: Button
    lateinit var btnUpdate: Button
    lateinit var btnDelete: Button
    lateinit var btnClear: Button

    lateinit var recycler: RecyclerView
    private lateinit var controller: AutonomyController
    private lateinit var adapter: AutonomyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_autonomy)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.autonomy_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        controller = AutonomyController()
        adapter = AutonomyAdapter()

        // --- Bind UI ---
        txtVehicleId = findViewById(R.id.autonomy_edit_vehicle_id)
        txtChargeDate = findViewById(R.id.autonomy_edit_charge_date)
        txtRange = findViewById(R.id.autonomy_edit_estimated_range)
        txtBatteryInitial = findViewById(R.id.autonomy_edit_battery_initial)
        txtBatteryFinal = findViewById(R.id.autonomy_edit_battery_final)
        txtBrand = findViewById(R.id.autonomy_edit_brand)
        txtOwner = findViewById(R.id.autonomy_edit_owner)
        spinnerType = findViewById(R.id.autonomy_spinner_type)

        btnSave = findViewById(R.id.autonomy_btn_save)
        btnUpdate = findViewById(R.id.autonomy_btn_update)
        btnDelete = findViewById(R.id.autonomy_btn_delete)
        btnClear = findViewById(R.id.autonomy_btn_clear)

        recycler = findViewById(R.id.autonomy_recycler_records)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        val types = listOf(
            getString(R.string.TextAutonomyTypeSedan),
            getString(R.string.TextAutonomyTypeCargo),
            getString(R.string.TextAutonomyTypeSUV),
            getString(R.string.TextAutonomyTypeVan)
        )
        spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, types)

        btnSave.setOnClickListener { saveAutonomy() }
        btnUpdate.setOnClickListener { updateAutonomy() }
        btnDelete.setOnClickListener { deleteAutonomy() }
        btnClear.setOnClickListener { clearFields() }

        refreshList()
    }

    private fun validationData(entity: Autonomy): Boolean {
        return try {
            controller.create(entity) // validation inside controller
            true
        } catch (e: IllegalArgumentException) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            false
        }
    }

    private fun saveAutonomy() {
        try {
            val autonomy = buildAutonomy()
            if (validationData(autonomy)) {
                Toast.makeText(this, getString(R.string.MsgAutonomySaved), Toast.LENGTH_SHORT).show()
                refreshList()
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun updateAutonomy() {
        try {
            val autonomy = buildAutonomy()
            controller.update(autonomy)
            Toast.makeText(this, getString(R.string.MsgAutonomyUpdated), Toast.LENGTH_SHORT).show()
            refreshList()
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteAutonomy() {
        val id = txtVehicleId.text.toString()
        if (id.isNotBlank()) {
            controller.delete(id)
            Toast.makeText(this, getString(R.string.MsgAutonomyDeleted), Toast.LENGTH_SHORT).show()
            refreshList()
        }
    }

    private fun clearFields() {
        txtVehicleId.text.clear()
        txtChargeDate.text.clear()
        txtRange.text.clear()
        txtBatteryInitial.text.clear()
        txtBatteryFinal.text.clear()
        txtBrand.text.clear()
        txtOwner.text.clear()
        spinnerType.setSelection(0)
    }

    private fun buildAutonomy(): Autonomy {
        val autonomy = Autonomy()
        autonomy.VehicleId = txtVehicleId.text.toString()
        autonomy.ChargeDate = txtChargeDate.text.toString()
        autonomy.EstimatedRangeKm = txtRange.text.toString().toIntOrNull() ?: 0
        autonomy.BatteryInitialPercent = txtBatteryInitial.text.toString().toIntOrNull() ?: 0
        autonomy.BatteryFinalPercent = txtBatteryFinal.text.toString().toIntOrNull() ?: 0
        autonomy.VehicleBrand = txtBrand.text.toString()
        autonomy.VehicleOwner = txtOwner.text.toString()
        autonomy.VehicleType = spinnerType.selectedItem.toString()
        return autonomy
    }

    private fun refreshList() {
        val data = controller.list()
        adapter.setData(data)
    }
}
