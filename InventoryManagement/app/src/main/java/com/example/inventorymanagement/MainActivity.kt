package com.example.inventorymanagement

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val username = intent.extras!!.getString("username", "Error")
        updateAppBar(username)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_add_product, R.id.navigation_outgoing, R.id.navigation_search))
        navView.setupWithNavController(navController)

    }

    private fun updateAppBar(username: String) {
        val db = Firebase.firestore
        db.collection("users").document(username)
            .get()
            .addOnSuccessListener { result ->
                println("RESULTS")
                println(result)
                println(result["full_name"])
                val name = result["full_name"]
                val role = result["role"]
                var str = (Html.fromHtml("\uD83D\uDC64    "+"<b>" + name + "</b>" +
                        "<br />" +"<small>" + "<i>" + role + "</i>" + "</small>" + "<br />"));
                getSupportActionBar()?.setTitle(str)
            }

    }
}