package cr.ac.utn.movil.controller

import cr.ac.utn.movil.data.MemoryDataManager
import cr.ac.utn.movil.entities.PayenInsuranceRequest

class PayenInsuranceController {

    // Add
    fun add(request: PayenInsuranceRequest): Boolean {
        // Check duplicate by ID
        val exists = MemoryDataManager.getById(request.ID)
        if (exists != null) {
            return false
        }

        MemoryDataManager.add(request)
        return true
    }

    // Update
    fun update(request: PayenInsuranceRequest): Boolean {
        val exists = MemoryDataManager.getById(request.ID)
        if (exists == null) {
            return false
        }

        MemoryDataManager.update(request)
        return true
    }

    // Delete
    fun delete(id: String): Boolean {
        val exists = MemoryDataManager.getById(id)
        if (exists == null) {
            return false
        }

        MemoryDataManager.remove(id)
        return true
    }

    // Get all
    fun getAll(): List<PayenInsuranceRequest> {
        return MemoryDataManager.getAll().filterIsInstance<PayenInsuranceRequest>()
    }

    // Get by ID
    fun getById(id: String): PayenInsuranceRequest? {
        val result = MemoryDataManager.getById(id)
        return result as? PayenInsuranceRequest
    }
}
