package com.wannaphong.todo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class ToDoAdapter(context:Context, itemList: MutableList<ToDoItem>) : BaseAdapter() {
    var items = itemList
    var mInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val objID = items.get(position).objID as String
        val todoName = items.get(position).todoName as String
        val statue = items.get(position).status as String

        if(convertView == null){

        }
        else{

        }
    }

    override fun getItem(position: Int): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemId(position: Int): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}