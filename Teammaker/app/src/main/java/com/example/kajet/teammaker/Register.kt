
package com.example.kajet.teammaker

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()
    lateinit var mDatebase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mDatebase = FirebaseDatabase.getInstance().getReference("Names")

        regBtn.setOnClickListener(View.OnClickListener {
            view -> register()
        })

    }

    private fun register () {

        var email = emailTxt.text.toString()
        var password = passwordTxt.text.toString()
        var name = nameTxt.text.toString()

        if (!name.isEmpty() && !password.isEmpty() && !email.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    val uid = user!!.uid
                    mDatebase.child(uid).child("Name").setValue(name)
                    mDatebase.child(uid).child("Id").setValue(uid)
                    Toast.makeText(this, "Successfully created an account", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainPage :: class.java))
                }
                else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            })
        }
        else {
            Toast.makeText(this,"Enter data", Toast.LENGTH_SHORT).show()
        }
    }
}