package com.dogs.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface DogDao{



    // var agrfs means this function accepts multiple aruments of type DogBreed
    // the inserted Objects id's will be returned in the list object
    // this function will be invoked on a different tread hence we use suspend keyword
    // a suspended function can be paused or resumed
    @Insert
    suspend fun insertAll(vararg dogs: DogBreed): List<Long>

    @Query("SELECT * FROM dogbreed")
    suspend fun getAllDogs():List<DogBreed>

    @Query("SELECT * FROM dogbreed where uuid = :dogId")
    suspend fun getDog(dogId:Int):DogBreed

    @Query("DELETE FROM dogbreed")
    suspend fun deleteAllDogs()


}