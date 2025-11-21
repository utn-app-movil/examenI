package cr.ac.utn.movil.interfaces

import cr.ac.utn.movil.identities.Identifier

interface IDataManager {
    fun add (obj: Identifier)
    fun update (obj: Identifier)
    fun remove (id: String)
    fun getAll(): List<Identifier>
    fun getById(id: String): Identifier?
}