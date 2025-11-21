package cr.ac.utn.movil.controller

import cr.ac.utn.movil.data.MemoryDataManager
import cr.ac.utn.movil.identities.Payroll

class PayrollController {
    private val dataManager = MemoryDataManager

    fun add(payroll: Payroll): Boolean {
        return try {
            if (getById(payroll.ID) != null) {
                false
            } else {
                dataManager.add(payroll)
                true
            }
        } catch (e: Exception) {
            false
        }
    }

    fun update(payroll: Payroll): Boolean {
        return try {
            dataManager.update(payroll)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun remove(id: String): Boolean {
        return try {
            dataManager.remove(id)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getAll(): List<Payroll> {
        return try {
            dataManager.getAll().filterIsInstance<Payroll>()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getById(id: String): Payroll? {
        return try {
            dataManager.getById(id) as? Payroll
        } catch (e: Exception) {
            null
        }
    }

    fun isValidIban(iban: String): Boolean {
        val cleanIban = iban.replace(" ", "").uppercase()
        val ibanRegex = Regex("^CR\\d{20}$")
        return ibanRegex.matches(cleanIban)
    }

    fun isValidMonth(month: Int, year: Int): Boolean {
        val currentYear = java.time.Year.now().value
        val currentMonth = java.time.LocalDate.now().monthValue

        if (month < 1 || month > 12) return false
        if (year > currentYear) return false
        if (year == currentYear && month > currentMonth) return false

        return true
    }

    fun isDuplicateEmployee(employeeNumber: String, excludeId: String? = null): Boolean {
        return getAll().any {
            it.EmployeeNumber == employeeNumber &&
            (excludeId == null || it.ID != excludeId)
        }
    }
}
