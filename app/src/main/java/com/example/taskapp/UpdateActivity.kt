package com.example.taskapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taskapp.databinding.ActivityUpdateBinding


class UpdateActivity : AppCompatActivity() {

    private lateinit var binding:ActivityUpdateBinding
    private lateinit var db:TasksDBHelper
    private var taskId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TasksDBHelper(this)

        taskId = intent.getIntExtra("task_id", -1)
        if (taskId == -1){
            finish()
            return
        }

        val task = db.getTaskByID(taskId)
        binding.updateTaskEditText.setText(task.tasks)
        binding.updateContentEditText.setText(task.content)

        binding.updateSaveButton.setOnClickListener {
            val newTask = binding.updateTaskEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()
            val updatedTask = Tasks(taskId, newTask, newContent)
            db.updateTask(updatedTask)
            finish()
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
        }
    }
}