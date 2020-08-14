package com.igor.filmlist.model

import com.google.gson.GsonBuilder
import com.igor.filmlist.contract.ContractInterface
import okhttp3.*
import java.io.IOException

class MainActivityModel(_presenter: ContractInterface.MovieListPresenter): ContractInterface.MovieListModel {

    private var homeFeed: HomeFeed = HomeFeed(listOf())
    private var presenter = _presenter

    override fun fetchJson(){
        println("Requesting")
        val url = "https://desafio-mobile.nyc3.digitaloceanspaces.com/movies"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response){
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                homeFeed = HomeFeed(gson.fromJson(body, Array<Movie>::class.java).toList())
                println("Request successful")
                presenter.updateViewData()
            }
            override fun onFailure(call: Call, e: IOException){
                println("Failed to execute request")
            }
        })
    }

    override fun getItemCount(): Int {
        return homeFeed.movies.count()
    }

    override fun getMovie(position: Int): Movie {
        return homeFeed.movies.get(position)
    }
}

class HomeFeed(val movies: List<Movie>)

class Movie(val id: Int,
            val vote_average: Float,
            val title: String,
            val poster_url: String,
            val genres: Array<String>,
            val release_date: String)