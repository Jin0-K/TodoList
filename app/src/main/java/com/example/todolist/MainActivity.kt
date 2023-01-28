package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdaptor : TodoAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todoAdaptor = TodoAdaptor(mutableListOf())

        rv_todoItems.adapter = todoAdaptor
        rv_todoItems.layoutManager = LinearLayoutManager(this)

        btn_addTodo.setOnClickListener {
            val todoTitle = et_todoTitle.text.toString()
            if (todoTitle.isNotEmpty()) {
                val todo = Todo(todoTitle)
                todoAdaptor.addTodo(todo)
                et_todoTitle.text.clear()
            }
        }

        btn_delTodo.setOnClickListener {
            todoAdaptor.deleteDoneTodos()
        }
    }
}