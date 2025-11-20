package cr.ac.utn.movil.data

import cr.ac.utn.movil.interfaces.IDataManager
import cr.ac.utn.movil.identities.Identifier

object MemoryDataManager: IDataManager {
    private var objectList = mutableListOf<Identifier>()
    override fun add(obj: Identifier) {
        objectList.add(obj)
    }

    override fun remove(id: String) {
        objectList.removeIf { it.ID.trim() == id.trim() }
    }

    override fun update(obj: Identifier) {
        remove(obj.ID)
        add(obj)
    }

    override fun getAll()= objectList

    override fun getById(id: String): Identifier? {
        val result = objectList.
        filter { it.ID.trim() == id.trim()}
        return if(result.any()) result[0] else null
    }
}