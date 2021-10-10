package com.wwt.nimbleviewing.data.model

data class Album(val userId: Int, val id: Int, val title: String)

data class Photos(val albumId: Int, val id: Int,val title: String, val url: String, val thumbnailUrl: String)