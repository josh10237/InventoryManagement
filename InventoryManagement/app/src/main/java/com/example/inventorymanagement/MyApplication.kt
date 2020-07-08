package com.example.inventorymanagement

import android.app.Application

class MyApplication: Application() {
    companion object {
        var username = "null"
    }

    override fun onCreate() {
        super.onCreate()
        // initialization code here
    }
}