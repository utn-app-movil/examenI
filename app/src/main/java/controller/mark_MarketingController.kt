package cr.ac.utn.movil.controller

import cr.ac.utn.movil.data.MemoryDataManager
import cr.ac.utn.movil.identities.mark_MarketingCampaign

object mark_MarketingController {

    private val db = MemoryDataManager

    fun add(campaign: mark_MarketingCampaign) = db.add(campaign)

    fun update(campaign: mark_MarketingCampaign) = db.update(campaign)

    fun delete(id: String) = db.remove(id)

    fun getAll(): List<mark_MarketingCampaign> =
        db.getAll()
            .filterIsInstance<mark_MarketingCampaign>()
            .sortedBy { it.ID }

    fun getById(id: String): mark_MarketingCampaign? =
        db.getById(id) as? mark_MarketingCampaign

    fun generateNextId(): String {
        val existing = getAll().mapNotNull { it.ID.substringAfter("mark_").toIntOrNull() }
        val nextNumber = if (existing.isEmpty()) 1 else existing.max() + 1
        return "mark_$nextNumber"
    }
}