package controller

import android.content.Context
import cr.ac.utn.movil.R
import cr.ac.utn.movil.data.MemoryDataManager
import cr.ac.utn.movil.interfaces.IDataManager
import identities.Notification

class NotificationController {

    private var dataManager: IDataManager = MemoryDataManager
    private var context: Context

    constructor(context: Context){
        this.context=context
    }

    fun createNotification(notification: Notification){
        try {
            dataManager.add(notification)
        }catch (e: Exception){
            throw Exception(context.getString(R.string))
        }
    }

}