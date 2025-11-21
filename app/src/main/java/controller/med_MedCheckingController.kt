package cr.ac.utn.movil.controller

import cr.ac.utn.movil.data.MemoryDataManager
import cr.ac.utn.movil.identities.med_NursingControl
import cr.ac.utn.movil.interfaces.IDataManager

class med_MedCheckingController(
    private val dataManager: IDataManager = MemoryDataManager
) {

    fun getAll(): List<med_NursingControl> =
        dataManager.getAll().filterIsInstance<med_NursingControl>()

    fun getById(id: String): med_NursingControl? =
        dataManager.getById(id) as? med_NursingControl

    fun add(entity: med_NursingControl): Boolean {
        if (exists(entity.ID)) {
            return false
        }
        dataManager.add(entity)
        return true
    }

    fun update(entity: med_NursingControl): Boolean {
        if (!exists(entity.ID)) {
            return false
        }
        dataManager.update(entity)
        return true
    }

    fun remove(id: String): Boolean {
        if (!exists(id)) {
            return false
        }
        dataManager.remove(id)
        return true
    }

    private fun exists(id: String): Boolean =
        dataManager.getById(id) != null
}
