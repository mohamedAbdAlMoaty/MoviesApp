package com.mohamed.moviesapp.ui

import android.os.Bundle
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.mohamed.moviesapp.R
import com.mohamed.moviesapp.Remote.MoviesClient
import com.mohamed.moviesapp.Utils.Constaints
import com.mohamed.moviesapp.Utils.Constaints.Companion.API
import com.mohamed.moviesapp.models.Movie
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class SpecialMovie : YouTubeBaseActivity() , YouTubePlayer.OnInitializedListener{

    private var id:Int = 0
    private lateinit var title: String
    private lateinit var vote: String
    private lateinit var overview: String
    private  var key: String?=null
    private lateinit var txt: TextView
    private lateinit var title_movie:TextView
    private lateinit var rate: RatingBar
    private lateinit var player: YouTubePlayerView
    private lateinit var parentJob :Job
    private lateinit var task :Job
    private lateinit var completableJob :CompletableJob

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_special_movie)

        title_movie = findViewById(R.id.title_of_movie)
        txt = findViewById(R.id.txt)
        rate = findViewById(R.id.rate)
        player = findViewById(R.id.player)

        checkIntent()


        //handlingDataUsingCoroutine1()        //  or
        handlingDataUsingCoroutine2()






    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider, youTubePlayer: YouTubePlayer, wasRestored: Boolean) {
        if (!wasRestored && key !=null) {
            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
            youTubePlayer.loadVideo(key)
            youTubePlayer.play()
        }
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider, youTubeInitializationResult: YouTubeInitializationResult) {
        Toast.makeText(this@SpecialMovie, youTubeInitializationResult.toString(), Toast.LENGTH_SHORT).show()
    }

    private suspend fun getkey(baseUrl:String, id:Int) {
        val response = MoviesClient.apiService(baseUrl).getmovie(id)
        if(response.isSuccessful){
            for (str in response.body()!!.results){
                key=str.key
            }
        }
    }

    private fun checkIntent(){
        if (intent.getSerializableExtra("arr") != null ) {
            val movie: Movie = intent.getSerializableExtra("arr") as Movie
            id = movie.id
            title = movie.title
            vote = movie.vote_average
            overview = movie.overview
            title_movie.setText(title)

            txt.text = overview
            rate.rating = 5 * vote.toFloat() / 10
        }
    }

    private fun handlingDataUsingCoroutine1(){

        val handler = CoroutineExceptionHandler { _, exception ->
            println("Exception thrown in one of the children: $exception")
        }


        parentJob = CoroutineScope(Dispatchers.IO).launch(handler){

            task=launch {
                val baseUrl= Constaints.BASE_URL+"movie/"
                getkey(baseUrl,id)
            }
            withContext(Dispatchers.Main){
                player.initialize(API, this@SpecialMovie)
                task.cancel()
            }

            task.invokeOnCompletion { throwable ->
                if (throwable != null) {
                    println("task failed: ${throwable}")
                }
            }
        }
        parentJob.invokeOnCompletion { throwable ->
            if(throwable != null){
                println("Parent job  failed: ${throwable}")

            }
            else{
                println("Parent job  SUCCESS")
            }
        }
    }

    private fun handlingDataUsingCoroutine2(){
        val handler = CoroutineExceptionHandler { _, exception ->
            println("Exception thrown in one of the children: $exception")
        }

        // completableJob shoud be intilized
        completableJob = Job()
        completableJob.let {
                theJob-> CoroutineScope(IO + theJob).launch(handler) {
                             val baseUrl= Constaints.BASE_URL+"movie/"
                             getkey(baseUrl,id)
                        withContext(Main){
                            player.initialize(API, this@SpecialMovie)
                            theJob.complete()
                        }
            }

            theJob.invokeOnCompletion { throwable ->
                if (throwable != null) {
                    println("task failed: ${throwable}")
                }
            }
        }

        completableJob.invokeOnCompletion { throwable ->
            if(throwable != null){
                println("Parent job  failed: ${throwable}")

            }
            else{
                println("Parent job  SUCCESS")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        parentJob.cancel()
        completableJob.complete()
    }
}
