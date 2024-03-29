package com.example.todolist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdaptor (
    private val todos : MutableList<Todo>
) : RecyclerView.Adapter<TodoAdaptor.TodoViewHolder>() {

    class TodoViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }

    fun getTodos() : MutableList<Todo> { return todos }

    fun addTodo(todo : Todo) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    fun deleteDoneTodos() : MutableList<Todo> {
        val checked = mutableListOf<Todo>()

        todos.forEach { todo ->
            if (todo.get_isChecked()) {
                checked.add(todo)
            }
        }

        todos.removeAll { todo ->
            todo.get_isChecked()
        }
        notifyDataSetChanged()

        return checked
    }

    fun clearAll() {
        todos.clear()
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(tv_todoTitle : TextView, isChecked : Boolean) {
        if (isChecked) {
            tv_todoTitle.paintFlags = tv_todoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tv_todoTitle.paintFlags = tv_todoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv() // remove flags in paintFlag
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = todos[position]
        holder.itemView.apply {
            tv_todoTitle.text = curTodo.get_title()
            cb_done.isChecked = curTodo.get_isChecked()
            toggleStrikeThrough(tv_todoTitle, curTodo.get_isChecked())
            cb_done.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(tv_todoTitle, isChecked)
                curTodo.check()
            }
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}