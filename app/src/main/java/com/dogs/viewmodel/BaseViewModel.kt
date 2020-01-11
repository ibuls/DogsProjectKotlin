package com.dogs.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


// We are using AndroidViewModel because it takes application context and we need application context
// for database related tasks
abstract class BaseViewModel(application: Application): AndroidViewModel(application),CoroutineScope {

private val job = Job()


    // we are overriding coroutineContext here
    //  get() = job+Dispatchers.Main -> it means that if the job is finished then we should swith to main thread
    override val coroutineContext: CoroutineContext
        get() = job+Dispatchers.Main


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}