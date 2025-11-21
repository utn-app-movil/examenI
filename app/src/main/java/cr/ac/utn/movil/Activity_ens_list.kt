package cr.ac.utn.movil

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import controller.EnsController
import cr.ac.utn.movil.databinding.ActivityEnsListBinding
import ens_adapter.EnsAdapter
import identities.EnsPolicy

class Activity_ens_list : AppCompatActivity() {

    private lateinit var binding: ActivityEnsListBinding
    private lateinit var adapter: EnsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.ens_title_list)

        setupRecyclerView()
        setupFab()
    }

    override fun onResume() {
        super.onResume()
        loadPolicies()
    }

    private fun setupRecyclerView() {
        adapter = EnsAdapter(
            mutableListOf(),
            onClick = { policy -> editPolicy(policy) },
            onDelete = { policy -> deletePolicy(policy) }
        )
        binding.ensRvPolicies.layoutManager = LinearLayoutManager(this)
        binding.ensRvPolicies.adapter = adapter
    }

    private fun setupFab() {
        binding.ensFabAdd.setOnClickListener {
            val intent = Intent(this, Activity_ens_form::class.java)
            startActivity(intent)
        }
    }

    private fun loadPolicies() {
        val list = EnsController.getAll()
        adapter.updateData(list)
    }

    private fun editPolicy(policy: identities.EnsPolicy) {
        val intent = Intent(this, Activity_ens_form::class.java)
        intent.putExtra("ENS_POLICY_ID", policy.ID)
        startActivity(intent)
    }

    private fun deletePolicy(policy: identities.EnsPolicy) {
        EnsController.delete(policy.ID)
        loadPolicies()
        toast(getString(R.string.ens_msg_deleted))
    }

    private fun toast(message: String) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
    }
}