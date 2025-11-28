package controller

import entity.Recy_RecyclingEntity
import cr.ac.utn.movil.data.MemoryDataManager
import cr.ac.utn.movil.identities.Identifier

class Recy_RecyclingController {

    fun add(entity: Recy_RecyclingEntity): Boolean {
        val exists: Identifier? = MemoryDataManager.getById(entity.ID)
        if (exists != null) return false // No permitir duplicados
        MemoryDataManager.add(entity)
        return true
    }

    fun update(entity: Recy_RecyclingEntity): Boolean {
        val exists: Identifier? = MemoryDataManager.getById(entity.ID)
        if (exists == null) return false // Solo si existe
        MemoryDataManager.update(obj = entity)
        return true
    }

    fun delete(id: String): Boolean {
        val exists: Identifier? = MemoryDataManager.getById(id)
        if (exists == null) return false
        MemoryDataManager.remove(id)
        return true
    }

    fun getById(id: String): Recy_RecyclingEntity? {
        return MemoryDataManager.getById(id) as? Recy_RecyclingEntity
    }

    fun getAll(): List<Recy_RecyclingEntity> {
        return MemoryDataManager.getAll()
            .filterIsInstance<Recy_RecyclingEntity>()
    }

    fun generateNextId(): String {
        val numbers = getAll()
            .mapNotNull { it.ID.removePrefix("recy_").toIntOrNull() }

        val next = if (numbers.isEmpty()) 1 else numbers.max() + 1
        return "recy_$next"
    }
}
