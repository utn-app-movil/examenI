package cr.ac.utn.movil.controller

import cr.ac.utn.movil.data.MemoryDataManager
import cr.ac.utn.movil.entities.PayenInsuranceRequest

class PayenInsuranceController {


    fun add(request: PayenInsuranceRequest): Boolean {
        // Check duplicate by ID
        val exists = MemoryDataManager.getById(request.ID)
        if (exists != null) {
            return false
        }

        MemoryDataManager.add(request)
        return true
    }


    fun update(request: PayenInsuranceRequest): Boolean {
        val exists = MemoryDataManager.getById(request.ID)
        if (exists == null) {
            return false
        }

        MemoryDataManager.update(request)
        return true
    }


    fun delete(id: String): Boolean {
        val exists = MemoryDataManager.getById(id)
        if (exists == null) {
            return false
        }

        MemoryDataManager.remove(id)
        return true
    }


    fun getAll(): List<PayenInsuranceRequest> {
        return MemoryDataManager.getAll().filterIsInstance<PayenInsuranceRequest>()
    }


    fun getById(id: String): PayenInsuranceRequest? {
        val result = MemoryDataManager.getById(id)
        return result as? PayenInsuranceRequest
    }
}
