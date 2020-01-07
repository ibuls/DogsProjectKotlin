package com.dogs.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dogs.model.DogBreed
import com.dogs.model.DogsApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel: ViewModel() {
    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    private val dogsService= DogsApiService()
    private val disposable = CompositeDisposable()



    fun refresh(){
        fetchFromRemote()
    }

    private fun fetchFromRemote()
    {
        loading.value = true

        disposable.add(dogsService.getDogs()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<DogBreed>>(){
                override fun onSuccess(listDogs: List<DogBreed>) {
                    dogsLoadError.value = false
                    loading.value = false
                    dogs.value = listDogs
                }

                override fun onError(e: Throwable) {

                    dogsLoadError.value = true
                    loading.value = false
                    e.printStackTrace()

                }

            })
        )

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}