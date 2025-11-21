package cr.ac.utn.movil.identities

abstract class Identifier {
    private var _id: String =""

    var ID: String
        get() = this._id
        set(value) {this._id = value}

    abstract val FullDescription: String
    abstract val FullName: String
}