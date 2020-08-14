package com.igor.filmlist.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.igor.filmlist.R
import com.igor.filmlist.contract.ContractInterface
import com.igor.filmlist.presenter.MainActivityPresenter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.film_row.view.*

class MainActivity: AppCompatActivity(), ContractInterface.MovieListView {

    private var presenter: ContractInterface.MovieListPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "tokenlab movies"
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.mipmap.tokenlab_actionbar_logo_foreground)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        presenter = MainActivityPresenter(this)
        presenter?.requestData()
    }

    override fun initView() {
        progressBar.visibility = View.VISIBLE
        recyclerView_main.layoutManager = LinearLayoutManager(this)
        recyclerView_main.adapter = MainAdapter(presenter)
    }

    override fun updateViewData() {
        runOnUiThread {
            progressBar.visibility = View.GONE
            recyclerView_main.adapter = MainAdapter(presenter)
        }
    }
}

class MainAdapter(val presenter: ContractInterface.MovieListPresenter?): RecyclerView.Adapter<MovieRowHolder>() {
    override fun getItemCount(): Int {
        if (presenter != null) return  presenter.getItemCount() else return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieRowHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.film_row, parent, false)
        return MovieRowHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: MovieRowHolder, position: Int) {
        presenter?.bindRowMovie(position, holder)
    }
}

class MovieRowHolder(val view: View): RecyclerView.ViewHolder(view), ContractInterface.MovieRowView {

    private var id: String? = null
    private var title: String? = null

    init {
        view.setOnClickListener {
            val intent = Intent(view.context, SideActivity::class.java)
            intent.putExtra("id", this.id)
            intent.putExtra("title", this.title)
            view.context.startActivity(intent)
        }
    }

    override fun setData(id: String, title: String, genres: String, score: String, date: String, url : String) {
        Picasso.with(view.context).load(url).error(R.drawable.default_movie_poster).into(view.imageView_poster)
        itemView.findViewById<TextView>(R.id.textView_film_title).text = title
        itemView.findViewById<TextView>(R.id.textView_genres).text = genres
        itemView.findViewById<TextView>(R.id.textView_score).text = "Vote Average: "+score
        itemView.findViewById<TextView>(R.id.textView_release_date).text = "Release Date: "+date
        this.id = id
        this.title = title
    }
}