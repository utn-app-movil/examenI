package cr.ac.utn.movil.identities

import java.time.LocalDateTime

class vac_Vaccine : Identifier() {

    private var _patientName: String = ""
    private var _patientIdNumber: String = ""
    private var _vaccineType: String = ""
    private var _vaccineSite: String = ""
    private var _vaccineDateTime: LocalDateTime? = null

    var vac_PatientName: String
        get() = this._patientName
        set(value) { this._patientName = value }

    var vac_PatientIdNumber: String
        get() = this._patientIdNumber
        set(value) { this._patientIdNumber = value }

    var vac_VaccineType: String
        get() = this._vaccineType
        set(value) { this._vaccineType = value }

    var vac_VaccineSite: String
        get() = this._vaccineSite
        set(value) { this._vaccineSite = value }

    var vac_VaccineDateTime: LocalDateTime?
        get() = this._vaccineDateTime
        set(value) { this._vaccineDateTime = value }

    override val FullName: String
        get() = "$vac_PatientName - $vac_VaccineType"

    override val FullDescription: String
        get() =
            "Patient: $vac_PatientName ($vac_PatientIdNumber) - Vaccine: $vac_VaccineType at $vac_VaccineSite on $vac_VaccineDateTime"
}
