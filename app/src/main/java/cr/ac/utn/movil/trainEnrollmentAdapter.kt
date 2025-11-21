package cr.ac.utn.movil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import identities.train_TrainingEnrollment

class trainEnrollmentAdapter(
    private val enrollments: MutableList<train_TrainingEnrollment>,
    private val onEdit: (train_TrainingEnrollment) -> Unit,
    private val onDelete: (train_TrainingEnrollment) -> Unit
) : RecyclerView.Adapter<trainEnrollmentAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvInfo: TextView = itemView.findViewById(R.id.tvEnrollmentInfo)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_train_item_enrollment, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = enrollments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val enrollment = enrollments[position]
        holder.tvInfo.text = enrollment.FullDescription

        holder.btnEdit.setOnClickListener { onEdit(enrollment) }
        holder.btnDelete.setOnClickListener { onDelete(enrollment) }
    }
}