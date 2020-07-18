package com.mohamed.moviesapp.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.mohamed.moviesapp.Remote.ApiResponse
import com.mohamed.moviesapp.Remote.MoviesClient
import com.mohamed.moviesapp.Utils.AppExecutors
import com.mohamed.moviesapp.Utils.Constaints
import com.mohamed.moviesapp.Utils.NetworkBoundResource
import com.mohamed.moviesapp.Utils.Resource
import com.mohamed.moviesapp.models.DataResponseMovies
import com.mohamed.moviesapp.models.Movie
import com.mohamed.moviesapp.persistence.MovieDao
import com.mohamed.moviesapp.persistence.MovieDatabase

class MoviesRepository(context: Context) {

    private val movietDao: MovieDao

    init {
        movietDao = MovieDatabase.getDatabase(context)!!.movieDao()
    }


        fun getMovies(): LiveData<Resource<List<Movie>>> {

            return object : NetworkBoundResource<List<Movie>, DataResponseMovies>(AppExecutors) {
                override fun saveCallResult(item: DataResponseMovies) {
                    movietDao.deleteAllRows()
                    movietDao.insertRow(item.results)
                }

                override fun shouldFetch(data: List<Movie>): Boolean {
                    return true
                }

                override fun loadFromDb(): LiveData<List<Movie>> {
                    return movietDao.getAllRows()
                }

                override fun createCall(): LiveData<ApiResponse<DataResponseMovies>> {
                    val baseUrl = Constaints.BASE_URL + "discover/"
                    return MoviesClient.apiService(baseUrl).getMovies()
                }
            }.asLiveData()
        }

}