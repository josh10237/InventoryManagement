package com.example.inventorymanagement.ui.outgoing

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.inventorymanagement.CreateOrderActivity
import com.example.inventorymanagement.MyApplication
import com.example.inventorymanagement.R
import kotlinx.android.synthetic.main.fragment_outgoing.*
import kotlinx.android.synthetic.main.product_page.*

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
        if (role == "Associate"){
            ao.setVisibility(View.INVISIBLE)
        }

        ao.setOnClickListener {
            val intent = Intent(activity, CreateOrderActivity::class.java)
            startActivity(intent)
        }

        return root
    }
}