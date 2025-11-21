package cr.ac.utn.movil.identities

class LicenseRenewal : Identifier {
    private var _userId: String = ""
    private var _userName: String = ""
    private var _userEmail: String = ""
    private var _userPhone: String = ""
    private var _licenseType: String = ""
    private var _medicalOpinionCode: String = ""
    private var _currentScore: Int = 0
    private var _renewalDate: String = ""
    private var _renewalTime: String = ""

    constructor()

    constructor(
        id: String,
        userId: String,
        userName: String,
        userEmail: String,
        userPhone: String,
        licenseType: String,
        medicalOpinionCode: String,
        currentScore: Int,
        renewalDate: String,
        renewalTime: String
    ) {
        this.ID = id
        this._userId = userId
        this._userName = userName
        this._userEmail = userEmail
        this._userPhone = userPhone
        this._licenseType = licenseType
        this._medicalOpinionCode = medicalOpinionCode
        this._currentScore = currentScore
        this._renewalDate = renewalDate
        this._renewalTime = renewalTime
    }

    var UserId: String
        get() = this._userId
        set(value) { this._userId = value }

    var UserName: String
        get() = this._userName
        set(value) { this._userName = value }

    var UserEmail: String
        get() = this._userEmail
        set(value) { this._userEmail = value }

    var UserPhone: String
        get() = this._userPhone
        set(value) { this._userPhone = value }

    var LicenseType: String
        get() = this._licenseType
        set(value) { this._licenseType = value }

    var MedicalOpinionCode: String
        get() = this._medicalOpinionCode
        set(value) { this._medicalOpinionCode = value }

    var CurrentScore: Int
        get() = this._currentScore
        set(value) { this._currentScore = value }

    var RenewalDate: String
        get() = this._renewalDate
        set(value) { this._renewalDate = value }

    var RenewalTime: String
        get() = this._renewalTime
        set(value) { this._renewalTime = value }

    override val FullName: String
        get() = _userName

    override val FullDescription: String
        get() = "License: $_licenseType - User: $_userName - Score: $_currentScore - Date: $_renewalDate $_renewalTime"
}
