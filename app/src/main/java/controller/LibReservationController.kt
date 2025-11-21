package cr.ac.utn.movil.controller


import cr.ac.utn.movil.data.MemoryDataManager
import cr.ac.utn.movil.identities.Identifier
import cr.ac.utn.movil.interfaces.IDataManager
import cr.ac.utn.movil.identities.LibReservation


class LibReservationController(private val dataManager: IDataManager = MemoryDataManager) {


    private fun isDuplicate(reservation: LibReservation): Boolean {

        return dataManager.getAll()
            .filterIsInstance<LibReservation>()
            .any { it.getUniqueKey() == reservation.getUniqueKey() && it.ID != reservation.ID }
    }

    fun addReservation(reservation: LibReservation): Boolean {
        if (isDuplicate(reservation)) {
            return false
        }
        dataManager.add(reservation as Identifier)
        return true
    }

    fun updateReservation(reservation: LibReservation): Boolean {
        if (isDuplicate(reservation)) {
            return false
        }
        dataManager.update(reservation as Identifier)
        return true
    }

    fun removeReservation(id: String) {
        dataManager.remove(id)
    }

    fun getAllReservations(): List<LibReservation> {
        return dataManager.getAll().filterIsInstance<LibReservation>()
    }

    fun getReservationById(id: String): LibReservation? {
        return dataManager.getById(id) as? LibReservation
    }


    fun validateDates(reservation: LibReservation): Boolean {
        val resDate = reservation.reservationDateTime
        val retDate = reservation.returnDate


        return if (resDate != null && retDate != null) {
            retDate.isAfter(resDate)
        } else {
            false
        }
    }
}