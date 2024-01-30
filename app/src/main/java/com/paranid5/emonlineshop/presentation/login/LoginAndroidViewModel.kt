package com.paranid5.emonlineshop.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private val RUSSIAN_REGEX = Regex("[а-яА-Я]")
private val PHONE_REGEX = Regex("\\+7 \\d{3} \\d{3} \\d{2} \\d{2}")

@HiltViewModel
class LoginAndroidViewModel @Inject constructor() : ViewModel() {
    private val _nameInputState by lazy {
        MutableStateFlow("")
    }

    private val _hasNameErrorState by lazy {
        MutableStateFlow(false)
    }

    val nameInputState by lazy {
        _nameInputState.asStateFlow()
    }

    fun setNameInput(name: String): Boolean {
        if (!correctNameInput(name))
            return false

        _nameInputState.update { name }
        return true
    }

    fun setNameInputError(hasInputError: Boolean) =
        _hasNameErrorState.update { hasInputError }

    private val _familyInputState by lazy {
        MutableStateFlow("")
    }

    private val _hasFamilyErrorState by lazy {
        MutableStateFlow(false)
    }

    val familyInputState by lazy {
        _familyInputState.asStateFlow()
    }

    fun setFamilyInput(family: String): Boolean {
        if (!correctNameInput(family))
            return false

        _familyInputState.update { family }
        return true
    }

    fun setFamilyInputError(hasInputError: Boolean) =
        _hasFamilyErrorState.update { hasInputError }

    private val _phoneInputState by lazy {
        MutableStateFlow("")
    }

    private val _hasPhoneErrorState by lazy {
        MutableStateFlow(false)
    }

    val phoneInputState by lazy {
        _phoneInputState.asStateFlow()
    }

    fun setPhoneInput(phone: String): Boolean {
        if (!correctPhoneInput(phone))
            return false

        _phoneInputState.update { phone }
        return true
    }

    fun setPhoneInputError(hasInputError: Boolean) =
        _hasPhoneErrorState.update { hasInputError }

    private val hasInputErrorFlow by lazy {
        combine(
            _hasNameErrorState,
            _hasFamilyErrorState,
            _hasPhoneErrorState
        ) { name, family, phone ->
            name && family && phone
        }
    }

    val hasInputErrorState by lazy {
        hasInputErrorFlow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)
    }
}

inline val LoginAndroidViewModel.nameInput
    get() = nameInputState.value

inline val LoginAndroidViewModel.familyInput
    get() = familyInputState.value

inline val LoginAndroidViewModel.phoneInput
    get() = phoneInputState.value

inline val LoginAndroidViewModel.hasInputError
    get() = hasInputErrorState.value

private fun correctNameInput(input: String) =
    input matches RUSSIAN_REGEX

private fun correctPhoneInput(phone: String) =
    phone matches PHONE_REGEX