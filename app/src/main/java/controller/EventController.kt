package cr.ac.utn.movil.controller

import cr.ac.utn.movil.data.MemoryDataManager
import cr.ac.utn.movil.identities.Event

class EventController {

    fun add(event: Event): Boolean {
        // Validate duplicated seat
        if (isSeatDuplicated(event)) return false

        MemoryDataManager.add(event)
        return true
    }

    fun update(event: Event): Boolean {
        if (isSeatDuplicated(event, isUpdate = true)) return false

        MemoryDataManager.update(event)
        return true
    }

    fun delete(id: String) {
        MemoryDataManager.remove(id)
    }

    fun getById(id: String): Event? {
        return MemoryDataManager.getById(id) as? Event
    }

    fun getAll(): List<Event> {
        return MemoryDataManager.getAll().filterIsInstance<Event>()
    }

    private fun isSeatDuplicated(event: Event, isUpdate: Boolean = false): Boolean {
        val list = getAll()

        return list.any {
            it.EventLocation.equals(event.EventLocation, ignoreCase = true) &&
                    it.EventDate == event.EventDate &&
                    it.EventTime == event.EventTime &&
                    it.SeatNumber == event.SeatNumber &&
                    (!isUpdate || it.ID != event.ID)
        }
    }
}
