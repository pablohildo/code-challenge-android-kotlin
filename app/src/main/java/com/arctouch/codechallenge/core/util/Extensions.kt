package com.arctouch.codechallenge.core.util

enum class UrlTypes {
    POSTER, BACKDROP
}

fun String.buildUrl(urlType: UrlTypes): String {
    val posterUrl = "https://image.tmdb.org/t/p/w154"
    val backdropUrl = "https://image.tmdb.org/t/p/w780"

    return when (urlType) {
        UrlTypes.POSTER -> posterUrl + this + "?api_key=" + Constants.API_KEY
        UrlTypes.BACKDROP -> backdropUrl + this + "?api_key=" + Constants.API_KEY
    }
}
