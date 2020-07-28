package com.mohamed.moviesapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mohamed.moviesapp.bases.BaseActivity
import com.mohamed.moviesapp.R
import com.mohamed.moviesapp.utils.Resource.Status
import com.mohamed.moviesapp.adapters.MovieAdapter
import com.mohamed.moviesapp.viewmodels.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesActivity : BaseActivity() {

    //https://www.youtube.com/watch?v=HZnLbJ3nvMc&list=PLXjbGq0ERjFpkwKH5jgwgstKj-twzhOqk&index=6
    //https://www.youtube.com/watch?v=UueBOQkH5fw&list=PLXjbGq0ERjFpkwKH5jgwgstKj-twzhOqk&index=7

    private val movieViewModel: MovieViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private val TAG = "MoviesActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler)



        intiRecylerView()


        // if any change happen in  it will update the activity which is appearing only (owner this)
        movieViewModel.getMovies().observe(this, Observer {
            if (it.data != null) {
            Log.d(TAG, "onChanged: status: " + it.status)
            when (it.status) {
                Status.LOADING -> {
                }
               Status.ERROR -> {
                    Log.e(TAG, "onChanged: cannot refresh the cache.")
                    Log.e(TAG, "onChanged: ERROR message: " + it.message)
                    Log.e(TAG, "onChanged: status: ERROR, " +it.data.size)
                    movieAdapter.setList(it.data)
                }
                Status.SUCCESS -> {
                    Log.d(TAG, "onChanged: cache has been refreshed.")
                    Log.d(TAG, "onChanged: status: SUCCESS, " + it.data.size)
                    movieAdapter.setList(it.data)
                  //  movieAdapter.updateUI(it.data)
                }
            }
        } })


    }

    private fun intiRecylerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this@MoviesActivity, 2)
        movieAdapter = MovieAdapter(this)
        recyclerView.setAdapter(movieAdapter)
    }
}
