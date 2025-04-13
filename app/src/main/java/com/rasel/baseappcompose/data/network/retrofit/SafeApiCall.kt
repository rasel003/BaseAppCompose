package com.rasel.baseappcompose.data.network.retrofit

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

interface SafeApiCall {

    suspend fun <T> apiRequest(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                throwable.message?.let { Log.e("rsl", it, throwable) }
                when (throwable) {
                    is NoInternetException -> {
                        Resource.Failure(true, null, "Please check internet connection")
                    }
                    is ConnectException -> {
                        Resource.Failure(false, null, "Connection Exception")
                    }
                    is SocketTimeoutException -> {
                        Resource.Failure(false, null, "Connection timeout")
                    }
                    is IOException -> {
                        Resource.Failure(false, null, "Something went wrong... Please try again")
                    }
                    is HttpException -> {

                        val error = throwable.response()?.errorBody()?.string()


                        val message = StringBuilder()
                        error?.let {
                            try {
                                message.append(JSONObject(it).getString("error"))
                            } catch (e: JSONException) {
//                                message.append("Empty error body")
                                try {
                                    message.append(JSONObject(it).get("non_field_errors"))
                                } catch (e: JSONException) {
                                    message.append("Sorry! Something went wrong")
                                }

                            }
                        } ?: kotlin.run {
                            message.append("Empty error body")
                        }
                        // recording exceptions in firebase
                        error?.let { FirebaseCrashlytics.getInstance().log(it) }
                        FirebaseCrashlytics.getInstance().recordException(throwable)

                        Resource.Failure(
                            false,
                            throwable.code(),
                            message.toString().replace("[\"", "").replace("\"]", "")
                        )
                    }
                    else -> {
                        // recording exceptions in firebase
                        FirebaseCrashlytics.getInstance().recordException(throwable)
                        Resource.Failure(false, null, "Something went wrong... Please try again")
                    }
                }
            }
        }
    }
}