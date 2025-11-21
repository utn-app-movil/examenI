package data

import cr.ac.utn.movil.identities.Identifier
import identities.fli_Reserva
import interfaces.fli_IReservas

class fli_MemoryManagerReservas: fli_IReservas {
    private var reservas = mutableListOf<fli_Reserva>()
    override fun add(obj: fli_Reserva) {
        reservas.add(obj)
    }

    override fun remove(id: String) {
        reservas.removeIf { it.ID.trim() == id.trim() }
    }

    override fun update(obj: fli_Reserva) {
        remove(obj.ID)
        add(obj)
    }

    override fun getAll()= reservas

    override fun getById(id: String): fli_Reserva? {
        val result = reservas.
        filter { it.ID.trim() == id.trim()}
        return if(result.any()) result[0] else null
    }
}