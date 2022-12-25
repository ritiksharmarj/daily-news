package com.ritiksharmarj.dailynews.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.ritiksharmarj.dailynews.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra("URL")
        if (url != null) {
            // enable javascript if the url page has
            binding.wvDetail.settings.javaScriptEnabled = true

            // enable mobile version for webview
            binding.wvDetail.settings.userAgentString = "Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3"

            binding.wvDetail.webViewClient = object : WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.progressBar.visibility = View.GONE
                    binding.wvDetail.visibility = View.VISIBLE
                }
            }

            binding.wvDetail.loadUrl(url)
        }

        // Close webview activity
        binding.ivClose.setOnClickListener {
            finish()
        }
    }
}