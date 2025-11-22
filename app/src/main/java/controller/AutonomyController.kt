package cr.ac.utn.movil.controllers

import cr.ac.utn.movil.identities.Autonomy
import cr.ac.utn.movil.data.MemoryDataManager

class AutonomyController {

    fun list(): List<Autonomy> {
        return MemoryDataManager.getAll()
            .filterIsInstance<Autonomy>()
    }

    fun findById(id: String): Autonomy? {
        val result = MemoryDataManager.getById(id)
        return if (result is Autonomy) result else null
    }

    fun create(entity: Autonomy) {
        validate(entity)
        checkDuplicate(entity)
        MemoryDataManager.add(entity)
    }

    fun update(entity: Autonomy) {
        validate(entity)
        MemoryDataManager.update(entity)
    }

    fun delete(id: String) {
        MemoryDataManager.remove(id)
    }

    private fun validate(entity: Autonomy) {
        require(entity.EstimatedRangeKm > 0) {
            "Estimated range must be greater than 0 km"
        }
        require(entity.BatteryFinalPercent <= 100) {
            "Final battery percentage cannot exceed 100%"
        }
        require(entity.BatteryInitialPercent in 0..100) {
            "Initial battery percentage must be between 0 and 100"
        }
        require(entity.BatteryFinalPercent in 0..100) {
            "Final battery percentage must be between 0 and 100"
        }
        require(entity.VehicleBrand.isNotBlank()) {
            "Vehicle brand is required"
        }
        require(entity.VehicleOwner.isNotBlank()) {
            "Owner name is required"
        }
        require(entity.VehicleType.isNotBlank()) {
            "Vehicle type is required"
        }
        require(entity.VehicleId.isNotBlank()) {
            "Vehicle ID is required"
        }
        require(entity.ChargeDate.isNotBlank()) {
            "Charge date is required"
        }
    }

    private fun checkDuplicate(entity: Autonomy) {
        val exists = list().any {
            it.VehicleId.trim() == entity.VehicleId.trim() &&
                    it.ChargeDate.trim() == entity.ChargeDate.trim()
        }
        if (exists) {
            throw IllegalArgumentException("Duplicate autonomy record for vehicle and date")
        }
    }
}
