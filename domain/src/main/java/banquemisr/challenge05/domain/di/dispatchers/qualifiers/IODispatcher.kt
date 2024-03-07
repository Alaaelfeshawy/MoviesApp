package banquemisr.challenge05.domain.di.dispatchers.qualifiers

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.BINARY

@Qualifier
@Retention(BINARY)
annotation class IODispatcher
