package com.paranid5.emonlineshop.presentation.login

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.paranid5.emonlineshop.R
import com.paranid5.emonlineshop.databinding.ActivityLoginBinding
import com.paranid5.emonlineshop.presentation.utils.applyInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val androidViewModel by viewModels<LoginAndroidViewModel>()

    private val viewModel by lazy {
        LoginViewModel(androidViewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel
        applyInsets(R.id.login_activity_layout)
    }

    private fun initViews() {

    }
}