package cr.ac.utn.movil

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import controller.exch_ExTransactionController
import identities.exch_ExchangeTransaction
import java.time.LocalDateTime
import cr.ac.utn.movil.util.util
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
    private lateinit var exch_tvStatus: TextView
    private var isEditMode: Boolean=false



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
        exch_tvStatus = findViewById(R.id.exch_tvStatus)



        exch_back_button.setOnClickListener(View.OnClickListener{view ->
            util.openActivity(this, MainActivity::class.java)
        })
        exch_btnSelectDate.setOnClickListener(View.OnClickListener{view ->
            showDatePickerDialog()
        })
        exch_cancel_button.setOnClickListener(View.OnClickListener{view ->
            cleanScreen()
        })
        exch_save_button.setOnClickListener {
            util.showDialogCondition(this, getString(R.string.TextSaveActionQuestion)) {saveTransaction()}
        }
        exch_edit_button.setOnClickListener {
            val id = exch_txtId.text.toString().trim()

            if (id.isNotEmpty()) {
                isEditMode = true
                util.showDialogCondition(this, getString(R.string.TextUpdateActionQuestion)) { saveTransaction() }
            } else {
                Toast.makeText(this, getString(R.string.MsgDataNoFound),Toast.LENGTH_SHORT).show()
                exch_txtId.requestFocus()
            }
        }

        exch_delete_button.setOnClickListener {
            val id = exch_txtId.text.toString().trim()

            if (id.isNotEmpty()) {
                util.showDialogCondition(this, getString(R.string.TextDeleteActionQuestion)) { deleteTransaction() }
            } else {
                Toast.makeText(this, getString(R.string.MsgDataNoFound), Toast.LENGTH_SHORT).show()
                exch_txtId.requestFocus()
            }
        }
        exch_Search_button.setOnClickListener {
            val id = exch_txtId.text.toString().trim()
            if (id.isNotEmpty()) {
                searchTransaction(id)
            } else {
                Toast.makeText(this, getString(R.string.MsgDataNoFound), Toast.LENGTH_SHORT).show()
                exch_txtId.requestFocus()
            }
        }

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

        val dateParsed = util.parseStringToDateModern(
            exch_tvDateSelected.text.toString().trim(),
            "dd/MM/yyyy"
        )
        val isValidDate = dateParsed != null && !dateParsed.isAfter(LocalDate.now())
        val hasOperationType =
            exch_chkCRCtoUSD.isChecked || exch_chkUSDtoCRC.isChecked
        val hasExchangeRate =
            exch_chkRateCRC.isChecked || exch_chkRateUSD.isChecked
        val amountValid = exch_txtAmountToExchange.text.toString().trim().isNotEmpty()
        return exch_txtId.text.toString().trim().isNotEmpty() &&
                exch_txtCompleteName.text.toString().trim().isNotEmpty() &&
                exch_txtPersonId.text.toString().trim().isNotEmpty() &&
                isValidDate &&
                hasOperationType &&
                hasExchangeRate &&
                amountValid
    }

    private fun saveTransaction() {
        try {
            if (!invalidationData()) {
                Toast.makeText(this, getString(R.string.exch_msg_incomplete_invalid), Toast.LENGTH_LONG).show()
                return
            }

            val transaction = exch_ExchangeTransaction()
            transaction.ID = exch_txtId.text.toString().trim()
            transaction.exch_PersonName = exch_txtCompleteName.text.toString().trim()
            transaction.exch_PersonId = exch_txtPersonId.text.toString().trim()
            val parsedDate = util.parseStringToDateModern(exch_tvDateSelected.text.toString().trim(), "dd/MM/yyyy"
            )
            transaction.exch_DateTime = parsedDate!!.atStartOfDay()
            transaction.exch_OperationType =
                if (exch_chkCRCtoUSD.isChecked) "CRC → USD" else "USD → CRC"

            transaction.exch_ExchangeRate =
                if (exch_chkRateCRC.isChecked) 540.0 else 1.0

            val amount = exch_txtAmountToExchange.text.toString().toDouble()
            transaction.AmountToExchange = amount
            transaction.exch_BankEntity = exch_spnBankEntity.selectedItem.toString()

            val finalAmount = if (transaction.exch_OperationType == "CRC → USD") {
                amount / transaction.exch_ExchangeRate
            } else {
                amount * transaction.exch_ExchangeRate
            }

            val formatted = String.format("%.2f", finalAmount)
            Toast.makeText(this, getString(R.string.exch_msg_amount_to_pay, formatted), Toast.LENGTH_LONG).show()


            if (exchangeController.getTransactionById(transaction.ID) == null && !isEditMode)
                exchangeController.addTransaction(transaction)
            else
                exchangeController.updateTransaction(transaction)

            cleanScreen()
            Toast.makeText(this,getString(R.string.exch_msg_saved_successfully), Toast.LENGTH_LONG).show()

        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    private fun searchTransaction(id: String) {
        try {
            val txn = exchangeController.getTransactionById(id)
            if (txn == null) {
                cleanScreen()
                Toast.makeText(this, R.string.MsgDataNoFound, Toast.LENGTH_LONG).show()
                return
            }
            isEditMode = true
            exch_txtId.setText(txn.ID)
            exch_txtId.isEnabled = false
            exch_txtCompleteName.setText(txn.exch_PersonName)
            exch_txtPersonId.setText(txn.exch_PersonId)
            exch_tvDateSelected.text = txn.exch_DateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            if (txn.exch_OperationType == "CRC → USD") {
                exch_chkCRCtoUSD.isChecked = true
            } else {
                exch_chkUSDtoCRC.isChecked = true
            }
            if (txn.exch_ExchangeRate == 540.0)
                exch_chkRateCRC.isChecked = true
            else
                exch_chkRateUSD.isChecked = true
            exch_txtAmountToExchange.setText(txn.AmountToExchange.toString())
            val adapter = exch_spnBankEntity.adapter
            val index = (0 until adapter.count).firstOrNull { adapter.getItem(it) == txn.exch_BankEntity } ?: 0
            exch_spnBankEntity.setSelection(index)

        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }
    private fun deleteTransaction() {
        try {
            exchangeController.removeTransaction(exch_txtId.text.toString().trim())
            cleanScreen()
            Toast.makeText(this, getString(R.string.MsgDeleteSuccess), Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.ErrorMsgRemove), Toast.LENGTH_LONG).show()
        }
    }
    private fun cleanScreen() {
        exch_txtId.setText("")
        exch_txtCompleteName.setText("")
        exch_txtPersonId.setText("")
        exch_tvDateSelected.text = ""
        exch_chkCRCtoUSD.isChecked = false
        exch_chkUSDtoCRC.isChecked = false
        exch_chkRateCRC.isChecked = false
        exch_chkRateUSD.isChecked = false
        exch_txtAmountToExchange.setText("")
        exch_spnBankEntity.setSelection(0)
        exch_txtId.isEnabled = true
    }

}