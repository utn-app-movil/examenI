package interfaces

import cr.ac.utn.movil.identities.Identifier
import identities.fli_Reserva

interface fli_IReservas {
    fun add (obj: fli_Reserva)
    fun update (obj: fli_Reserva)
    fun remove (id: String)
    fun getAll(): List<fli_Reserva>
    fun getById(id: String): fli_Reserva?
}