package com.dogs.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.locks.Lock

@Database(entities = arrayOf(DogBreed::class),version = 1)
abstract class DogDatabase: RoomDatabase() {
    abstract fun dogDao():DogDao


    companion object {
        @Volatile private var instance:DogDatabase? = null
        private val LOCK = Any()


        /*
        if instance is not null then instance will be returned

        ?: is called as elvis operator (it is specified for handling null conditions)
         */

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance ?: buildDatabase(context).also{
                instance = it
            }
        }

        private fun buildDatabase(context: Context)= Room.databaseBuilder(
            context.applicationContext,
            DogDatabase::class.java,
            "dogdatabase"
        ).build()
    }
}