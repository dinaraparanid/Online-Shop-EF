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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _currentFragmentNameState by lazy {
        MutableStateFlow("")
    }

    val currentFragmentNameState: StateFlow<String> by lazy {
        _currentFragmentNameState.asStateFlow()
    }

    fun setCurrentFragmentName(name: String): Unit =
        _currentFragmentNameState.update { name }
}

inline val MainViewModel.currentFragmentName: String
    get() = currentFragmentNameState.value

@OptIn(ExperimentalCoroutinesApi::class)
inline val MainViewModel.currentAppLabelResFlow: Flow<Int>
    get() = currentFragmentNameState.mapLatest {
        when (it) {
            BagFragment::class.simpleName -> R.string.bag_item_title
            CatalogFragment::class.simpleName -> R.string.catalog_item_title
            DiscountsFragment::class.simpleName -> R.string.discounts_item_title
            HomeFragment::class.simpleName -> R.string.home_item_title
            ProfileFragment::class.simpleName -> R.string.profile_app_label
            else -> throw IllegalStateException("Unknown fragment")
        }
    }

inline fun <reified F : Fragment> MainViewModel.isFragmentCurrent(): Boolean =
    F::class.simpleName == currentFragmentName

inline fun <reified F : Fragment> MainViewModel.setCurrentFragmentName(): Unit =
    setCurrentFragmentName(F::class.simpleName ?: "")