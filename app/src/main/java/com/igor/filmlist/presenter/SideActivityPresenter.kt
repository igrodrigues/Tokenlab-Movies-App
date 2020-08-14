package com.igor.filmlist.presenter

import com.igor.filmlist.contract.ContractInterface
import com.igor.filmlist.model.Company
import com.igor.filmlist.model.SideActivityModel

class SideActivityPresenter(_view: ContractInterface.MovieDetailView): ContractInterface.MovieDetailPresenter {

    private val view: ContractInterface.MovieDetailView = _view
    private val model: ContractInterface.MovieDetailModel = SideActivityModel(this)

    init{
        view.initView()
    }

    override fun requestData(id: String?) {
        model.fetchJson(id)
    }

    override fun updateViewData() {
        val movieDetail = model.getMovieDetail()
        if(movieDetail!=null) {
            val companies: MutableList<String> = ArrayList()
            for(company in movieDetail.production_companies){
                companies.add(company.name+", "+company.origin_country)
            }
            val countries: MutableList<String> = ArrayList()
            for(country in movieDetail.production_countries){
                countries.add(country.name)
            }
            val languages: MutableList<String> = ArrayList()
            for(language in movieDetail.spoken_languages){
                languages.add(language.name)
            }
            view.updateViewData(movieDetail.title,
                                movieDetail.vote_average.toString(),
                                movieDetail.vote_count.toString(),
                                movieDetail.popularity.toString(),
                                movieDetail.genres.joinToString("\n"),
                                movieDetail.original_language,
                                movieDetail.original_title,
                                movieDetail.overview,
                                movieDetail.tagline,
                                movieDetail.poster_url,
                                movieDetail.release_date.split('-').reversed().joinToString("/"),
                                movieDetail.adult.toString(),
                                movieDetail.budget.toString(),
                                companies.joinToString("\n"),
                                countries.joinToString("\n"),
                                movieDetail.revenue.toString(),
                                languages.joinToString("\n"))
        }
    }
}