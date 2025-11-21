package cr.ac.utn.movil

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import controller.sin_SinpeController
import identities.sin_Sinpe
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class sin_SinpeActivity : AppCompatActivity() {
    private lateinit var txtOriginName: EditText
    private lateinit var txtOriginPhone: EditText
    private lateinit var txtDestinationName: EditText
    private lateinit var txtDestinationPhone: EditText
    private lateinit var txtAmount: EditText
    private lateinit var txtDescription: EditText
    private lateinit var txtDate: EditText
    private lateinit var txtTime: EditText
    private lateinit var btnSave: Button
    private lateinit var txtPreviewTitle: TextView
    private lateinit var txtPreviewBody: TextView

    private lateinit var sinpeController: sin_SinpeController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sin_sinpe)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sinpeController = sin_SinpeController(this)

        txtOriginName = findViewById(R.id.txtOriginName_sinpe)
        txtOriginPhone = findViewById(R.id.txtOriginPhone_sinpe)
        txtDestinationName = findViewById(R.id.txtDestinationName_sinpe)
        txtDestinationPhone = findViewById(R.id.txtDestinationPhone_sinpe)
        txtAmount = findViewById(R.id.txtAmount_sinpe)
        txtDescription = findViewById(R.id.txtDescription_sinpe)
        txtDate = findViewById(R.id.txtDate_sinpe)
        txtTime = findViewById(R.id.txtTime_sinpe)
        btnSave = findViewById(R.id.btnSave_sinpe)

        txtPreviewTitle = findViewById(R.id.txtPreviewTitle_sinpe)
        txtPreviewBody = findViewById(R.id.txtPreviewBody_sinpe)

        setNowToFields()

        btnSave.setOnClickListener {
            saveSinpe()
        }
    }
    private fun setNowToFields() {
        val now = LocalDateTime.now()
        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

        txtDate.setText(now.format(dateFormatter))
        txtTime.setText(now.format(timeFormatter))
    }

    private fun saveSinpe() {
        try {
            val originName = txtOriginName.text.toString().trim()
            val originPhone = txtOriginPhone.text.toString().trim()
            val destName = txtDestinationName.text.toString().trim()
            val destPhone = txtDestinationPhone.text.toString().trim()
            val amountText = txtAmount.text.toString().trim()
            val description = txtDescription.text.toString().trim()
            val dateText = txtDate.text.toString().trim()
            val timeText = txtTime.text.toString().trim()

            if (originName.isEmpty() || originPhone.isEmpty() ||
                destName.isEmpty() || destPhone.isEmpty() ||
                amountText.isEmpty() || dateText.isEmpty() || timeText.isEmpty()
            ) {
                Toast.makeText(
                    this,
                    getString(R.string.error_empty_fields),
                    Toast.LENGTH_LONG
                ).show()
                return
            }

            val amount = amountText.toDoubleOrNull()
            if (amount == null) {
                Toast.makeText(
                    this,
                    getString(R.string.error_amount_invalid),
                    Toast.LENGTH_LONG
                ).show()
                return
            }

            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
            val dateTime = LocalDateTime.parse("$dateText $timeText", formatter)

            val sinpe = sin_Sinpe().apply {
                this.OriginName = originName
                this.OriginPhone = originPhone
                this.DestinationName = destName
                this.DestinationPhone = destPhone
                this.Amount = amount
                this.Description = description
                this.DateTime = dateTime
            }

            sinpeController.addSinpe(sinpe)

            showPreview(sinpe)

            Toast.makeText(
                this,
                getString(R.string.msg_sinpe_saved),
                Toast.LENGTH_LONG
            ).show()

        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun showPreview(sinpe: sin_Sinpe) {
        val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")

        txtPreviewTitle.text = getString(R.string.preview_title)

        val body = """
            ${getString(R.string.preview_body_1)} ${sinpe.OriginName} 
            ${getString(R.string.preview_body_2)} ${sinpe.DestinationPhone} 
            ${getString(R.string.preview_body_3)} ${sinpe.DestinationName}
            ${getString(R.string.preview_reference)} ${sinpe.ID}
            ${getString(R.string.preview_date)} ${sinpe.DateTime.format(dateFormatter)}
            ${getString(R.string.preview_time)} ${sinpe.DateTime.format(timeFormatter)}
            ${getString(R.string.preview_amount)} ₡${sinpe.Amount}
            ${getString(R.string.preview_detail)} ${if (sinpe.Description.isEmpty()) getString(R.string.preview_no_detail) else sinpe.Description}
        """.trimIndent()

        txtPreviewBody.text = body
    }
}