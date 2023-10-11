package com.example.flixster

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"
class PopularFragment : Fragment(), OnListFragmentInteractionListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_popular_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerViewMovie = view.findViewById<View>(R.id.movieList) as RecyclerView
        val recyclerViewShow = view.findViewById<View>(R.id.showList) as RecyclerView
        val context = view.context
        recyclerViewMovie.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewShow.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        updateAdapter(progressBar, recyclerViewMovie, recyclerViewShow)
        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerViewMovie: RecyclerView, recyclerViewShow: RecyclerView) {
        progressBar.show()

        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api-key"] = API_KEY

        client[
            "https://api.themoviedb.org/3/movie/popular?api_key=$API_KEY",
            params,
            object : JsonHttpResponseHandler()
            {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Headers,
                    json: JSON
                ) {
                    progressBar.hide()

                    val arrayMovieType = object : TypeToken<List<PopularMovie>>() {}.type
                    val resultsArray = json.jsonObject.getJSONArray("results")
                    val gson = Gson()
                    val models: List<PopularMovie> = gson.fromJson(resultsArray.toString(), arrayMovieType)
                    recyclerViewMovie.adapter = PopularMovieRecyclerViewAdapter(models, this@PopularFragment)

                }
                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    errorResponse: String,
                    t: Throwable?
                ) {
                    progressBar.hide()
                    t?.message?.let {
                        Log.e("PopularMovieFragment", errorResponse)
                    }
                }
            }]

        client[
            "https://api.themoviedb.org/3/tv/popular?api_key=$API_KEY",
            params,
            object : JsonHttpResponseHandler()
            {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Headers,
                    json: JSON
                ) {
                    progressBar.hide()

                    val arrayShowType = object : TypeToken<List<PopularShow>>() {}.type
                    val resultsArray = json.jsonObject.getJSONArray("results")
                    val gson = Gson()
                    val models: List<PopularShow> = gson.fromJson(resultsArray.toString(), arrayShowType)
                    recyclerViewShow.adapter = PopularShowRecyclerViewAdapter(models, this@PopularFragment)

                }
                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    errorResponse: String,
                    t: Throwable?
                ) {
                    progressBar.hide()
                    t?.message?.let {
                        Log.e("PopularTVFragment", errorResponse)
                    }
                }
            }]
    }

    override fun onItemClick(item: PopularMovie) {
    }

    override fun onItemClick(item: PopularShow) {
    }
}
