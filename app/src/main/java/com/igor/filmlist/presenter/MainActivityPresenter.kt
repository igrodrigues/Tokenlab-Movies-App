package com.igor.filmlist.presenter

import com.igor.filmlist.contract.ContractInterface
import com.igor.filmlist.model.MainActivityModel
import com.igor.filmlist.model.Movie

class MainActivityPresenter(_view: ContractInterface.MovieListView): ContractInterface.MovieListPresenter {

    private var view: ContractInterface.MovieListView = _view
    private var model: ContractInterface.MovieListModel = MainActivityModel(this)

    init{
        view.initView()
    }

    override fun requestData() {
        model.fetchJson()
    }

    override fun updateViewData() {
        view.updateViewData()
    }

    override fun getItemCount(): Int{
        return  model.getItemCount()
    }

    override fun bindRowMovie(position: Int, holder: ContractInterface.MovieRowView) {
        val movie: Movie = model.getMovie(position)
        holder.setData(movie.id.toString(),
                       movie.title,
                       movie.genres.joinToString("\n"),
                       movie.vote_average.toString(),
                       movie.release_date.split('-').reversed().joinToString("/"),
                       movie.poster_url)
    }

}