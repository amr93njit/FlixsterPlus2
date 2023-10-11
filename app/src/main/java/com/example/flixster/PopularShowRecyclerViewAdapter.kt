package com.example.flixster

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.content.Context
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class PopularShowRecyclerViewAdapter(
    private val shows: List<PopularShow>,
    private val mListener: OnListFragmentInteractionListener?
)
    : RecyclerView.Adapter<PopularShowRecyclerViewAdapter.ShowViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_popular_show, parent, false)
        return ShowViewHolder(view)
    }

    inner class ShowViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: PopularShow? = null
        val mShowTitle: TextView = mView.findViewById<View>(R.id.show_title) as TextView
        //val mShowDescription: TextView = mView.findViewById<View>(R.id.show_description) as TextView
        val mShowImage: ImageView = mView.findViewById<View>(R.id.show_image) as ImageView
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val show = shows[position]
        holder.mShowTitle.text = show.title
        //holder.mShowDescription.text = show.description
        Glide.with(holder.mView)
            .load("https://image.tmdb.org/t/p/w500/${show.posterPath}")//normal
            .placeholder(R.drawable.ic_launcher_foreground)//placeholder
            .error(R.drawable.ic_launcher_background)//error
            .centerCrop()
            .transform(RoundedCorners(30))
            .into(holder.mShowImage)

        holder.mView.setOnClickListener {
            holder.mItem?.let { show ->
                mListener?.onItemClick(show)
            }
            val intent = Intent(holder.mView.context, DetailActivityShow::class.java)
            intent.putExtra("show", show)

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                holder.mView.context as Activity,
                holder.mShowImage, "poster"
            )
            ActivityCompat.startActivity(holder.mView.context, intent, options.toBundle())
        }
    }

    override fun getItemCount(): Int {
        return shows.size
    }
}