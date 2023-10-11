package com.example.flixster

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PopularShow : Serializable {
    @SerializedName("name")
    var title: String? = null

    @SerializedName("poster_path")
    var posterPath: String? = null

    @SerializedName("overview")
    var description: String? = null
}