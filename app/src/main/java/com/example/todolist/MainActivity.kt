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

    override fun onResume() {
        super.onResume()

        // clear all the items displayed
        todoAdaptor.clearAll()

        // display items in database
        val dbHelper = DBHelper(context)
        dbHelper.getTodos().forEach{ todo ->
            todoAdaptor.addTodo(todo)
        }
    }
}