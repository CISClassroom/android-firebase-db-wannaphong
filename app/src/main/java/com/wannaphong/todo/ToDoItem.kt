package com.wannaphong.todo

class ToDoItem{
    companion object Factory{ // สร้างเมดทอนแบบย่อ ๆ
        fun create():ToDoItem = ToDoItem()
    }
    var objID:String? = null
    var todoName:String? = null
    var status:Boolean? = null
}