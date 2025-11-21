package cr.ac.utn.movil.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.movil.R
import cr.ac.utn.movil.identities.LicenseRenewal

class LicenseAdapter(
    private var licenses: MutableList<LicenseRenewal>,
    private val onItemClick: (LicenseRenewal) -> Unit
) : RecyclerView.Adapter<LicenseAdapter.LicenseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LicenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lic_license, parent, false)
        return LicenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: LicenseViewHolder, position: Int) {
        val license = licenses[position]
        holder.bind(license)
        holder.itemView.setOnClickListener {
            onItemClick(license)
        }
    }

    override fun getItemCount(): Int = licenses.size

    fun updateList(newLicenses: List<LicenseRenewal>) {
        licenses.clear()
        licenses.addAll(newLicenses)
        notifyDataSetChanged()
    }

    class LicenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvUserName: TextView = itemView.findViewById(R.id.lic_item_tvUserName)
        private val tvUserId: TextView = itemView.findViewById(R.id.lic_item_tvUserId)
        private val tvLicenseType: TextView = itemView.findViewById(R.id.lic_item_tvLicenseType)
        private val tvScore: TextView = itemView.findViewById(R.id.lic_item_tvScore)
        private val tvDateTime: TextView = itemView.findViewById(R.id.lic_item_tvDateTime)

        fun bind(license: LicenseRenewal) {
            val context = itemView.context

            tvUserName.text = license.UserName
            tvUserId.text = context.getString(R.string.lic_list_item_id, license.UserId)
            tvLicenseType.text = context.getString(R.string.lic_list_item_type, license.LicenseType)
            tvScore.text = context.getString(R.string.lic_list_item_score, license.CurrentScore)
            tvDateTime.text = context.getString(R.string.lic_list_item_date, license.RenewalDate, license.RenewalTime)
        }
    }
}
