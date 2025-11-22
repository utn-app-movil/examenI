package cr.ac.utn.movil

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.movil.util.util

class BiddingMenuActivity_bid : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bidding_menu_bid)

        // este es el codigo del boton que habre el formulario de pujas
        val btnNewBid = findViewById<Button>(R.id.btnNewBid_bid)
        btnNewBid.setOnClickListener {
            util.openActivity(this, BiddingFormActivity_bid::class.java)
        }

        // Más adelante usaremos este botón:
        // val btnBidList = findViewById<Button>(R.id.btnBidList_bid)
        // para abrir la lista de pujas
    }
}
