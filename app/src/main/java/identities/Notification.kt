package identities

import cr.ac.utn.movil.identities.Identifier
import cr.ac.utn.movil.identities.Person
import java.time.LocalDate

class Notification: Identifier  {

    private lateinit var sender: Person // Datos de la persona que envía el correo.
    private var message: String = "" //Mensaje El contenido del cuerpo del correo, que debe ser claro y gramaticalmente correcto.
    private var title: String = "" //Título Asunto del correo.
    private lateinit var dateSent: LocalDate //Fecha de envío Debe ser una fecha posterior al día actual.
    private lateinit var cc: MutableList<Person> // Lista de destinatarios con copia
    private lateinit var cco: MutableList<Person> // Lista de destinatarios con copia oculta
    private lateinit var receivers: MutableList<Person> // Destinatarios Lista de personas seleccionadas a través de un sistema de selección múltiple

    constructor()
    constructor(
        sender: Person,
        tittle: String,
        message: String,
        cc: MutableList<Person>,
        dateSent: LocalDate,
        cco: MutableList<Person>,
        receivers: MutableList<Person>
    ) {
        this.sender = sender
        this.title = title
        this.message = message
        this.cc = cc
        this.dateSent = dateSent
        this.cco = cco
        this.receivers = receivers
    }

    var Sender: Person
        get() = this.sender
        set(value) {this.sender = value}

    var Message: String
        get() = this.message
        set(value) {this.message = value}

    var Title: String
        get() = this.title
        set(value) {this.title = value}

    var DateSent: LocalDate
        get() = this.dateSent
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


    override val FullDescription = "Sender: $Sender, Title: $Title, Message: $Message,  Date sent: $DateSent "

    override val FullName: String
        get() = TODO("Not yet implemented")
}
