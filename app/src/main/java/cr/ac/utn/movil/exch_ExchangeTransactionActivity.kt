package cr.ac.utn.movil

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import controller.exch_ExTransactionController
import java.time.LocalDateTime
import util.util
import java.time.LocalDate

class exch_ExchangeTransactionActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var exchangeController: exch_ExTransactionController
    private lateinit var exch_back_button: ImageButton
    private lateinit var exch_save_button: ImageButton
    private lateinit var exch_edit_button: ImageButton
    private lateinit var exch_delete_button: ImageButton
    private lateinit var exch_cancel_button: ImageButton
    private lateinit var exch_Search_button: ImageButton
    private lateinit var exch_txtId: EditText
    private lateinit var exch_txtCompleteName: EditText
    private lateinit var exch_txtPersonId: EditText
    private lateinit var exch_btnSelectDate: ImageButton
    private lateinit var exch_tvDateSelected: TextView
    private lateinit var exch_chkCRCtoUSD: CheckBox
    private lateinit var exch_chkUSDtoCRC: CheckBox
    private lateinit var exch_chkRateCRC: CheckBox
    private lateinit var exch_chkRateUSD: CheckBox
    private lateinit var exch_txtAmountToExchange: EditText
    private lateinit var exch_spnBankEntity: Spinner
    private var year: Int = LocalDateTime.now().year
    private var month: Int = LocalDateTime.now().monthValue - 1 // DatePicker = 0-11
    private var day: Int = LocalDateTime.now().dayOfMonth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_exch_exchange_transaction)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        exchangeController = exch_ExTransactionController(this)
        exch_back_button = findViewById(R.id.exch_back_button)
        exch_save_button = findViewById(R.id.exch_save_button)
        exch_edit_button = findViewById(R.id.exch_edit_button)
        exch_delete_button = findViewById(R.id.exch_delete_button)
        exch_cancel_button = findViewById(R.id.exch_cancel_button)
        exch_Search_button = findViewById(R.id.exch_Search_button)
        exch_txtId = findViewById(R.id.exch_txtId)
        exch_txtCompleteName = findViewById(R.id.exch_txtCompleteName)
        exch_txtPersonId = findViewById(R.id.exch_txtPersonId)
        exch_btnSelectDate = findViewById(R.id.exch_btnSelectDate)
        exch_tvDateSelected = findViewById(R.id.exch_tvDateSelected)
        exch_chkCRCtoUSD = findViewById(R.id.exch_chkCRCtoUSD)
        exch_chkUSDtoCRC = findViewById(R.id.exch_chkUSDtoCRC)
        exch_chkRateCRC = findViewById(R.id.exch_chkRateCRC)
        exch_chkRateUSD = findViewById(R.id.exch_chkRateUSD)
        exch_txtAmountToExchange = findViewById(R.id.exch_txtAmountToExchange)
        exch_spnBankEntity = findViewById(R.id.exch_spnBankEntity)


        exch_back_button.setOnClickListener(View.OnClickListener{view ->
            util.openActivity(this, MainActivity::class.java)
        })

        exch_btnSelectDate.setOnClickListener(View.OnClickListener{view ->
            showDatePickerDialog()
        })

    }
    private fun getDateFormatString(dayOfMonth: Int, monthValue: Int,yearValue: Int): String{
        return "${if (dayOfMonth < 10) "0" else "" }$dayOfMonth/${if (monthValue < 10) "0" else "" }$monthValue/$yearValue"
    }
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val formattedDate = getDateFormatString(dayOfMonth, month + 1, year)
        exch_tvDateSelected.text = formattedDate
    }
    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(this, this, year, month, day)
        datePickerDialog.show()
    }

    private fun invalidationData(): Boolean {
        val idValid = exch_txtId.text.toString().trim().isNotEmpty()
        val nameValid = exch_txtCompleteName.text.toString().trim().isNotEmpty()
        val personIdValid = exch_txtPersonId.text.toString().trim().isNotEmpty()
        val dateText = exch_tvDateSelected.text.toString().trim()
        val dateValid = dateText.isNotEmpty()
        val parsedDate = util.parseStringToDateModern(dateText, "dd/MM/yyyy")
        val dateNotFuture = parsedDate != null && !parsedDate.isAfter(LocalDate.now())
        val operationValid = exch_chkCRCtoUSD.isChecked || exch_chkUSDtoCRC.isChecked
        val rateValid = exch_chkRateCRC.isChecked || exch_chkRateUSD.isChecked
        val amountText = exch_txtAmountToExchange.text.toString().trim()
        val amountValid = amountText.isNotEmpty() && amountText.toDoubleOrNull() != null
        val bankValid = exch_spnBankEntity.selectedItem != null
        return idValid && nameValid && personIdValid &&
               dateValid && dateNotFuture && operationValid &&
               rateValid && amountValid && bankValid
    }



}