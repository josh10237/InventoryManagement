package com.example.inventorymanagement.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
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
import com.example.inventorymanagement.ProductPageActivity
import com.example.inventorymanagement.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.product_page.*


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
        val search = root.findViewById(R.id.search_bar) as TextView
        val ll_main = root.findViewById(R.id.lin_layout) as LinearLayout
        queryDatabase(ll_main)

        search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateList(ll_main)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        return root
    }

    fun updateList(linlay: LinearLayout){
        var current_set = search_bar.text.toString()
        for (btn in linlay) {
            var b = btn as Button
            val buttonText = b.text.toString()
            if (buttonText.contains(current_set, ignoreCase = true)){
                btn.setVisibility(View.VISIBLE)
            }
            else {
                btn.setVisibility(View.GONE)
            }
        }
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
        val button_dynamic = Button(activity)
        button_dynamic.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        button_dynamic.setGravity(Gravity.LEFT or Gravity.START)
        var str = (Html.fromHtml("\uD83D\uDCE6    "+"<b>" + name + "</b>" +
                "<small>" + "<i>" + "  x" + quantity + "</i>" + "</small>" + "<br />" +
                "<small>" + "<pre>" + "          " + t_id + "</pre>" + "</small>"));
        button_dynamic.text = str
        // add Button to LinearLayout
        button_dynamic.setOnClickListener {
            goToProductPage(t_id, name)
        }
        ll.addView(button_dynamic)

    }
    fun goToProductPage(tracking: String, product_name: String){
        val intent = Intent(activity, ProductPageActivity::class.java)
        intent.putExtra("id", tracking)
        intent.putExtra("name", product_name)
        startActivity(intent)
    }

}