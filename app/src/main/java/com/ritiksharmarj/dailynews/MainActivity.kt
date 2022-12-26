package com.ritiksharmarj.dailynews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ritiksharmarj.dailynews.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var adapter: NewsAdapter
    lateinit var layoutManager: LinearLayoutManager
    private var articles = mutableListOf<Article>()
    private var isScrolling = false
    private var currentItems = 0
    private var totalItems = 0
    private var scrollOutItems = 0
    var totalResults = -1
    var pageNumber = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NewsAdapter(this, articles)
        binding.newsList.adapter = adapter
        layoutManager = LinearLayoutManager(this)
        binding.newsList.layoutManager = layoutManager

        binding.newsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItems = layoutManager.childCount
                totalItems = layoutManager.itemCount
                scrollOutItems = layoutManager.findFirstVisibleItemPosition()

                if (isScrolling) {
                    if (totalResults > totalItems && scrollOutItems >= totalItems - 5) {
                        isScrolling = false
                        pageNumber++
                        getNews()
                    }
                }

                /*if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false
                    pageNumber++
                    getNews()
                }*/
            }
        })

        getNews()

        // Day/Night mode
        binding.ivNightMode.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.ivNightMode.visibility = View.GONE
            binding.ivLightMode.visibility = View.VISIBLE
        }
        binding.ivLightMode.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.ivNightMode.visibility = View.VISIBLE
            binding.ivLightMode.visibility = View.GONE
        }

        // Toast for avatar img
        binding.avatar.setOnClickListener {
            Toast.makeText(this, "Metaverse in progress", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getNews() {
        val news = NewsService.newsInstance.getHeadlines("in", pageNumber)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news != null) {
                    totalResults = news.totalResults
                    articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}