package com.paranid5.emonlineshop.presentation.login

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.paranid5.emonlineshop.BR

class LoginViewModel(
    private val androidViewModel: LoginAndroidViewModel
) : BaseObservable() {
    var nameInput
        @Bindable get() = androidViewModel.nameInput
        set(value) {
            androidViewModel.setNameInput(value)
            notifyPropertyChanged(BR.nameInput)
        }

    var familyInput
        @Bindable get() = androidViewModel.familyInput
        set(value) {
            androidViewModel.setFamilyInput(value)
            notifyPropertyChanged(BR.familyInput)
        }

    var phoneInput
        @Bindable get() = androidViewModel.phoneInput
        set(value) {
            androidViewModel.setPhoneInput(value)
            notifyPropertyChanged(BR.phoneInput)
        }

    val hasInputError
        @Bindable get() = androidViewModel.hasInputError
}