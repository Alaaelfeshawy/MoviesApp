package banquemisr.challenge05.data

object Constants {

    object Headers {
        const val AUTHORIZATION = "Authorization"
    }

    object QUERIES {
        const val PAGE = "page"
    }

    object URLS {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }

    object EndPoints {
        const val NOW_PLAYING = "movie/now_playing"
        const val POPULAR = "movie/popular"
        const val UPCOMING = "movie/upcoming"
    }

    const val TIME_OUT_CODE = 100
    const val NO_CONNECTION_CODE = 101
}