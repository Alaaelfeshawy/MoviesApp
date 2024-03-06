package banquemisr.challenge05.moviesapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// for testing
open class AppCore: Application()

@HiltAndroidApp
class MoviesApp : AppCore() {
}