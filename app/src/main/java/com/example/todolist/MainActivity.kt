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

        todoAdaptor = TodoAdaptor(mutableListOf())

        rv_todoItems.adapter = todoAdaptor
        rv_todoItems.layoutManager = LinearLayoutManager(this)

        // display items in database
        val dbHelper = DBHelper(context)
        dbHelper.writableDatabase

        // Application stops in this part
        dbHelper.getTodos().forEach{ todo ->
            todoAdaptor.addTodo(todo)
        }


        btn_addTodo.setOnClickListener {
            val todo : Todo
            val todoTitle = et_todoTitle.text.toString()
            if (todoTitle.isNotEmpty()) {
                todo = Todo(-1, todoTitle)
                todoAdaptor.addTodo(todo)
                et_todoTitle.text.clear()
            }
            else {
                todo = Todo(-1, "")
            }

            //dbHelper.add(todo)
        }

        btn_delTodo.setOnClickListener {
            todoAdaptor.deleteDoneTodos()
        }
    }
}