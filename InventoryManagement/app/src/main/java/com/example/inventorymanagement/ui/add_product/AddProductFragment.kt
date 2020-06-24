package com.example.inventorymanagement.ui.add_product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.inventorymanagement.R

class AddProductFragment : Fragment() {

    private lateinit var addProductModel: AddProductModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        addProductModel =
                ViewModelProviders.of(this).get(AddProductModel::class.java)
        val root = inflater.inflate(R.layout.fragment_outgoing, container, false)
        val textView: TextView = root.findViewById(R.id.t_outgoing)
        addProductModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}