package com.example.kajet.teammaker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.kajet.teammaker.R.id.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_announcements.*

class Announcements : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()
    var user = FirebaseAuth.getInstance().currentUser
    lateinit var mDatabase : DatabaseReference
    lateinit var addList: MutableList<Add>
    var exist = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcements)

        var uid = user!!.uid

        addList = mutableListOf()

        mDatabase = FirebaseDatabase.getInstance().getReference("Games")

        searchBtn.setOnClickListener {
            addList.clear()

            val gameName = gameNameTxt.text.toString()
            val day = dayTxt.text.toString()

            if (gameName.isEmpty())
                gameNameTxt.error = "Fill the gap"
            else if ( !gameName.equals("League of Legends") && !gameName.equals("LoL") && !gameName.equals("lol") && !gameName.equals("Overwatch"))
                gameNameTxt.error = "Unknown game"
            else if (day.isEmpty())
                dayTxt.error = "Fill the gap"
            else
                search()
        }

    }

    fun search(){

        mDatabase.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val gameName = gameNameTxt.text.toString()
                val day = dayTxt.text.toString()

                if (p0!!.exists()){
                    dbEmotyTxt.visibility = View.GONE

                    exist = false

                    addList.clear()
                    for (a in p0.children){
                        val add = a.getValue(Add::class.java)
                        addList.add(add!!)

                        if(gameName.equals(add.game) && day.equals(add.day))
                            exist = true
                    }
                    val adapter = AddAdapter(this@Announcements, R.layout.adds, addList, gameName, day)
                    listView.adapter = adapter

                    if (!exist) {
                        Toast.makeText(this@Announcements, "No matches has been found", Toast.LENGTH_LONG).show()
                        exist = true
                    }
                }
                else
                    dbEmotyTxt.visibility = View.VISIBLE
                    //Toast.makeText(this@Announcements, "Database is empty", Toast.LENGTH_LONG).show()
            }

        })
    }
}
