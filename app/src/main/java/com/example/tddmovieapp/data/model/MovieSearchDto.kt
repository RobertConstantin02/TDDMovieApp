package com.example.tddmovieapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieSearchDto(
    @SerializedName("results")
    val results: List<MovieDto>
) {
    data class MovieDto(
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String?,
        @SerializedName("popularity")
        val popularity: Double?,
        @SerializedName("poster_path")
        val image: String?
    )
}