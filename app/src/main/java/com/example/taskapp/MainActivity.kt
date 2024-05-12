package com.example.taskapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: TasksDBHelper
    private lateinit var tasksAdapter: TasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TasksDBHelper(this)
        tasksAdapter = TasksAdapter(db.getAllTasks(), this)

        binding.tasksRView.layoutManager = LinearLayoutManager(this)
        binding.tasksRView.adapter = tasksAdapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this, MakeTasks::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        tasksAdapter.refreshData(db.getAllTasks())
    }
}