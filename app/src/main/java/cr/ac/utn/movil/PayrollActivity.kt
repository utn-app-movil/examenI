package cr.ac.utn.movil

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import cr.ac.utn.movil.adapter.PayrollAdapter
import cr.ac.utn.movil.controller.PayrollController
import cr.ac.utn.movil.util.EXTRA_ID

class PayrollActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvEmptyList: TextView
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var adapter: PayrollAdapter
    private val controller = PayrollController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payroll)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pay_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeViews()
        setupRecyclerView()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun initializeViews() {
        recyclerView = findViewById(R.id.rvPayroll_pay)
        tvEmptyList = findViewById(R.id.tvEmptyList_pay)
        fabAdd = findViewById(R.id.fabAdd_pay)
    }

    private fun setupRecyclerView() {
        adapter = PayrollAdapter(emptyList()) { payroll ->
            val intent = Intent(this, PayrollFormActivity::class.java)
            intent.putExtra(EXTRA_ID, payroll.ID)
            startActivity(intent)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupListeners() {
        fabAdd.setOnClickListener {
            val intent = Intent(this, PayrollFormActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadData() {
        val payrollList = controller.getAll()
        adapter.updateData(payrollList)

        if (payrollList.isEmpty()) {
            tvEmptyList.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            tvEmptyList.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }
}
