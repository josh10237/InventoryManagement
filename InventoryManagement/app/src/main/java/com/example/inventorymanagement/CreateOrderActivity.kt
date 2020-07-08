package com.example.inventorymanagement

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
import kotlinx.android.synthetic.main.product_page.*
import kotlinx.android.synthetic.main.product_page.p_name
import kotlinx.android.synthetic.main.quantity_editor.*
import java.lang.Exception

class CreateOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_outgoing_order_activitiy)
        try {
            val username = intent.extras!!.getString("product_name", "Error")
        }catch (e: Exception) {
            print(e)
            print("Not passed")
        }

//        q_edit.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                subbtn.text = "Save"
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//        })
//
//        u_edit.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                subbtn.text = "Save"
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//        })
//
//
//        subbtn.setOnClickListener {
//            if (subbtn.text == "Save"){
//                updateServer(id)
//            }
//            val intent = Intent(this, ProductPageActivity::class.java)
//            intent.putExtra("id", id)
//            intent.putExtra("name", name)
//            intent.putExtra("id", id)
//            intent.putExtra("name", name)
//            startActivity(intent)
//        }
    }
//    fun updateServer(t_id: String){
//        val units = u_edit.text.toString()
//        val quantity = q_edit.text.toString()
//        val data = hashMapOf(
//            "units" to units,
//            "quantity" to quantity
//        )
//        val db = Firebase.firestore
//        db.collection("products").document(t_id)
//            .set(data, SetOptions.merge())
//    }
//
//    fun updateFromServer(t_id: String) {
//        val db = Firebase.firestore
//        db.collection("products")
//            .whereEqualTo("tracking_id", t_id)
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    mapData( document["quantity"] as String, document["units"] as String)
//
//                }
//            }
//    }
//
//    fun mapData(q: String, u: String) {
//        q_edit.setText(q)
//        u_edit.setText(u)
//    }
}
//
