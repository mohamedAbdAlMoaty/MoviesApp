package com.mohamed.moviesapp.viewmodels


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mohamed.moviesapp.utils.Resource
import com.mohamed.moviesapp.models.Movie
import com.mohamed.moviesapp.repositories.MoviesRepository



class MovieViewModel @ViewModelInject constructor(private val movieRepository: MoviesRepository): ViewModel() {


     fun getMovies(): LiveData<Resource<List<Movie>>> {
         return movieRepository.getMovies()
    }
}