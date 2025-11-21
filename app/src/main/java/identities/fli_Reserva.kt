package identities

import cr.ac.utn.movil.identities.Identifier
import cr.ac.utn.movil.identities.Person
import java.time.LocalDate
import java.time.LocalDateTime

class fli_Reserva: Person {
    private var _originCountry:String=""
    private var _destinationCountry:String=""
    private var _flyNumber: String = ""
    private lateinit var _date: LocalDateTime

    constructor()

    var OriginCountry: String
        get() = this._originCountry
        set(value) {this._originCountry = value}

    var DestinationCountry: String
        get() = this._destinationCountry
        set(value) {this._destinationCountry = value}

    var FlyNumber: String
        get() = this._flyNumber
        set(value) {this._flyNumber = value}

    var Date: LocalDateTime
        get() = this._date
        set(value) {this._date = value}

}