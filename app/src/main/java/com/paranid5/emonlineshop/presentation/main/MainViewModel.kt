package com.paranid5.emonlineshop.presentation.main

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.paranid5.emonlineshop.R
import com.paranid5.emonlineshop.presentation.main.fragments.bag.BagFragment
import com.paranid5.emonlineshop.presentation.main.fragments.catalog.CatalogFragment
import com.paranid5.emonlineshop.presentation.main.fragments.discounts.DiscountsFragment
import com.paranid5.emonlineshop.presentation.main.fragments.home.HomeFragment
import com.paranid5.emonlineshop.presentation.main.fragments.profile.ProfileFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val fragmentBackStackState by lazy {
        MutableStateFlow(listOf(CatalogFragment::class.simpleName ?: ""))
    }

    fun addFragment(name: String): Unit =
        fragmentBackStackState.update { it + name }

    fun removeFragmentAndGetCurId(): Int =
        fragmentMenuId(
            fragmentName = fragmentBackStackState.updateAndGet {
                when (it.size) {
                    1 -> it
                    else -> it.dropLast(1)
                }
            }.last()
        )
}

internal fun fragmentMenuId(fragmentName: String) =
    when (fragmentName) {
        HomeFragment::class.simpleName -> R.id.home_item
        CatalogFragment::class.simpleName -> R.id.catalog_item
        BagFragment::class.simpleName -> R.id.bag_item
        DiscountsFragment::class.simpleName -> R.id.discounts_item
        ProfileFragment::class.simpleName -> R.id.profile_item
        else -> throw IllegalStateException("Unknown fragment")
    }

inline fun <reified F : Fragment> MainViewModel.addFragment(): Unit =
    addFragment(F::class.simpleName ?: "")