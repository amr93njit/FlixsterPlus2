package com.example.flixster;

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

private const val TAG = "DetailActivityMovie"

@Suppress("DEPRECATION")
class DetailActivityMovie: AppCompatActivity() {
    private lateinit var mediaImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var overviewTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_movie_detail)

        val movie = intent.getSerializableExtra("movie") as PopularMovie?
        if (movie != null) {    //still don't get why IDE made me put this
            val titleTextView = findViewById<TextView>(R.id.movie_title)
            val overviewTextView = findViewById<TextView>(R.id.movie_description)
            val mediaImageView = findViewById<ImageView>(R.id.movie_image)
            titleTextView.text = movie.title
            overviewTextView.text = movie.description
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500/${movie.posterPath}")
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .centerCrop()
                .transform(RoundedCorners(30))
                .into(mediaImageView)
        }
    }

}