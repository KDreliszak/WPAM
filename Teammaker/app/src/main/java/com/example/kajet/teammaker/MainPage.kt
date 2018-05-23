package com.example.kajet.teammaker

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main_page.*

class MainPage : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()
    var user = FirebaseAuth.getInstance().currentUser
    lateinit var mDatabase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        var uid = user!!.uid

        mDatabase = FirebaseDatabase.getInstance().getReference("Names")

        mDatabase.child(uid).child("Name").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val result = snapshot.value.toString()
                welcomeTxt.text = ("Welcome " + result)
            }
        })

        addTxt.setOnClickListener{
            startActivity(Intent(this, Creator::class.java))
        }

        searchTxt.setOnClickListener{
            startActivity(Intent(this, Announcements::class.java))
        }

        lolTxt.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.na.leagueoflegends.com/en_US")))
        }

        overTxt.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://playoverwatch.com/pl-pl/")))
        }
    }

    fun deleteAcc(){
        mDatabase = FirebaseDatabase.getInstance().getReference("Names")
        mDatabase.child(user!!.uid).removeValue()

        user!!.delete()

        Toast.makeText(this, "Account has been deleted", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.signOutItem -> {
                Toast.makeText(this, "Logged out", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
                mAuth.signOut()
            }
            R.id.deleteAccItem -> {
                deleteAcc()
            }
        }

        return super.onOptionsItemSelected(item)
    }

}