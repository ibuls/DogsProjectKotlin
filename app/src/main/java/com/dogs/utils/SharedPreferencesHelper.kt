package com.dogs.utils

import android.content.Context
import android.content.SharedPreferences


import androidx.core.content.edit
import androidx.preference.PreferenceManager


class SharedPreferencesHelper {


    val PREF_TIME:String = "PREF_TIME"
    companion object{
        private var prefs:SharedPreferences?=null

        @Volatile private var instance: SharedPreferencesHelper? = null
        private val Lock = Any()


        operator fun invoke(context: Context):SharedPreferencesHelper = instance?: synchronized(Lock){
            instance ?:buildHelper(context).also{
                instance = it
            }
        }

        private fun buildHelper(context: Context): SharedPreferencesHelper {
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferencesHelper()
        }
    }

    fun saveUpdateTime(time:Long)
    {
        prefs?.edit(commit = true){
            putLong(PREF_TIME,time)
        }
    }

}