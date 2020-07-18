package com.mohamed.moviesapp.Remote

import androidx.lifecycle.LiveData
import com.mohamed.moviesapp.Utils.Constaints.Companion.BASE_URL
import com.mohamed.moviesapp.Utils.Constaints.Companion.CONNECTION_TIMEOUT
import com.mohamed.moviesapp.Utils.Constaints.Companion.READ_TIMEOUT
import com.mohamed.moviesapp.Utils.Constaints.Companion.WRITE_TIMEOUT
import com.mohamed.moviesapp.Utils.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object MoviesClient {

    private val logging by lazy {  HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) }

    private fun getOkHttpClient(): OkHttpClient? {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)       // establish connection to server
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)                  // time between each byte read from the server //
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)               //time between each byte sent to server
            .retryOnConnectionFailure(false)
            .addInterceptor(logging)
            .build()
    }

   private fun getInstance(url :String) : Retrofit.Builder  {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory( RxJava2CallAdapterFactory.create())
            .addCallAdapterFactory( LiveDataCallAdapterFactory())
            .client(getOkHttpClient())

    }

    fun apiService(url :String)  : MovieInterface{
       return getInstance(url).build().create(MovieInterface:: class.java)
    }


}