package data

import cr.ac.utn.movil.controller.Recy_RecyclingController
import entity.Recy_RecyclingEntity

class Recy_RecyclingRepository {

    private val controller = Recy_RecyclingController()

    fun add(entity: Recy_RecyclingEntity) {
        controller.add(entity)
    }

    fun update(entity: Recy_RecyclingEntity) {
        controller.update(entity)
    }

    fun delete(id: String) {
        controller.delete(id)
    }

    // Solo existe getByMaterial en tu controller (NO getById, NO getByCampaign)
    fun getByMaterial(material: String): Recy_RecyclingEntity? {
        return controller.getByMaterial(material)
    }

    fun getAll(): List<Recy_RecyclingEntity> {
        return controller.getAll()
    }
}
