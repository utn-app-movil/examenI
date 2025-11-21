package controller

import cr.ac.utn.movil.data.MemoryDataManager
import entity.DashContribution

class DashContributionController {

    fun addContribution(contribution: DashContribution): Boolean {
        // The MemoryDataManager uses the ID to check for existence, so we don't need to do it manually.
        val existing = MemoryDataManager.getById(contribution.ID)
        return if (existing == null) {
            MemoryDataManager.add(contribution)
            true
        } else {
            false
        }
    }

    fun getContribution(person: String, month: Int, year: Int): DashContribution? {
        val id = "$person-$month-$year"
        return MemoryDataManager.getById(id) as? DashContribution
    }

    fun getAllContributions(): List<DashContribution> {
        return MemoryDataManager.getAll().mapNotNull { it as? DashContribution }
    }

    fun updateContribution(contribution: DashContribution) {
        MemoryDataManager.update(contribution)
    }

    fun deleteContribution(person: String, month: Int, year: Int) {
        val id = "$person-$month-$year"
        MemoryDataManager.remove(id)
    }
}