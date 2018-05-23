package com.example.kajet.teammaker

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_creator.*

class Creator : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()
    var user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creator)

        postBtn.setOnClickListener{
            saveAdd()
        }
    }

    fun saveAdd(){

        val gameName = gameNameTxt.text.toString()
        val nameInGame = nameInGameTxt.text.toString()
        val hour = hourTxt.text.toString()
        val day = dayTxt.text.toString()

        if(gameName.isEmpty() || nameInGame.isEmpty() || day.isEmpty() || hour.isEmpty()){
            Toast.makeText(this, "All gaps have to be filled", Toast.LENGTH_SHORT).show()
            return
        }
        //if (!gameName.equals("League of Legends") && !gameName.equals("LoL") && !gameName.equals("lol") && !gameName.equals("Overwatch")) {
        //    gameNameTxt.error = "Unknown game"
        //    return
        //}
        var uid = user!!.uid

        val mDatabase = FirebaseDatabase.getInstance().getReference("Games")
        val gameId = mDatabase.push().key

        val game = Add(uid, gameId, gameName, nameInGame, day, hour)

        mDatabase.child(gameId).setValue(game).addOnCompleteListener {
            Toast.makeText(applicationContext, "Post saved successfully", Toast.LENGTH_SHORT).show()
        }
        startActivity(Intent(this, MainPage::class.java))

    }
}
