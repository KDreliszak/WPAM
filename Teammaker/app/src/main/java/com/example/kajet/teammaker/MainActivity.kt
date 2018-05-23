
package com.example.kajet.teammaker

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val loginBtn = findViewById<View>(R.id.loginBtn) as Button
        //val regTxt = findViewById<View>(R.id.regTxt) as TextView

        loginBtn.setOnClickListener{
            login()
        }

        regTxt.setOnClickListener{
            register()
        }

    }

    private fun login () {

        var email = emailTxt.text.toString()
        var password = passwordTxt.text.toString()

        if (email.isEmpty())
            emailTxt.error = "Write your name"
        else if (password.isEmpty())
            passwordTxt.error = "Write your password"
        else {
            this.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainPage :: class.java))
                    Toast.makeText(this, "Successfully logged in", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun register () {
        startActivity(Intent(this, Register :: class.java))
    }
}