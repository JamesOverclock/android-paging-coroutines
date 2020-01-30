package com.l3ios.androidpagingwithcoroutines.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.l3ios.androidpagingwithcoroutines.R
import com.l3ios.androidpagingwithcoroutines.models.RedditPost
import com.l3ios.androidpagingwithcoroutines.utils.DiffUtilCallBack
import kotlinx.android.synthetic.main.list_item.view.*

class MainAdapter :
    PagedListAdapter<RedditPost, MainAdapter.ViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bindPost(it) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindPost(redditPost: RedditPost) {
            with(redditPost) {
                itemView.score.text = score.toString()
                itemView.comments.text = commentCount.toString()
                itemView.title.text = title
            }
        }
    }

}