package com.example.inventorymanagement.ui.outgoing

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.inventorymanagement.CreateOrderActivity
import com.example.inventorymanagement.MyApplication
import com.example.inventorymanagement.MyApplication.Companion.rebuildMap
import com.example.inventorymanagement.ProductPageActivity
import com.example.inventorymanagement.R
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_outgoing.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.product_page.*
import java.lang.Exception

class OutgoingFragment : Fragment() {

    private lateinit var outgoingViewModel: OutgoingViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        outgoingViewModel =
                ViewModelProviders.of(this).get(OutgoingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_outgoing, container, false)
        val role = MyApplication.Companion.role
        val ao = root.findViewById<ImageButton>(R.id.add_order)
        val ll_main = root.findViewById(R.id.lin_layout) as LinearLayout
        queryDatabase(ll_main)
        if (role == "Associate"){
            ao.setVisibility(View.INVISIBLE)
        }

        ao.setOnClickListener {
            val intent = Intent(activity, CreateOrderActivity::class.java)
            startActivity(intent)
        }
        return root
    }

    fun queryDatabase(linlay: LinearLayout) {
        val db = Firebase.firestore
        db.collection("products")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val name = document["name"] as String
                    val tracking_id = document["tracking_id"] as String
                    val quantity = document["quantity"] as String
                    val units = document["units"] as String
                    val outgoing = document["outgoing"] as java.util.HashMap<*, *>
                    print("OUTGOING:")
                    println (outgoing)
                    for((k, v) in outgoing){
                        val items = v as HashMap<String, String>
                        val o_address = items["address"] as String
                            print("Address:")
                            println(o_address)
                            val o_date = items["date"] as String
                            val o_quantity = items["quantity"] as String
                            mapData(
                                name,
                                tracking_id,
                                quantity,
                                units,
                                o_quantity,
                                o_address,
                                o_date,
                                linlay
                            )

                        Log.d("Outgoing items", "$k : $v")
                    }
                }

            }
    }
    fun mapData(name: String, t_id: String, quantity: String, units: String, o_quantity: String, o_address: String, o_date: String, ll: LinearLayout) {
        val button_dynamic = Button(activity)
        button_dynamic.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        button_dynamic.setGravity(Gravity.LEFT or Gravity.START)
        var str = (Html.fromHtml("\uD83D\uDCE6    "+"<b>" + name + "</b>" +
                "<small>" + "<i>" + "   " + o_quantity + " " + units + "</i>" + "</small>" + "<br />" +
                "<small>" + "<pre>" + "<b>" + "          " + o_address + "</b>" + "   Processed:  "+o_date+ "</pre>" + "</small>"));
        button_dynamic.text = str
        // add Button to LinearLayout
        button_dynamic.setOnClickListener {
            goToProductPage(t_id, name, quantity, units)
        }
        button_dynamic.setOnLongClickListener{
            val btn1 = button_dynamic as Button
            btn1.setVisibility(View.GONE)
            orderComplete(t_id, name, quantity, units, o_quantity, o_address, o_date)
            return@setOnLongClickListener true
        }

        ll.addView(button_dynamic)

    }
    fun goToProductPage(tracking: String, product_name: String, quantity: String, units: String){
        val intent = Intent(activity, ProductPageActivity::class.java)
        intent.putExtra("id", tracking)
        intent.putExtra("name", product_name)
        intent.putExtra("quantity", quantity)
        intent.putExtra("units", units)
        startActivity(intent)
    }
    fun orderComplete(tracking1: String, product_name1: String, quantity1: String, units1: String, o_quantity1: String, o_address1: String, o_date1: String){
        println("LONG CLICKED BUTTON")
//        var rebuildMap = HashMap<String, Map<String, String>>()
        rebuildMap.clear()
        info_error_txt.text = ""
        val db = Firebase.firestore
        db.collection("products")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val tracking_id = document["tracking_id"] as String
                    val quantity = document["quantity"] as String
                    val outgoing = document["outgoing"] as java.util.HashMap<*, *>
                    //if correct product
                    if(tracking_id == tracking1){
                        println("FOUND PRODUCT")
                        for ((k, v) in outgoing) {
                            val key = k as String
                            val items = v as HashMap<String, String>
                            val o_address = items["address"] as String
                            val o_date = items["date"] as String
                            val o_quantity = items["quantity"] as String
                            // If same order
                            println(o_address)
                            println(o_address1)
                            if (o_quantity == o_quantity1 && o_address == o_address1 && o_date == o_date1){
                                //Subtract order quantity from total quantity and remerge quantity into database and throw error if negative
                                println("REMOVED")
                                println(key)
                                var newQuantity = quantity.toInt() - o_quantity1.toInt()
                                if (newQuantity < 0){
                                    info_error_txt.text = "Order too large. Cannot be filled with current inventory"
                                }else{
                                    var data = hashMapOf("quantity" to newQuantity.toString())
                                    db.collection("products").document(tracking_id)
                                        .set(data, SetOptions.merge())
                                }
                            }else{
                                rebuildMap.put(key, v)
                            }
                        }
                        var data1 = hashMapOf("outgoing" to rebuildMap)
                        //delete outgoing field
                        val docRef = db.collection("products").document(tracking1)
                        val updates = hashMapOf<String, Any>("outgoing" to FieldValue.delete())
                        docRef.update(updates)
                        //merge in new outgoing map
                        db.collection("products").document(tracking1)
                            .set(data1, SetOptions.merge())
                    }
                }
            }

    }


}
