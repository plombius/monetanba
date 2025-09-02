package cz.moneta.monetanba.common.http

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import cz.moneta.monetanba.R
import cz.moneta.monetanba.common.RepositoryCallResponse
import cz.moneta.monetanba.common.UnsuccessfulRepositoryCall
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit



/**
 * Builds a preconfigured [Retrofit] instance for the Ball Don't Lie API.
 *
 * - Base URL: `https://api.balldontlie.io/v1/`
 * - OkHttp timeouts: 60s (call/connect/read/write)
 * - Interceptors: [AuthInterceptor] + BODY-level [HttpLoggingInterceptor]
 * - Converter: kotlinx.serialization (lenient, ignore unknown keys)
 *
 * @return A ready-to-use [Retrofit] instance.
 */
fun createRetrofit(
): Retrofit {

    val json = Json {
        ignoreUnknownKeys = true   // API may send extra fields
        isLenient = true
    }

    return Retrofit.Builder()
        .baseUrl(
            "https://api.balldontlie.io/v1/"
        )
        .client(
            OkHttpClient.Builder()
                .callTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(AuthInterceptor())
                .addInterceptor (HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

        )
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
}

/**
 * Executes a Retrofit call, wrapping the result in [RepositoryCallResponse].
 *
 * - On network/serialization exception, returns a response with [UnsuccessfulRepositoryCall.callException].
 * - On non-2xx HTTP status, returns a response with [UnsuccessfulRepositoryCall.errorCode].
 * - On success, returns a response with [RepositoryCallResponse.dto] set from [Response.body].
 *
 * @param DTO The expected payload type.
 * @param call Suspend lambda performing the Retrofit request.
 * @return A [RepositoryCallResponse] containing either the DTO or error details.
 */
suspend fun <DTO> performNetworkCall(
    call: suspend () -> Response<DTO>
): RepositoryCallResponse<DTO> {

    val response = try {
        call.invoke()
    } catch (exception: Exception){
        return RepositoryCallResponse(unsuccessfulRepositoryCall = UnsuccessfulRepositoryCall(
            callException = exception
        )
        )
    }

    return if(response.isSuccessful){
        try {
            RepositoryCallResponse(dto = response.body())
        } catch (exception: Exception){
            RepositoryCallResponse(unsuccessfulRepositoryCall = UnsuccessfulRepositoryCall(callException = exception))
        }
    } else {
        RepositoryCallResponse(unsuccessfulRepositoryCall = UnsuccessfulRepositoryCall(errorCode = response.code()))
    }
}


