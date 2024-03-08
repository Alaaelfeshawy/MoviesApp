package banquemisr.challenge05.data.validate

import banquemisr.challenge05.data.Constants
import banquemisr.challenge05.data.Constants.GENERAL_CODE
import banquemisr.challenge05.data.remote.validate.APIResponseState
import banquemisr.challenge05.data.remote.validate.IValidateAPIResponse
import banquemisr.challenge05.data.remote.validate.ValidateAPIResponse
import banquemisr.challenge05.domain.R
import com.google.gson.Gson
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class ValidateAPIResponseShould {

    private val mGson = Gson()
    private lateinit var iValidateAPIResponse: IValidateAPIResponse
    private lateinit var mRetrofitResponseHelper: RetrofitResponseHelper

    @Before
    fun initTests() {
        iValidateAPIResponse = ValidateAPIResponse(mGson)
        mRetrofitResponseHelper = RetrofitResponseHelper(mGson)
    }

    @Test
    fun `return success in case of api response is success`() =
        runTest {
            val response = iValidateAPIResponse.validateResponse(
                mRetrofitResponseHelper.successResponse()
            )
            assertEquals(
                APIResponseState.ValidResponse(listOf<Any>()),
                response
            )
        }

    @Test
    fun `return NotValidResponse in case of api response is not success`() = runTest {
        val response =
            iValidateAPIResponse.validateResponse(mRetrofitResponseHelper.errorResponse(mRetrofitResponseHelper.getErrorMessage()))
        assertEquals(APIResponseState.NotValidResponse(600, mRetrofitResponseHelper.getErrorMessage()), response)
    }

    @Test
    fun `return NotValidResponse in case of api throw an exception`() = runTest {
        val mock = mockk<Response<*>>()
        every {mock.body() } throws RuntimeException(mRetrofitResponseHelper.getErrorMessage())
        val response = iValidateAPIResponse.validateResponse(mock)
        assertEquals(APIResponseState.NotValidResponse(GENERAL_CODE, mRetrofitResponseHelper.getErrorMessage()), response)
    }

    @Test
    fun `return general error message in case of error body is null`() = runTest {
        val response = iValidateAPIResponse.validateResponse(
            mRetrofitResponseHelper.emptyErrorResponse()
        ) as APIResponseState.NotValidResponse
        assertEquals(
            R.string.something_went_wrong, response.message
        )
    }

    @Test
    fun `return NotValidResponse in case of api throw HttpException`() = runTest {
        val mock = mockk<Response<String>>()
        every { mock.body() } throws HttpException(mRetrofitResponseHelper.emptyErrorResponse())
        val response = iValidateAPIResponse.validateResponse(mock)
        assertEquals(
            APIResponseState.NotValidResponse(
                600, R.string.something_went_wrong
            ), response
        )
    }

    @Test
    fun `return NotValidResponse in case of api throw SocketTimeoutException`() = runTest {
        val mock = mockk<Response<Any>>()
        every { mock.body()} throws  SocketTimeoutException(mRetrofitResponseHelper.getErrorMessage())
        val response = iValidateAPIResponse.validateResponse(mock)
        assertEquals(
            APIResponseState.NotValidResponse(
                Constants.TIME_OUT_CODE, mRetrofitResponseHelper.getErrorMessage()
            ), response
        )
    }
    @Test
    fun `return NotValidResponse in case of api throw IOException`() = runTest {
        val mock = mockk<Response<Any>>()
        every { mock.body()} throws  IOException(mRetrofitResponseHelper.getErrorMessage())
        val response = iValidateAPIResponse.validateResponse(mock)
        assertEquals(
            APIResponseState.NotValidResponse(
                Constants.NO_CONNECTION_CODE, mRetrofitResponseHelper.getErrorMessage()
            ), response
        )
    }
}