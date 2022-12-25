package com.ritiksharmarj.dailynews

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ritiksharmarj.dailynews.webview.DetailActivity

class NewsAdapter(private val context: Context, private val articles: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var newsImage: ImageView = itemView.findViewById(R.id.ivNewsImage)
        var newsTitle: TextView = itemView.findViewById(R.id.tvNewsTitle)
        var newsDescription: TextView = itemView.findViewById(R.id.tvNewsDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_layout, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.newsTitle.text = article.title
        holder.newsDescription.text = article.description

        // Glide to load article image
        Glide.with(context).load(article.urlToImage).into(holder.newsImage)

        // Open news URL in Detail Activity (webview)
        holder.itemView.setOnClickListener {
            Intent(context, DetailActivity::class.java).putExtra("URL", article.url).also {
                context.startActivity(it)
            }
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}
