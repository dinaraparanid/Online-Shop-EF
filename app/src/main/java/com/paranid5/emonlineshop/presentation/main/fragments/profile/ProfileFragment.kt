package com.paranid5.emonlineshop.presentation.main.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.paranid5.emonlineship.data.config.sources.user.userNameFamilyFlow
import com.paranid5.emonlineship.data.config.sources.user.userPhoneFlow
import com.paranid5.emonlineshop.R
import com.paranid5.emonlineshop.databinding.FragmentProfileBinding
import com.paranid5.emonlineshop.presentation.login.LoginActivity
import com.paranid5.emonlineshop.presentation.main.MainActivity
import com.paranid5.emonlineshop.presentation.ui.launchOnStarted
import com.paranid5.emonlineshop.presentation.utils.productOnRussianRes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        )

        launchStateChangesMonitoring()
        initView()

        return binding.root
    }

    private fun initView() {
        binding.favouritesLayout.setOnClickListener {
            (requireActivity() as MainActivity).navigateToFavouritesFragment()
        }

        binding.signOutButton.setOnClickListener {
            viewModel.viewModelScope.launch {
                withContext(Dispatchers.IO) { viewModel.removeUser() }

                requireActivity().startActivity(
                    Intent(requireActivity(), LoginActivity::class.java)
                )

                requireActivity().finish()
            }
        }
    }

    private fun launchStateChangesMonitoring() {
        launchOnStarted {
            viewModel.userNameFamilyFlow.collectLatest {
                binding.userNameView.text = it
            }
        }

        launchOnStarted {
            viewModel.userPhoneFlow.collectLatest {
                binding.userPhoneView.text = it
            }
        }

        launchOnStarted {
            viewModel.favouritesNumberFlow.collectLatest {
                binding.favouritesNumberView.text = getString(productOnRussianRes(it), it)
            }
        }
    }
}