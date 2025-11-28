package entity

import cr.ac.utn.movil.identities.Identifier

data class Recy_RecyclingEntity(
    val campaignName: String,
    val materialType: String,
    val quantityKg: Double,
    val date: String,
    val personName: String,
    val companyName: String
) : Identifier() {

    init {
        // Unique ID: campaign + person + date (to avoid duplicates)
        ID = "$campaignName-$personName-$date"
    }

    override val FullDescription: String
        get() = "Campaign: $campaignName | Material: $materialType | Quantity: $quantityKg kg | Date: $date | Person: $personName | Company: $companyName"

    override val FullName: String
        get() = campaignName
}