package identities

import cr.ac.utn.movil.identities.Identifier

class train_Person(
    private val _fullName: String
) : Identifier() {

    override val FullName: String
        get() = _fullName

    override val FullDescription: String
        get() = "Person: $_fullName"
}