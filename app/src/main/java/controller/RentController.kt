package controller

import cr.ac.utn.movil.data.MemoryDataManager
import cr.ac.utn.movil.identities.RentEntity
import cr.ac.utn.movil.identities.Identifier

class RentController {

    fun addRent(rent: RentEntity) {
        MemoryDataManager.add(rent)
    }

    fun updateRent(rent: RentEntity) {
        MemoryDataManager.update(rent)
    }

    fun deleteRent(id: String) {
        MemoryDataManager.remove(id)
    }

    fun getAllRents(): List<Identifier> {
        return MemoryDataManager.getAll()
    }
}
