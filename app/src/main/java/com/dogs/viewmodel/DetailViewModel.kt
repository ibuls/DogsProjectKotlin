package com.dogs.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dogs.model.DogBreed
import com.dogs.model.DogDatabase
import kotlinx.coroutines.launch
import java.util.*

class DetailViewModel(application: Application): BaseViewModel(application) {
    val dog = MutableLiveData<DogBreed>()



    fun fetch(uuid: Int){
        launch {
            val dogList = DogDatabase(getApplication()).dogDao().getDog(uuid)
            dog.value = dogList
        }

    }
}