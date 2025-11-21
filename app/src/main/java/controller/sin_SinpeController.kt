package controller

import android.content.Context
import cr.ac.utn.movil.R
import cr.ac.utn.movil.data.MemoryDataManager
import cr.ac.utn.movil.interfaces.IDataManager
import identities.sin_Sinpe
import java.time.LocalDateTime
import kotlin.collections.remove

class sin_SinpeController {
    private var dataManager : IDataManager = MemoryDataManager
    private  var context: Context

    constructor(context: Context){
        this.context=context
    }
       fun addSinpe(sinpe: sin_Sinpe) {

        if (sinpe.DestinationPhone.length != 8 ||
            !sinpe.DestinationPhone.all { it.isDigit() }) {
            throw Exception(context.getString(R.string.error_phone_destination_invalid))
        }

        if (sinpe.OriginPhone == sinpe.DestinationPhone) {
            throw Exception(context.getString(R.string.error_phone_same))
        }

        val now = LocalDateTime.now()
        if (sinpe.DateTime.isAfter(now)) {
            throw Exception(context.getString(R.string.error_date_future))
        }

        if (sinpe.Amount <= 0.0) {
            throw Exception(context.getString(R.string.error_amount_invalid))
        }

       dataManager.add(sinpe)
    }

     fun updateSinpe(sinpe: sin_Sinpe) {
        dataManager.update(sinpe)
    }

    fun deleteSinpe(id: String) {
        dataManager.remove(id)
    }

    fun getAll(): List<sin_Sinpe> {
        return dataManager.getAll()
            .filterIsInstance<sin_Sinpe>()
    }

    fun getById(id: String): sin_Sinpe? {
        val result = dataManager.getById(id)
        return if (result is sin_Sinpe) result else null
    }


}





    // DELETE


