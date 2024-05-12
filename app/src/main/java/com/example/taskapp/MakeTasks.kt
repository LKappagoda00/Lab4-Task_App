package com.example.taskapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taskapp.databinding.ActivityMakeTasksBinding

class MakeTasks : AppCompatActivity() {

    //declare binding variable
    private lateinit var binding: ActivityMakeTasksBinding

    //declare a variable for db helper
    private lateinit var db: TasksDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inflate the layout
        binding = ActivityMakeTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initialize db helper
        db = TasksDBHelper(this)

        binding.saveButton.setOnClickListener {
            //retrieve task name and content
            val task = binding.taskEditText.text.toString()
            val content = binding.contentEditText.text.toString()

            //create task object
            val t = Tasks(0, task, content)

            //insert task object to db
            db.insertTask(t)
            finish()
            Toast.makeText(this, "Task Saved", Toast.LENGTH_SHORT).show()
        }
    }
}