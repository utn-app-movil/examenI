package cr.ac.utn.movil

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.movil.controller.mark_MarketingController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import cr.ac.utn.movil.identities.mark_MarketingCampaign


class MarkListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mark_list)

        setSupportActionBar(findViewById(R.id.mark_toolbar_list))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Campaign of Marketing"

        findViewById<FloatingActionButton>(R.id.mark_fab_add).setOnClickListener {
            startActivity(Intent(this, MarkFormActivity::class.java))
        }

        loadList()
    }

    private fun loadList() {
        val lista = mark_MarketingController.getAll()

        val adapter = mark_CampaignAdapter(lista)

        adapter.onItemClick = { campaign: mark_MarketingCampaign ->
            val intent = Intent(this@MarkListActivity, MarkFormActivity::class.java)
            intent.putExtra("cr.ac.utn.appmovil.Id", campaign.ID)
            startActivity(intent)
        }

        findViewById<RecyclerView>(R.id.mark_recycler_view).apply {
            layoutManager = LinearLayoutManager(this@MarkListActivity)
            this.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        loadList()
    }
}