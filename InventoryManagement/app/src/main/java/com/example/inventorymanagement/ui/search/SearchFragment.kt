package com.example.inventorymanagement.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.inventorymanagement.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_search.view.*


class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProviders.of(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        val searchProductBtn = root.findViewById<ImageButton>(R.id.searchBtn)
        val ll_main = root.findViewById(R.id.lin_layout) as LinearLayout

        searchProductBtn.setOnClickListener {
            println("start")
            queryDatabase(ll_main)
            println("finish")

        }
        return root
    }

    fun queryDatabase(linlay: LinearLayout) {
        val db = Firebase.firestore
            db.collection("products")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    mapData(document["name"] as String, document["tracking_id"] as String,
                        document["quantity"] as String, linlay as LinearLayout
                    )

                }
            }
        }
    fun mapData(name: String, t_id: String, quantity: String, ll: LinearLayout) {
        println(name)
        println(t_id)
        println(quantity)
        // creating the button
        val button_dynamic = Button(activity)
        // setting layout_width and layout_height using layout parameters
        button_dynamic.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        button_dynamic.text = name + "   " + t_id
        // add Button to LinearLayout
        ll.addView(button_dynamic)

    }

}