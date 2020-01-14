package com.dogs.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class DogBreed(
    /*
    {
    "bred_for": "Small rodent hunting, lapdog",
    "breed_group": "Toy",
    "height": {
      "imperial": "9 - 11.5",
      "metric": "23 - 29"
    },
    "id": 1,
    "life_span": "10 - 12 years",
    "name": "Affenpinscher",
    "origin": "Germany, France",
    "temperament": "Stubborn, Curious, Playful, Adventurous, Active, Fun-loving",
    "weight": {
      "imperial": "6 - 13",
      "metric": "3 - 6"
    },
    "url": "https://raw.githubusercontent.com/DevTides/DogsApi/master/1.jpg"
  }
    */

    @ColumnInfo(name = "breed_id")
    @SerializedName("id")
    val breedId:String?,

    @ColumnInfo(name = "dog_name")
    @SerializedName("name")
    val dogBreed:String?,

    @ColumnInfo(name = "life_span")
    @SerializedName("life_span")
    val lifeSpan:String?,

    @ColumnInfo(name = "breed_group")
    @SerializedName("breed_group")
    val breedGroup:String?,

    @ColumnInfo(name = "bred_for")
    @SerializedName("bred_for")
    val breedFor:String?,

    @ColumnInfo(name = "temperament")
    @SerializedName("temperament")
    val temprament:String?,

    @ColumnInfo(name = "url")
    @SerializedName("url")
    val imageUrl:String?
){

    @PrimaryKey(autoGenerate = true)
    var uuid:Int= 0

}
data class DogPalette(var color:Int)