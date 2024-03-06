package banquemisr.challenge05

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import banquemisr.challenge05.moviesapp.AppCore
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltTestApplication

class CustomTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}

@CustomTestApplication(AppCore::class)
interface HiltTestApplication