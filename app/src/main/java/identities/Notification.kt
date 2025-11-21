package identities

import cr.ac.utn.movil.identities.Identifier
import cr.ac.utn.movil.identities.Person
import java.time.LocalDate

class Notification: Identifier  {

    private var sender: Person? = null
    private var message: String = ""
    private var title: String = ""
    private var dateSent: LocalDate? = null
    private var cc: MutableList<Person> = mutableListOf()
    private var cco: MutableList<Person> = mutableListOf()
    private var receivers: MutableList<Person> = mutableListOf()

    constructor()

    constructor(
        id: String,
        sender: Person,
        title: String,
        message: String,
        cc: MutableList<Person>,
        dateSent: LocalDate,
        cco: MutableList<Person>,
        receivers: MutableList<Person>
    ) {
        this.ID = id
        this.sender = sender
        this.title = title
        this.message = message
        this.cc = cc
        this.dateSent = dateSent
        this.cco = cco
        this.receivers = receivers
    }

    var Sender: Person
        get() = this.sender ?: Person()
        set(value) {this.sender = value}

    var Message: String
        get() = this.message
        set(value) {this.message = value}

    var Title: String
        get() = this.title
        set(value) {this.title = value}

    var DateSent: LocalDate
        get() = this.dateSent ?: LocalDate.now()
        set(value) {this.dateSent = value}

    var Cc: MutableList<Person>
        get() = this.cc
        set(value) {this.cc = value}

    var Cco: MutableList<Person>
        get() = this.cco
        set(value) {this.cco = value}

    var Receivers: MutableList<Person>
        get() = this.receivers
        set(value) {this.receivers = value}

    override val FullDescription: String
        get() = "Sender: ${sender?.Name ?: "N/A"}, Title: $Title, Message: $Message, Date sent: ${dateSent ?: "N/A"}"

    override val FullName: String
        get() = Title
}