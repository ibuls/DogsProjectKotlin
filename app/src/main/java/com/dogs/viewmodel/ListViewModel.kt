package com.dogs.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dogs.model.DogBreed
import com.dogs.model.DogDatabase
import com.dogs.model.DogsApiService
import com.dogs.utils.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application): BaseViewModel(application) {

    private var prefHelper = SharedPreferencesHelper(getApplication())
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
                    storeDogsLocally(listDogs)
                }

                override fun onError(e: Throwable) {

                    dogsLoadError.value = true
                    loading.value = false
                    e.printStackTrace()

                }

            })
        )

    }



    private fun storeDogsLocally(listDogs: List<DogBreed>) {
        // launch will execute all the task in another thread
        // this is because of CouritinesScope in BaseVM class
        launch {
            val dogDao = DogDatabase(getApplication()).dogDao()
            dogDao.deleteAllDogs()
            // in kotlin we can split the list (or array) into individual parameters using * operator
            val result  = dogDao.insertAll(*listDogs.toTypedArray())

            var i = 0;

            while (i < listDogs.size){
                listDogs[i].uuid = result[i].toInt()
                ++i
            }
            dogsRetrieved(listDogs)

        }

        prefHelper.saveUpdateTime(System.nanoTime() )
    }

    private fun dogsRetrieved(listDogs: List<DogBreed>) {
        dogsLoadError.value = false
        loading.value = false
        dogs.value = listDogs

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}