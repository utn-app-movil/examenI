package cr.ac.utn.movil.activities

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.movil.R
import cr.ac.utn.movil.adapters.PayenInsuranceAdapter
import cr.ac.utn.movil.controller.PayenInsuranceController
import cr.ac.utn.movil.entities.PayenInsuranceRequest
import cr.ac.utn.movil.util.util



class PayenInsuranceListActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var btnAdd: Button

    private val controller = PayenInsuranceController()
    private lateinit var adapter: PayenInsuranceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payen_insurance_list)

        recycler = findViewById(R.id.payen_rv_requests)
        btnAdd = findViewById(R.id.payen_btn_add)

        recycler.layoutManager = LinearLayoutManager(this)

        loadList()

        btnAdd.setOnClickListener {
            util.openActivity(
                this,
                PayenInsuranceFormActivity::class.java,
                "id",
                null
            )
        }
    }

    private fun loadList() {
        val data = controller.getAll()

        adapter = PayenInsuranceAdapter(data) { item ->
            util.openActivity(
                this,
                PayenInsuranceFormActivity::class.java,
                "id",
                item.ID
            )
        }

        recycler.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        loadList()
    }
}
