package com.dogs.viewmodel

import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.dogs.model.DogBreed
import com.dogs.model.DogDatabase
import com.dogs.model.DogsApiService
import com.dogs.utils.NotificationHelper
import com.dogs.utils.SharedPreferencesHelper
import com.dogs.view.ListFragmentDirections
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

class ListViewModel(application: Application): BaseViewModel(application) {

    private var prefHelper = SharedPreferencesHelper(getApplication())

    private var refreshTime = 5 * 60 * 1000 * 1000 *1000L // This is 5 min in nano sec (milli micro nano)


    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    private val dogsService= DogsApiService()
    private val disposable = CompositeDisposable()



    fun refresh(){

        checkCacheDuration()
        val updateTime:Long? = prefHelper.getUpdateTime()

        if (updateTime!=null && updateTime!=0L && System.nanoTime() - updateTime < refreshTime)
        {
            fetchFromDatabase()
        }else
        {
            fetchFromRemote()
        }
    }

    private fun checkCacheDuration() {
        val cachePreference = prefHelper.getCacheDuration()
        try{
            val cachePreferenceInt = cachePreference?.toInt() ?: 5* 60
            refreshTime = cachePreferenceInt.times( 1000 * 1000 *1000L)
        }catch (e:NumberFormatException){
            e.printStackTrace()
        }
    }


    fun refreshBypassCache(){
        fetchFromRemote()
    }

    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            val dogs= DogDatabase(getApplication()).dogDao().getAllDogs()
            dogsRetrieved(dogs)
            Toast.makeText(getApplication(),"Data Retrived from Database",Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchFromRemote()
    {
        loading.value = true

        disposable.add(dogsService.getDogs()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<DogBreed>>(){
                override fun onSuccess(listDogs: List<DogBreed>) {

                    NotificationHelper(getApplication()).createNotification()
                    Toast.makeText(getApplication(),"Data Retrived from Endpoint",Toast.LENGTH_SHORT).show()

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

    fun onSettingsClicked(view: View){
        val actionListToSettings = ListFragmentDirections.actionListToSettings()
        Navigation.findNavController(view).navigate(actionListToSettings)

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}