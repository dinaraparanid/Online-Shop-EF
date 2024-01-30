package com.paranid5.emonlineshop.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.paranid5.emonlineshop.R
import com.paranid5.emonlineshop.databinding.ActivityLoginBinding
import com.paranid5.emonlineshop.presentation.main.MainActivity
import com.paranid5.emonlineshop.presentation.ui.getColorCompat
import com.paranid5.emonlineshop.presentation.ui.launchOnStarted
import com.paranid5.emonlineshop.presentation.utils.applyInsets
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

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
        initViews()
        launchErrorMonitoring()
    }

    private fun initViews() {
        binding.nameInput.addTextChangedListener(
            TextListener { viewModel.nameInput = it }
        )

        binding.familyInput.addTextChangedListener(
            TextListener { viewModel.familyInput = it }
        )

        binding.phoneInput.addTextChangedListener(
            TextListener { viewModel.phoneInput = it }
        )

        binding.phoneInput.setOnFocusChangeListener { _: View, hasFocus: Boolean ->
            viewModel.setPhoneInputFocused(hasFocus)
        }

        binding.signInButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun launchErrorMonitoring() {
        launchOnStarted {
            androidViewModel.hasNameErrorState.collectLatest {
                binding.nameLayout.boxStrokeColor = getColorCompat(androidViewModel.nameBoxColor)
            }
        }

        launchOnStarted {
            androidViewModel.hasFamilyErrorState.collectLatest {
                binding.familyLayout.boxStrokeColor = getColorCompat(androidViewModel.familyBoxColor)
            }
        }

        launchOnStarted {
            androidViewModel.hasPhoneErrorState.collectLatest {
                binding.phoneLayout.boxStrokeColor = getColorCompat(androidViewModel.phoneBoxColor)
            }
        }
    }
}
