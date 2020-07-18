package com.mohamed.moviesapp.adapters

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.mohamed.moviesapp.R
import com.mohamed.moviesapp.models.Movie
import com.mohamed.moviesapp.ui.SpecialMovie
import java.util.*

class MovieAdapter (var context: Context) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){

    private var grp: List<Movie> = ArrayList<Movie>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.customlayout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        // Glide better than picasso because picasso bring all photos from API with full size which make freezing to screen but Glide reduce the size of photos
        Glide.with(context).load("https://image.tmdb.org/t/p/original" + grp[position].poster_path).into(holder.poster)
        holder.moviename.setText(grp[position].title)

        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, SpecialMovie::class.java)
            intent.putExtra("arr", grp[position])
            context.startActivity(intent)
        })
    }


    override fun getItemCount(): Int {
        return if (grp != null) grp.size else 0
    }

    fun setList(grp: List<Movie>) {
        this.grp = grp
        notifyDataSetChanged()
    }

    //https://codingwithmitch.com/courses/android-local-database-cache-rest-api/glide-recyclerview-preloaders/



    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var poster: ImageView
        var moviename: TextView

        init {
            poster = itemView.findViewById(R.id.poster)
            moviename = itemView.findViewById(R.id.moviename)
        }
    }

    // DiffUtil class for performance better than notify data change
    // https://www.youtube.com/watch?v=y31fzLe2Ajw&list=PLgCYzUzKIBE-4SBxHzntuDDEREffjyoUj&index=9
     class MovieDiffCalkback( oldMoviesList: List<Movie>, newMoviesList: List<Movie>) : DiffUtil.Callback() {

        private var oldMoviesList: List<Movie>
        private var newMoviesList: List<Movie>

        init {
            this.oldMoviesList=oldMoviesList
            this.newMoviesList=newMoviesList
        }


        override fun getOldListSize(): Int {
            return oldMoviesList.size
        }

        override fun getNewListSize(): Int {
            return newMoviesList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldMoviesList[oldItemPosition].id == newMoviesList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldMoviesList[oldItemPosition].equals(newMoviesList[newItemPosition])
        }
    }

        fun updateUI(newData: List<Movie>) {
            
            val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(MovieDiffCalkback(grp, newData))
            diffResult.dispatchUpdatesTo(this)
        }

}