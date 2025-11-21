package cr.ac.utn.movil

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import controller.NotificationController
import identities.Notification
import java.time.LocalDate
import java.util.Calendar
import cr.ac.utn.movil.data.MemoryDataManager
import cr.ac.utn.movil.identities.Person
import cr.ac.utn.movil.util.EXTRA_ID

class NotificationActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var txtId: EditText
    private lateinit var txtSender: EditText
    private lateinit var txtTitle: EditText
    private lateinit var txtMessage: EditText
    private lateinit var lbDateSent: TextView
    private lateinit var lbReceivers: TextView
    private lateinit var lbCC: TextView
    private lateinit var lbCCO: TextView

    private lateinit var notificationController: NotificationController
    private lateinit var dataManager: cr.ac.utn.movil.interfaces.IDataManager

    private var isEditMode: Boolean = false
    private var day: Int = 0
    private var month: Int = 0
    private var year: Int = 0

    private lateinit var menuItemDelete: MenuItem

    private var senderPerson: Person? = null
    private var receiversList: MutableList<Person> = mutableListOf()
    private var ccList: MutableList<Person> = mutableListOf()
    private var ccoList: MutableList<Person> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_notification)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        notificationController = NotificationController(this)
        dataManager = MemoryDataManager

        txtId = findViewById<EditText>(R.id.txtId_notification)
        txtSender = findViewById<EditText>(R.id.txtSender_notification)
        txtTitle = findViewById<EditText>(R.id.txtTitle_notification)
        txtMessage = findViewById<EditText>(R.id.txtMessage_notification)
        lbDateSent = findViewById<TextView>(R.id.lbDateSent_notification)
        lbReceivers = findViewById<TextView>(R.id.lbReceivers_notification)
        lbCC = findViewById<TextView>(R.id.lbCC_notification)
        lbCCO = findViewById<TextView>(R.id.lbCCO_notification)

        resetDate()
        initializeSamplePersons()

        val notificationId = intent.getStringExtra(EXTRA_ID)
        if (notificationId != null && notificationId.trim().length > 0) {
            searchNotification(notificationId)
        }

        val btnSelectDate = findViewById<ImageButton>(R.id.btnSelectDate_notification)
        btnSelectDate.setOnClickListener(View.OnClickListener { view ->
            showDatePickerDialog()
        })

        val btnSearch = findViewById<ImageButton>(R.id.btnSearchId_notification)
        btnSearch.setOnClickListener(View.OnClickListener { view ->
            searchNotification(txtId.text.trim().toString())
        })

        val btnSearchSender = findViewById<ImageButton>(R.id.btnSearchSender)
        btnSearchSender.setOnClickListener(View.OnClickListener { view ->
            searchSender(txtSender.text.trim().toString())
        })

        val btnSelectReceivers = findViewById<Button>(R.id.btnSelectReceivers)
        btnSelectReceivers.setOnClickListener(View.OnClickListener { view ->
            selectPersons(getString(R.string.Senders), receiversList) { selected ->
                receiversList = selected
                updateReceiversList()
            }
        })

        val btnSelectCC = findViewById<Button>(R.id.btnSelectCC)
        btnSelectCC.setOnClickListener(View.OnClickListener { view ->
            selectPersons("CC", ccList) { selected ->
                ccList = selected
                updateCCList()
            }
        })

        val btnSelectCCO = findViewById<Button>(R.id.btnSelectCCO)
        btnSelectCCO.setOnClickListener(View.OnClickListener { view ->
            selectPersons("CCO", ccoList) { selected ->
                ccoList = selected
                updateCCOList()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_crud, menu)
        menuItemDelete = menu!!.findItem(R.id.mnu_delete)
        menuItemDelete.isVisible = isEditMode
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnu_save -> {
                if (isEditMode) {
                    cr.ac.utn.movil.util.util.showDialogCondition(this,
                        getString(R.string.TextSaveActionQuestion)
                    ) { saveNotification() }
                } else {
                    saveNotification()
                }
                return true
            }
            R.id.mnu_delete -> {
                cr.ac.utn.movil.util.util.showDialogCondition(this,
                    getString(R.string.TextDeleteActionQuestion)
                ) { deleteNotification() }
                return true
            }
            R.id.mnu_cancel -> {
                cleanScreen()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun resetDate() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
    }

    private fun initializeSamplePersons() {
        try {
            val allIdentifiers = dataManager.getAll()
            val existingPersons = allIdentifiers.filterIsInstance<Person>()

            if (existingPersons.isEmpty()) {
                val person1 = Person()
                person1.ID = "001"
                person1.Name = "Juan"
                person1.FLastName = "Pérez"
                person1.SLastName = "García"
                person1.Email = "juan.perez@email.com"
                person1.Phone = 88888888
                person1.Address = "Calle 1, San José"
                person1.Country = "Costa Rica"

                val person2 = Person()
                person2.ID = "002"
                person2.Name = "María"
                person2.FLastName = "González"
                person2.SLastName = "López"
                person2.Email = "maria.gonzalez@email.com"
                person2.Phone = 88887777
                person2.Address = "Calle 2, Alajuela"
                person2.Country = "Costa Rica"

                val person3 = Person()
                person3.ID = "003"
                person3.Name = "Carlos"
                person3.FLastName = "Rodríguez"
                person3.SLastName = "Martínez"
                person3.Email = "carlos.rodriguez@email.com"
                person3.Phone = 88886666
                person3.Address = "Calle 3, Cartago"
                person3.Country = "Costa Rica"

                val person4 = Person()
                person4.ID = "004"
                person4.Name = "Ana"
                person4.FLastName = "Fernández"
                person4.SLastName = "Sánchez"
                person4.Email = "ana.fernandez@email.com"
                person4.Phone = 88885555
                person4.Address = "Calle 4, Heredia"
                person4.Country = "Costa Rica"

                val person5 = Person()
                person5.ID = "005"
                person5.Name = "Luis"
                person5.FLastName = "Morales"
                person5.SLastName = "Castro"
                person5.Email = "luis.morales@email.com"
                person5.Phone = 88884444
                person5.Address = "Calle 5, Puntarenas"
                person5.Country = "Costa Rica"

                dataManager.add(person1)
                dataManager.add(person2)
                dataManager.add(person3)
                dataManager.add(person4)
                dataManager.add(person5)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(this, this, year, month, day)
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() + (24 * 60 * 60 * 1000)
        datePickerDialog.show()
    }

    private fun getDateFormatString(dayOfMonth: Int, monthValue: Int, yearValue: Int): String {
        return "${if (dayOfMonth < 10) "0" else ""}$dayOfMonth/${if (monthValue < 10) "0" else ""}$monthValue/$yearValue"
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        lbDateSent.text = getDateFormatString(dayOfMonth, month + 1, year)
        this.year = year
        this.month = month
        this.day = dayOfMonth
    }

    private fun searchNotification(id: String) {
        try {
            if (id.isEmpty()) {
                Toast.makeText(this, getString(R.string.AddAnId), Toast.LENGTH_SHORT).show()
                return
            }

            val allNotifications = dataManager.getAll()
            val notification = allNotifications.find { it.ID.trim() == id.trim() } as? Notification

            if (notification != null) {
                isEditMode = true
                txtId.setText(notification.ID)
                txtId.isEnabled = false
                txtTitle.setText(notification.Title)
                txtMessage.setText(notification.Message)

                senderPerson = notification.Sender
                txtSender.setText(senderPerson?.ID ?: "")

                val dateSent = notification.DateSent
                if (dateSent != null) {
                    lbDateSent.text = getDateFormatString(
                        dateSent.dayOfMonth,
                        dateSent.month.value,
                        dateSent.year
                    )
                    year = dateSent.year
                    month = dateSent.month.value - 1
                    day = dateSent.dayOfMonth
                }

                receiversList.clear()
                receiversList.addAll(notification.Receivers)
                ccList.clear()
                ccList.addAll(notification.Cc)
                ccoList.clear()
                ccoList.addAll(notification.Cco)

                updateReceiversList()
                updateCCList()
                updateCCOList()

                invalidateOptionsMenu()
            } else {
                Toast.makeText(this, "Notificación no encontrada", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    private fun searchSender(id: String) {
        try {
            if (id.isEmpty()) {
                Toast.makeText(this, "Ingrese un ID", Toast.LENGTH_SHORT).show()
                return
            }

            val allIdentifiers = dataManager.getAll()
            val person = allIdentifiers.find { it.ID.trim() == id.trim() } as? Person

            if (person != null) {
                senderPerson = person
                txtSender.setText(person.ID)
                val fullName = "${person.Name} ${person.FLastName} ${person.SLastName}".trim()
                Toast.makeText(this, "Remitente: $fullName", Toast.LENGTH_SHORT).show()
            } else {
                senderPerson = null
                Toast.makeText(this, "Persona no encontrada", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            senderPerson = null
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    private fun selectPersons(
        title: String,
        currentSelection: MutableList<Person>,
        onSelected: (MutableList<Person>) -> Unit
    ) {
        try {
            val allIdentifiers = dataManager.getAll()
            val allPersons = allIdentifiers.filterIsInstance<Person>()

            if (allPersons.isEmpty()) {
                Toast.makeText(this, "No hay personas registradas", Toast.LENGTH_LONG).show()
                return
            }

            val personNames = allPersons.map {
                "${it.Name} ${it.FLastName} ${it.SLastName}"
            }.toTypedArray()

            val checkedItems = BooleanArray(allPersons.size) { index ->
                currentSelection.any { it.ID == allPersons[index].ID }
            }

            val selectedPersons = mutableListOf<Person>()
            selectedPersons.addAll(currentSelection)

            AlertDialog.Builder(this)
                .setTitle("Seleccionar $title")
                .setMultiChoiceItems(personNames, checkedItems) { _, which, isChecked ->
                    if (isChecked) {
                        if (!selectedPersons.any { it.ID == allPersons[which].ID }) {
                            selectedPersons.add(allPersons[which])
                        }
                    } else {
                        selectedPersons.removeAll { it.ID == allPersons[which].ID }
                    }
                }
                .setPositiveButton("Aceptar") { _, _ ->
                    onSelected(selectedPersons)
                }
                .setNegativeButton("Cancelar", null)
                .show()

        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateReceiversList() {
        lbReceivers.text = if (receiversList.isEmpty()) {
            "Ninguno seleccionado"
        } else {
            receiversList.joinToString(", ") { "${it.Name} ${it.FLastName}" }
        }
    }

    private fun updateCCList() {
        lbCC.text = if (ccList.isEmpty()) {
            "Ninguno seleccionado"
        } else {
            ccList.joinToString(", ") { "${it.Name} ${it.FLastName}" }
        }
    }

    private fun updateCCOList() {
        lbCCO.text = if (ccoList.isEmpty()) {
            "Ninguno seleccionado"
        } else {
            ccoList.joinToString(", ") { "${it.Name} ${it.FLastName}" }
        }
    }

    fun isValidationData(): Boolean {
        if (txtId.text.trim().isEmpty()) {
            Toast.makeText(this, "Falta ID", Toast.LENGTH_SHORT).show()
            return false
        }
        if (txtTitle.text.trim().isEmpty()) {
            Toast.makeText(this, "Falta título", Toast.LENGTH_SHORT).show()
            return false
        }
        if (txtMessage.text.trim().isEmpty()) {
            Toast.makeText(this, "Falta mensaje", Toast.LENGTH_SHORT).show()
            return false
        }
        if (senderPerson == null) {
            Toast.makeText(this, "Falta remitente", Toast.LENGTH_SHORT).show()
            return false
        }
        if (receiversList.isEmpty()) {
            Toast.makeText(this, "Falta al menos un destinatario", Toast.LENGTH_SHORT).show()
            return false
        }
        if (lbDateSent.text.trim().isEmpty()) {
            Toast.makeText(this, "Falta fecha", Toast.LENGTH_SHORT).show()
            return false
        }

        val dateparse = cr.ac.utn.movil.util.util.parseStringToDateModern(lbDateSent.text.toString(), "dd/MM/yyyy")
        if (dateparse == null) {
            Toast.makeText(this, "Fecha inválida", Toast.LENGTH_SHORT).show()
            return false
        }

        val currentDate = LocalDate.now()
        if (!dateparse.isAfter(currentDate)) {
            Toast.makeText(this, "La fecha debe ser posterior a hoy", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun cleanScreen() {
        resetDate()
        isEditMode = false
        txtId.isEnabled = true
        txtId.setText("")
        txtSender.setText("")
        txtTitle.setText("")
        txtMessage.setText("")
        lbDateSent.setText("")

        senderPerson = null
        receiversList.clear()
        ccList.clear()
        ccoList.clear()

        updateReceiversList()
        updateCCList()
        updateCCOList()

        invalidateOptionsMenu()
    }

    fun saveNotification() {
        try {
            if (!isValidationData()) {
                return
            }

            val allNotifications = dataManager.getAll()
            val existingNotification = allNotifications.find { it.ID.trim() == txtId.text.toString().trim() }

            if (existingNotification != null && !isEditMode) {
                Toast.makeText(this, "Ya existe una notificación con este ID", Toast.LENGTH_LONG).show()
                return
            }

            val notification = Notification()
            notification.ID = txtId.text.toString().trim()
            notification.Title = txtTitle.text.toString().trim()
            notification.Message = txtMessage.text.toString().trim()
            notification.Sender = senderPerson!!

            val dateParse = cr.ac.utn.movil.util.util.parseStringToDateModern(
                lbDateSent.text.toString(),
                "dd/MM/yyyy"
            )
            notification.DateSent = LocalDate.of(
                dateParse!!.year,
                dateParse.month.value,
                dateParse.dayOfMonth
            )

            notification.Receivers = receiversList.toMutableList()
            notification.Cc = ccList.toMutableList()
            notification.Cco = ccoList.toMutableList()

            if (!isEditMode) {
                notificationController.createNotification(notification)
            } else {
                notificationController.updateNotification(notification)
            }

            cleanScreen()
            Toast.makeText(this, "Guardado exitosamente", Toast.LENGTH_LONG).show()

        } catch (e: Exception) {
            Toast.makeText(this, "Error al guardar: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    fun deleteNotification() {
        try {
            notificationController.deleteNotification(txtId.text.toString().trim())
            cleanScreen()
            Toast.makeText(this, "Eliminado exitosamente", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error al eliminar: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}