package com.example.flixster

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

private const val TAG = "DetailActivityShow"

@Suppress("DEPRECATION")
class DetailActivityShow: AppCompatActivity() {
    private lateinit var mediaImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var overviewTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_show_detail)

        val show = intent.getSerializableExtra("show") as PopularShow?
        if (show != null) {
            val titleTextView = findViewById<TextView>(R.id.show_title)
            val overviewTextView = findViewById<TextView>(R.id.show_description)
            val mediaImageView = findViewById<ImageView>(R.id.show_image)
            titleTextView.text = show.title
            overviewTextView.text = show.description
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500/${show.posterPath}")
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .centerCrop()
                .transform(RoundedCorners(30))
                .into(mediaImageView)
        }
    }

}