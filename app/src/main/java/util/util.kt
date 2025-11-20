package cr.ac.utn.movil.util

import android.app.AlertDialog
import cr.ac.utn.movil.R
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import java.time.LocalDate
import java.util.Locale
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val EXTRA_ID = "cr.ac.utn.appmovil.Id"

class util {
    companion object  {
        fun openActivity(context: Context, objclass: Class<*>, keyName: String="", value: String?=null){
            val intent = Intent(context, objclass).apply { putExtra(keyName, value)}
            context.startActivity(intent)
        }

        fun parseStringToDateModern(dateString: String, pattern: String): LocalDate? {
            return try {
                val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
                LocalDate.parse(dateString, formatter)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        fun parseStringToDateTimeModern(dateTimeString: String, pattern: String): LocalDateTime? {
            return try {
                val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
                LocalDateTime.parse(dateTimeString, formatter)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        fun showDialogCondition(context: Context, questionText: String, callback: () ->  Unit){
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setMessage(questionText)
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.TextYes), DialogInterface.OnClickListener{
                        dialog, id -> callback()
                })
                .setNegativeButton(context.getString(R.string.TextNo), DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })

            val alert = dialogBuilder.create()
            alert.setTitle(context.getString(R.string.TextTitleDialogQuestion))
            alert.show()
        }
    }
}