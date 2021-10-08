package com.example.submission2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission2.R
import com.example.submission2.databinding.ListItemsBinding
import com.example.submission2.model.GitItem

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.UserViewHolder>() {
    private val mData = ArrayList<GitItem>()
    private var onItemClickCallback: OnItemClickCallback? = null
    private lateinit var binding: ListItemsBinding

    fun setData(item: ArrayList<GitItem>) {
        mData.clear()
        mData.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_items, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(gitItems: GitItem) {
            with(itemView) {

                binding.username.text = gitItems.username
                binding.url.text = gitItems.url
                Glide.with(itemView.context)
                    .load(gitItems.avatar)
                    .into(binding.avatar)
                itemView.setOnClickListener { onItemClickCallback?.onIemClicked(gitItems) }

            }
        }
    }

    override fun getItemCount(): Int = mData.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

}

interface OnItemClickCallback {
    fun onIemClicked(gitItems: GitItem)
}