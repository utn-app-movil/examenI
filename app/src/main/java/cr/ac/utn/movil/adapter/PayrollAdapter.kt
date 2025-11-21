package cr.ac.utn.movil.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.movil.R
import cr.ac.utn.movil.identities.Payroll

class PayrollAdapter(
    private var payrollList: List<Payroll>,
    private val onItemClick: (Payroll) -> Unit
) : RecyclerView.Adapter<PayrollAdapter.PayrollViewHolder>() {

    private lateinit var context: Context

    class PayrollViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvEmployeeName: TextView = itemView.findViewById(R.id.tvEmployeeName_pay_item)
        val tvEmployeeNumber: TextView = itemView.findViewById(R.id.tvEmployeeNumber_pay_item)
        val tvPosition: TextView = itemView.findViewById(R.id.tvPosition_pay_item)
        val tvSalary: TextView = itemView.findViewById(R.id.tvSalary_pay_item)
        val tvBank: TextView = itemView.findViewById(R.id.tvBank_pay_item)
        val tvPaymentPeriod: TextView = itemView.findViewById(R.id.tvPaymentPeriod_pay_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayrollViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_payroll, parent, false)
        return PayrollViewHolder(view)
    }

    override fun onBindViewHolder(holder: PayrollViewHolder, position: Int) {
        val payroll = payrollList[position]

        holder.tvEmployeeName.text = payroll.FullName
        holder.tvEmployeeNumber.text = context.getString(R.string.pay_item_employee_number, payroll.EmployeeNumber)
        holder.tvPosition.text = context.getString(R.string.pay_item_position, payroll.Position)
        holder.tvSalary.text = context.getString(R.string.pay_item_salary, String.format("%,.2f", payroll.Salary))
        holder.tvBank.text = context.getString(R.string.pay_item_bank, payroll.BankName)

        val monthsArray = context.resources.getStringArray(R.array.pay_months_array)
        val monthName = if (payroll.PaymentMonth in 1..12) monthsArray[payroll.PaymentMonth - 1] else ""
        holder.tvPaymentPeriod.text = context.getString(R.string.pay_item_period, monthName, payroll.PaymentYear)

        holder.itemView.setOnClickListener {
            onItemClick(payroll)
        }
    }

    override fun getItemCount(): Int = payrollList.size

    fun updateData(newList: List<Payroll>) {
        payrollList = newList
        notifyDataSetChanged()
    }
}
