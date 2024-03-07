package banquemisr.challenge05.presentation.utils.extensions

import android.content.Context
import banquemisr.challenge05.domain.R


fun Any?.getStringFromMessage(context: Context): String {
    return if (this == null)
        context.getString(R.string.something_went_wrong)
    else {
        when (this) {
            is String -> this
            is Int -> context.getString(this)
            is ArrayList<*> -> {
                val result = ""
                this.forEach {
                    val item = if (it is Int)
                        context.getString(it)
                    else
                        it as String
                    result.plus(",$item")
                }
                return result
            }

            else -> context.getString(R.string.something_went_wrong)
        }
    }
}