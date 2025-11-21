package cr.ac.utn.movil.controller

import cr.ac.utn.movil.data.MemoryDataManager
import cr.ac.utn.movil.identities.LicenseRenewal

class LicenseController {

    fun add(license: LicenseRenewal) {
        MemoryDataManager.add(license)
    }

    fun update(license: LicenseRenewal) {
        MemoryDataManager.update(license)
    }

    fun delete(id: String) {
        MemoryDataManager.remove(id)
    }

    fun getById(id: String): LicenseRenewal? {
        return MemoryDataManager.getById(id) as? LicenseRenewal
    }

    fun getAll(): List<LicenseRenewal> {
        return MemoryDataManager.getAll().filterIsInstance<LicenseRenewal>()
    }

    fun existsById(id: String): Boolean {
        return getById(id) != null
    }

    fun existsByUserId(userId: String, excludeId: String = ""): Boolean {
        return getAll().any { it.UserId.trim() == userId.trim() && it.ID != excludeId }
    }
}
