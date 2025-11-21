package cr.ac.utn.movil.identities


import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class LibReservation : Identifier() {


    var studentDetails: String = ""
    var reservedBooks: List<String> = emptyList()
    var reservationDateTime: LocalDateTime? = null
    var returnDate: LocalDateTime? = null
    var libraryBranch: String = ""

    init {
        if (ID.isEmpty()) {
            ID = UUID.randomUUID().toString()
        }
    }

    override val FullName: String
        get() = "$studentDetails - ${libraryBranch}"

    override val FullDescription: String
        get() {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
            val resDate = reservationDateTime?.format(formatter) ?: "N/A"
            val retDate = returnDate?.format(formatter) ?: "N/A"
            return "Reserved: ${reservedBooks.joinToString(", ")} | Date: $resDate | Return: $retDate"
        }


    fun getUniqueKey(): String {
        return "${studentDetails.trim().uppercase()}-${reservationDateTime.toString()}"
    }
}