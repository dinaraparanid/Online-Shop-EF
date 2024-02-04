package com.paranid5.emonlineshop.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewModelScope
import com.paranid5.emonlineshop.R
import com.paranid5.emonlineshop.databinding.ActivityLoginBinding
import com.paranid5.emonlineshop.presentation.main.MainActivity
import com.paranid5.emonlineshop.presentation.ui.getColorCompat
import com.paranid5.emonlineshop.presentation.ui.launchOnStarted
import com.paranid5.emonlineshop.presentation.utils.applyInsets
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val MOVE_PHONE_SELECTOR_REGEX = Regex("(\\+7 )*\\d")

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val androidViewModel by viewModels<LoginAndroidViewModel>()

    private val viewModel by lazy {
        LoginViewModel(androidViewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchUserLoggedInMonitoring()

        enableEdgeToEdge()
        supportActionBar?.hide()

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
            TextListener(
                onTextChanged = { viewModel.phoneInput = it },
                afterTextChanged = {
                    if (it matches MOVE_PHONE_SELECTOR_REGEX)
                        binding.phoneInput.run {
                            post { setSelection(length()) }
                        }
                }
            )
        )

        binding.phoneInput.setOnFocusChangeListener { _: View, hasFocus: Boolean ->
            viewModel.setPhoneInputFocused(hasFocus)
        }

        binding.signInButton.setOnClickListener {
            androidViewModel.viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    androidViewModel.storeUser()
                }
            }
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

    private fun launchUserLoggedInMonitoring() {
        launchOnStarted {
            androidViewModel.userOrNullFlow.filterNotNull().collectLatest {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}
