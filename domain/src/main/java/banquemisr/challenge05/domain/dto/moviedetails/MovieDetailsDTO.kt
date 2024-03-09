package banquemisr.challenge05.domain.dto.moviedetails


data class MovieDetailsDTO(
    val isAdult: Boolean? = null,
    val backdropPath: String? = null,
    val genres: List<String?>? = null,
    val id: Int? = null,
    val language: String? = null,
    val title: String? = null,
    val voteAverage: Double? = null,
    val languages: List<String?>? = null,
    val runTime: Int? = null,
    val status: String? = null,
    val overView: String? = null,
)