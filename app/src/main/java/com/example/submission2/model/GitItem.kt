package com.example.submission2.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class GitItem(
    @JvmField
    var username: String? = "",
    var followers: Int? = 0,
    var following: Int? = 0,
    var url: String? = "",
    var avatar: String? = ""
) : Parcelable
