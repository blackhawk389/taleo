package com.sarah.objectives.preferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.sarah.objectives.utils.Constants
import com.sarah.objectives.utils.Constants.PREFERENCES.IS_LOGGED_IN
import com.sarah.objectives.utils.Constants.PREFERENCES.TOKEN
import com.sarah.objectives.utils.Constants.PREFERENCES.USER_DETAILS

object PreferenceHelper {


    private var editor: SharedPreferences.Editor? = null
    private var sharedPreferences: SharedPreferences? = null

    @SuppressLint("CommitPrefEdits")
    fun getInstance(context: Context) {

        val sharedPrefsFile = Constants.PREFERENCES.FILENAME

        val mainKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        sharedPreferences = EncryptedSharedPreferences.create(
            context,
            sharedPrefsFile,
            mainKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        editor = sharedPreferences!!.edit()
    }


    fun putString(key: String, value: String) {
        editor!!.putString(key, value)
        editor!!.apply()
    }
    fun getString(key: String) = sharedPreferences!!.getString(key, null)

    fun getBoolean(key: String) = sharedPreferences!!.getBoolean(key, false)

    fun putBoolean(key: String, value: Boolean) {
        editor!!.putBoolean(key, value)
        editor!!.apply()
    }

    fun putToken(token:String){
        editor!!.putString(TOKEN,token)
        editor!!.apply()
    }

    fun getToken() = sharedPreferences!!.getString(TOKEN,null)

    fun clearLoggedInPreferences(){
        editor!!.remove(TOKEN)
        editor!!.remove(IS_LOGGED_IN)
        editor!!.remove(USER_DETAILS)
        editor!!.apply()
    }



}