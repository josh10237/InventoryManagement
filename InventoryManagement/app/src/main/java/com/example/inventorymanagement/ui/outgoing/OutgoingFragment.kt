package com.example.inventorymanagement.ui.outgoing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.inventorymanagement.R

class OutgoingFragment : Fragment() {

    private lateinit var outgoingViewModel: OutgoingViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        outgoingViewModel =
                ViewModelProviders.of(this).get(OutgoingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_add_product, container, false)
        val textView: TextView = root.findViewById(R.id.t_outgoing)
        outgoingViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}