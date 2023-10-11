package com.example.flixster

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


class PopularMovieRecyclerViewAdapter(
    private val movies: List<PopularMovie>,
    private val mListener: OnListFragmentInteractionListener?
)
    : RecyclerView.Adapter<PopularMovieRecyclerViewAdapter.MovieViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_popular_movie, parent, false)
        return MovieViewHolder(view)
    }

    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: PopularMovie? = null
        val mMovieTitle: TextView = mView.findViewById<View>(R.id.movie_title) as TextView
        //val mMovieDescription: TextView = mView.findViewById<View>(R.id.movie_description) as TextView
        val mMovieImage: ImageView = mView.findViewById<View>(R.id.movie_image) as ImageView
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.mMovieTitle.text = movie.title
        //holder.mMovieDescription.text = movie.description
        Glide.with(holder.mView)
            .load("https://image.tmdb.org/t/p/w500/${movie.posterPath}")//normal
            .placeholder(R.drawable.ic_launcher_foreground)//placeholder
            .error(R.drawable.ic_launcher_background)//error
            .centerCrop()
            .transform(RoundedCorners(30))
            .into(holder.mMovieImage)


        holder.mView.setOnClickListener {
            holder.mItem?.let { movie ->
                mListener?.onItemClick(movie)
            }

            val intent = Intent(holder.mView.context, DetailActivityMovie::class.java)
            intent.putExtra("movie", movie)

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                holder.mView.context as Activity,
                holder.mMovieImage, "poster"
            )
            ActivityCompat.startActivity(holder.mView.context, intent, options.toBundle())
        }

    }

    override fun getItemCount(): Int {
        return movies.size
    }
}