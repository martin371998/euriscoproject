package com.example.euriskocodechallenge.ui.home.news

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.euriskocodechallenge.databinding.NewsItemBinding
import com.example.euriskocodechallenge.model.News
import com.example.euriskocodechallenge.model.Result
import com.example.euriskocodechallenge.utils.Constants

class NewsRVAdapter : RecyclerView.Adapter<NewsRVAdapter.NewsViewHolder>() {

    private lateinit var mListener: onItemClickedListener
    private var newsList = emptyList<News>()

    inner class NewsViewHolder(val binding: NewsItemBinding, listener: onItemClickedListener) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }


    interface onItemClickedListener {
        fun onItemClick(position: Int)
    }


    fun setOnItemClickListener(listener: onItemClickedListener) {
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            NewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),mListener
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.apply {
            titleTv.text = newsList[position].title
            authorTv.text = newsList[position].byline
            newsThumb.load(newsList[position].media[0].mediaMetadata[0].url) {
                crossfade(true)
                crossfade(1000)
                transformations(RoundedCornersTransformation(10F))
            }
        }
    }

    fun setData(result: Result) {
        this.newsList = result.news
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}