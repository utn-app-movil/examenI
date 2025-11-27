package cr.ac.utn.movil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.movil.identities.mark_MarketingCampaign

class mark_CampaignAdapter(
    private val campaigns: List<mark_MarketingCampaign>
) : RecyclerView.Adapter<mark_CampaignAdapter.ViewHolder>() {

    var onItemClick: ((mark_MarketingCampaign) -> Unit)? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(android.R.id.text1)
        val tvDetail: TextView = view.findViewById(android.R.id.text2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val campaign = campaigns[position]

        holder.tvTitle.text = campaign.toString()
        holder.tvDetail.text = "ID: ${campaign.ID}"

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(campaign)
        }
    }

    override fun getItemCount() = campaigns.size
}