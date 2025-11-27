package controller

import android.content.Context
import cr.ac.utn.movil.R
import cr.ac.utn.movil.data.MemoryDataManager
import cr.ac.utn.movil.interfaces.IDataManager
import identities.exch_ExchangeTransaction

class exch_ExTransactionController {
    private var dataManager: IDataManager = MemoryDataManager
    private var context: Context

    constructor(context: Context) {
        this.context = context
    }

    fun addTransaction(transaction: exch_ExchangeTransaction) {
        try {
            dataManager.add(transaction)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgAdd))
        }
    }


    //         UPDATE TRANSACTION
    fun updateTransaction(transaction: exch_ExchangeTransaction) {
        try {
            dataManager.update(transaction)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgUpdate))
        }
    }

    //         REMOVE TRANSACTION
    fun removeTransaction(id: String) {
        try {
            val result = dataManager.getById(id)
            if (result == null) {
                throw Exception(context.getString(R.string.MsgDataNoFound))
            }
            dataManager.remove(id)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgRemove))
        }
    }

    //        GET ALL TRANSACTIONS
    fun getAllTransactions(): List<exch_ExchangeTransaction> {
        try {
            return dataManager.getAll()
                .filterIsInstance<exch_ExchangeTransaction>()
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgGetAll))
        }
    }

    //        GET TRANSACTION BY ID
    fun getTransactionById(id: String): exch_ExchangeTransaction? {
        try {
            return dataManager.getById(id) as? exch_ExchangeTransaction
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgGetById))
        }
    }
}