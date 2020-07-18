package com.mohamed.moviesapp.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mohamed.moviesapp.Remote.MoviesClient
import com.mohamed.moviesapp.Utils.Constaints.Companion.BASE_URL
import com.mohamed.moviesapp.Utils.Resource
import com.mohamed.moviesapp.models.Movie
import com.mohamed.moviesapp.repositories.MoviesRepository

class MovieViewModel :ViewModel() {


     fun getMovies(context: Context): LiveData<Resource<List<Movie>>> {
        return MoviesRepository(context).getMovies()
    }

}