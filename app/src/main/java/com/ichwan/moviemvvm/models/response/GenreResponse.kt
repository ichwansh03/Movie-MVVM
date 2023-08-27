package com.ichwan.moviemvvm.models.response

import com.google.gson.annotations.SerializedName
import com.ichwan.moviemvvm.models.entity.Genres

data class GenreResponse(

	@field:SerializedName("genres")
	val genres: List<Genres>? = null
)