package com.paranid5.emonlineshop.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paranid5.emonlineship.data.config.ConfigRepository
import com.paranid5.emonlineship.data.config.sources.user.UserPublisher
import com.paranid5.emonlineship.data.config.sources.user.UserPublisherImpl
import com.paranid5.emonlineship.data.config.sources.user.UserSubscriber
import com.paranid5.emonlineship.data.config.sources.user.UserSubscriberImpl
import com.paranid5.emonlineshop.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private val RUSSIAN_REGEX = Regex("[а-яА-Я]+")
private val PHONE_REGEX = Regex("\\+7 \\d{3} \\d{3} \\d{2} \\d{2}")

@HiltViewModel
class LoginAndroidViewModel @Inject constructor(
    configRepository: ConfigRepository
) : ViewModel(),
    UserSubscriber by UserSubscriberImpl(configRepository),
    UserPublisher by UserPublisherImpl(configRepository) {
    private val _nameInputState by lazy {
        MutableStateFlow("")
    }

    private val _hasNameErrorState by lazy {
        MutableStateFlow(true)
    }

    val nameInputState: StateFlow<String> by lazy {
        _nameInputState.asStateFlow()
    }

    val hasNameErrorState: StateFlow<Boolean> by lazy {
        _hasNameErrorState.asStateFlow()
    }

    fun setNameInput(name: String): Boolean {
        if (!correctNameInput(name))
            return false

        _nameInputState.update { name }
        return true
    }

    fun setNameInputError(hasInputError: Boolean): Unit =
        _hasNameErrorState.update { hasInputError }

    private val _familyInputState by lazy {
        MutableStateFlow("")
    }

    private val _hasFamilyErrorState by lazy {
        MutableStateFlow(true)
    }

    val familyInputState: StateFlow<String> by lazy {
        _familyInputState.asStateFlow()
    }

    val hasFamilyErrorState: StateFlow<Boolean> by lazy {
        _hasFamilyErrorState.asStateFlow()
    }

    fun setFamilyInput(family: String): Boolean {
        if (!correctNameInput(family))
            return false

        _familyInputState.update { family }
        return true
    }

    fun setFamilyInputError(hasInputError: Boolean): Unit =
        _hasFamilyErrorState.update { hasInputError }

    private val _phoneInputState by lazy {
        MutableStateFlow("")
    }

    private val _hasPhoneErrorState by lazy {
        MutableStateFlow(true)
    }

    val phoneInputState: StateFlow<String> by lazy {
        _phoneInputState.asStateFlow()
    }

    val hasPhoneErrorState: StateFlow<Boolean> by lazy {
        _hasPhoneErrorState.asStateFlow()
    }

    fun setPhoneInput(phone: String): Boolean {
        if (!correctPhoneInput(phone))
            return false

        _phoneInputState.update { phone }
        return true
    }

    fun setPhoneInputError(hasInputError: Boolean): Unit =
        _hasPhoneErrorState.update { hasInputError }

    private val hasInputErrorFlow by lazy {
        combine(
            _hasNameErrorState,
            _hasFamilyErrorState,
            _hasPhoneErrorState
        ) { name, family, phone ->
            name || family || phone
        }
    }

    val hasInputErrorState: StateFlow<Boolean> by lazy {
        hasInputErrorFlow.stateIn(viewModelScope, SharingStarted.Eagerly, true)
    }
}

inline val LoginAndroidViewModel.nameInput: String
    get() = nameInputState.value

inline val LoginAndroidViewModel.familyInput: String
    get() = familyInputState.value

inline val LoginAndroidViewModel.phoneInput: String
    get() = phoneInputState.value

inline val LoginAndroidViewModel.hasInputError: Boolean
    get() = hasInputErrorState.value

inline val LoginAndroidViewModel.hasNameError: Boolean
    get() = hasNameErrorState.value

inline val LoginAndroidViewModel.hasFamilyError: Boolean
    get() = hasFamilyErrorState.value

inline val LoginAndroidViewModel.hasPhoneError: Boolean
    get() = hasPhoneErrorState.value

private inline val LoginAndroidViewModel.user
    get() = User(
        name = nameInput,
        family = familyInput,
        phone = phoneInput
    )

suspend fun LoginAndroidViewModel.storeUser(): Unit = storeUser(user)

private fun correctNameInput(input: String) =
    input matches RUSSIAN_REGEX

private fun correctPhoneInput(phone: String) =
    phone matches PHONE_REGEX