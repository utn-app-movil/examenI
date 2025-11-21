package cr.ac.utn.movil.entities

import cr.ac.utn.movil.identities.Identifier

class PayenInsuranceRequest : Identifier() {

    // ----- Person info -----
    var personId: String = ""
    var personName: String = ""
    var personEmail: String = ""

    // ----- Appointment info -----
    var specialty: String = ""
    var appointmentDate: String = ""
    var appointmentTime: String = ""

    // ----- Request info -----
    var requestDate: String = ""
    var requestTime: String = ""

    // ----- Amounts -----
    var appointmentAmount: Double = 0.0
    var medicinesTotalAmount: Double = 0.0

    // Medicines list (multiselect)
    var medicinesList: MutableList<String> = mutableListOf()

    // 80% refund
    val refundAmount: Double
        get() = (appointmentAmount + medicinesTotalAmount) * 0.80

    override val FullName: String
        get() = personName

    override val FullDescription: String
        get() = "$specialty - $appointmentDate $appointmentTime"
}
