package cr.ac.utn.movil

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import cr.ac.utn.movil.data.MemoryDataManager
import identities.train_TrainingEnrollment

class train_TrainingEnrollmentListActivity : AppCompatActivity() {

    private lateinit var adapter: trainEnrollmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_train_training_enrollment_list)

        val rv: RecyclerView = findViewById(R.id.rvTrainEnrollments)
        rv.layoutManager = LinearLayoutManager(this)

        adapter = trainEnrollmentAdapter(
            MemoryDataManager.getAll()
                .filterIsInstance<train_TrainingEnrollment>()
                .toMutableList(),
            onEdit = { enrollment -> editEnrollment(enrollment) },
            onDelete = { enrollment ->
                MemoryDataManager.remove(enrollment.ID)
                refreshList()
            }
        )

        rv.adapter = adapter

        val fab: FloatingActionButton = findViewById(R.id.fabTrainAddEnrollment)
        fab.setOnClickListener {
            startActivity(Intent(this, train_TrainingEnrollmentFormActivity::class.java))
        }
    }

    private fun refreshList() {
        adapter = trainEnrollmentAdapter(
            MemoryDataManager.getAll()
                .filterIsInstance<train_TrainingEnrollment>()
                .toMutableList(),
            onEdit = { enrollment -> editEnrollment(enrollment) },
            onDelete = { enrollment ->
                MemoryDataManager.remove(enrollment.ID)
                refreshList()
            }
        )
        findViewById<RecyclerView>(R.id.rvTrainEnrollments).adapter = adapter
    }

    private fun editEnrollment(enrollment: train_TrainingEnrollment) {
        val intent = Intent(this, train_TrainingEnrollmentFormActivity::class.java)
        intent.putExtra("ENROLLMENT_ID", enrollment.ID)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }
}
