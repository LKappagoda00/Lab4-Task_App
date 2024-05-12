package com.example.taskapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TasksDBHelper (context: Context) : SQLiteOpenHelper (context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "tasksapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "alltasks"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TASK = "tasks"
        private const val COLUMN_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TASK TEXT, $COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertTask(tasks: Tasks){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TASK, tasks.tasks)
            put(COLUMN_CONTENT, tasks.content)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllTasks(): List<Tasks> {
        val tasksList = mutableListOf<Tasks>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val task = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            val t = Tasks(id, task, content)
            tasksList.add(t)
        }
        cursor.close()
        db.close()
        return tasksList
    }

    fun updateTask(task: Tasks){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TASK, task.tasks)
            put(COLUMN_CONTENT, task.content)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(task.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getTaskByID(taskId: Int): Tasks{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $taskId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val task = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return Tasks(id, task, content)
    }

    fun deleteTask(taskId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(taskId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }
}