package umc.standard.todaygym.util

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class SharePreferences:Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceUtil(applicationContext)
        KakaoSdk.init(this, "bc0622fd155cb0b0046afa5bfc9ad9ad")
    }
}