package cr.ac.utn.movil.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import cr.ac.utn.movil.R
import cr.ac.utn.movil.identities.vac_Vaccine
import java.time.format.DateTimeFormatter

class vac_VaccineAdapter(
    private val context: Context,
    private var vaccines: List<vac_Vaccine>
) : BaseAdapter() {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    override fun getCount(): Int = vaccines.size

    override fun getItem(position: Int): Any = vaccines[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(
                R.layout.vac_item_vaccine_list,
                parent,
                false
            )
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val vaccine = vaccines[position]
        holder.bind(vaccine, position + 1, formatter)

        return view
    }

    fun updateData(newVaccines: List<vac_Vaccine>) {
        vaccines = newVaccines
        notifyDataSetChanged()
    }

    private class ViewHolder(view: View) {
        private val txtNumber: TextView = view.findViewById(R.id.vac_txt_item_number)
        private val txtPatient: TextView = view.findViewById(R.id.vac_txt_item_patient)
        private val txtVaccine: TextView = view.findViewById(R.id.vac_txt_item_vaccine)
        private val txtDateTime: TextView = view.findViewById(R.id.vac_txt_item_datetime)

        fun bind(vaccine: vac_Vaccine, number: Int, formatter: DateTimeFormatter) {
            txtNumber.text = "$number."
            txtPatient.text = "${vaccine.vac_PatientName} (${vaccine.vac_PatientIdNumber})"
            txtVaccine.text = "${vaccine.vac_VaccineType} - ${vaccine.vac_VaccineSite}"
            txtDateTime.text = vaccine.vac_VaccineDateTime?.format(formatter) ?: "N/A"
        }
    }
}