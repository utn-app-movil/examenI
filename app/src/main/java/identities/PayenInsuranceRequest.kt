package cr.ac.utn.movil.entities

import cr.ac.utn.movil.identities.Identifier

class PayenInsuranceRequest : Identifier() {


    var personId: String = ""
    var personName: String = ""
    var personEmail: String = ""


    var specialty: String = ""
    var appointmentDate: String = ""
    var appointmentTime: String = ""


    var requestDate: String = ""
    var requestTime: String = ""


    var appointmentAmount: Double = 0.0
    var medicinesTotalAmount: Double = 0.0


    var medicinesList: MutableList<String> = mutableListOf()


    val refundAmount: Double
        get() = (appointmentAmount + medicinesTotalAmount) * 0.80

    override val FullName: String
        get() = personName

    override val FullDescription: String
        get() = "$specialty - $appointmentDate $appointmentTime"
}
