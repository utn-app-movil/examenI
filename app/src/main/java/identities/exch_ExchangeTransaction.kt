package identities

import cr.ac.utn.movil.identities.Identifier
import java.time.LocalDateTime

class exch_ExchangeTransaction: Identifier{
    private var exch_personName: String = ""
    private var exch_personId: String = ""
    private lateinit var exch_dateTime: LocalDateTime
    private var exch_exchangeRate: Double = 0.0
    private var exch_amountToExchange: Double = 0.0
    private var exch_operationType: String = ""
    private var exch_bankEntity: String = ""

    constructor()

    var exch_PersonName: String
        get() = this.exch_personName
        set(value) { this.exch_personName = value }

    var exch_PersonId: String
        get() = this.exch_personId
        set(value) { this.exch_personId = value }

    var exch_DateTime: LocalDateTime
        get() = this.exch_dateTime
        set(value) { this.exch_dateTime = value }

    var exch_ExchangeRate: Double
        get() = this.exch_exchangeRate
        set(value) { this.exch_exchangeRate = value }

    var AmountToExchange: Double
        get() = this.exch_amountToExchange
        set(value) { this.exch_amountToExchange = value }

    var exch_OperationType: String
        get() = this.exch_operationType
        set(value) { this.exch_operationType = value }

    var exch_BankEntity: String
        get() = this.exch_bankEntity
        set(value) { this.exch_bankEntity = value }

    override val FullName: String
        get() = exch_personName

    override val FullDescription: String
        get() = "Transaction by $exch_personName ($exch_personId) at $exch_bankEntity"

}
