package com.example.myflixster

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myflixster.R.id

class MoviesRecyclerViewAdapter (
    private val movies: List<Movie>,
    private val mListener: OnListFragmentIteractionListener?
    ) : RecyclerView.Adapter<MoviesRecyclerViewAdapter.MovieViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
            Log.v("MoviesRecyclerViewAdapter", "hello from the recycler")

            Log.v("MoviesRecyclerViewAdapter", movies.toString())
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_item, parent, false)
            return MovieViewHolder(view)
        }

        inner class MovieViewHolder(val mView: View): RecyclerView.ViewHolder(mView) {
            var mMovie: Movie? = null
            var mTitle: TextView = mView.findViewById<View>(id.movie_title) as TextView

            override fun toString(): String {
                return mTitle.toString()
            }
        }

        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
            val movie = movies[position]

            Log.v("MoviesRecyclerViewAdapter", movie.title.toString())


            holder.mMovie = movie
            holder.mTitle.text = movie.title
        }

    override fun getItemCount(): Int {
       return movies.size
    }
}