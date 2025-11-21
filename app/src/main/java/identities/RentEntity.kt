package cr.ac.utn.movil.identities

class RentEntity : Identifier() {

    var ClientName: String = ""
    var ClientId: String = ""
    var VehicleType: String = ""
    var Plate: String = ""
    var Mileage: Int = 0
    var License: String = ""

    override val FullName: String
        get() = ClientName

    override val FullDescription: String
        get() = "$ClientName - $VehicleType - $Plate"
}
