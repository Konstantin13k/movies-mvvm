package od.konstantin.myapplication.data.remote.interceptors

import od.konstantin.myapplication.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

private const val QUERY_KEY_API_KEY = "api_key"

class MoviesApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter(QUERY_KEY_API_KEY, BuildConfig.MOVIES_API_KEY).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}