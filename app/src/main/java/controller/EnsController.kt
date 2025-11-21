
package controller

import data.MemoryDataManager
import identities.EnsPolicy

object EnsController {
    fun add(policy: EnsPolicy) = MemoryDataManager.add(policy)
    fun update(policy: EnsPolicy) = MemoryDataManager.update(policy)
    fun delete(id: String) = MemoryDataManager.remove(id)
    fun getAll(): List<EnsPolicy> = MemoryDataManager.getAll().filterIsInstance<EnsPolicy>()
    fun getById(id: String) = MemoryDataManager.getById(id) as? EnsPolicy

    fun isPolicyNumberUnique(policyNumber: String, excludeId: String? = null): Boolean {
        return getAll().none {
            it.ID.trim() == policyNumber.trim() && (excludeId == null || it.ID != excludeId)
        }
    }
}