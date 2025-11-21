package cr.ac.utn.movil.identities

import java.time.LocalDateTime

// Representa una puja (bidding) hecha por una persona
class Bidding_bid : Person() {

    var BidDateTime: LocalDateTime = LocalDateTime.now()
    var BidAmount: Double = 0.0
    var ArticleDescription: String = ""
    var IsAwarded: Boolean = false

    // Texto que usaremos para mostrar esta puja en las listas
    override val FullDescription: String
        get() = "$FullName - $ArticleDescription - $BidAmount"
}
