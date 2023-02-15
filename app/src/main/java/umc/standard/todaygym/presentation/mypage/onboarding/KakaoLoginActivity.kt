package umc.standard.todaygym.presentation.mypage.onboarding

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import umc.standard.todaygym.databinding.ActivityKakaologinBinding

class KakaoLoginActivity:AppCompatActivity() {
    private lateinit var binding: ActivityKakaologinBinding
    lateinit var kakaoCallback: (OAuthToken?, Throwable?) -> Unit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKakaologinBinding.inflate(layoutInflater)
        Log.d("카카오",UserApiClient.instance.loginWithKakaoTalk(this, callback = kakaoCallback).toString());
        setContentView(binding.root)
    }






//
//    fun setKakaoCallback() {
//        kakaoCallback = { token, error ->
//            if (error != null) {
//                when {
//                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
//                        Log.d("[카카오로그인]","접근이 거부 됨(동의 취소)")
//                    }
//                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
//                        Log.d("[카카오로그인]","유효하지 않은 앱")
//                    }
//                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
//                        Log.d("[카카오로그인]","인증 수단이 유효하지 않아 인증할 수 없는 상태")
//                    }
//                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
//                        Log.d("[카카오로그인]","요청 파라미터 오류")
//                    }
//                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
//                        Log.d("[카카오로그인]","유효하지 않은 scope ID")
//                    }
//                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
//                        Log.d("[카카오로그인]","설정이 올바르지 않음(android key hash)")
//                    }
//                    error.toString() == AuthErrorCause.ServerError.toString() -> {
//                        Log.d("[카카오로그인]","서버 내부 에러")
//                    }
//                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
//                        Log.d("[카카오로그인]","앱이 요청 권한이 없음")
//                    }
//                    else -> { // Unknown
//                        Log.d("[카카오로그인]","기타 에러")
//                    }
//                }
//            }
//            else if (token != null) {
//                Log.d("[카카오로그인]","로그인에 성공하였습니다.\n${token.accessToken}")
//                UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
//                    UserApiClient.instance.me { user, error ->
//                        nickname.text = "닉네임: ${user?.kakaoAccount?.profile?.nickname}"
//                    }
//                }
//            }
//            else {
//                Log.d("카카오로그인", "토큰==null error==null")
//            }
//        }
//    }






}