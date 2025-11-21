package identities

import cr.ac.utn.movil.identities.Identifier

class cli_GestionClientes() : Identifier() {

    override val FullDescription: String
        get() = "$CompanyName - $Province, $Canton, $District"

    override val FullName: String
        get() = CompanyName

    private var companyName: String = ""
    private var legalId: String = ""
    private var province: String = ""
    private var canton: String = ""
    private var district: String = ""
    private var services: MutableList<String> = mutableListOf()



    constructor(
        id: String,
        companyName: String,
        legalId: String,
        province: String,
        canton: String,
        district: String,
        services: MutableList<String>
    ) : this() {

        this.ID = id
        this.companyName = companyName
        this.legalId = legalId
        this.province = province
        this.canton = canton
        this.district = district
        this.services = services
    }

    var CompanyName: String
        get() = companyName
        set(value) { companyName = value }

    var LegalId: String
        get() = legalId
        set(value) { legalId = value }

    var Province: String
        get() = province
        set(value) { province = value }

    var Canton: String
        get() = canton
        set(value) { canton = value }

    var District: String
        get() = district
        set(value) { district = value }

    var Services: MutableList<String>
        get() = services
        set(value) { services = value }
}
