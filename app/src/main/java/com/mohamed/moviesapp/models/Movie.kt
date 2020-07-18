package com.mohamed.moviesapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "movies") // name of table in database
data class Movie (


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "iddatabase")
    var iddatabase: Int,


    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id : Int,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title : String,

    @ColumnInfo(name = "vote_average")                    // Room names
    @SerializedName("vote_average")                // retrofit names
    val vote_average : String,

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    val overview : String ,

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    val poster_path : String) : Serializable{

}