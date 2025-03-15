package com.rasel.baseappcompose.data.model

import kotlinx.serialization.Serializable

data class Source(
    val id: String,
    val name: String
)

@Serializable
data class UnsplashSearchResponse(
     val results: List<UnsplashPhoto>,
     val total_pages: Int
)
@Serializable
data class UnsplashPhoto (

    val id: String,

    val urls: UnsplashPhotoUrls,

    val user: UnsplashUser
)

@Serializable
data class UnsplashUser(
   val name: String,
     val username: String
)  {
    val attributionUrl: String
        get() {
            return "https://unsplash.com/$username?utm_source=sunflower&utm_medium=referral"
        }
}
@Serializable
data class UnsplashPhotoUrls(
     val small: String
)