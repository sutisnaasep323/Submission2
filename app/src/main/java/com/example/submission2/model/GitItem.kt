package com.example.submission2.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class GitItem(
    @JvmField
    var login: String? = "",
    var url: String? = "",
    var avatar: String? = ""
) : Parcelable
