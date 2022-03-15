package com.logger.practicletask.common

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager private constructor(context: Context) {

    private val SP_NAME = javaClass.getPackage().name
    private var sharedPreferences: SharedPreferences? = null

    init {
        sharedPreferences = context.getSharedPreferences(SP_NAME, 0)
    }

    companion object {
        private lateinit var rmPrefs: SharedPreferencesManager
        fun getInstance(context: Context): SharedPreferencesManager {
            if (!::rmPrefs.isInitialized) {
                rmPrefs = SharedPreferencesManager(context)
            }
            return rmPrefs
        }
    }

    object PreferencesKeys {
        const val PREFERENCE_KEY_USER_ID = "pref_user_id"
    }

    fun getUserId(): String? {
        return sharedPreferences?.getString(PreferencesKeys.PREFERENCE_KEY_USER_ID, null)
    }

    fun setUserId(userId: String) {
        sharedPreferences?.edit()?.putString(PreferencesKeys.PREFERENCE_KEY_USER_ID, userId)?.apply()
    }

    fun clearPrefs() {
        sharedPreferences?.edit()?.clear()?.apply()
    }
}