package cr.ac.utn.movil

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.movil.controller.EventController
import cr.ac.utn.movil.identities.Event

class EventosActivity : AppCompatActivity() {

    private lateinit var controller: EventController
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos)

        controller = EventController()


        val etId = findViewById<EditText>(R.id.etStudentId_evt)
        val etName = findViewById<EditText>(R.id.etName_evt)
        val etFLast = findViewById<EditText>(R.id.etFirstLastName_evt)
        val etSLast = findViewById<EditText>(R.id.etSecondLastName_evt)
        val etPhone = findViewById<EditText>(R.id.etPhone_evt)
        val etEmail = findViewById<EditText>(R.id.etEmail_evt)
        val etAddress = findViewById<EditText>(R.id.etAddress_evt)
        val etCountry = findViewById<EditText>(R.id.etCountry_evt)

        val etInstitution = findViewById<EditText>(R.id.etInstitution_evt)
        val etLocation = findViewById<EditText>(R.id.etEventLocation_evt)
        val etDate = findViewById<EditText>(R.id.etEventDate_evt)
        val etTime = findViewById<EditText>(R.id.etEventTime_evt)
        val etSeat = findViewById<EditText>(R.id.etSeatNumber_evt)


        val spType = findViewById<Spinner>(R.id.spEventType_evt)
        spType.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            listOf("Theater", "Cinema", "Concert")
        )


        val btnSearch = findViewById<Button>(R.id.btnSearch_evt)
        val btnClear = findViewById<Button>(R.id.btnClear_evt)
        val btnSave = findViewById<Button>(R.id.btnSave_evt)     // <-- NEW
        val btnBack = findViewById<Button>(R.id.btnBack_evt)     // <-- NEW


        val lv = findViewById<ListView>(R.id.lvEvents_evt)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList())
        lv.adapter = adapter

        refreshList()

        btnBack.setOnClickListener { finish() }

        btnSave.setOnClickListener {

            if (etId.text.toString().isBlank()) {
                Toast.makeText(this, "ID Required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val exists = controller.getById(etId.text.toString())

            if (exists != null) {
                Toast.makeText(this, "ID already exists, use Search to update", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val event = Event().apply {
                ID = etId.text.toString()
                Name = etName.text.toString()
                FLastName = etFLast.text.toString()
                SLastName = etSLast.text.toString()
                Phone = etPhone.text.toString().toIntOrNull() ?: 0
                Email = etEmail.text.toString()
                Address = etAddress.text.toString()
                Country = etCountry.text.toString()

                Institution = etInstitution.text.toString()
                EventLocation = etLocation.text.toString()
                EventDate = etDate.text.toString()
                EventTime = etTime.text.toString()
                SeatNumber = etSeat.text.toString().toIntOrNull() ?: 0
                EventType = spType.selectedItem.toString()
            }

            val success = controller.add(event)

            if (!success) {
                Toast.makeText(this, "Seat already assigned for this event", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Event saved", Toast.LENGTH_SHORT).show()
                refreshList()
            }
        }

        btnSearch.setOnClickListener {

            if (etId.text.toString().isBlank()) {
                Toast.makeText(this, "ID Required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val obj = controller.getById(etId.text.toString())

            if (obj == null) {
                // Insert (your original logic)
                val event = Event().apply {
                    ID = etId.text.toString()
                    Name = etName.text.toString()
                    FLastName = etFLast.text.toString()
                    SLastName = etSLast.text.toString()
                    Phone = etPhone.text.toString().toIntOrNull() ?: 0
                    Email = etEmail.text.toString()
                    Address = etAddress.text.toString()
                    Country = etCountry.text.toString()

                    Institution = etInstitution.text.toString()
                    EventLocation = etLocation.text.toString()
                    EventDate = etDate.text.toString()
                    EventTime = etTime.text.toString()
                    SeatNumber = etSeat.text.toString().toIntOrNull() ?: 0
                    EventType = spType.selectedItem.toString()
                }

                val success = controller.add(event)

                if (!success) {
                    Toast.makeText(this, "Seat already assigned for this event", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Event saved", Toast.LENGTH_SHORT).show()
                    refreshList()
                }

            } else {
                // Update (your original logic)
                obj.Name = etName.text.toString()
                obj.FLastName = etFLast.text.toString()
                obj.SLastName = etSLast.text.toString()
                obj.Phone = etPhone.text.toString().toIntOrNull() ?: 0
                obj.Email = etEmail.text.toString()
                obj.Address = etAddress.text.toString()
                obj.Country = etCountry.text.toString()

                obj.Institution = etInstitution.text.toString()
                obj.EventLocation = etLocation.text.toString()
                obj.EventDate = etDate.text.toString()
                obj.EventTime = etTime.text.toString()
                obj.SeatNumber = etSeat.text.toString().toIntOrNull() ?: 0
                obj.EventType = spType.selectedItem.toString()

                val success = controller.update(obj)

                if (!success) {
                    Toast.makeText(this, "Seat already used by another person", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Event updated", Toast.LENGTH_SHORT).show()
                    refreshList()
                }
            }
        }


        btnClear.setOnClickListener {
            etId.setText("")
            etName.setText("")
            etFLast.setText("")
            etSLast.setText("")
            etPhone.setText("")
            etEmail.setText("")
            etAddress.setText("")
            etCountry.setText("")
            etInstitution.setText("")
            etLocation.setText("")
            etDate.setText("")
            etTime.setText("")
            etSeat.setText("")
            spType.setSelection(0)
        }
    }

    private fun refreshList() {
        adapter.clear()
        adapter.addAll(controller.getAll().map { it.FullDescription })
        adapter.notifyDataSetChanged()
    }
}
