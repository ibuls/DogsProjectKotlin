package com.dogs.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dogs.model.DogBreed

class ListViewModel: ViewModel() {
    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()



    fun refresh(){
        val obj1 = DogBreed("1","Corgy","20 year","Normal","BreedFor","Normal","")
        val obj2 = DogBreed("2","Lebrador","20 year","Normal","BreedFor","Normal","")
        val obj3 = DogBreed("3","Beetel","20 year","Normal","BreedFor","Normal","")
        val obj4 = DogBreed("4","Pomerian","20 year","Normal","BreedFor","Normal","")
        val obj5 = DogBreed("5","Desi Dog","20 year","Normal","BreedFor","Normal","")
        val dogList = arrayListOf<DogBreed>(obj1,obj2,obj3,obj4,obj5)

        dogs.value = dogList
        dogsLoadError.value = false
        loading.value = false
        Log.d("TEST","refresh completed!")
    }
}