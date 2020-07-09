package com.example.inventorymanagement

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.new_outgoing_order_activitiy.*
import kotlinx.android.synthetic.main.product_page.*
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class CreateOrderActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_outgoing_order_activitiy)
        try {
            val p_id = intent.extras!!.getString("id", "Error")
            tnum_edit.setText(p_id.toString())
        }catch (e: Exception) {
            print(e)
            print("Not passed")
        }
        back_button1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            //intent.putExtra("username", user)
            startActivity(intent)
        }

        subbtn3.setOnClickListener {
            updateServer()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun updateServer() {
        val tracking = tnum_edit.text.toString()
        val quantity = editTextNumber2.text.toString()
        val address = editTextTextPostalAddress.text.toString()
        val fdate = LocalDateTime.now()
        val out = hashMapOf(
            "quantity" to quantity,
            "adress" to address,
            "date" to fdate
        )
        updateFromServer(tracking, out)
    }
}


    fun updateFromServer(t_id: String, out: Any){
        val db = Firebase.firestore
        val randomKey = java.util.UUID.randomUUID().toString()
        db.collection("products")
            .whereEqualTo("tracking_id", t_id)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) { //only runs once
                    val o_map = document["outgoing"]
                    var finalHashMap : HashMap<String, HashMap<*,*>> = o_map as HashMap<String, HashMap<*, *>>
                    finalHashMap.put(randomKey, out as java.util.HashMap<*, *>)
                    var data = hashMapOf("outgoing" to finalHashMap)
                    db.collection("products").document(t_id)
                        .set(data, SetOptions.merge())
                }
            }
    }
