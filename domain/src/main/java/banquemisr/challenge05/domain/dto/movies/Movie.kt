package banquemisr.challenge05.domain.dto.movies

data class Movie(
    val title: String? = null,
    val id: Int? = null,
    val backdropPath: String? = null,
    val overview: String? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null,
    val releaseDate: String? = null,
    val moviePoster: String? = null
)
