package com.example.cloneinstagram.common.util

import android.text.Editable
import android.text.TextWatcher

class OnTextWatcher(private var txtWatcher: (String)-> Unit): TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(txt: CharSequence?, p1: Int, p2: Int, p3: Int) {
        txtWatcher(txt.toString())
    }

    override fun afterTextChanged(p0: Editable?) {}
}