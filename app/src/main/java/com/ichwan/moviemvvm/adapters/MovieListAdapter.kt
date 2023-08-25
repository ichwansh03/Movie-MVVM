package com.ichwan.moviemvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ichwan.moviemvvm.BuildConfig
import com.ichwan.moviemvvm.databinding.MovieListBinding
import com.ichwan.moviemvvm.models.Movies

class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    private val movieList = ArrayList<Movies>()

    fun setMovies(list: List<Movies>) {
        this.movieList.clear()
        this.movieList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: MovieListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(movieList[position]) {
                binding.apply {
                    title.text = originalTitle
                    lang.text = originalLanguage
                    releaseDate.text = releaseDate.toString()
                    ratingText.text = voteAverage.toString()
                    ratingBar.rating = voteAverage ?: 0f

                    Glide.with(itemView).load("${BuildConfig.PHOTO_BASE_URL}$posterPath").into(poster)
                }
            }
        }
    }
}