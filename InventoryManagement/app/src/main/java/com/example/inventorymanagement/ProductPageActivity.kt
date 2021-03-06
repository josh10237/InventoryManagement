package com.example.inventorymanagement

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorymanagement.ui.search.SearchFragment
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_outgoing.*
import kotlinx.android.synthetic.main.product_page.*

class ProductPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_page)
        val id = intent.extras!!.getString("id", "Tracking ID Not Found")
        val name = intent.extras!!.getString("name", "Name Not Found")
        p_name.text = name
        disp_id.text = id
        notes.isEnabled = false
        save_notes.setVisibility(View.INVISIBLE)
        notes.setTypeface(null, Typeface.ITALIC)
        updateFromServer(id)
        if (MyApplication.Companion.role == "Associate"){
            add_order1.setVisibility(View.INVISIBLE)
        }

        edit_notes.setOnClickListener {
            notes.isEnabled = true
            notes.setTypeface(null, Typeface.NORMAL)
            edit_notes.setVisibility(View.INVISIBLE);
            save_notes.setVisibility(View.VISIBLE);
        }

        add_order1.setOnClickListener {
            val intent = Intent(this, CreateOrderActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("name", name)
            startActivity(intent)
        }

        save_notes.setOnClickListener {
            val n = notes.text.toString()
            notes.isEnabled = false
            notes.setTypeface(null, Typeface.ITALIC)
            edit_notes.setVisibility(View.VISIBLE);
            save_notes.setVisibility(View.INVISIBLE);
            updateNotes(id, n)
        }

        back_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            //intent.putExtra("username", user)
            startActivity(intent)
        }
        edit_units.setOnClickListener{
            val intent = Intent(this, EditQuantityActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("name", name)
            startActivity(intent)
        }
    }
    fun updateNotes(t_id: String, notes: String){
        val data = hashMapOf("notes" to notes)
        val db = Firebase.firestore
        db.collection("products").document(t_id)
            .set(data, SetOptions.merge())
    }

    fun updateFromServer(t_id: String) {
        val db = Firebase.firestore
        db.collection("products")
            .whereEqualTo("tracking_id", t_id)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    mapData(document["notes"] as String, document["quantity"] as String,
                        document["units"] as String
                    )

                }
            }
    }

    fun mapData(n: String, q: String, u: String) {
        notes.setText(n)
        println("x$q $u")
        quantity.text = "x$q $u"
    }
}

