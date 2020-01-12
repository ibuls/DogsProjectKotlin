package com.dogs.model

import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.locks.Lock

class DogsApiService {

    private val BASE_URL = "https://raw.githubusercontent.com/";


    private val client:OkHttpClient = OkHttpClient()

    private var api:DogsApi? = null
    private var Lock = Any()


    fun  getApi()=api?: synchronized(Lock){
        api?{
           Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(DogsApi::class.java)
               .also {
                   api = it
               }
        }
    }

    fun getDogs():Single<List<DogBreed>>{
        return api.getDogs()
    }
}