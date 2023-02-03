package umc.standard.todaygym.data.util

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import umc.standard.todaygym.data.util.API_CONSTNATS.BASE_URL
import umc.standard.todaygym.util.AddCookiesInterceptor
import java.util.concurrent.TimeUnit

object RetrofitClient {
    //retrofit 객체 생성
    private var retrofitClient: Retrofit?=null
    val TAG = "retrofit"
    fun getClient(): Retrofit?{
        Log.d(TAG,"retrofitClient - getClient() called")

        //api 통신상태를 상세하게 로깅 찍을수있는 httpclient
        val okHttpClient: OkHttpClient by lazy {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .addInterceptor(AddCookiesInterceptor())
//                .addInterceptor(ReceivedCookiesInterceptor())
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS)
                .writeTimeout(100,TimeUnit.SECONDS)
                .build()
        }

        //아직 retrofit 객체가 생성되지않았다면
        if(retrofitClient == null){
            //생성해준다
            Log.d(TAG,"retrofitClient - init start")
            retrofitClient = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }
        return retrofitClient
    }
}