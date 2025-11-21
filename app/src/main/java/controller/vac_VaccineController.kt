package cr.ac.utn.movil.controllers

import cr.ac.utn.movil.data.MemoryDataManager
import cr.ac.utn.movil.identities.vac_Vaccine
import cr.ac.utn.movil.interfaces.IDataManager
import java.time.LocalDateTime

class vac_VaccineController(
    private val vac_dataManager: IDataManager = MemoryDataManager
) {

    fun vac_GetAll(): List<vac_Vaccine> {
        return vac_dataManager.getAll().filterIsInstance<vac_Vaccine>()
    }

    fun vac_GetById(id: String): vac_Vaccine? {
        return vac_dataManager.getById(id) as? vac_Vaccine
    }

    fun vac_Save(vaccine: vac_Vaccine): vac_SaveResult {

        val dateTime = vaccine.vac_VaccineDateTime
        if (dateTime == null) {
            return vac_SaveResult.InvalidDateTime
        }

        // Validación: no se permite fecha/hora futura
        if (dateTime.isAfter(LocalDateTime.now())) {
            return vac_SaveResult.FutureDateTime
        }

        // Validación de duplicado: mismo paciente + misma fecha/hora
        val duplicated = vac_GetAll().any {
            it.vac_PatientIdNumber.trim().equals(
                vaccine.vac_PatientIdNumber.trim(),
                ignoreCase = true
            ) &&
                    it.vac_VaccineDateTime == vaccine.vac_VaccineDateTime &&
                    it.ID != vaccine.ID
        }

        if (duplicated) {
            return vac_SaveResult.Duplicated
        }

        // Nuevo registro vs actualización
        if (vaccine.ID.isBlank()) {
            vaccine.ID = vac_GenerateId()
            vac_dataManager.add(vaccine)
        } else {
            vac_dataManager.update(vaccine)
        }

        return vac_SaveResult.Success
    }

    fun vac_Delete(id: String): Boolean {
        val obj = vac_dataManager.getById(id)
        return if (obj is vac_Vaccine) {
            vac_dataManager.remove(id)
            true
        } else {
            false
        }
    }

    private fun vac_GenerateId(): String {
        return "vac_${System.currentTimeMillis()}"
    }
}

enum class vac_SaveResult {
    Success,
    InvalidDateTime,
    FutureDateTime,
    Duplicated
}
