package com.example.myflixster

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONObject

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"
private const val NY_API_KEY = "EeNDb39VWWhGxunyJSOb8KE2RI3SOWJE"
private const val NY_URL = "https://api.nytimes.com/svc/books/v3/lists/current/hardcover-fiction.json"
private const val BASE_URL = "https://api.themoviedb.org/3/movie/"
private const val END_POINT = "now_playing?language=en-US&page=1&api_key="

class MoviesFragment: Fragment(), OnListFragmentIteractionListener {
    /*
     * What happens when a particular book is clicked.
     */
    override fun onItemClick(item: Movie) {
        Toast.makeText(context, "test: " + item.title, Toast.LENGTH_LONG).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val  view = inflater.inflate(R.layout.movies_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context =  view.context


        recyclerView.layoutManager = LinearLayoutManager(context)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        // Create a AsyncHttpClient
        val client = AsyncHttpClient()
        val params = RequestParams()
        //params["api-key"] = NY_API_KEY

        var url: String = BASE_URL + END_POINT + API_KEY
        Log.v("MoviesFragment", url)

        client[url,
                params,
                object: JsonHttpResponseHandler() {
                    override fun onSuccess(statusCode: Int, headers: Headers, json: JSON?) {
                        progressBar.hide()
                        //Log.v("MoviesFragment", json.toString())
                        //Log.v("MoviesFragment", json?.jsonObject?.get("results").toString())

                        val resultJSON: JSONObject = json?.jsonObject as JSONObject
                        Log.v("MoviesFragment", resultJSON.toString())
                        val movieRawJson: String = resultJSON.get("results").toString()

                        val gson = Gson()
                        val arrayMovie = object : TypeToken<List<Movie>>() {}.type
                        val models: List<Movie> = gson.fromJson(movieRawJson, arrayMovie )

                        recyclerView.adapter = MoviesRecyclerViewAdapter(models, this@MoviesFragment)
                        Log.d("MoviesFragment","response success")
                    }

                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        errorResponse: String,
                        t: Throwable?
                    ) {
                        // The wait for a response is over
                        progressBar.hide()

                        // If the error is not null, log it!
                        t?.message?.let {
                            Log.e("BestSellerBooksFragment", errorResponse)
                        }
                    }
                }
        ]
    }

}

