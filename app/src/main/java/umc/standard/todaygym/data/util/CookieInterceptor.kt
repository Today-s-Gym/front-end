package umc.standard.todaygym.util

import android.util.Log
import umc.standard.todaygym.util.SharePreferences.Companion.prefs
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.cookieToString
import okio.IOException

// 헤더에 자동으로 jwt 값을 넣어주는 함수
// 제가 테스트는 안해봐서 제대로 작동이 안될까봐 일단 주석처리해두었습니다
class ReceivedCookiesInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse: Response = chain.proceed(chain.request())
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            val cookies: HashSet<String> = HashSet()
            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
            }
            // Preference에 cookies를 넣어주는 작업을 수행
            prefs.putSharedPreference(APIPreferences.SHARED_PREFERENCE_NAME_COOKIE,cookies);
        }
        return originalResponse
    }
}
class AddCookiesInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        // Preference에서 cookies를 가져오는 작업을 수행
        val preferences: Set<String> = prefs.getSharedPreference(
            APIPreferences.SHARED_PREFERENCE_NAME_COOKIE,
            HashSet<String>()
        )
        for (cookie in preferences) {
            var tmp_cookie = cookie.split("=")?.get(1)?.split(";")?.get(0)
            Log.d("Interceptor","Bearer ${tmp_cookie}")
            builder.addHeader("Authorization", "Bearer ${tmp_cookie}")
        }

        // Web,Android,iOS 구분을 위해 User-Agent세팅
        builder.removeHeader("User-Agent").addHeader("User-Agent", "Android")
        return chain.proceed(builder.build())
    }
}