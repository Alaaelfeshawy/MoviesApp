package banquemisr.challenge05.presentation.utils.extensions

fun Double.formatDecimals(): String {
    return " %.0f".format(this)
}