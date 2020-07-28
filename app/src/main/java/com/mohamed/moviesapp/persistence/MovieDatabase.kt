package com.mohamed.moviesapp.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mohamed.moviesapp.utils.Constaints.Companion.DATABASE_NAME
import com.mohamed.moviesapp.models.Movie

@Database(entities = [Movie::class], version = 18, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}