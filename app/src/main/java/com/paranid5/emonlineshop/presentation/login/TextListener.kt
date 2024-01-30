package com.paranid5.emonlineshop.presentation.login

import android.text.Editable
import android.text.TextWatcher

inline fun TextListener(
    crossinline beforeTextChanged: (s: String) -> Unit = {},
    crossinline afterTextChanged: (s: String) -> Unit = {},
    crossinline onTextChanged: (s: String) -> Unit,
): TextWatcher = object : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
        beforeTextChanged(s?.toString() ?: "")

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
        onTextChanged(s?.toString() ?: "")

    override fun afterTextChanged(s: Editable?) = afterTextChanged(s?.toString() ?: "")
}
