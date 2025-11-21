package identities

import cr.ac.utn.movil.identities.Identifier
import java.time.LocalDateTime

open class Sinpe_sin:  Identifier {
    var originName: String = ""
    var originPhone: String = ""
    var destinationName: String = ""
    var destinationPhone: String = ""
    var amount: Double = 0.0
    var description: String = ""
    var dateTime: LocalDateTime = LocalDateTime.now()
}