package com.paranid5.emonlineshop.presentation.main

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.paranid5.emonlineshop.R
import com.paranid5.emonlineshop.databinding.ActivityMainBinding
import com.paranid5.emonlineshop.presentation.main.fragments.bag.BagFragment
import com.paranid5.emonlineshop.presentation.main.fragments.catalog.CatalogFragment
import com.paranid5.emonlineshop.presentation.main.fragments.discounts.DiscountsFragment
import com.paranid5.emonlineshop.presentation.main.fragments.favourites.FavouritesFragment
import com.paranid5.emonlineshop.presentation.main.fragments.home.HomeFragment
import com.paranid5.emonlineshop.presentation.main.fragments.profile.ProfileFragment
import com.paranid5.emonlineshop.presentation.ui.launchOnStarted
import com.paranid5.emonlineshop.presentation.utils.applyInsets
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<MainViewModel>()

    private val onBackPressedCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() =
                moveToPreviousFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        applyInsets(R.id.main)

        navController = (supportFragmentManager
            .findFragmentById(R.id.nav_host_main_fragments)
                as NavHostFragment)
            .navController

        initViews()
        setupNavigation()

        launchAppLabelMonitoring()
    }

    private fun initViews() =
        binding.backButton.setOnClickListener {
            moveToPreviousFragment()
        }

    private fun setupNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_item -> navigateToHomeFragment()
                R.id.catalog_item -> navigateToCatalogFragment()
                R.id.bag_item -> navigateToBagFragment()
                R.id.discounts_item -> navigateToDiscountsFragment()
                R.id.profile_item -> navigateToProfileFragment()
                else -> false
            }
        }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun moveToPreviousFragment() {
        viewModel.removeFragment()
        navController.popBackStack()
    }

    private fun navigateToFragment(@IdRes fragmentId: Int): Boolean {
        navController.navigate(fragmentId)
        return true
    }

    private fun navigateToHomeFragment(): Boolean {
        viewModel.addFragment<HomeFragment>()
        return navigateToFragment(R.id.homeFragment)
    }

    private fun navigateToCatalogFragment(): Boolean {
        viewModel.addFragment<CatalogFragment>()
        return navigateToFragment(R.id.catalogFragment)
    }

    private fun navigateToBagFragment(): Boolean {
        viewModel.addFragment<BagFragment>()
        return navigateToFragment(R.id.bagFragment)
    }

    private fun navigateToDiscountsFragment(): Boolean {
        viewModel.addFragment<DiscountsFragment>()
        return navigateToFragment(R.id.discountsFragment)
    }

    private fun navigateToProfileFragment(): Boolean {
        viewModel.addFragment<ProfileFragment>()
        return navigateToFragment(R.id.profileFragment)
    }

    fun navigateToFavouritesFragment(): Boolean {
        viewModel.addFragment<FavouritesFragment>()
        return navigateToFragment(R.id.favouritesFragment)
    }

    private fun launchAppLabelMonitoring() = launchOnStarted {
        viewModel.currentAppLabelResFlow.collectLatest {
            binding.appLabel.text = getString(it)
            binding.backButton.visibility = backButtonVisibility(it)
            binding.backButton.isEnabled = backButtonEnabled(it)
            constraintLabel(it)
        }
    }

    private fun constraintLabel(fragmentType: Int) =
        when (fragmentType) {
            R.string.favourites -> constraintLabelToBackButton()
            else -> constraintLabelToCenter()
        }

    private fun constraintLabelToCenter() {
        val layout = binding.main

        ConstraintSet().run {
            clone(layout)
            connect(R.id.app_label, ConstraintSet.START, R.id.main, ConstraintSet.START, 0)
            connect(R.id.app_label, ConstraintSet.END, R.id.main, ConstraintSet.END, 0)
            applyTo(layout)
        }
    }

    private fun constraintLabelToBackButton() {
        val layout = binding.main

        ConstraintSet().run {
            clone(layout)
            connect(R.id.app_label, ConstraintSet.START, R.id.backButton, ConstraintSet.END, 28)
            clear(R.id.app_label, ConstraintSet.END)
            applyTo(layout)
        }
    }
}

internal fun backButtonVisibility(fragmentType: Int) =
    when (fragmentType) {
        R.string.favourites -> View.VISIBLE
        else -> View.INVISIBLE
    }

internal fun backButtonEnabled(fragmentType: Int) =
    when (fragmentType) {
        R.string.favourites -> true
        else -> false
    }