package com.example.inventorymanagement.ui.add_product

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.inventorymanagement.R
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_add_product.*

class AddProductFragment : Fragment() {

    private lateinit var addProductModel: AddProductModel

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
        addProductModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        })
        scanProductBtn.setOnClickListener {
            val scanner = IntentIntegrator(activity)

            scanner.initiateScan()
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
}