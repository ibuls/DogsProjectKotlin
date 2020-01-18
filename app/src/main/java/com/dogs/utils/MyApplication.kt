package com.dogs.utils

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.google.firebase.FirebaseApp


// for multidex issue fix
class MyApplication: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        // multidex crash fixed
        MultiDex.install(this)
    }

}