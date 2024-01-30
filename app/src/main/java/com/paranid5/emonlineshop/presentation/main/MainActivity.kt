package com.paranid5.emonlineshop.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.paranid5.emonlineshop.R
import com.paranid5.emonlineshop.presentation.utils.applyInsets

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MAIN_ACTIVITY", "LOGIN")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        applyInsets(R.id.main)
        println("MAIN")
    }
}
