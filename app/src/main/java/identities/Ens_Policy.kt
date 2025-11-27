package cr.ac.utn.movil.identities

class Ens_Policy : Identifier() {

    var policyNumber: String = ""
    var company: String = ""
    var insuranceType: String = ""
    var startDate: String = ""
    var endDate: String = ""
    var premium: Double = 0.0

    override val FullName: String
        get() = policyNumber

    override val FullDescription: String
        get() = "$policyNumber - $company - $insuranceType"
}