package cr.ac.utn.movil.controller

import entity.Recy_RecyclingEntity

class Recy_RecyclingController {

    private val list = mutableListOf<Recy_RecyclingEntity>()

    // CREATE
    fun add(entity: Recy_RecyclingEntity) {
        list.add(entity)
    }

    // READ - Buscar por material
    fun getByMaterial(material: String): Recy_RecyclingEntity? {
        return list.find { it.materialType.equals(material, ignoreCase = true) }
    }

    // UPDATE
    fun update(entity: Recy_RecyclingEntity) {
        val index = list.indexOfFirst { it.ID == entity.ID }
        if (index != -1) {
            list[index] = entity
        }
    }

    // DELETE
    fun delete(id: String) {
        val index = list.indexOfFirst { it.ID == id }
        if (index != -1) {
            list.removeAt(index)
        }
    }

    // EXTRA — Obtener todo
    fun getAll(): List<Recy_RecyclingEntity> = list
}
