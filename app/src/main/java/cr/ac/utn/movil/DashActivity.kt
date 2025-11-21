package cr.ac.utn.movil

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import controller.DashContributionController
import entity.DashContribution

class DashActivity : AppCompatActivity() {

    private val dashContributionController = DashContributionController()
    private lateinit var logTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash)

        logTextView = findViewById(R.id.dash_log_textview)

        val saveButton = findViewById<Button>(R.id.dash_save_button)
        val searchButton = findViewById<Button>(R.id.dash_search_button)
        val updateButton = findViewById<Button>(R.id.dash_update_button)
        val deleteButton = findViewById<Button>(R.id.dash_delete_button)

        val personInput = findViewById<EditText>(R.id.dash_person_input)
        val contributionsInput = findViewById<EditText>(R.id.dash_contributions_input)
        val dayInput = findViewById<EditText>(R.id.dash_day_input)
        val monthInput = findViewById<EditText>(R.id.dash_month_input)
        val yearInput = findViewById<EditText>(R.id.dash_year_input)

        saveButton.setOnClickListener {
            val person = personInput.text.toString()
            val contributions = contributionsInput.text.toString()
            val day = dayInput.text.toString()
            val month = monthInput.text.toString()
            val year = yearInput.text.toString()

            if (person.isBlank() || contributions.isBlank() || day.isBlank() || month.isBlank() || year.isBlank()) {
                Toast.makeText(this, R.string.dash_toast_fill_fields, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val contribution = DashContribution(person, contributions.toInt(), day.toInt(), month.toInt(), year.toInt())

            if (dashContributionController.addContribution(contribution)) {
                Toast.makeText(this, R.string.dash_toast_saved, Toast.LENGTH_SHORT).show()
                clearFields(personInput, contributionsInput, dayInput, monthInput, yearInput)
                updateContributionsLog()
            } else {
                Toast.makeText(this, R.string.dash_toast_exists, Toast.LENGTH_SHORT).show()
            }
        }

        searchButton.setOnClickListener {
            val person = personInput.text.toString()
            val month = monthInput.text.toString()
            val year = yearInput.text.toString()

            if (person.isBlank() || month.isBlank() || year.isBlank()) {
                Toast.makeText(this, R.string.dash_toast_search_criteria, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val contribution = dashContributionController.getContribution(person, month.toInt(), year.toInt())

            if (contribution != null) {
                personInput.setText(contribution.person)
                contributionsInput.setText(contribution.contributions.toString())
                dayInput.setText(contribution.day.toString())
                monthInput.setText(contribution.month.toString())
                yearInput.setText(contribution.year.toString())
            } else {
                Toast.makeText(this, R.string.dash_toast_not_found, Toast.LENGTH_SHORT).show()
                clearFields(personInput, contributionsInput, dayInput, monthInput, yearInput)
            }
        }

        updateButton.setOnClickListener {
            val person = personInput.text.toString()
            val contributions = contributionsInput.text.toString()
            val day = dayInput.text.toString()
            val month = monthInput.text.toString()
            val year = yearInput.text.toString()

            if (person.isBlank() || contributions.isBlank() || day.isBlank() || month.isBlank() || year.isBlank()) {
                Toast.makeText(this, R.string.dash_toast_fill_fields, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedContribution = DashContribution(person, contributions.toInt(), day.toInt(), month.toInt(), year.toInt())
            dashContributionController.updateContribution(updatedContribution)
            Toast.makeText(this, R.string.dash_toast_updated, Toast.LENGTH_SHORT).show()
            clearFields(personInput, contributionsInput, dayInput, monthInput, yearInput)
            updateContributionsLog()
        }

        deleteButton.setOnClickListener {
            val person = personInput.text.toString()
            val month = monthInput.text.toString()
            val year = yearInput.text.toString()

            if (person.isBlank() || month.isBlank() || year.isBlank()) {
                Toast.makeText(this, R.string.dash_toast_delete_criteria, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            dashContributionController.deleteContribution(person, month.toInt(), year.toInt())
            Toast.makeText(this, R.string.dash_toast_deleted, Toast.LENGTH_SHORT).show()
            clearFields(personInput, contributionsInput, dayInput, monthInput, yearInput)
            updateContributionsLog()
        }

        // Initial load of the log
        updateContributionsLog()
    }

    private fun clearFields(vararg fields: EditText) {
        fields.forEach { it.text.clear() }
    }

    private fun updateContributionsLog() {
        val allContributions = dashContributionController.getAllContributions()
        if (allContributions.isEmpty()) {
            logTextView.text = "No contributions saved yet."
            return
        }

        // Use a StringBuilder for better performance when creating the string
        val logText = StringBuilder()
        for (contribution in allContributions) {
            logText.append("- ").append(contribution.FullDescription).append("\n")
        }
        logTextView.text = logText.toString()
    }
}