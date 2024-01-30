package com.paranid5.emonlineshop.presentation.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.paranid5.emonlineshop.R
import com.paranid5.emonlineshop.presentation.utils.applyInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()

        setContentView(R.layout.activity_main)
        applyInsets(R.id.main)
    }
}
