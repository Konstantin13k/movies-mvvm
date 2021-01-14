package od.konstantin.myapplication.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response

private const val QUERY_KEY_LANGUAGE = "language"
private const val DEFAULT_LANGUAGE = "en-US"

class MoviesLanguageInterceptor(private val language: String = DEFAULT_LANGUAGE) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter(QUERY_KEY_LANGUAGE, language).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}