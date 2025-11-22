package Controller


import identities.cli_GestionClientes
import android.content.Context
import cr.ac.utn.movil.R

import cr.ac.utn.movil.data.MemoryDataManager

class cli_GesClientesCotroller(private val context: Context) {

    fun addClient(client: cli_GestionClientes) {
        try {
            MemoryDataManager.add(client)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgAdd))
        }
    }

    fun updateClient(client: cli_GestionClientes) {
        try {
            MemoryDataManager.update(client)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgUpdate))
        }
    }

    fun getById(id: String): cli_GestionClientes? {
        return MemoryDataManager.getById(id) as? cli_GestionClientes
    }

    fun removeClient(id: String) {
        try {
            val obj = MemoryDataManager.getById(id)
            if (obj == null) {
                throw Exception(context.getString(R.string.MsgDataNoFound))
            }
            MemoryDataManager.remove(id)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgRemove))
        }
    }


    fun getAll(): List<cli_GestionClientes> {
        return MemoryDataManager.getAll()
            .filterIsInstance<cli_GestionClientes>()
    }
}
