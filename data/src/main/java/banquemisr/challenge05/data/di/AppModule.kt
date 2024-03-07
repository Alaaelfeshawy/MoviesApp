package banquemisr.challenge05.data.di

import android.content.Context
import android.net.ConnectivityManager
import banquemisr.challenge05.data.BuildConfig
import banquemisr.challenge05.data.Constants.URLS
import banquemisr.challenge05.data.remote.api.API
import banquemisr.challenge05.data.remote.datasource.IRemoteDataSource
import banquemisr.challenge05.data.remote.datasource.RemoteDataSource
import banquemisr.challenge05.data.remote.internet.CheckInternetConnection
import banquemisr.challenge05.data.remote.internet.ICheckInternetConnection
import banquemisr.challenge05.data.remote.tokeninterceptor.ITokenInterceptor
import banquemisr.challenge05.data.remote.tokeninterceptor.TokenInterceptor
import banquemisr.challenge05.data.remote.validate.IValidateAPIResponse
import banquemisr.challenge05.data.remote.validate.ValidateAPIResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun providesCheckInternetConnection(connectivityManager: ConnectivityManager): ICheckInternetConnection {
        return CheckInternetConnection(connectivityManager)
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun providesValidateAPIResponse(
        gson: Gson
    ): IValidateAPIResponse {
        return ValidateAPIResponse(gson)
    }

    @Provides
    @Singleton
    fun providesRemoteDataSource(
        iValidateAPIResponse: IValidateAPIResponse,
        iCheckInternetConnection: ICheckInternetConnection,
        api: API
    ): IRemoteDataSource {
        return RemoteDataSource(iValidateAPIResponse, iCheckInternetConnection, api)
    }

    @Provides
    @Singleton
    fun providesTokenInterceptor(): ITokenInterceptor = TokenInterceptor()

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return logging
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        tokenInterceptor: TokenInterceptor
    ): OkHttpClient {
        return getOkHttpClient()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofitInstance(
        client: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URLS.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun providesAPI(retrofit: Retrofit): API {
        return retrofit.create(API::class.java)
    }

    private fun getOkHttpClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(20, SECONDS)
            .readTimeout(30, SECONDS)
            .writeTimeout(20, SECONDS)
            .addInterceptor { interceptSocketException(it) }
            .retryOnConnectionFailure(true)
    }

    @Throws(IOException::class)
    private fun interceptSocketException(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val url: HttpUrl = request.url
            .newBuilder()
            .build()
        request = request
            .newBuilder()
            .url(url)
            .build()
        val response = chain.proceed(request)
        try {
            return response.newBuilder()
                .body(response.body?.string()?.toResponseBody(response.body?.contentType()))
                .build()
        } catch (exception: SocketTimeoutException) {
            Response.Builder().body("".toResponseBody()).build()
        }
        return response
    }
}