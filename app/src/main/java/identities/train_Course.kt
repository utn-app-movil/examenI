package identities

import cr.ac.utn.movil.identities.Identifier

class train_Course(
    private val _name: String
) : Identifier() {

    val name: String
        get() = _name

    override val FullName: String
        get() = _name

    override val FullDescription: String
        get() = "Course: $_name"
}