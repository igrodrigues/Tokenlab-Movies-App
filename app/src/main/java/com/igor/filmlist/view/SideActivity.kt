package com.igor.filmlist.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.igor.filmlist.R
import com.igor.filmlist.contract.ContractInterface
import com.igor.filmlist.presenter.SideActivityPresenter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_side.*

class SideActivity: AppCompatActivity(), ContractInterface.MovieDetailView{

    private var presenter: ContractInterface.MovieDetailPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_side)
        val navBarTitle = intent.getStringExtra("title")
        supportActionBar?.title = navBarTitle
        presenter = SideActivityPresenter(this)
        presenter?.requestData(intent.getStringExtra("id"))
}

    override fun initView() {
        progressBar2.visibility = View.VISIBLE
        scrollView2.visibility = View.GONE
    }

    override fun updateViewData(title: String, vote_average: String, vote_count: String, popularity: String, genres: String, original_language: String, original_title: String,
                                overview: String, tagline: String, poster_url: String, date: String, adult: String, budget: String, production_companies: String, production_countries: String,
                                revenue: String, spoken_languages: String)
    {
        runOnUiThread {
            progressBar2.visibility = View.GONE
            scrollView2.visibility = View.VISIBLE
            Picasso.with(this).load(poster_url).error(R.drawable.default_movie_poster)
                .into(this.imageView_poster)
            this.findViewById<TextView>(R.id.textView_title).text = title
            this.findViewById<TextView>(R.id.textView_original_title).text = original_title
            this.findViewById<TextView>(R.id.textView_original_language).text = "Original Language: " + original_language.capitalize()
            this.findViewById<TextView>(R.id.textView_genres).text = genres
            this.findViewById<TextView>(R.id.textView_date).text = "Release Date: " + date
            when {
                tagline != "" -> this.findViewById<TextView>(R.id.textView_tagline).text =
                    "'" + tagline + "'"
                else -> this.findViewById<TextView>(R.id.textView_tagline).text =
                    "Tagline Not Available"
            }
            this.findViewById<TextView>(R.id.textView_overview).text = "Overview: " + overview
            this.findViewById<TextView>(R.id.textView_companies).text = production_companies
            this.findViewById<TextView>(R.id.textView_countries).text = production_countries
            this.findViewById<TextView>(R.id.textView_budget).text = "Budget: $"+budget
            this.findViewById<TextView>(R.id.textView_revenue).text = "Revenue: $"+revenue
            this.findViewById<TextView>(R.id.textView_score).text = "Vote Average: "+vote_average
            this.findViewById<TextView>(R.id.textView_count).text = "Vote Count: "+vote_count
            this.findViewById<TextView>(R.id.textView_languages).text = spoken_languages
            //itemView.findViewById<TextView>(R.id.textView_score).text = "Score: "+score
        }
    }
}
