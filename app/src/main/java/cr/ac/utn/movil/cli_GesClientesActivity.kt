package cr.ac.utn.movil

import identities.cli_GestionClientes
import Controller.cli_GesClientesCotroller
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.*
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.util.Util
import cr.ac.utn.movil.util.util
import cr.ac.utn.movil.util.EXTRA_ID




class cli_GesClientesActivity : AppCompatActivity() {

    private lateinit var txtId: EditText
    private lateinit var txtCompanyName: EditText
    private lateinit var txtLegalId: EditText
    private lateinit var txtProvince: EditText
    private lateinit var txtCanton: EditText
    private lateinit var txtDistrict: EditText

    private lateinit var chkInternet: CheckBox
    private lateinit var chkTelefonia: CheckBox
    private lateinit var chkHosting: CheckBox
    private lateinit var chkSoporte: CheckBox

    private lateinit var controller: cli_GesClientesCotroller
    private var isEditMode = false
    private lateinit var menuItemDelete: MenuItem


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cli_ges_clientes)

        controller = cli_GesClientesCotroller(this)

        txtCompanyName = findViewById(R.id.cli_txtcompanyNameAgregar)
        txtLegalId = findViewById(R.id.cli_txtlegalIdAgregar)
        txtProvince = findViewById(R.id.cli_txtprovinceAgregar)
        txtCanton = findViewById(R.id.cli_txtcantonAgregar)
        txtDistrict = findViewById(R.id.cli_txtdistrictAgregar)

        chkInternet = findViewById(R.id.chkInternet)
        chkTelefonia = findViewById(R.id.chkTelefonia)
        chkHosting = findViewById(R.id.chkHosting)
        chkSoporte = findViewById(R.id.chkSoporte)

        findViewById<ImageButton>(R.id.cli_btnSearch).setOnClickListener {
            searchClient(txtId.text.toString().trim())
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_crud, menu)

        menuItemDelete = menu!!.findItem(R.id.mnu_delete)
        menuItemDelete.isVisible = isEditMode

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnu_save -> {

                true
            }
            R.id.mnu_delete -> {

                true
            }
            R.id.mnu_cancel -> {
                clearScreen()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun validateData(): Boolean {
        val fields = listOf(
            txtId, txtCompanyName, txtLegalId,
            txtProvince, txtCanton, txtDistrict
        )

        for (field in fields) {
            if (field.text.toString().trim().isEmpty()) {
                field.error = "Required field"
                return false
            }
        }

        return true
    }

    private fun searchClient(id: String) {
        try {
            val client = controller.getById(id)

            if (client != null) {

                isEditMode = true

                txtCompanyName.setText(client.CompanyName)
                txtLegalId.setText(client.LegalId)
                txtProvince.setText(client.Province)
                txtCanton.setText(client.Canton)
                txtDistrict.setText(client.District)

                chkInternet.isChecked = "Business Internet" in client.Services
                chkTelefonia.isChecked = "IP Telephony" in client.Services
                chkHosting.isChecked = "Hosting" in client.Services
                chkSoporte.isChecked = "Technical Support" in client.Services

                menuItemDelete.isVisible = true

            } else {
                Toast.makeText(this, "Client not found.", Toast.LENGTH_LONG).show()
            }

        } catch (e: Exception) {
            clearScreen()
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }




    private fun saveClient() {
        try {

            val services = mutableListOf<String>()
            if (chkInternet.isChecked) services.add("Business Internet")
            if (chkTelefonia.isChecked) services.add("IP Telephony")
            if (chkHosting.isChecked) services.add("Hosting")
            if (chkSoporte.isChecked) services.add("Technical Support")

            val client = cli_GestionClientes(
                txtId.text.toString().trim(),
                txtCompanyName.text.toString().trim(),
                txtLegalId.text.toString().trim(),
                txtProvince.text.toString().trim(),
                txtCanton.text.toString().trim(),
                txtDistrict.text.toString().trim(),
                services
            )

            if (controller.getById(client.ID) != null && !isEditMode) {
                Toast.makeText(this, "Client already exists.", Toast.LENGTH_LONG).show()
            } else {
                if (!isEditMode)
                    controller.addClient(client)
                else
                    controller.updateClient(client)

                clearScreen()
                Toast.makeText(this, "Client saved successfully.", Toast.LENGTH_LONG).show()
            }

        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }




    private fun deleteClient() {
        try {
            controller.removeClient(txtId.text.toString().trim())
            clearScreen()
            Toast.makeText(this, "Client deleted successfully.", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun clearScreen() {
        isEditMode = false

        txtId.setText("")
        txtCompanyName.setText("")
        txtLegalId.setText("")
        txtProvince.setText("")
        txtCanton.setText("")
        txtDistrict.setText("")

        chkInternet.isChecked = false
        chkTelefonia.isChecked = false
        chkHosting.isChecked = false
        chkSoporte.isChecked = false

        menuItemDelete.isVisible = false
        invalidateOptionsMenu()
    }

}
