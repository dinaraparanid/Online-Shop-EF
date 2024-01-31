package com.paranid5.emonlineshop.presentation.main

import androidx.fragment.app.Fragment
import com.paranid5.emonlineshop.R
import com.paranid5.emonlineshop.presentation.main.fragments.home.HomeFragment

fun MainActivity.setupInitialFragment(viewModel: MainViewModel) {
    viewModel.setCurrentFragmentName<HomeFragment>()

    supportFragmentManager
        .beginTransaction()
        .replace(
            R.id.nav_host_main_fragments,
            HomeFragment::class.java,
            null
        )
        .addToBackStack(null)
        .commit()
}

inline fun <reified F : Fragment> MainActivity.navigateToFragmentIfNotSame(
    viewModel: MainViewModel
): Boolean = when {
    viewModel.isFragmentCurrent<F>() -> false

    else -> {
        viewModel.setCurrentFragmentName<F>()
        navigateToFragment<F>()
        true
    }
}

inline fun <reified F : Fragment> MainActivity.navigateToFragment(): Int =
    supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        .replace(
            R.id.nav_host_main_fragments,
            F::class.java,
            null
        )
        .addToBackStack(null)
        .commit()