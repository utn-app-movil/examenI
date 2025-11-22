package cr.ac.utn.movil

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import controller.prod_ProductionOrderController
import identities.prod_ProductionOrder
import java.time.LocalDate
import java.util.Calendar

import cr.ac.utn.movil.util.util

class prod_ProductionOrderActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    lateinit var txtID: TextView
    lateinit var txtNumber: TextView
    lateinit var txtQuantity: TextView
    lateinit var lblOrderDate: TextView
    lateinit var lblDeliveryDate: TextView
    lateinit var spinnerCompanies: Spinner

    private var isEditMode: Boolean = false
    private var isOrderDate = true
    private var day: Int = 0
    private var month: Int = 0
    private var year: Int = 0

    private lateinit var productionOrderController: prod_ProductionOrderController

    private lateinit var menuItemDelete: MenuItem

    private val companies = arrayOf("Walmart", "FreshMarket", "AutoMercado")
    private val products = listOf("Pan", "Queso", "Torta carne", "Salsas", "Cebolla", "Bacon")
    private val selectedProducts = mutableListOf<String>()

    //region Layout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.prod_activity_productionorder)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.TableLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        productionOrderController = prod_ProductionOrderController(this)

        txtID = findViewById(R.id.prod_txtID_order)
        txtNumber = findViewById(R.id.prod_txtNumber_order)
        txtQuantity = findViewById(R.id.prod_txtQuantity_order)
        lblOrderDate = findViewById(R.id.prod_lblDate_order)
        lblDeliveryDate = findViewById(R.id.prod_lblDelivery_order)

        resetDate()

        spinnerCompanies = findViewById(R.id.spinner_companies)
        val adapterCompanies = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            companies
        )

        spinnerCompanies.adapter = adapterCompanies

        val btnSearchId = findViewById<ImageButton>(R.id.prod_btnSearchId_order)
        btnSearchId.setOnClickListener(View.OnClickListener { view ->
            searchOrder(txtID.text.toString())
        })

        val btnSelectedDate = findViewById<ImageButton>(R.id.prod_btnSelectedDate_order)
        btnSelectedDate.setOnClickListener(View.OnClickListener { view ->
            isOrderDate = true
            showDatePickerDialog()
        })

        val btnSelectedDelivery = findViewById<ImageButton>(R.id.prod_btnSelectedDelivery_order)
        btnSelectedDelivery.setOnClickListener(View.OnClickListener { view ->
            isOrderDate = false
            showDatePickerDialog()
        })

        val btnProducts = findViewById<Button>(R.id.prod_btnProducts)
        btnProducts.setOnClickListener {
            showProductSelectionDialog()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        if (isOrderDate)
            lblOrderDate.text = getDateFormatString(day, month + 1, year)
        else
            lblDeliveryDate.text = getDateFormatString(day, month + 1, year)

        this.year = year
        this.month = month
        this.day = day
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_crud, menu)
        menuItemDelete= menu!!.findItem(R.id.mnu_delete)
        menuItemDelete.isVisible = isEditMode
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mnu_save ->{
                if (isEditMode){
                    util.showDialogCondition(this
                        , getString(R.string.TextSaveActionQuestion)
                        , { saveProductionOrder() })
                } else {
                    saveProductionOrder()
                }
                return true
            }
            R.id.mnu_delete ->{
                util.showDialogCondition(this
                    , getString(R.string.TextDeleteActionQuestion)
                    , { deleteProductionOrder() })
                return true
            }
            R.id.mnu_cancel ->{
                cleanScreen()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDatePickerDialog(){
        val datePickerDialog = DatePickerDialog(this, this
            , year, month, day)
        datePickerDialog.show()
    }

    private fun showProductSelectionDialog() {
        val checkedItems = BooleanArray(products.size) { index ->
            selectedProducts.contains(products[index])
        }

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.prod_ShowSelectProducts))
            .setMultiChoiceItems(products.toTypedArray(), checkedItems)
            { _, index, isChecked ->
                val product = products[index]
                if (isChecked) {
                    selectedProducts.add(product)
                } else {
                    selectedProducts.remove(product)
                }
            }
            .setPositiveButton(R.string.prod_Accept, null)
            .setNegativeButton(R.string.prod_Cancel, null)
            .show()
    }
    //endregion

    //region Methods
    private fun getDateFormatString(dayOfMonth: Int, month: Int, year: Int): String {
        return "${String.format("%02d", dayOfMonth)}/${String.format("%02d",month)}/${year}"
    }

    private fun resetDate(){
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
    }

    private fun cleanScreen(){
        resetDate()
        isEditMode = false
        txtID.isEnabled = true
        txtID.setText("")
        txtNumber.setText("")
        selectedProducts.clear()
        txtQuantity.setText("")
        lblOrderDate.setText("")
        lblDeliveryDate.setText("")
        invalidateOptionsMenu()
    }

    fun isValidationData(): Boolean {
        val dateparseDateOrder = util.parseStringToDateModern(lblOrderDate.text.toString(),
            "dd/MM/yyyy")
        val dateparseDeliveryOrder = util.parseStringToDateModern(lblDeliveryDate.text.toString(),
            "dd/MM/yyyy")

        val quantity = txtQuantity.text.toString().toFloatOrNull()
        if (quantity != null && quantity <= 0) {
            Toast.makeText(this, getString(R.string.prod_MsgQuantityInvalid),
                Toast.LENGTH_LONG).show()
            return false
        }

        if (dateparseDateOrder != null && dateparseDeliveryOrder != null) {
            if (dateparseDateOrder > dateparseDeliveryOrder){
                Toast.makeText(this, getString(R.string.prod_MsgDateInvalid),
                    Toast.LENGTH_LONG).show()
                return false
            }
        }
        else {
            Toast.makeText(this, getString(R.string.prod_MsgDateInvalid),
                Toast.LENGTH_LONG).show()
            return false
        }

        return txtID.text.trim().isNotBlank() && txtNumber.text.trim().isNotBlank()
                && txtQuantity.text.trim().isNotBlank() && selectedProducts.isNotEmpty()
                && lblOrderDate.text.trim().isNotEmpty() && lblOrderDate.text.trim().isNotEmpty()
    }

    //endregion

    //region CRUD
    private fun searchOrder(id: String){
        try {
            val productionOrder = productionOrderController.getById(id)
            if (productionOrder != null){
                val position = companies.indexOfFirst { it == productionOrder.CompanyName }

                isEditMode = true
                txtID.setText(productionOrder.ID.toString())
                txtID.isEnabled=false
                txtNumber.setText(productionOrder.OrderNumber.toString())
                selectedProducts.addAll(productionOrder.ProductList)
                txtQuantity.setText(productionOrder.Quantity.toString())
                lblOrderDate.setText(getDateFormatString(
                    productionOrder.OrderDate.dayOfMonth,
                    productionOrder.OrderDate.month.value,
                    productionOrder.OrderDate.year ))
                lblDeliveryDate.setText(getDateFormatString(
                    productionOrder.DeliveryDate.dayOfMonth,
                    productionOrder.DeliveryDate.month.value,
                    productionOrder.DeliveryDate.year ))
                spinnerCompanies.setSelection(position)
                year = productionOrder.OrderDate.year
                month = productionOrder.OrderDate.month.value - 1
                day = productionOrder.OrderDate.dayOfMonth
                invalidateOptionsMenu()
            }else{
                Toast.makeText(this, getString(R.string.MsgDataNoFound),
                    Toast.LENGTH_LONG).show()
            }
        }catch (e: Exception){
            cleanScreen()
            Toast.makeText(this, e.message.toString(),
                Toast.LENGTH_LONG).show()
        }
    }

    fun saveProductionOrder(){
        try {
            if (isValidationData()){
                if (productionOrderController.getById(txtID.text.toString().trim()) != null
                    && !isEditMode){
                    Toast.makeText(this, getString(R.string.MsgDuplicateDate)
                        , Toast.LENGTH_LONG).show()
                } else {
                    val productionOrder = prod_ProductionOrder("", "",
                        emptyList(), 0.0f,
                        LocalDate.now(), LocalDate.now(), "")
                    productionOrder.ID = txtID.text.toString()
                    productionOrder.OrderNumber = txtNumber.text.toString()
                    productionOrder.Quantity = txtQuantity.text.toString().toFloat()
                    productionOrder.ProductList = selectedProducts.toList()
                        .map { it.trim() }
                    productionOrder.CompanyName = companies[spinnerCompanies.selectedItemPosition]

                    val orderDateParse = util.parseStringToDateModern(
                        lblOrderDate.text.toString(), "dd/MM/yyyy")
                    productionOrder.OrderDate = LocalDate.of(orderDateParse?.year!!,
                        orderDateParse.month.value, orderDateParse.dayOfMonth
                    )

                    val deliveryDateParse = util.parseStringToDateModern(
                        lblDeliveryDate.text.toString(), "dd/MM/yyyy")
                    productionOrder.DeliveryDate = LocalDate.of(deliveryDateParse?.year!!,
                        deliveryDateParse.month.value, deliveryDateParse.dayOfMonth
                    )

                    if (!isEditMode)
                        productionOrderController.addProductionOrder(productionOrder)
                    else
                        productionOrderController.updateProductionOrder(productionOrder)

                    cleanScreen()

                    Toast.makeText(this, getString(R.string.MsgSaveSuccess)
                        , Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, R.string.prod_MsgDataIncomplete
                    , Toast.LENGTH_LONG).show()
            }
        }catch (e: Exception){
            Toast.makeText(this, e.message.toString()
                , Toast.LENGTH_LONG).show()
        }
    }

    fun deleteProductionOrder(): Unit{
        try {
            productionOrderController.removeProductionOrder(txtID.text.toString())
            cleanScreen()
            Toast.makeText(this, getString(R.string.MsgDeleteSuccess)
                , Toast.LENGTH_LONG).show()
        }catch (e: Exception){
            Toast.makeText(this, e.message.toString()
                , Toast.LENGTH_LONG).show()
        }
    }
    //endregion
}