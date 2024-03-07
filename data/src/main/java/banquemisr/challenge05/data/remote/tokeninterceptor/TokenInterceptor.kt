package banquemisr.challenge05.data.remote.tokeninterceptor

import banquemisr.challenge05.data.BuildConfig
import banquemisr.challenge05.data.Constants.Headers.AUTHORIZATION
import okhttp3.Interceptor.Chain
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor() : ITokenInterceptor {
    override fun intercept(chain: Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        builder.addHeader(AUTHORIZATION, "Bearer ${BuildConfig.API_KEY}")
        return chain.proceed(builder.build())

    }
}