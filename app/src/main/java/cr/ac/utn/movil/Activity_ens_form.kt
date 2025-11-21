// app/src/main/java/cr/ac/utn/movil/Activity_ens_form.kt
package cr.ac.utn.movil

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.movil.controllers.EnsController
import cr.ac.utn.movil.databinding.ActivityEnsFormBinding
import cr.ac.utn.movil.entities.EnsPolicy
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Activity_ens_form : AppCompatActivity() {

    private lateinit var binding: ActivityEnsFormBinding
    private var currentPolicy: EnsPolicy? = null
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private val companies = arrayOf("INS", "Mapfre", "Qualitas", "Sagicor", "Pan American")
    private val types = arrayOf("Responsabilidad Civil", "Todo Riesgo", "Incendio", "Robo", "Vida Colectiva")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnsFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.ens_title_form)
        setupSpinners()
        setupDatePickers()
        loadPolicyIfEdit()
        setupSaveButton()
    }

    private fun setupSpinners() {
        binding.ensSpCompany.adapter = android.widget.ArrayAdapter(this, android.R.layout.simple_spinner_item, companies)
        binding.ensSpType.adapter = android.widget.ArrayAdapter(this, android.R.layout.simple_spinner_item, types)
    }

    private fun setupDatePickers() {
        val calendar = Calendar.getInstance()

        val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            calendar.set(year, month, day)
            if (binding.ensEtStartDate.isFocused || binding.ensEtStartDate.text.isNullOrEmpty()) {
                binding.ensEtStartDate.setText(dateFormat.format(calendar.time))
            } else {
                binding.ensEtEndDate.setText(dateFormat.format(calendar.time))
            }
        }

        binding.ensEtStartDate.setOnClickListener {
            DatePickerDialog(this, dateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.ensEtEndDate.setOnClickListener {
            DatePickerDialog(this, dateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun loadPolicyIfEdit() {
        val id = intent.getStringExtra("ENS_POLICY_ID") ?: return
        currentPolicy = EnsController.getById(id) ?: return

        currentPolicy?.let { p ->
            binding.ensEtPolicyNumber.setText(p.ID)
            binding.ensEtPolicyNumber.isEnabled = false  // No editar número de póliza
            binding.ensSpCompany.setSelection(companies.indexOf(p.company))
            binding.ensSpType.setSelection(types.indexOf(p.insuranceType))
            binding.ensEtStartDate.setText(p.startDate)
            binding.ensEtEndDate.setText(p.endDate)
            binding.ensEtPremium.setText(p.premium.toString())
        }
    }

    private fun setupSaveButton() {
        binding.ensBtnSave.setOnClickListener {
            if (validateFields()) {
                savePolicy()
            }
        }
    }

    private fun validateFields(): Boolean {
        val policyNumber = binding.ensEtPolicyNumber.text.toString().trim()
        val start = binding.ensEtStartDate.text.toString()
        val end = binding.ensEtEndDate.text.toString()
        val premiumText = binding.ensEtPremium.text.toString()

        if (policyNumber.isEmpty() || start.isEmpty() || end.isEmpty() || premiumText.isEmpty()
            || binding.ensSpCompany.selectedItem == null || binding.ensSpType.selectedItem == null) {
            toast(getString(R.string.ens_msg_fill_all))
            return false
        }

        if (!EnsController.isPolicyNumberUnique(policyNumber, currentPolicy?.ID)) {
            toast(getString(R.string.ens_msg_policy_exists))
            return false
        }

        val startDate = dateFormat.parse(start)
        val endDate = dateFormat.parse(end)
        if (startDate == null || endDate == null || endDate <= startDate) {
            toast(getString(R.string.ens_msg_end_before_start))
            return false
        }

        return true
    }

    private fun savePolicy() {
        val policy = currentPolicy ?: EnsPolicy()

        policy.ID = binding.ensEtPolicyNumber.text.toString().trim()
        policy.company = binding.ensSpCompany.selectedItem.toString()
        policy.insuranceType = binding.ensSpType.selectedItem.toString()
        policy.startDate = binding.ensEtStartDate.text.toString()
        policy.endDate = binding.ensEtEndDate.text.toString()
        policy.premium = binding.ensEtPremium.text.toString().toDoubleOrNull() ?: 0.0

        if (currentPolicy == null) {
            EnsController.add(policy)
        } else {
            EnsController.update(policy)
        }

        finish()
    }

    private fun toast(message: String) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
    }
}