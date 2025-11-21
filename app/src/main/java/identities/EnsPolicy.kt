package identities


class EnsPolicy : Identifier() {
    var company: String = ""
    var insuranceType: String = ""
    var startDate: String = ""       // Formato: "yyyy-MM-dd"
    var endDate: String = ""
    var premium: Double = 0.0

    override val FullDescription: String
        get() = "$company - $insuranceType (Prima: $$premium)"

    override val FullName: String
        get() = "Póliza $ID"

    override fun toString(): String {
        return "Póliza $ID | $company | $insuranceType | $startDate → $endDate"
    }
}
