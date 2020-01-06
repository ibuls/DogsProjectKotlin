package com.dogs.model

import io.reactivex.Single
import retrofit2.http.GET

interface DogsApi {

    //https://raw.githubusercontent.com/
    @GET("DevTides/DogsApi/master/dogs.json")
    fun getDogs():Single<List<DogBreed>>
}