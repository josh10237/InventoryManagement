package com.example.inventorymanagement.ui.add_product

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.inventorymanagement.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_add_product.*
import java.time.LocalDate
import java.time.LocalDateTime

class AddProductFragment : Fragment() {

    private lateinit var addProductModel: AddProductModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        addProductModel =
                ViewModelProviders.of(this).get(AddProductModel::class.java)
        val root = inflater.inflate(R.layout.fragment_add_product, container, false)
        //val textView: TextView = root.findViewById(R.id.t_outgoing)
        val scanProductBtn = root.findViewById<Button>(R.id.scanBtn)
        val submitBtn = root.findViewById<Button>(R.id.submit_button)
        addProductModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        })
        scanProductBtn.setOnClickListener {
            val scanner = IntentIntegrator(activity)
            scanner.initiateScan()
        }

        submitBtn.setOnClickListener {
            var product_tracking_number = et_tracking_number.text.toString()
            et_tracking_number.getText().clear()
            var product_name = et_product_name.text.toString()
            et_product_name.getText().clear()
            var product_quantity = et_quantity.text.toString()
            val currentDT = LocalDateTime.now()
            queryDatabase(product_name, product_quantity, product_tracking_number, currentDT)
            et_quantity.getText().clear()
            error_info_ap.text = ""
        }
        return root
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val result =
                IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Scanned: " + result.contents, Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
    fun queryDatabase(product_name: String, product_quantity: String, product_tracking_number: String, currentDT: Any){
        val db = Firebase.firestore
        var called = false
        db.collection("products")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (product_tracking_number == document["tracking_id"]) {
                        if (product_name == document["name"]){
                            var current_q = document["quantity"].toString()
                            println("Current")
                            println(current_q)
                            println("Add")
                            println(product_quantity)
                            var new_q = current_q.toIntOrNull()?.plus(product_quantity.toInt())
                            var final_q = new_q.toString()
                            println("New")
                            println(final_q)
                            var data = hashMapOf("quantity" to final_q)
                            db.collection("products").document(product_tracking_number)
                                .set(data, SetOptions.merge())
                            called = true
                        }
                        else{
                            error_info_ap.text = "Name and ID do not match"
                        }
                    }
                }
                if (!called){
                    addProduct(product_name, product_quantity, product_tracking_number, currentDT)
                }
            }
    }
    fun addProduct(product_name: String, product_quantity: String, product_tracking_number: String, currentDT: Any){
        val db = Firebase.firestore
        val history = hashMapOf(
            "created_at" to currentDT
        )
        val outgoing = hashMapOf(
            "created_at" to null
        )
        val newProduct = hashMapOf(
            "name" to product_name,
            "notes" to "No notes",
            "quantity" to product_quantity,
            "tracking_id" to product_tracking_number,
            "units" to "Units",
            "history" to history,
            "outgoing" to outgoing
        )
        println("Created Product")
        println(newProduct)
        db.collection("products").document(product_tracking_number).set(newProduct)
            .addOnSuccessListener { documentReference ->
                //Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun increment(product_name: String, product_quantity: String, product_tracking_number: String){
        val db = Firebase.firestore
        db.collection("products")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (product_tracking_number == document["tracking_id"]) {

                    }
                }
            }
    }
}
