package com.example.todolist

class Todo (
    private val id : Int,
    private val title: String,
    private var isChecked: Boolean = false
) {
    fun get_id() : Int { return id }
    fun get_title() : String { return title }
    fun get_isChecked() : Boolean { return isChecked }

    fun check() : Boolean {
        isChecked = !isChecked
        return isChecked
    }

    // toString
    override fun toString(): String {
        return "Todo(id=$id, title='$title', isChecked=$isChecked)"
    }
}