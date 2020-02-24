package com.wannaphong.todo

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.*

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ItemRowListener {
    lateinit var mDB: DatabaseReference
    var todoItemList: MutableList<ToDoItem>? = null
    lateinit var adapter: ToDoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mDB = FirebaseDatabase.getInstance().reference
        var listviewitem:ListView? = findViewById<View>(R.id.items_list) as ListView

        todoItemList = mutableListOf<ToDoItem>()
        adapter = ToDoAdapter(this,todoItemList!!)

        listviewitem!!.setAdapter(adapter)// !! ไม่มีทางเป็น null
        mDB.orderByKey().addListenerForSingleValueEvent(itemListener)


        fab.setOnClickListener { view ->
            addNewItem()
        }
    }
    val itemListener:ValueEventListener = object :ValueEventListener{
        override fun onCancelled(p0: DatabaseError) {
            // get data failed
        }

        override fun onDataChange(p0: DataSnapshot) {
            addDataToList(p0)
        }

    }

    private fun addDataToList(dataSnapshot: DataSnapshot) {
        val items = dataSnapshot.children.iterator()
        if(items.hasNext()){
            val todoListIndex = items.next()
            val itemsIterator = todoListIndex.children.iterator()

            while (itemsIterator.hasNext()){
                val currentItem = itemsIterator.next()
                val map = currentItem.getValue() as HashMap<String,Any>
                val toDoItem = ToDoItem.create()
                toDoItem.objID = currentItem.key
                toDoItem.status = map.get("status") as Boolean
                toDoItem.todoName = map.get("todoName") as String
                todoItemList!!.add(toDoItem)
            }
            adapter.notifyDataSetChanged()
        }

    }

    fun addNewItem(){
        val dialog = AlertDialog.Builder(this)
         val et = EditText(this)

         dialog.setMessage("Add New ToDo")
         dialog.setTitle("Enter ToDo item")
         dialog.setView(et)

         dialog.setPositiveButton("Submit"){
             dialog,positiveButton ->
             val newTODO = ToDoItem.create()
             newTODO.todoName = et.text.toString()
             newTODO.status = false


             val newItemDB = mDB.child("TODO_item").push()
             newTODO.objID = newItemDB.key
             newItemDB.setValue(newTODO)
             dialog.dismiss()

             todoItemList!!.add(newTODO)
             adapter.notifyDataSetChanged()

         }
         dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun modifyItemState(itemID: String, index: Int, status: Boolean) {
        // update adapter
        todoItemList!!.get(index).status = !status
        adapter.notifyDataSetChanged()
        // update firebase
        val itemRef = mDB.child("TODO_item").child(itemID)
        itemRef.child("status").setValue(status)
       // adapter.notifyDataSetChanged()
    }

    override fun onItemDelete(itemID: String, index: Int) {
        todoItemList!!.removeAt(index)
        adapter.notifyDataSetChanged()

        val itemRef = mDB.child("TODO_item").child(itemID)
        itemRef.removeValue()
    }
}
