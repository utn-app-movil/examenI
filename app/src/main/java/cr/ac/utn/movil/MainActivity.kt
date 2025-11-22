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
import identities.recru_Form
import cr.ac.utn.movil.util.EXTRA_ID
import cr.ac.utn.movil.ui.vac_VaccineActivity
import cr.ac.utn.movil.autonomy.AutonomyActivity
import cr.ac.utn.movil.util.util
import cr.ac.utn.movil.ui.RentActivity

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

        val btnClients_main = findViewById<Button>(R.id.btnClients_main)
        btnClients_main.setOnClickListener(View.OnClickListener{ view->
            //cli_
            util.openActivity(this, cli_GesClientesActivity::class.java)
        })

        val btnFlights_main = findViewById<Button>(R.id.btnFlights_main)
        btnFlights_main.setOnClickListener(View.OnClickListener{ view->
            util.openActivity(this, fli_BookingsRegister::class.java)
        })

        val btnRent_main = findViewById<Button>(R.id.btnRent_main)
        btnRent_main.setOnClickListener {
            val intent = Intent(this, RentActivity::class.java)
            startActivity(intent)
        }


        val btnEvents_main = findViewById<Button>(R.id.btnEvents_main)
        btnEvents_main.setOnClickListener(View.OnClickListener { view ->
            util.openActivity(this, EventosActivity::class.java)
        })

        val btnPharmacy_main = findViewById<Button>(R.id.btnTemplate_main)
        btnPharmacy_main.setOnClickListener(View.OnClickListener{ view->
            //pha_
        })

        val btnRecruitering_main = findViewById<Button>(R.id.btnRecruitering_main)
        btnRecruitering_main.setOnClickListener(View.OnClickListener{ view->

            val intent = Intent(this, recru_alex::class.java)
            startActivity(intent)

        })

        val btnBidding_main = findViewById<Button>(R.id.btnTemplate_main)
        btnBidding_main.setOnClickListener(View.OnClickListener{ view->
            //bid_
        })

        val btnSinpe_main = findViewById<Button>(R.id.btnSinpe_main)
        btnSinpe_main.setOnClickListener(View.OnClickListener{ view->
            util.openActivity(this, sin_SinpeActivity::class.java)
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

        val btnLibrary_main = findViewById<Button>(R.id.btnLibrary_main)
        btnLibrary_main.setOnClickListener(View.OnClickListener{ view->
            //lib_
            util.openActivity(this, LibLibraryActivity::class.java)
        })

        val btnExchange_main = findViewById<Button>(R.id.btnExchange_main)
        btnExchange_main.setOnClickListener(View.OnClickListener{ view->
            //exch_
            util.openActivity(this,exch_ExchangeTransactionActivity::class.java)

        })

        val btnTraining_main = findViewById<Button>(R.id.btnTemplate_main)
        btnTraining_main.setOnClickListener(View.OnClickListener{ view->
            //train_
            util.openActivity(this, train_TrainingEnrollmentFormActivity::class.java)

        })

        val btnNotification_main = findViewById<Button>(R.id.btnNotification_main)
        btnNotification_main.setOnClickListener(View.OnClickListener{ view->
            util.openActivity(this, NotificationActivity::class.java)
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
            util.openActivity(this, AutonomyActivity::class.java)
            //veh_
        })

        val btnProduction_main = findViewById<Button>(R.id.btnProduction_main)
        btnProduction_main.setOnClickListener(View.OnClickListener{ view->
            util.openActivity(this, prod_ProductionOrderActivity::class.java,
                EXTRA_ID, "prod_")
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