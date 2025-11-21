package controller

import android.content.Context
import cr.ac.utn.movil.R
import cr.ac.utn.movil.data.MemoryDataManager
import cr.ac.utn.movil.identities.Identifier
import cr.ac.utn.movil.interfaces.IDataManager
import identities.prod_ProductionOrder

class prod_ProductionOrderController {
    private var dataManager: IDataManager = MemoryDataManager

    private var context: Context

    constructor(context: Context){
        this.context = context
    }

    fun addProductionOrder(productionOrder: prod_ProductionOrder) {
        try {
            dataManager.add(productionOrder)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgAdd))
        }
    }

    fun updateProductionOrder(productionOrder: prod_ProductionOrder) {
        try {
            dataManager.update(productionOrder)
        } catch (e: Exception){
            throw Exception(context.getString(R.string.ErrorMsgUpdate))
        }
    }

    fun getProductionOrder(): List<prod_ProductionOrder> {
        try {
            return dataManager.getAll() as List<prod_ProductionOrder>
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgGetAll))
        }
    }

    fun getById(id: String) : prod_ProductionOrder? {
        try {
            return dataManager.getById(id) as prod_ProductionOrder?
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgGetById))
        }
    }

    fun removeProductionOrder(id: String) {
        try {
            val result = dataManager.getById(id)
            if (result == null) {
                throw Exception(context.getString(R.string.ErrorMsgRemove))
            }
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgRemove))
        }
    }
}