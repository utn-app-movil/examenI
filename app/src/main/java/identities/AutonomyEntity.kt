package cr.ac.utn.movil.identities

open class Autonomy: Identifier {
    private var _vehicleId: String = ""
    private var _chargeDate: String = ""   // MM-dd-yyyy
    private var _estimatedRangeKm: Int = 0
    private var _batteryInitialPercent: Int = 0
    private var _batteryFinalPercent: Int = 0
    private var _vehicleBrand: String = ""
    private var _vehicleOwner: String = ""
    private var _vehicleType: String = ""  // sedan, cargo, suv, van

    constructor()

    var VehicleId: String
        get() = this._vehicleId
        set(value) { this._vehicleId = value }

    var ChargeDate: String
        get() = this._chargeDate
        set(value) { this._chargeDate = value }

    var EstimatedRangeKm: Int
        get() = this._estimatedRangeKm
        set(value) { this._estimatedRangeKm = value }

    var BatteryInitialPercent: Int
        get() = this._batteryInitialPercent
        set(value) { this._batteryInitialPercent = value }

    var BatteryFinalPercent: Int
        get() = this._batteryFinalPercent
        set(value) { this._batteryFinalPercent = value }

    var VehicleBrand: String
        get() = this._vehicleBrand
        set(value) { this._vehicleBrand = value }

    var VehicleOwner: String
        get() = this._vehicleOwner
        set(value) { this._vehicleOwner = value }

    var VehicleType: String
        get() = this._vehicleType
        set(value) { this._vehicleType = value }


    override val FullName: String
        get() = "$VehicleBrand $VehicleId"

    override val FullDescription: String
        get() = TODO("Not yet implemented")
}
