package cr.ac.utn.movil.identities

class mark_MarketingCampaign : Identifier() {

    var campaignName: String = ""
    var budget: Double = 0.0
    var startDate: String = ""
    var endDate: String = ""
    var channel: String = ""
    var leaderFullName: String = ""
    var province: String = ""

    override val FullName: String
        get() = campaignName.ifBlank { "Campaign ${ID}" }

    override val FullDescription: String
        get() = "$campaignName | $channel | ₡${String.format("%.2f", budget)} | $province"
}