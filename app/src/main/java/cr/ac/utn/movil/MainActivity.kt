package cr.ac.utn.movil

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cr.ac.utn.movil.ui.vac_VaccineActivity
import cr.ac.utn.movil.util.util

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.txtId)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnAppointment = findViewById<Button>(R.id.btnAppointment_main)
        btnAppointment.setOnClickListener {
            val intent = Intent(this, activity_app_citas::class.java)
            startActivity(intent)
        }


        val btnVaccine_main = findViewById<Button>(R.id.btnVaccine_main)
        btnVaccine_main.setOnClickListener(View.OnClickListener{ view->
            util.openActivity(this, vac_VaccineActivity::class.java)
        })

        val btnLicense_main = findViewById<Button>(R.id.btnTemplate_main)
        btnLicense_main.setOnClickListener(View.OnClickListener{ view->
            //lic_
        })

        val btnMedChecking_main = findViewById<Button>(R.id.btnMedChecking_main)
        btnMedChecking_main.setOnClickListener(View.OnClickListener { view ->
            cr.ac.utn.movil.util.util.openActivity(this, med_MedCheckingActivity::class.java)
        })



        val btnClients_main = findViewById<Button>(R.id.btnTemplate_main)
        btnClients_main.setOnClickListener(View.OnClickListener{ view->
            //cli_
        })

        val btnFlights_main = findViewById<Button>(R.id.btnFlights_main)
        btnFlights_main.setOnClickListener(View.OnClickListener{ view->
            util.openActivity(this, fli_BookingsRegister::class.java)
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

        val btnPayroll_main = findViewById<Button>(R.id.btnPayroll_main)
        btnPayroll_main.setOnClickListener(View.OnClickListener{ view->
            val intent = Intent(this, PayrollActivity::class.java)
            startActivity(intent)
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

        val btnDashboard_main = findViewById<Button>(R.id.btnDashboard_main)
        btnDashboard_main.setOnClickListener(View.OnClickListener{ view->
            util.openActivity(this, DashActivity::class.java)
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