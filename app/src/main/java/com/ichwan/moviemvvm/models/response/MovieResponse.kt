package com.ichwan.moviemvvm.models.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.ichwan.moviemvvm.models.entity.Movies

@Parcelize
data class MovieResponse(

    @field:SerializedName("page")
	val page: Int? = 0,

    @field:SerializedName("total_pages")
	val totalPages: Int? = 0,

    @field:SerializedName("results")
	val results: MutableList<Movies>,

    @field:SerializedName("total_results")
	val totalResults: Int? = 0
) : Parcelable