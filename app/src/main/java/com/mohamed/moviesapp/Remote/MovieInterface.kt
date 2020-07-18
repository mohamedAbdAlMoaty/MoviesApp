package com.mohamed.moviesapp.Remote

import androidx.lifecycle.LiveData
import com.mohamed.moviesapp.models.DataResponseMovies
import com.mohamed.moviesapp.models.DataResponseYouTube
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieInterface {

 @GET("movie?sort_by=popularity.desc&api_key=b1f5db0adfa30b9e8714dba42e9edb44")
 fun getMovies(): LiveData<ApiResponse<DataResponseMovies>>



 @GET("{id}/videos?api_key=18147b0826078c6d5e462bf97f3e032d")
 suspend fun getmovie(@Path("id") idMovie : Int): Response<DataResponseYouTube>

}