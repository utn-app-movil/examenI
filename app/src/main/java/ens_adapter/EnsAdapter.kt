package ens_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.movil.entities.EnsPolicy

class EnsAdapter(
    private val policies: MutableList<EnsPolicy>,
    private val onClick: (EnsPolicy) -> Unit,
    private val onDelete: (EnsPolicy) -> Unit
) : RecyclerView.Adapter<EnsAdapter.EnsViewHolder>() {

    class EnsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvInfo: TextView = itemView.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return EnsViewHolder(view)
    }

    override fun onBindViewHolder(holder: EnsViewHolder, position: Int) {
        val policy = policies[position]
        holder.tvInfo.text = policy.toString()
        holder.itemView.setOnClickListener { onClick(policy) }

        holder.itemView.setOnLongClickListener {
            onDelete(policy)
            true
        }
    }

    override fun getItemCount() = policies.size

    fun updateData(newList: List<EnsPolicy>) {
        policies.clear()
        policies.addAll(newList)
        notifyDataSetChanged()
    }
}