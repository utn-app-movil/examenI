package entity

import cr.ac.utn.movil.identities.Identifier

data class DashContribution(
    val person: String,
    val contributions: Int,
    val day: Int,
    val month: Int,
    val year: Int
) : Identifier() {

    init {
        // The requirement is that a person cannot be duplicated in the same month and year.
        // We will use this combination as the unique ID.
        ID = "$person-$month-$year"
    }

    override val FullDescription: String
        get() = "$person made $contributions contributions on $day/$month/$year"

    override val FullName: String
        get() = person
}