package cr.ac.utn.movil.identities

class Payroll : Identifier() {
    private var _name: String = ""
    private var _firstLastName: String = ""
    private var _secondLastName: String = ""
    private var _phone: String = ""
    private var _email: String = ""
    private var _employeeNumber: String = ""
    private var _position: String = ""
    private var _salary: Double = 0.0
    private var _ibanAccount: String = ""
    private var _paymentMonth: Int = 0
    private var _paymentYear: Int = 0
    private var _bankName: String = ""

    var Name: String
        get() = this._name
        set(value) { this._name = value }

    var FirstLastName: String
        get() = this._firstLastName
        set(value) { this._firstLastName = value }

    var SecondLastName: String
        get() = this._secondLastName
        set(value) { this._secondLastName = value }

    var Phone: String
        get() = this._phone
        set(value) { this._phone = value }

    var Email: String
        get() = this._email
        set(value) { this._email = value }

    var EmployeeNumber: String
        get() = this._employeeNumber
        set(value) { this._employeeNumber = value }

    var Position: String
        get() = this._position
        set(value) { this._position = value }

    var Salary: Double
        get() = this._salary
        set(value) { this._salary = value }

    var IbanAccount: String
        get() = this._ibanAccount
        set(value) { this._ibanAccount = value }

    var PaymentMonth: Int
        get() = this._paymentMonth
        set(value) { this._paymentMonth = value }

    var PaymentYear: Int
        get() = this._paymentYear
        set(value) { this._paymentYear = value }

    var BankName: String
        get() = this._bankName
        set(value) { this._bankName = value }

    override val FullName: String
        get() = "$_name $_firstLastName $_secondLastName"

    override val FullDescription: String
        get() = "Employee: $FullName - Position: $_position - Salary: $_salary - Bank: $_bankName"
}
