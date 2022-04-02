package com.multitech.motivation.utils

import android.content.Context
import android.content.SharedPreferences

class SecurityPreferences(context: Context) {
    private val msharedPreferences: SharedPreferences =
        context.getSharedPreferences("motivation", Context.MODE_PRIVATE)

    fun storeString(key: String, value: String) {
        msharedPreferences.edit().putString(key, value).apply()
    }

    fun getStoreString(key: String): String? {
        return msharedPreferences.getString(key, "")
    }
}
