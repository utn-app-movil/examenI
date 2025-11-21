package cr.ac.utn.movil.identities

class Event : Identifier() {

    private var _name: String = ""
    private var _fLastName: String = ""
    private var _sLastName: String = ""
    private var _phone: Int = 0
    private var _email: String = ""
    private var _address: String = ""
    private var _country: String = ""

    var Name: String
        get() = this._name
        set(value) { this._name = value }

    var FLastName: String
        get() = this._fLastName
        set(value) { this._fLastName = value }

    var SLastName: String
        get() = this._sLastName
        set(value) { this._sLastName = value }

    override val FullName: String
        get() = "$Name $FLastName $SLastName"

    var Phone: Int
        get() = this._phone
        set(value) { this._phone = value }

    var Email: String
        get() = this._email
        set(value) { this._email = value }

    var Address: String
        get() = this._address
        set(value) { this._address = value }

    var Country: String
        get() = this._country
        set(value) { this._country = value }

    // Propiedades del evento
    private var _institution: String = ""
    private var _eventLocation: String = ""
    private var _eventDate: String = ""
    private var _eventTime: String = ""
    private var _seatNumber: Int = 0
    private var _eventType: String = ""

    var Institution: String
        get() = this._institution
        set(value) { this._institution = value }

    var EventLocation: String
        get() = this._eventLocation
        set(value) { this._eventLocation = value }

    var EventDate: String
        get() = this._eventDate
        set(value) { this._eventDate = value }

    var EventTime: String
        get() = this._eventTime
        set(value) { this._eventTime = value }

    var SeatNumber: Int
        get() = this._seatNumber
        set(value) { this._seatNumber = value }

    var EventType: String
        get() = this._eventType
        set(value) { this._eventType = value }

    override val FullDescription: String
        get() = "$FullName - $Institution - $EventType at $EventLocation on $EventDate $EventTime - Seat: $SeatNumber"
}