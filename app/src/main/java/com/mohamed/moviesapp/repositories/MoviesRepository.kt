package com.mohamed.moviesapp.repositories

import androidx.lifecycle.LiveData
import com.mohamed.moviesapp.remote.ApiResponse
import com.mohamed.moviesapp.utils.AppExecutors
import com.mohamed.moviesapp.utils.NetworkBoundResource
import com.mohamed.moviesapp.utils.Resource
import com.mohamed.moviesapp.models.DataResponseMovies
import com.mohamed.moviesapp.models.DataResponseYouTube
import com.mohamed.moviesapp.models.Movie
import com.mohamed.moviesapp.persistence.MovieDao
import com.mohamed.moviesapp.remote.MovieInterface
import retrofit2.Response
import javax.inject.Inject

class MoviesRepository@Inject constructor(private val movieInterface: MovieInterface, private val movieDao: MovieDao){


        fun getMovies(): LiveData<Resource<List<Movie>>> {

            return object : NetworkBoundResource<List<Movie>, DataResponseMovies>(AppExecutors) {
                override fun saveCallResult(item: DataResponseMovies) {
                    movieDao.deleteAllRows()
                    movieDao.insertRow(item.results)
                }

                override fun shouldFetch(data: List<Movie>): Boolean {
                    return true
                }

                override fun loadFromDb(): LiveData<List<Movie>> {
                    return movieDao.getAllRows()
                }

                override fun createCall(): LiveData<ApiResponse<DataResponseMovies>> {
                    return movieInterface.getMovies()
                }
            }.asLiveData()
        }

    suspend fun getYoutubeMovie(id:Int): Response<DataResponseYouTube> {
       return movieInterface.getmovie(id)
    }

}