package cr.ac.utn.movil.autonomy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.movil.identities.Autonomy

class AutonomyAdapter : RecyclerView.Adapter<AutonomyAdapter.ViewHolder>() {

    private var data: List<Autonomy> = emptyList()

    fun setData(newData: List<Autonomy>) {
        data = newData
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text1: TextView = view.findViewById(android.R.id.text1)
        val text2: TextView = view.findViewById(android.R.id.text2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.text1.text = "${item.VehicleId} - ${item.VehicleBrand}"
        holder.text2.text = "Date: ${item.ChargeDate}, Range: ${item.EstimatedRangeKm} km"
    }

    override fun getItemCount(): Int = data.size
}
