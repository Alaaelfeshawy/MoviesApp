package banquemisr.challenge05.presentation.base

object Routes {
    fun String.setPatArgumentsToRoutes(key: String, value: String): String {
        return if (this.contains(key))
            this.replace("{", "").replace("}", "").replace(key, value)
        else
            this
    }

    object Movies {
        const val MOVIES_HOME = "movies"
    }
}