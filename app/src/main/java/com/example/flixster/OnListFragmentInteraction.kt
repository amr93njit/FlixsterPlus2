package com.example.flixster

/**
 * This interface is used by the [PopularMovieRecyclerViewAdapter] to ensure
 * it has an appropriate Listener.
 *
 * In this app, it's implemented by [PopularFragment]
 */
interface OnListFragmentInteractionListener {
    fun onItemClick(item: PopularMovie){}

    fun onItemClick(item: PopularShow)
}
