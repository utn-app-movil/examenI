package identities

import cr.ac.utn.movil.identities.Identifier
import java.time.LocalDateTime

open class sin_Sinpe:  Identifier() {

    private var _originName: String = ""
    private var _originPhone: String = ""
    private var _destinationName: String = ""
    private var _destinationPhone: String = ""
    private var _amount: Double = 0.0
    private var _description: String = ""
    private var _dateTime: LocalDateTime = LocalDateTime.now()

    var OriginName: String
        get() = this._originName
        set(value) {this._originName = value}
    var OriginPhone: String
        get() = this._originPhone
        set(value) {this._originPhone = value}
    var DestinationName: String
        get() = this._destinationName
        set(value) {this._destinationName = value}
    var DestinationPhone: String
        get() = this._destinationPhone
        set(value) {this._destinationPhone = value}

    var Amount: Double
        get() = this._amount
        set(value) {this._amount = value}
    var Description: String
        get() = this._description
        set(value) {this._description = value}
    var DateTime: LocalDateTime
        get() = this._dateTime
        set(value) {this._dateTime = value}



    override val FullDescription: String
        get() = "From: $_originName ($_originPhone) To: $_destinationName - Amount: $_amount"

    override val FullName: String
        get() = _originName


}