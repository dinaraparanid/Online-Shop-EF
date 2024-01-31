package com.paranid5.emonlineshop.presentation.main

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _currentFragmentNameState by lazy {
        MutableStateFlow("")
    }

    val currentFragmentNameState by lazy {
        _currentFragmentNameState.asStateFlow()
    }

    fun setCurrentFragmentName(name: String) =
        _currentFragmentNameState.update { name }
}

inline val MainViewModel.currentFragmentName
    get() = currentFragmentNameState.value

inline fun <reified F : Fragment> MainViewModel.isFragmentCurrent() =
    F::class.simpleName == currentFragmentName

inline fun <reified F : Fragment> MainViewModel.setCurrentFragmentName() =
    setCurrentFragmentName(F::class.simpleName ?: "")