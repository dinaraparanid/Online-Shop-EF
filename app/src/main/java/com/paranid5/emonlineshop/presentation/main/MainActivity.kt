package com.paranid5.emonlineshop.presentation.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.paranid5.emonlineshop.R
import com.paranid5.emonlineshop.databinding.ActivityMainBinding
import com.paranid5.emonlineshop.presentation.main.fragments.bag.BagFragment
import com.paranid5.emonlineshop.presentation.main.fragments.catalog.CatalogFragment
import com.paranid5.emonlineshop.presentation.main.fragments.discounts.DiscountsFragment
import com.paranid5.emonlineshop.presentation.main.fragments.home.HomeFragment
import com.paranid5.emonlineshop.presentation.main.fragments.profile.ProfileFragment
import com.paranid5.emonlineshop.presentation.ui.launchOnStarted
import com.paranid5.emonlineshop.presentation.utils.applyInsets
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        applyInsets(R.id.main)

        setupInitialFragment(viewModel)
        setupNavigation()

        launchAppLabelMonitoring()
    }

    private fun setupNavigation() =
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_item -> navigateToFragmentIfNotSame<HomeFragment>(viewModel)
                R.id.catalog_item -> navigateToFragmentIfNotSame<CatalogFragment>(viewModel)
                R.id.bag_item -> navigateToFragmentIfNotSame<BagFragment>(viewModel)
                R.id.discounts_item -> navigateToFragmentIfNotSame<DiscountsFragment>(viewModel)
                R.id.profile_item -> navigateToFragmentIfNotSame<ProfileFragment>(viewModel)
                else -> false
            }
        }

    private fun launchAppLabelMonitoring() = launchOnStarted {
        viewModel.currentAppLabelResFlow.collectLatest {
            binding.appLabel.text = getString(it)
        }
    }
}
