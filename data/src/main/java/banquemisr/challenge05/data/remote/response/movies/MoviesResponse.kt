package banquemisr.challenge05.data.remote.response.movies

import banquemisr.challenge05.domain.dto.movies.Movie
import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    val dates: Dates,
    val page: Int,
    @SerializedName("results")
    val movies: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

fun List<Result?>?.mapToMovies() = this?.map {
    Movie(
        title = it?.title,
        backdropPath = it?.backdropPath,
        voteCount = it?.voteCount,
        voteAverage = it?.voteAverage,
        id = it?.id,
        overview = it?.overview,
        releaseDate = it?.releaseDate,
        moviePoster = it?.posterPath
    )
}