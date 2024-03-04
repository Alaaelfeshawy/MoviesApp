package banquemisr.challenge05.domain.dto.movies
data class MoviesDTO(
    val currentPage: Int? = null,
    val totalPages: Int? = null,
    val movies: MutableList<Movie>? = arrayListOf(),
    val canPaginate: Boolean? = (currentPage ?: 0) < (totalPages ?: 0)
)