package cr.ac.utn.movil

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.movil.util.util
import android.widget.Toast

class BiddingFormActivity_bid : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bidding_form_bid)

        val txtName = findViewById<EditText>(R.id.txtName_bid)
        val txtFLastName = findViewById<EditText>(R.id.txtFLastName_bid)
        val txtSLastName = findViewById<EditText>(R.id.txtSLastName_bid)
        val txtPhone = findViewById<EditText>(R.id.txtPhone_bid)
        val txtEmail = findViewById<EditText>(R.id.txtEmail_bid)
        val txtAmount = findViewById<EditText>(R.id.txtAmount_bid)
        val txtDateTime = findViewById<EditText>(R.id.txtDateTime_bid)
        val chkAwarded = findViewById<CheckBox>(R.id.chkAwarded_bid)

        val lstArticles = findViewById<ListView>(R.id.lstArticles_bid)
        val btnCancel = findViewById<Button>(R.id.btnCancel_bid)
        val btnSave = findViewById<Button>(R.id.btnSave_bid)


        val articles = resources.getStringArray(R.array.bid_articles_array)


        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_single_choice,
            articles
        )

        lstArticles.adapter = adapter
        lstArticles.choiceMode = ListView.CHOICE_MODE_SINGLE

        // 3. Botón Cancel: simplemente cerramos esta Activity
        btnSave.setOnClickListener {

            // 1. Leer campos
            val name = txtName.text.toString().trim()
            val firstLN = txtFLastName.text.toString().trim()
            val secondLN = txtSLastName.text.toString().trim()
            val phone = txtPhone.text.toString().trim()
            val email = txtEmail.text.toString().trim()
            val amountStr = txtAmount.text.toString().trim()
            val dateTimeStr = txtDateTime.text.toString().trim()
            val awarded = chkAwarded.isChecked

            val pos = lstArticles.checkedItemPosition
            if (pos == -1) {
                Toast.makeText(this, "Please select an article", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val article = lstArticles.adapter.getItem(pos).toString()


            // 2. Validar campos vacíos
            if (name.isEmpty() || firstLN.isEmpty() || secondLN.isEmpty() ||
                phone.isEmpty() || email.isEmpty() || amountStr.isEmpty() ||
                dateTimeStr.isEmpty()) {

                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            // 3. Validar monto numérico
            val amount = amountStr.toDoubleOrNull()
            if (amount == null || amount <= 0) {
                Toast.makeText(this, "Invalid bid amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            // 4. Validar fecha
            val formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val bidDateTime = try {
                java.time.LocalDateTime.parse(dateTimeStr, formatter)
            } catch (e: Exception) {
                Toast.makeText(this, "Invalid date format (yyyy-MM-dd HH:mm)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (bidDateTime.isAfter(java.time.LocalDateTime.now())) {
                Toast.makeText(this, "Date and time cannot be in the future", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }



            // 5. Si todo está bien, seguimos (por ahora solo mostramos que pasó)
            Toast.makeText(
                this,
                "Validation OK!\nReady to save...",
                Toast.LENGTH_LONG
            ).show()
        }


        btnSave.setOnClickListener {

            // 1. Leer datos personales
            val name = txtName.text.toString().trim()
            val firstLN = txtFLastName.text.toString().trim()
            val secondLN = txtSLastName.text.toString().trim()
            val phone = txtPhone.text.toString().trim()
            val email = txtEmail.text.toString().trim()

            // 2. Leer datos de la puja
            val amount = txtAmount.text.toString().trim()
            val dateTime = txtDateTime.text.toString().trim()

            // 3. Leer si está adjudicado
            val awarded = chkAwarded.isChecked

            // 4. Leer artículo seleccionado
            val pos = lstArticles.checkedItemPosition
            val article = if (pos != -1) {
                lstArticles.adapter.getItem(pos).toString()
            } else {
                "(none selected)"
            }

            // 5. Mostrar los datos capturados
            Toast.makeText(
                this,
                "Name: $name\nFirst LN: $firstLN\nSecond LN: $secondLN\nPhone: $phone\nEmail: $email\nAmount: $amount\nDateTime: $dateTime\nArticle: $article\nAwarded: $awarded",
                Toast.LENGTH_LONG
            ).show()
        }


        // Más adelante aquí agregaremos el código del botón SAVE BID
        // para validar datos y guardar la puja en MemoryDataManager
    }
}
