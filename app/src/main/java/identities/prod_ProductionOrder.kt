package identities

import cr.ac.utn.movil.identities.Identifier
import java.time.LocalDate

class prod_ProductionOrder: Identifier {
    private var orderNumber: String = ""
    private var productList: List<String> = emptyList()
    private var quantity: Float = 0.0f
    private var orderDate: LocalDate = LocalDate.now()
    private var deliveryDate: LocalDate = LocalDate.now()
    private var companyName: String = ""

    constructor(id: String, orderNumber: String, productList: List<String>, quantity: Float,
                orderDate: LocalDate, deliveryDate: LocalDate, companyName: String) {
        super.ID = id
        this.orderNumber = orderNumber
        this.productList = productList
        this.quantity = quantity
        this.orderDate = orderDate
        this.deliveryDate = deliveryDate
        this.companyName = companyName
    }

    var OrderNumber: String
        get() = this.orderNumber
        set(value) { this.orderNumber = value }

    var ProductList: List<String>
        get() = this.productList
        set(value) { this.productList = value }

    var Quantity: Float
        get() = this.quantity
        set(value) { this.quantity = value }

    var OrderDate: LocalDate
        get() = this.orderDate
        set(value) { this.orderDate = value }

    var DeliveryDate: LocalDate
        get() = this.deliveryDate
        set(value) { this.deliveryDate = value }

    var CompanyName: String
        get() = this.companyName
        set(value) { this.companyName = value }

    override val FullName = "$orderNumber - $companyName"

    override val FullDescription: String
        get() = "$orderNumber - $companyName"
}