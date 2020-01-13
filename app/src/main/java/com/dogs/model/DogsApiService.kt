package com.dogs.model

import com.facebook.stetho.okhttp3.StethoInterceptor
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DogsApiService {


    fun getDogs():Single<List<DogBreed>>{
        return getSingletonClient().getDogs()
    }

    companion object{
        @Volatile private var api:DogsApi? = null

        val BASE_URL = "https://raw.githubusercontent.com/";
        val client: OkHttpClient =
            OkHttpClient.Builder().addInterceptor(StethoInterceptor()).build()

        private var lock = Any()

        private fun getSingletonClient():DogsApi= api?: synchronized(lock){
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
}