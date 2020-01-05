package com.dogs.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dogs.model.DogBreed

class DetailViewModel: ViewModel() {
    val dogs = MutableLiveData<DogBreed>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()



    fun refresh(){

    }
}