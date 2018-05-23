package com.example.kajet.teammaker

import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddAdapter(val mCtx: Context, val layoutResId: Int, val addList: List<Add>, val gameName: String, val day: String)
    :ArrayAdapter<Add> (mCtx, layoutResId, addList){

    var user = FirebaseAuth.getInstance().currentUser

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        var uid = user!!.uid

        val textViewName = view.findViewById<TextView>(R.id.textViewName)
        val deleteBtn = view.findViewById<Button>(R.id.deleteBtn)

        val add = addList[position]

        if (add.game.equals(gameName) && add.day.equals(day)){
            textViewName.text = ("Game: " + add.game + "\n" + "Hour: " + add.hour + "\n" + "Player: " + add.nameingame)
            if (!add.uid.equals(uid))
                deleteBtn.visibility = View.GONE
            else
                deleteBtn.setOnClickListener{
                    val mDatabase = FirebaseDatabase.getInstance().getReference("Games")
                    mDatabase.child(add.id).removeValue()
                }
        }
        else {
            textViewName.visibility = View.GONE
            deleteBtn.visibility = View.GONE
        }

        return view
    }


}