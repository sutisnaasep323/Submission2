package com.example.submission2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission2.databinding.ListItemsBinding
import com.example.submission2.model.UserItem
import java.util.*
import kotlin.collections.ArrayList

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val list = ArrayList<UserItem>()
    private val listUsed = ArrayList<UserItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

//    fun filter(textSearch: String) {
//        var textSearch = textSearch
//        textSearch = textSearch.toLowerCase(Locale.getDefault())
//        list.clear()
//        if (textSearch.length == 0) {
//            list.addAll(listUsed)
//        } else {
//            for (customerPaid in listUsed) {
//                if (customerPaid.getName().toLowerCase(Locale.getDefault())
//                        .contains(textSearch)
//                ) customerList.add(customerPaid)
//            }
//        }
//        notifyDataSetChanged()
//    }

    fun setUser(users: ArrayList<UserItem>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ListItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserItem) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(user.avatar_url)
                    .apply(RequestOptions())
                    .into(avatar)
                username.text = user.login
                url.text = user.url
            }
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ListItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: UserItem)
    }
}