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

    fun addTodo(todo : Todo) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    fun deleteDoneTodos() {
        todos.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(tv_todoTitle : TextView, isChecked : Boolean) {
        if (isChecked) {
            tv_todoTitle.paintFlags = tv_todoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tv_todoTitle.paintFlags = tv_todoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv() // remove flangs in paintFlag
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = todos[position]
        holder.itemView.apply {
            tv_todoTitle.text = curTodo.title
            cb_done.isChecked = curTodo.isChecked
            toggleStrikeThrough(tv_todoTitle, curTodo.isChecked)
            cb_done.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(tv_todoTitle, isChecked)
                curTodo.isChecked = !curTodo.isChecked
            }
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}