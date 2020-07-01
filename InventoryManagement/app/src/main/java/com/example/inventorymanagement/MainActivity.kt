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


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        var str = (Html.fromHtml("\uD83D\uDC64    "+"<b>" + "Josh Benson" + "</b>" +
                "<br />" +"<small>" + "<i>" + "  Associate" + "</i>" + "</small>" + "<br />"));
        getSupportActionBar()?.setTitle(str)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_add_product, R.id.navigation_outgoing, R.id.navigation_search))
        navView.setupWithNavController(navController)

    }
}