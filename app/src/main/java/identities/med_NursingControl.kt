package cr.ac.utn.movil.identities

import java.time.LocalDateTime

class med_NursingControl : Identifier() {

    private var _patientName: String = ""
    private var _bloodPressure: String = ""
    private var _weightKg: Double = 0.0
    private var _heightCm: Double = 0.0
    private var _oxygenation: Int = 0
    private var _visitDateTime: LocalDateTime? = null

    var PatientName: String
        get() = _patientName
        set(value) { _patientName = value }

    var BloodPressure: String
        get() = _bloodPressure
        set(value) { _bloodPressure = value }

    var WeightKg: Double
        get() = _weightKg
        set(value) { _weightKg = value }

    var HeightCm: Double
        get() = _heightCm
        set(value) { _heightCm = value }

    var Oxygenation: Int
        get() = _oxygenation
        set(value) { _oxygenation = value }

    var VisitDateTime: LocalDateTime?
        get() = _visitDateTime
        set(value) { _visitDateTime = value }

    override val FullDescription: String
        get() = "BP: $BloodPressure, O2: $Oxygenation%, DateTime: ${_visitDateTime ?: ""}"

    override val FullName: String
        get() = PatientName
}
