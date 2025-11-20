package cr.ac.utn.movil.identities

open class Person: Identifier {
    private var _name: String =""
    private var _fLastName: String=""
    private var _sLastName: String=""
    private var _phone: Int = 0
    private var _email: String=""
    private var _address: String=""
    private var _country: String=""

    constructor()

    var Name: String
        get() = this._name
        set(value) {this._name = value}

    var FLastName: String
        get() = this._fLastName
        set(value) {this._fLastName = value}

    var SLastName: String
        get() = this._sLastName
        set(value) {this._sLastName = value}

    override val FullName = "$Name $FLastName $SLastName"

    override val FullDescription: String
        get() = TODO("Not yet implemented")

    var Phone: Int
        get() = this._phone
        set(value) {this._phone = value}

    var Email: String
        get() = this._email
        set(value) {this._email = value}

    var Address: String
        get() = this._address
        set(value) {this._address = value}

    var Country: String
        get() = this._country
        set(value) {this._country = value}
}