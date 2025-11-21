// app/src/main/java/cr/ac/utn/movil/entities/EnsPolicy.kt
package cr.ac.utn.movil.entities

import cr.ac.utn.movil.identities.Identifier

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

    // Para mostrar en la lista
    override fun toString(): String {
        return "Póliza $ID | $company | $insuranceType | $startDate → $endDate"
    }
}