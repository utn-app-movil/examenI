package controller

import android.content.Context
import cr.ac.utn.movil.R
import cr.ac.utn.movil.data.MemoryDataManager
import cr.ac.utn.movil.identities.Identifier
import cr.ac.utn.movil.interfaces.IDataManager
import identities.Notification  //

class NotificationController(private val context: Context) {

    private var dataManager: IDataManager = MemoryDataManager

    fun createNotification(notification: Notification){
        try {
            dataManager.add(notification)
        } catch (e: Exception){
            throw Exception(context.getString(R.string.ErrorMsgAdd_notif))
        }
    }

    fun updateNotification(notification: Notification){
        try {
            dataManager.update(notification)
        } catch (e: Exception){
            throw Exception(context.getString(R.string.ErrorMsgUpdate_notif))
        }
    }

    fun getNotifications(): List<Identifier>{
        try {
            return dataManager.getAll()
        } catch (e: Exception){
            throw Exception(context.getString(R.string.ErrorMsgGetAll))
        }
    }

    fun getNotificationById(id: String): Notification {
        try {
            val result = dataManager.getById(id)
            if (result == null){
                throw Exception("pe")
            }

            return result as? Notification
                ?: throw Exception("rrp")
        } catch (e: Exception){
            throw Exception(context.getString(R.string.ErrorMsgGetById))
        }
    }

    fun deleteNotification(id: String) {
        try {
            dataManager.remove(id)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgRemove_notif))
        }
    }
}