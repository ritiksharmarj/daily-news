package com.ritiksharmarj.dailynews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.ritiksharmarj.dailynews.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        val news = NewsService.newsInstance.getHeadlines("in", 1)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news != null) {
                    adapter = NewsAdapter(this@MainActivity, news.articles)
                    binding.newsList.adapter = adapter
                    binding.newsList.layoutManager = LinearLayoutManager(this@MainActivity)
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("NEWS", "Error in fetching news", t)
            }
        })
    }
}