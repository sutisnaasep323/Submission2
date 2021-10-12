package com.example.submission2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission2.databinding.ListItemsBinding
import com.example.submission2.model.GitItem
import com.example.submission2.model.UserItem

//class SearchAdapter : RecyclerView.Adapter<SearchAdapter.UserViewHolder>() {
//    private val mData = ArrayList<GitItem>()
//    private var onItemClickCallback: OnItemClickCallback? = null
//
//    fun setData(item: ArrayList<GitItem>) {
//        mData.clear()
//        mData.addAll(item)
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
//        val binding = ListItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return UserViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
//        holder.bind(mData[position])
//    }
//
//    inner class UserViewHolder(private val binding: ListItemsBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(user: GitItem) {
//            with(binding) {
//                Glide.with(itemView.context)
//                    .load(user.avatar)
//                    .apply(RequestOptions())
//                    .into(avatar)
//                username.text = user.login
//                url.text = user.url
//                itemView.setOnClickListener { onItemClickCallback?.onIemClicked(user) }
//            }
//        }
//    }
//
//    override fun getItemCount(): Int = mData.size
//
//    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback
//    }
//
//}
//
//interface OnItemClickCallback {
//    fun onIemClicked(gitItems: UserItem)
//}