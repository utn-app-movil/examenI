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

        val btnUser = findViewById<Button>(R.id.btnUser_main)
        btnUser.setOnClickListener(View.OnClickListener{ view->
            Toast.makeText(this, R.string.TextTestEver, Toast.LENGTH_LONG).show()
        })

        val btnEvent = findViewById<Button>(R.id.btnEvent_main)
        btnEvent.setOnClickListener(View.OnClickListener{ view->

        })

        val btnNews = findViewById<Button>(R.id.btnNews_main)
        btnNews.setOnClickListener(View.OnClickListener{ view->

        })

        val btnTask = findViewById<Button>(R.id.btnTask_main)
        btnTask.setOnClickListener(View.OnClickListener{ view->

        })

        val btnProduct = findViewById<Button>(R.id.btnProduct_main)
        btnProduct.setOnClickListener(View.OnClickListener{ view->

        })

        val btnBooking = findViewById<Button>(R.id.btnBooking_main)
        btnBooking.setOnClickListener(View.OnClickListener{ view->

        })

        val btnSurvey = findViewById<Button>(R.id.btnSurvery_main)
        btnSurvey.setOnClickListener(View.OnClickListener{ view->

        })

        val btnReceipt = findViewById<Button>(R.id.btnReceipt_main)
        btnReceipt.setOnClickListener(View.OnClickListener{ view->

        })

        val btnService = findViewById<Button>(R.id.btnService_main)
        btnService.setOnClickListener(View.OnClickListener{ view->

        })

        val btnSupplier = findViewById<Button>(R.id.btnSupplier_main)
        btnSupplier.setOnClickListener(View.OnClickListener{ view->

        })

        val btnIncident = findViewById<Button>(R.id.btnIncident_main)
        btnIncident.setOnClickListener(View.OnClickListener{ view->

        })

        val btnVehicle = findViewById<Button>(R.id.btnVehicle_main)
        btnVehicle.setOnClickListener(View.OnClickListener{ view->

        })

        val btnVisit = findViewById<Button>(R.id.btnVisit_main)
        btnVisit.setOnClickListener(View.OnClickListener{ view->

        })

        val btnMeeting = findViewById<Button>(R.id.btnMeeting_main)
        btnMeeting.setOnClickListener(View.OnClickListener{ view->

        })

        val btnTraining = findViewById<Button>(R.id.btnTraining_main)
        btnTraining.setOnClickListener(View.OnClickListener{ view->

        })

        val btnInventory = findViewById<Button>(R.id.btnInventory_main)
        btnInventory.setOnClickListener(View.OnClickListener{ view->

        })

        val btnVolunteer = findViewById<Button>(R.id.btnVolunteer_main)
        btnVolunteer.setOnClickListener(View.OnClickListener{ view->

        })

        val btnEntertainment = findViewById<Button>(R.id.btnEntertainment_main)
        btnEntertainment.setOnClickListener(View.OnClickListener{ view->

        })

        val btnLibrary = findViewById<Button>(R.id.btnLibrary_main)
        btnLibrary.setOnClickListener(View.OnClickListener{ view->

        })

        val btnRent = findViewById<Button>(R.id.btnRent_main)
        btnRent.setOnClickListener(View.OnClickListener{ view->

        })

        val btnLearning = findViewById<Button>(R.id.btnLearning_main)
        btnLearning.setOnClickListener(View.OnClickListener{ view->

        })

        val btnHarvest = findViewById<Button>(R.id.btnHarvest_main)
        btnHarvest.setOnClickListener(View.OnClickListener{ view->

        })

        val btnMeal = findViewById<Button>(R.id.btnMeal_main)
        btnMeal.setOnClickListener(View.OnClickListener{ view->

        })

        val btnSponsor = findViewById<Button>(R.id.btnSponsor_main)
        btnSponsor.setOnClickListener(View.OnClickListener{ view->

        })

        val btnFilm = findViewById<Button>(R.id.btnFilm_main)
        btnFilm.setOnClickListener(View.OnClickListener{ view->

        })

        val btnRecipe = findViewById<Button>(R.id.btnRecipe_main)
        btnRecipe.setOnClickListener(View.OnClickListener{ view->

        })
    }
}