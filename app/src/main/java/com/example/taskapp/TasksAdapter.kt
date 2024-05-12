package com.example.taskapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TasksAdapter (private var tasks:List<Tasks>, context: Context) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>(){

    private val db:TasksDBHelper = TasksDBHelper(context)
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val taskTextView: TextView = itemView.findViewById(R.id.taskTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_items, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val t = tasks[position]
        holder.taskTextView.text = t.tasks
        holder.contentTextView.text = t.content

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateActivity::class.java).apply {
                putExtra("task_id", t.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.deleteTask(t.id)
            refreshData(db.getAllTasks())
            Toast.makeText(holder.itemView.context, "Task Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newTasks: List<Tasks>){
        tasks = newTasks
        notifyDataSetChanged()
    }
}