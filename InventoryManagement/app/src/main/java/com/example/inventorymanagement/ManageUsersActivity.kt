package com.example.inventorymanagement

import android.content.ContentValues
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.manage_users.*


class ManageUsersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manage_users)

        back_button2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        rmv.setOnClickListener {
            val db = Firebase.firestore
            val usr = rmvusrnm.text.toString()
            rmvusrnm.setText("")
            db.collection("users").document(usr)
                .delete()
        }
        add.setOnClickListener {
            val username = usrnm.text.toString()
            usrnm.setText("")
            val password = pswrd.text.toString()
            pswrd.setText("")
            val full_name = flnm.text.toString()
            flnm.setText("")
            val role = rl.text.toString()
            rl.setText("")
            val data = hashMapOf(
                "full_name" to full_name,
                "username" to username,
                "password" to password,
                "role" to role
            )
            val db = Firebase.firestore
            db.collection("users").document(username).set(data)
                .addOnSuccessListener { documentReference ->
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding document", e)
                }
        }
    }
}

