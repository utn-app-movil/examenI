package cr.ac.utn.movil

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.movil.data.MemoryDataManager
import cr.ac.utn.movil.util.util
import identities.Role
import identities.recru_Form
import java.time.LocalDateTime
import java.util.*

class recru_alex : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var txtId: EditText
    private lateinit var txtName: EditText
    private lateinit var txtFLast: EditText
    private lateinit var txtSLast: EditText
    private lateinit var txtProvince: EditText
    private lateinit var txtState: EditText
    private lateinit var txtDistrict: EditText
    private lateinit var txtCompany: EditText
    private lateinit var txtSalary: EditText
    private lateinit var txtExperience: EditText
    private lateinit var lbDate: TextView
    private lateinit var btnPickRoles: Button
    private lateinit var btnPickDate: ImageButton
    private lateinit var btnSearch: ImageButton

    private var selectedRoles = mutableListOf<Role>()
    private var year = 0
    private var month = 0
    private var day = 0
    private var hour = 0
    private var minute = 0
    private var isEditMode = false
    private var menuItemDelete: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recru_alex)

        initUI()
        resetDate()

        btnPickDate.setOnClickListener { showDatePicker() }
        btnSearch.setOnClickListener { searchForm() }
        btnPickRoles.setOnClickListener { showRolesDialog() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_crud, menu)
        menuItemDelete = menu?.findItem(R.id.mnu_delete)
        menuItemDelete?.isVisible = isEditMode
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnu_save -> {
                if (isEditMode) {
                    util.showDialogCondition(this,
                        getString(R.string.TextSaveActionQuestion),
                        { saveForm() })
                } else saveForm()
                true
            }
            R.id.mnu_delete -> {
                util.showDialogCondition(this,
                    getString(R.string.TextDeleteActionQuestion),
                    { deleteForm() })
                true
            }
            R.id.mnu_cancel -> {
                clearForm()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initUI() {
        txtId = findViewById(R.id.TextID)
        txtName = findViewById(R.id.TextName)
        txtFLast = findViewById(R.id.TextfLastName)
        txtSLast = findViewById(R.id.TextsLastName)
        txtProvince = findViewById(R.id.TextProvince)
        txtState = findViewById(R.id.TextState)
        txtDistrict = findViewById(R.id.TextDistrict)
        txtCompany = findViewById(R.id.TextCompany)
        txtSalary = findViewById(R.id.DecimalSalary)
        txtExperience = findViewById(R.id.NumberExperience)
        lbDate = findViewById(R.id.lbDate)
        btnPickRoles = findViewById(R.id.btnPickRoles)
        btnPickDate = findViewById(R.id.btnPickDate)
        btnSearch = findViewById(R.id.btnSearch)
    }

    private fun resetDate() {
        val cal = Calendar.getInstance()
        year = cal.get(Calendar.YEAR)
        month = cal.get(Calendar.MONTH)
        day = cal.get(Calendar.DAY_OF_MONTH)
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun showDatePicker() {
        DatePickerDialog(this, this, year, month, day).show()
    }

    override fun onDateSet(view: DatePicker?, y: Int, m: Int, d: Int) {
        year = y
        month = m
        day = d
        TimePickerDialog(this, { _, h, min ->
            hour = h
            minute = min
            lbDate.text = String.format("%02d/%02d/%04d %02d:%02d", day, month + 1, year, hour, minute)
        }, hour, minute, true).show()
    }

    private fun showRolesDialog() {
        val rolesArray = Role.values().map { it.name }.toTypedArray()
        val checkedItems = BooleanArray(rolesArray.size) { selectedRoles.contains(Role.values()[it]) }

        AlertDialog.Builder(this)
            .setTitle("Selecciona Roles")
            .setMultiChoiceItems(rolesArray, checkedItems) { _, which, isChecked ->
                checkedItems[which] = isChecked
            }
            .setPositiveButton("OK") { dialog, _ ->
                selectedRoles.clear()
                rolesArray.forEachIndexed { index, roleName ->
                    if (checkedItems[index]) selectedRoles.add(Role.valueOf(roleName))
                }
                updateRolesButtonText()
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun updateRolesButtonText() {
        if (selectedRoles.isEmpty()) {
            btnPickRoles.text = "Seleccionar roles"
        } else {
            btnPickRoles.text = selectedRoles.joinToString(", ") { it.name }
        }
    }

    private fun searchForm() {
        val objAny = MemoryDataManager.getById(txtId.text.toString())
        if (objAny !is recru_Form) {
            Toast.makeText(this, R.string.MsgDataNoFound, Toast.LENGTH_LONG).show()
            return
        }
        val obj = objAny

        isEditMode = true
        txtName.setText(obj.Name)
        txtFLast.setText(obj.FLastName)
        txtSLast.setText(obj.SLastName)
        txtProvince.setText(obj.Province)
        txtState.setText(obj.State)
        txtDistrict.setText(obj.District)
        txtCompany.setText(obj.Company)
        txtSalary.setText(obj.Salary.toString())
        txtExperience.setText(obj.Experience.toString())
        selectedRoles = obj.Roles.toMutableList()

        val dt = obj.Date
        lbDate.text = String.format("%02d/%02d/%04d %02d:%02d", dt.dayOfMonth, dt.monthValue, dt.year, dt.hour, dt.minute)
    }




    private fun validate(): Boolean {
        return txtId.text.isNotEmpty()
                && txtName.text.isNotEmpty()
                && txtFLast.text.isNotEmpty()
                && txtCompany.text.isNotEmpty()
                && lbDate.text.isNotEmpty()
                && selectedRoles.isNotEmpty()
    }

    private fun saveForm() {
        if (!validate()) {
            Toast.makeText(this, R.string.ErrorMsgAdd, Toast.LENGTH_LONG).show()
            return
        }

        val selectedDateTime = LocalDateTime.of(year, month + 1, day, hour, minute)
        if (selectedDateTime.isAfter(LocalDateTime.now())) {
            Toast.makeText(this, "La fecha y hora de registro no puede ser mayor a la actual", Toast.LENGTH_LONG).show()
            return
        }

        val existing = MemoryDataManager.getAll().filterIsInstance<recru_Form>()
        if (!isEditMode && existing.any { it.Company == txtCompany.text.toString() && it.ID == txtId.text.toString() }) {
            Toast.makeText(this, R.string.ErrorMsgAdd, Toast.LENGTH_LONG).show()
            return
        }

        val form = recru_Form().apply {
            ID = txtId.text.toString()
            Name = txtName.text.toString()
            FLastName = txtFLast.text.toString()
            SLastName = txtSLast.text.toString()
            Province = txtProvince.text.toString()
            State = txtState.text.toString()
            District = txtDistrict.text.toString()
            Company = txtCompany.text.toString()
            Salary = txtSalary.text.toString().toFloatOrNull() ?: 0f
            Experience = txtExperience.text.toString().toIntOrNull() ?: 0
            Roles = selectedRoles
            Date = selectedDateTime
        }

        if (isEditMode)
            MemoryDataManager.update(form)
        else
            MemoryDataManager.add(form)

        Toast.makeText(this, R.string.MsgSaveSuccess, Toast.LENGTH_SHORT).show()
        clearForm()
    }



    private fun deleteForm() {
        MemoryDataManager.remove(txtId.text.toString())
        Toast.makeText(this, R.string.MsgDeleteSuccess, Toast.LENGTH_SHORT).show()
        clearForm()
    }

    private fun clearForm() {
        txtId.setText("")
        txtName.setText("")
        txtFLast.setText("")
        txtSLast.setText("")
        txtProvince.setText("")
        txtState.setText("")
        txtDistrict.setText("")
        txtCompany.setText("")
        txtSalary.setText("")
        txtExperience.setText("")
        lbDate.text = ""
        selectedRoles.clear()
        btnPickRoles.clearFocus()
        isEditMode = false
        resetDate()
        menuItemDelete?.isVisible = false
    }
}
