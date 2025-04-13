package com.rasel.baseappcompose.data.network.retrofit

import android.content.Context
import com.rasel.baseappcompose.core.datastore.TokenManager
import com.rasel.baseappcompose.data.util.isNetworkAvailable
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkConnectionInterceptor @Inject constructor(
    private val tokenManager: TokenManager,
    context: Context
) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
       /* if (!applicationContext.isNetworkAvailable()) throw NoInternetException("Make sure you have an active data connection")
        return chain.proceed(chain.request())*/

        val token = runBlocking {
            tokenManager.getToken().first()
        }

        var request = chain.request()
        request = if (applicationContext.isNetworkAvailable()) {
            request.newBuilder()
                .header("Cache-Control", "public, max-age=" + 5)
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                .build()
        }
        return chain.proceed(request)

        /* val builder = chain.request().newBuilder()
         builder.addHeader("Accept", "application/json")*/

    }


}

/*class NetworkConnectionInterceptor(private val ctx: WeakReference<Context>) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (ctx.get() !is Application) throw IllegalArgumentException("You mast provide an application context to don't cause memory leaks")


        return ctx.get()?.takeIf {
            it.isConnected() }?.run {
            return@run chain.request().run(chain::proceed)
        } ?: throw ConnectivityException()
    }

    class ConnectivityException : IOException() {
        override val message: String = "Connection Not Found Exception"
    }
} */