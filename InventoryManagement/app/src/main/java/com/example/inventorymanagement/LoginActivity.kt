package com.example.inventorymanagement

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Base64
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.login_activity.*
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        login_button.setOnClickListener {
            error_info.text = ""
            val username = username.text.toString()
            val pass = password.text.toString()
            val db = Firebase.firestore
            //TODO: Figure out encryption
            println("Login Clicked")
            validate(username, pass)
        }
    }

    fun validate(usr: String, ps: String) {
        val db = Firebase.firestore
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    println("user:")
                    println(document["username"])
                    println("pass:")
                    println(document["password"])
                    if (document["username"] == usr)
                    {
                        if (document["password"] == ps)
                        {
                            goToApp(usr)
                        }
                        else {
                            error_info.text = "Wrong Password"
                        }
                    }
                    else {
                        error_info.text = "Invalid Credentials"
                    }

                }
            }
    }
    fun goToApp(user: String){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("username", user)
        startActivity(intent)
    }
}
