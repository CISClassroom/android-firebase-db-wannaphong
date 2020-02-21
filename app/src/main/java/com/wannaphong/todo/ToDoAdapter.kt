package com.wannaphong.todo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView

class ToDoAdapter(context:Context, itemList: MutableList<ToDoItem>) : BaseAdapter() {
    var items = itemList
    var mInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val objID = items.get(position).objID as String
        val todoName = items.get(position).todoName as String
        val statue = items.get(position).status as Boolean
        val view:View
        val vh: ListRowHolder

        if(convertView == null){
            view = mInflater.inflate(R.layout.list_item,parent,false)
            vh = ListRowHolder(view)
            view.tag = vh
        }
        else{
            view = convertView
            vh = view.tag as ListRowHolder
        }
        vh.label.text = todoName
        vh.checkbox.isChecked = statue
        return view
    }
    private class ListRowHolder(row:View?){
        val label = row!!.findViewById<TextView>(R.id.textView)
        val checkbox = row!!.findViewById<CheckBox>(R.id.checkBox) as CheckBox
        val deleteButton = row!!.findViewById<ImageButton>(R.id.imageButton) as ImageButton
    }

    override fun getItem(position: Int): Any {
        return  items.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
       return items.size
    }
}