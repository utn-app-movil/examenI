package controller

import android.content.Context
import cr.ac.utn.movil.R
import cr.ac.utn.movil.identities.Identifier
import data.fli_MemoryManagerReservas
import identities.fli_Reserva
import interfaces.fli_IReservas

class fli_ReservasController {
    private var dataManager: fli_IReservas = fli_MemoryManagerReservas()
    private lateinit var context: Context

    constructor(context: Context){
        this.context = context
    }

    fun addBooking(reserva: fli_Reserva){
        try {
            return dataManager.add(reserva)
        } catch(e: Exception){
            throw Exception(context.getString(R.string.ErrorMsgAdd))
        }
    }

    fun updatePerson(reserva: fli_Reserva){
        try {
            return dataManager.update(reserva)
        } catch (e: Exception){
            throw Exception(context.getString(R.string.ErrorMsgUpdate))
        }
    }

    fun getById(id: String): fli_Reserva?{
        try {
            return dataManager.getById(id)
        } catch (e: Exception){
            throw Exception(context.getString(R.string.ErrorMsgGetById))
        }
    }

    fun remove (id: String){
        try{
            val result = dataManager.getById(id)
            if(result == null){
                throw Exception(context.getString(R.string.MsgDataNoFound))
            }
            dataManager.remove(id)
        } catch (e: Exception){
            throw Exception(context.getString(R.string.ErrorMsgRemove))
        }
    }

    fun getAll(): List<fli_Reserva> = dataManager.getAll()

}