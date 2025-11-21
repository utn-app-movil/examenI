package identities

import java.time.LocalDateTime
import cr.ac.utn.movil.identities.Identifier

class AppointmentEntity(
    id: String,
    val patientName: String,
    val contact: String,
    val doctorName: String,
    val specialty: String,
    val dateTime: LocalDateTime
) : Identifier() {

    init {
        this.ID = id
    }

    override val FullDescription: String
        get() = "Appointment with Dr. $doctorName on $dateTime for $patientName"

    override val FullName: String
        get() = patientName

    override fun toString(): String {
        return "ID: $ID | Name: $patientName | Doctor: $doctorName | Specialty: $specialty | Date: $dateTime"
    }
}