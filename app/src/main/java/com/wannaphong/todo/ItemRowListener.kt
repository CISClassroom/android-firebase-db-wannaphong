package com.wannaphong.todo

interface ItemRowListener{
    fun modifyItemState(itemID: String,index:Int, status: Boolean)
    fun onItemDelete(itemID: String,index:Int)
}