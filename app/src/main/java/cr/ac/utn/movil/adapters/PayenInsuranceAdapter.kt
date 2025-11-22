package cr.ac.utn.movil.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.movil.R
import cr.ac.utn.movil.entities.PayenInsuranceRequest

class PayenInsuranceAdapter(
    private var list: List<PayenInsuranceRequest>,
    private val onItemClick: (PayenInsuranceRequest) -> Unit
) : RecyclerView.Adapter<PayenInsuranceAdapter.PayenViewHolder>() {

    inner class PayenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtName: TextView = itemView.findViewById(R.id.payen_txt_item_name)
        val txtSpecialty: TextView = itemView.findViewById(R.id.payen_txt_item_specialty)
        val txtRefund: TextView = itemView.findViewById(R.id.payen_txt_item_refund)

        fun bind(item: PayenInsuranceRequest) {
            txtName.text = item.personName
            txtSpecialty.text = item.specialty
            txtRefund.text = "Refund: ₡${String.format("%.2f", item.refundAmount)}"

            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayenViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_payen_insurance, parent, false)
        return PayenViewHolder(view)
    }

    override fun onBindViewHolder(holder: PayenViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun updateList(newList: List<PayenInsuranceRequest>) {
        list = newList
        notifyDataSetChanged()
    }
}
