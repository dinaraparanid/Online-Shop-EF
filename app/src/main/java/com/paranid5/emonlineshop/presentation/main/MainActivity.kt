package com.paranid5.emonlineshop.presentation.main

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.paranid5.emonlineshop.R
import com.paranid5.emonlineshop.databinding.ActivityMainBinding
import com.paranid5.emonlineshop.domain.product.IProduct
import com.paranid5.emonlineshop.presentation.main.fragments.bag.BagFragment
import com.paranid5.emonlineshop.presentation.main.fragments.catalog.CatalogFragment
import com.paranid5.emonlineshop.presentation.main.fragments.discounts.DiscountsFragment
import com.paranid5.emonlineshop.presentation.main.fragments.favourites.FavouritesFragment
import com.paranid5.emonlineshop.presentation.main.fragments.home.HomeFragment
import com.paranid5.emonlineshop.presentation.main.fragments.products.ProductFragment
import com.paranid5.emonlineshop.presentation.main.fragments.profile.ProfileFragment
import com.paranid5.emonlineshop.presentation.utils.applyInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val viewModel by viewModels<MainViewModel>()

    private var onBackPressedRunning = false

    private val onBackPressedCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateToPreviousFragment()
            }
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
    }

    private fun initViews() {
        binding.bottomNavigationView.run {
            setOnApplyWindowInsetsListener(null)
            setPadding(0)
        }
    }

    private fun setupNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            if (onBackPressedRunning) {
                onBackPressedRunning = false
                return@setOnItemSelectedListener true
            }

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

    fun navigateToPreviousFragment() {
        onBackPressedRunning = true
        navController.popBackStack()
        binding.bottomNavigationView.selectedItemId = viewModel.removeFragmentAndGetCurId()
    }

    private fun navigateToFragment(
        @IdRes fragmentId: Int,
        args: Bundle? = null
    ): Boolean {
        navController.navigate(fragmentId, args)
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

    fun navigateToProfileFragment(): Boolean {
        viewModel.addFragment<ProfileFragment>()
        return navigateToFragment(R.id.profileFragment)
    }

    fun navigateToFavouritesFragment(): Boolean {
        viewModel.addFragment<ProfileFragment>()
        return navigateToFragment(R.id.favouritesFragment)
    }

    internal inline fun <reified F : Fragment> navigateToProductFragment(product: IProduct): Boolean {
        viewModel.addFragment<F>()

        return navigateToFragment(
            fragmentId = R.id.productFragment,
            args = ProductFragment.argumentsBundle(product)
        )
    }
}