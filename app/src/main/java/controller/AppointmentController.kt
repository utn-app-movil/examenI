package controllers

import cr.ac.utn.movil.data.MemoryDataManager
import identities.AppointmentEntity

class AppointmentsController {

    fun addAppointment(appointment: AppointmentEntity): Boolean {

        val existing = MemoryDataManager.getById(appointment.ID)
        if (existing != null) return false


        val duplicates = getAppointments().any {
            it.patientName == appointment.patientName && it.dateTime == appointment.dateTime
        }
        if (duplicates) return false

        MemoryDataManager.add(appointment)
        return true
    }

    fun getAppointments(): List<AppointmentEntity> {
        return MemoryDataManager.getAll().filterIsInstance<AppointmentEntity>()
    }

    fun updateAppointment(appointment: AppointmentEntity): Boolean {
        MemoryDataManager.update(appointment)
        return true
    }

    fun deleteAppointment(id: String) {
        MemoryDataManager.remove(id)
    }
}