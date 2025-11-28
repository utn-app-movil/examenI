package cr.ac.utn.movil.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.movil.R
import cr.ac.utn.movil.controller.Recy_RecyclingController
import entity.Recy_RecyclingEntity

class Recy_RecyclingActivity : AppCompatActivity() {

    private val controller = Recy_RecyclingController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycling_main)

        val txtMaterial = findViewById<EditText>(R.id.recy_input_material)
        val txtWeight = findViewById<EditText>(R.id.recy_input_weight)
        val txtDate = findViewById<EditText>(R.id.recy_input_date)

        val btnSave = findViewById<Button>(R.id.recy_btn_save)
        val btnSearch = findViewById<Button>(R.id.recy_btn_search)
        val btnUpdate = findViewById<Button>(R.id.recy_btn_update)
        val btnDelete = findViewById<Button>(R.id.recy_btn_delete)

        // SAVE
        btnSave.setOnClickListener {

            val material = txtMaterial.text.toString()
            val weight = txtWeight.text.toString()
            val date = txtDate.text.toString()

            if (material.isBlank() || weight.isBlank() || date.isBlank()) {
                Toast.makeText(this, "Complete all fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val entity = Recy_RecyclingEntity(
                campaignName = "Default",
                materialType = material,
                quantityKg = weight.toDouble(),
                date = date,
                personName = "Anon",
                companyName = "None"
            )

            controller.add(entity)
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
        }

        // SEARCH
        btnSearch.setOnClickListener {
            val material = txtMaterial.text.toString()
            val result = controller.getByMaterial(material)

            if (result != null) {
                txtWeight.setText(result.quantityKg.toString())
                txtDate.setText(result.date)
            } else {
                Toast.makeText(this, "Not found.", Toast.LENGTH_SHORT).show()
            }
        }

        // UPDATE
        btnUpdate.setOnClickListener {
            val entity = Recy_RecyclingEntity(
                campaignName = "Default",
                materialType = txtMaterial.text.toString(),
                quantityKg = txtWeight.text.toString().toDouble(),
                date = txtDate.text.toString(),
                personName = "Anon",
                companyName = "None"
            )

            controller.update(entity)
            Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show()
        }

        // DELETE
        btnDelete.setOnClickListener {
            controller.delete(txtMaterial.text.toString())
            Toast.makeText(this, "Deleted.", Toast.LENGTH_SHORT).show()
        }
    }
}
