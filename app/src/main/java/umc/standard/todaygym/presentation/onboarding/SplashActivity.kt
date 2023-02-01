package umc.standard.todaygym.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints.TAG
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.standard.todaygym.MainActivity
import umc.standard.todaygym.data.api.UserInterface
import umc.standard.todaygym.data.model.SignUpResponse
import umc.standard.todaygym.data.util.API_CONSTNATS.BASE_URL
import umc.standard.todaygym.data.util.RetrofitClient
import umc.standard.todaygym.databinding.ActivitySplashBinding

class SplashActivity:AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    lateinit var kakaoCallback: (OAuthToken?, Throwable?) -> Unit
    // 카카오계정으로 로그인 공통 callback 구성
// 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
            val userInterface: UserInterface? = RetrofitClient.getClient(BASE_URL)?.create(UserInterface::class.java)
            val call = userInterface?.kakaoLogIn(kakaoToken = token.accessToken)
            call?.enqueue(object : Callback<SignUpResponse> {
                override fun onResponse(
                    call: Call<SignUpResponse>,
                    response: Response<SignUpResponse>
                ) {
                    Log.d("성공공",response.code().toString())
                    Log.d("성공!",response.body()?.result?.accessToken.toString());

                }

                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                    Log.d("실패!",t.toString());
                }

            })
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val keyHash = Utility.getKeyHash(this)
        Log.d("Hash", keyHash)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnKakaoLogin.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@SplashActivity)) {
                UserApiClient.instance.loginWithKakaoTalk(this@SplashActivity) { token, error ->
                    if (error != null) {
                        Log.e(TAG, "로그인 실패", error)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            Log.e(TAG, "로그인 실패", error)
                        }
                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(this@SplashActivity, callback = callback)

                    } else if (token != null) {
                        Log.i(TAG, "로그인 성공 ${token.accessToken}")

                    }
                }

            }else{
                UserApiClient.instance.loginWithKakaoAccount(this@SplashActivity, callback = callback)
            }

        }



    }


}
