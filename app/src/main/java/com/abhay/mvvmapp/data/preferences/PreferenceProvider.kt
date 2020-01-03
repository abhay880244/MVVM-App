package com.abhay.mvvmapp.data.preferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

private const val KEY_SAVED_AT: String = "key_saved_at"

class PreferenceProvider(context: Context) {

    private val appContext = context.applicationContext

    private val preference: SharedPreferences
        get() =
            PreferenceManager.getDefaultSharedPreferences(appContext)


    fun saveLastSavedAt(savedAt: String) {

        preference.edit().putString(KEY_SAVED_AT, savedAt).apply()
    }

    fun getLastSavedAt(): String? {
        return preference.getString(KEY_SAVED_AT, null)
    }
}