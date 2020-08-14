package com.igor.filmlist.contract

import com.igor.filmlist.model.Movie
import com.igor.filmlist.model.MovieDetail

interface ContractInterface {

    interface MovieListView{
        fun initView()
        fun updateViewData()
    }

    interface MovieDetailView{
        fun initView()
        fun updateViewData(title: String,
                    vote_average: String,
                    vote_count: String,
                    popularity: String,
                    genres:String,
                    original_language: String,
                    original_title: String,
                    overview: String,
                    tagline: String,
                    poster_url: String,
                    date: String,
                    adult: String,
                    budget: String,
                    production_companies: String,
                    production_countries: String,
                    revenue: String,
                    spoken_languages: String)
    }

    interface  MovieRowView{
        fun setData(id: String,
                    title: String,
                    genres: String,
                    score: String,
                    date: String,
                    url: String)
    }

    interface MovieListPresenter{
        fun getItemCount(): Int
        fun bindRowMovie(position: Int, holder: MovieRowView)
        fun requestData()
        fun updateViewData()
    }

    interface MovieDetailPresenter{
        fun requestData(id: String?)
        fun updateViewData()
    }

    interface MovieListModel{
        fun fetchJson()
        fun getItemCount(): Int
        fun getMovie(position: Int): Movie
    }

    interface MovieDetailModel{
        fun fetchJson(id: String?)
        fun getMovieDetail(): MovieDetail?
    }
}