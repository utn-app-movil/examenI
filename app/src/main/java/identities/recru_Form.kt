package identities

import cr.ac.utn.movil.identities.Identifier
import java.time.LocalDateTime

enum class Role {
    Sofware, Infraestructura, Redes, Seguridad, QA
}

class recru_Form() : Identifier() {

    private var _date: LocalDateTime = LocalDateTime.now()
    private var _name: String = ""
    private var _fLastName: String = ""
    private var _sLastName: String = ""
    private var _province: String = ""
    private var _state: String = ""
    private var _district: String = ""
    private var _company: String = ""
    private var _salary: Float = 0f
    private var _experience: Int = 0
    private var _roles: MutableList<Role> = mutableListOf()

    constructor(
        Date: LocalDateTime,
        Name: String,
        FLastName: String,
        SLastName: String,
        Experience: Int,
        Province: String,
        State: String,
        District: String,
        Company: String,
        Salary: Float,
        Roles: MutableList<Role>
    ) : this() {
        this._date = Date
        this._name = Name
        this._fLastName = FLastName
        this._sLastName = SLastName
        this._province = Province
        this._state = State
        this._district = District
        this._company = Company
        this._salary = Salary
        this._experience = Experience
        this._roles = Roles
    }

    var Date: LocalDateTime
        get() = _date
        set(value) { _date = value }

    var Name: String
        get() = _name
        set(value) { _name = value }

    var FLastName: String
        get() = _fLastName
        set(value) { _fLastName = value }

    var SLastName: String
        get() = _sLastName
        set(value) { _sLastName = value }

    var Experience: Int
        get() = _experience
        set(value) { _experience = value }

    var Province: String
        get() = _province
        set(value) { _province = value }

    var State: String
        get() = _state
        set(value) { _state = value }

    var District: String
        get() = _district
        set(value) { _district = value }

    var Company: String
        get() = _company
        set(value) { _company = value }

    var Salary: Float
        get() = _salary
        set(value) { _salary = value }

    var Roles: MutableList<Role>
        get() = _roles
        set(value) { _roles = value }

    override val FullName: String
        get() = "$Name $FLastName $SLastName"

    override val FullDescription: String
        get() = "$FullName | Empresa: $Company | Roles: ${Roles.joinToString()}"
}
