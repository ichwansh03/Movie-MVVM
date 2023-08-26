package com.ichwan.moviemvvm.models

import com.google.gson.annotations.SerializedName

data class Genres(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)