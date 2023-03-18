package com.example.todolist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Todos.db"
        const val TODO_TABLE = "TODO_TABLE"
        const val COLUMN_ID = "ID"
        const val COLUMN_TITLE = "TITLE"
        const val COLUMN_CHECKED = "CHECKED"
    }



    override fun onCreate(db: SQLiteDatabase) {
        val createTableStatement : String = "CREATE TABLE " +  TODO_TABLE +
                                            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                            COLUMN_TITLE + " TEXT)"
        db.execSQL(createTableStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // handle upgrades here
    }

    fun add(todo : Todo): Boolean {
        val db : SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()

        cv.put(COLUMN_TITLE, todo.get_title())
        cv.put(COLUMN_CHECKED, todo.get_isChecked())

        val insert = db.insert(TODO_TABLE, null, cv)
        return !insert.equals(-1)
    }

    fun delete(todo: Todo) : Boolean {
        val db : SQLiteDatabase = this.writableDatabase
        val whereClause = "ID = ?"
        val whereArgs = arrayOf(todo.get_id().toString())
        val delete = db.delete(TODO_TABLE, whereClause, whereArgs)

        return delete > 0
    }

    fun getTodos() : List<Todo> {
        val returnList  = mutableListOf<Todo>()

        // Get data from database
        val queryString = "SELECT * FROM $TODO_TABLE"

        val db : SQLiteDatabase = this.readableDatabase

        // execute query
        // Cursor is the result set from a SQL statement
        val cursor : Cursor = db.rawQuery(queryString, null)
        // moveToFirst() returns true if there were items selected
        if (cursor.moveToFirst()){
            // loop through the cursor and create new todo objects.
            do {
                val todoID: Int = cursor.getInt(0)
                val todoTitle: String = cursor.getString(1)
                val todoDone: Boolean = cursor.getInt(2) == 1

                val newTodo = Todo(todoID, todoTitle, todoDone)
                returnList.add(newTodo)
            } while (cursor.moveToNext())
        }
        else {
            // failure. do no add anyting to the list
        }

        // close both cursor and the db when done
        cursor.close()
        db.close()

        return returnList
    }
}
