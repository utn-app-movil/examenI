package identities

import cr.ac.utn.movil.identities.Identifier

class train_TrainingEnrollment(
    val personName: String,
    val courses: List<String>,
    val date: String
) : Identifier() {

    override val FullName: String
        get() = personName

    override val FullDescription: String
        get() = "Courses: ${courses.joinToString()} - Date: $date"
}