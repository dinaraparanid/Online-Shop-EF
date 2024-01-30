package com.paranid5.emonlineshop.presentation.login

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.paranid5.emonlineshop.BR
import com.paranid5.emonlineshop.R

class LoginViewModel(
    private val androidViewModel: LoginAndroidViewModel,
) : BaseObservable() {
    var nameInput
        @Bindable get() = androidViewModel.nameInput
        set(value) {
            androidViewModel.run { setNameInputError(!setNameInput(value)) }
            notifyPropertyChanged(BR.nameInput)
            notifyPropertyChanged(BR.textHintColor)
            notifyPropertyChanged(BR.hasInputError)
        }

    var familyInput
        @Bindable get() = androidViewModel.familyInput
        set(value) {
            androidViewModel.run { setFamilyInputError(!setFamilyInput(value)) }
            notifyPropertyChanged(BR.familyInput)
            notifyPropertyChanged(BR.familyHintColor)
            notifyPropertyChanged(BR.hasInputError)
        }

    var phoneInput
        @Bindable get() = androidViewModel.phoneInput
        set(value) {
            androidViewModel.run { setPhoneInputError(!setPhoneInput(value)) }
            notifyPropertyChanged(BR.phoneInput)
            notifyPropertyChanged(BR.phoneHintColor)
            notifyPropertyChanged(BR.hasInputError)
            notifyPropertyChanged(BR.phoneHintColor)
        }

    val hasInputError
        @Bindable get() = androidViewModel.hasInputError

    val textHintColor
        @Bindable get() = hintColor(nameInput)

    val familyHintColor
        @Bindable get() = hintColor(familyInput)

    val phoneHintColor
        @Bindable get() = hintColor(phoneInput)

    private var isPhoneInputFocused = false

    fun setPhoneInputFocused(isFocused: Boolean) {
        isPhoneInputFocused = isFocused
        notifyPropertyChanged(BR.phoneInputHint)
    }

    val phoneInputHint
        @Bindable get() = when {
            isPhoneInputFocused -> R.string.phone_hint
            else -> R.string.phone_number
        }
}

private fun hintColor(input: String) = when {
    input.isEmpty() -> R.color.grey
    else -> android.R.color.transparent
}