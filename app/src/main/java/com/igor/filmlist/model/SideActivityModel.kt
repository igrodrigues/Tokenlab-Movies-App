package com.igor.filmlist.model

import com.google.gson.GsonBuilder
import com.igor.filmlist.contract.ContractInterface
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class SideActivityModel(_presenter: ContractInterface.MovieDetailPresenter): ContractInterface.MovieDetailModel {

    private val presenter: ContractInterface.MovieDetailPresenter = _presenter
    private var movieDetail: MovieDetail? = null

    override fun fetchJson(id: String?) {
        if(id==null){
            println("Failed to execute request: ID is null!!")
            return
        }
        println("Requesting")
        val url = "https://desafio-mobile.nyc3.digitaloceanspaces.com/movies/$id"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: okhttp3.Response){
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                movieDetail = gson.fromJson(body, MovieDetail::class.java)
                println("Request successful")
                presenter.updateViewData()
            }
            override fun onFailure(call: Call, e: IOException){
                println("Failed to execute request")
            }
        })
    }

    override fun getMovieDetail(): MovieDetail? {
        return movieDetail
    }
}

class MovieDetail(val title: String,
                  val vote_average: Float,
                  val vote_count: Int,
                  val popularity: Float,
                  val genres: Array<String>,
                  val original_language: String,
                  val original_title: String,
                  val overview: String,
                  val tagline: String,
                  val poster_url: String,
                  val release_date: String,
                  val adult: Boolean,
                  val collection: String,
                  val budget: Int,
                  val production_companies: Array<Company>,
                  val production_countries: Array<Origin>,
                  val revenue: Int,
                  val spoken_languages: Array<Origin>)

class Company(val name: String, val origin_country: String)

class Origin(val name: String)