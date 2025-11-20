package cr.ac.utn.movil

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnAppointment_main = findViewById<Button>(R.id.btnTemplate_main)
        btnAppointment_main.setOnClickListener(View.OnClickListener{ view->
            //app_
        })

        val btnVaccine_main = findViewById<Button>(R.id.btnTemplate_main)
        btnVaccine_main.setOnClickListener(View.OnClickListener{ view->
            //vac_

        })

        val btnLicense_main = findViewById<Button>(R.id.btnTemplate_main)
        btnLicense_main.setOnClickListener(View.OnClickListener{ view->
            //lic_
        })

        val btnMedChecking_main = findViewById<Button>(R.id.btnTemplate_main)
        btnMedChecking_main.setOnClickListener(View.OnClickListener{ view->
            //med_
        })

        val btnClients_main = findViewById<Button>(R.id.btnTemplate_main)
        btnClients_main.setOnClickListener(View.OnClickListener{ view->
            //cli_
        })

        val btnFlights_main = findViewById<Button>(R.id.btnTemplate_main)
        btnFlights_main.setOnClickListener(View.OnClickListener{ view->
            //fli_
        })

        val btnRent_main = findViewById<Button>(R.id.btnTemplate_main)
        btnRent_main.setOnClickListener(View.OnClickListener{ view->
            //rent_
        })

        val btnEvents_main = findViewById<Button>(R.id.btnTemplate_main)
        btnEvents_main.setOnClickListener(View.OnClickListener{ view->
            //eve_
        })

        val btnPharmacy_main = findViewById<Button>(R.id.btnTemplate_main)
        btnPharmacy_main.setOnClickListener(View.OnClickListener{ view->
            //pha_
        })

        val btnRecruitering_main = findViewById<Button>(R.id.btnTemplate_main)
        btnRecruitering_main.setOnClickListener(View.OnClickListener{ view->
            //recru_
        })

        val btnBidding_main = findViewById<Button>(R.id.btnTemplate_main)
        btnBidding_main.setOnClickListener(View.OnClickListener{ view->
            //bid_
        })

        val btnSinpe_main = findViewById<Button>(R.id.btnTemplate_main)
        btnSinpe_main.setOnClickListener(View.OnClickListener{ view->
            //sin_
        })

        val btnPayroll_main = findViewById<Button>(R.id.btnTemplate_main)
        btnPayroll_main.setOnClickListener(View.OnClickListener{ view->
            //pay_
        })

        val btnInventory_main = findViewById<Button>(R.id.btnTemplate_main)
        btnInventory_main.setOnClickListener(View.OnClickListener{ view->
            //inv_
        })

        val btnShipper_main = findViewById<Button>(R.id.btnTemplate_main)
        btnShipper_main.setOnClickListener(View.OnClickListener{ view->
            //ship_
        })

        val btnLibrary_main = findViewById<Button>(R.id.btnTemplate_main)
        btnLibrary_main.setOnClickListener(View.OnClickListener{ view->
            //lib_
        })

        val btnExchange_main = findViewById<Button>(R.id.btnTemplate_main)
        btnExchange_main.setOnClickListener(View.OnClickListener{ view->
            //exch_
        })

        val btnTraining_main = findViewById<Button>(R.id.btnTemplate_main)
        btnTraining_main.setOnClickListener(View.OnClickListener{ view->
            //train_
        })

        val btnNotification_main = findViewById<Button>(R.id.btnTemplate_main)
        btnNotification_main.setOnClickListener(View.OnClickListener{ view->
            //notif_
        })

        val btnDashboard_main = findViewById<Button>(R.id.btnTemplate_main)
        btnDashboard_main.setOnClickListener(View.OnClickListener{ view->
            //dash_
        })

        val btnPaymentEnsurance_main = findViewById<Button>(R.id.btnTemplate_main)
        btnPaymentEnsurance_main.setOnClickListener(View.OnClickListener{ view->
            //payen_
        })

        val btnMarketing_main = findViewById<Button>(R.id.btnTemplate_main)
        btnMarketing_main.setOnClickListener(View.OnClickListener{ view->
            //mark_
        })

        val btnEnsurance_main = findViewById<Button>(R.id.btnTemplate_main)
        btnEnsurance_main.setOnClickListener(View.OnClickListener{ view->
            //ens_
        })

        val btnVehicle_main = findViewById<Button>(R.id.btnTemplate_main)
        btnVehicle_main.setOnClickListener(View.OnClickListener{ view->
            //veh_
        })

        val btnProduction_main = findViewById<Button>(R.id.btnTemplate_main)
        btnProduction_main.setOnClickListener(View.OnClickListener{ view->
            //prod_
        })

        val btnWater_main = findViewById<Button>(R.id.btnTemplate_main)
        btnWater_main.setOnClickListener(View.OnClickListener{ view->
            //wat_
        })

        val btnRecycling_main = findViewById<Button>(R.id.btnTemplate_main)
        btnRecycling_main.setOnClickListener(View.OnClickListener{ view->
            //recy_
        })

        val btnTemplate_main = findViewById<Button>(R.id.btnTemplate_main)
        btnTemplate_main.setOnClickListener(View.OnClickListener{ view->
            Toast.makeText(this, R.string.TextTemplate, Toast.LENGTH_LONG).show()
        })
    }
}