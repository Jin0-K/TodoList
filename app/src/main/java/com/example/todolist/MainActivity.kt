package com.example.todolist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdaptor : TodoAdaptor
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the content property
        context = applicationContext

        val dbHelper = DBHelper(context)

        todoAdaptor = TodoAdaptor(mutableListOf())

        rv_todoItems.adapter = todoAdaptor
        rv_todoItems.layoutManager = LinearLayoutManager(context)

        // clear all the items displayed
        todoAdaptor.clearAll()
        // display items in database
        dbHelper.getTodos().forEach{ todo ->
            todoAdaptor.addTodo(todo)
        }

        // Button click listeners
        btn_addTodo.setOnClickListener {
            val todo : Todo
            val todoTitle = et_todoTitle.text.toString()
            if (todoTitle.isNotEmpty()) {
                todo = Todo(-1, todoTitle)
                dbHelper.add(todo)
                todoAdaptor.addTodo(todo)
                et_todoTitle.text.clear()
            }
        }

        btn_delTodo.setOnClickListener {
            dbHelper.delete(todoAdaptor.deleteDoneTodos())
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        val dbHelper = DBHelper(context)

        val todoList = todoAdaptor.getTodos()

        if (todoList.size > 0) {
            dbHelper.updateCheck(todoList)
        }
    }
}