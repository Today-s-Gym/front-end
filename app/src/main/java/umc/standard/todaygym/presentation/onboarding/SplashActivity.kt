package umc.standard.todaygym.presentation.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.standard.todaygym.MainActivity
import umc.standard.todaygym.R
import umc.standard.todaygym.data.api.UserInterface
import umc.standard.todaygym.data.model.SignUpResponse
import umc.standard.todaygym.data.util.RetrofitClient
import umc.standard.todaygym.databinding.ActivitySplashBinding
import umc.standard.todaygym.databinding.BottomsheetCheckPolicyBinding
import umc.standard.todaygym.util.APIPreferences.SHARED_PREFERENCE_NAME_COOKIE
import umc.standard.todaygym.util.SharePreferences.Companion.prefs

class SplashActivity:AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
//    val dialog : BottomSheetDialog = BottomSheetDialog(this)

    // 카카오계정으로 로그인 공통 callback 구성
    // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i(TAG, "카카오계정으로 로그인 성공")
            kakaoApi(token)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val keyHash = Utility.getKeyHash(this)
        Log.d("Hash", keyHash)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnKakaoLogin.setOnClickListener {
            Log.d("fd","fdsf");
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@SplashActivity)) {
                UserApiClient.instance.loginWithKakaoTalk(this@SplashActivity) { token, error ->
                    if (error != null) {
                        Log.e(TAG, "로그인 실패", error)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            Log.e(TAG, "로그인 취소", error)
                        }
                    } else if (token != null) {
                        Log.i(TAG, "카카오앱으로 로그인 성공")
                        kakaoApi(token)
                    }
                }
            }else {
                        UserApiClient.instance.loginWithKakaoAccount(
                            this@SplashActivity,
                            callback = callback)
                    }


        }
    }

//카카오에서 반환해준 토큰을 우리쪽 서버와 통신하여 jwt를 얻는 함수
fun kakaoApi(token:OAuthToken){
        Log.i(TAG, "토큰반환 성공 ${token.accessToken}")
        val userInterface: UserInterface? =
            RetrofitClient.getClient()?.create(UserInterface::class.java)
        val call = userInterface?.kakaoLogIn(kakaoToken = token.accessToken)
        call?.enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                Log.d("성공!", response.body()?.result?.accessToken.toString());
                prefs.putSharedPreference(
                    SHARED_PREFERENCE_NAME_COOKIE,
                    response.body()?.result?.accessToken
                )
//                val bottomSheetView = CheckPolicyBottomSheet()
                Log.d("bottomsheet","작동되긴하니?")
//                showBottomSheet(supportFragmentManager)
                showBottomSheet(this@SplashActivity)

//                val Intent = Intent(this@SplashActivity, MainActivity::class.java)
//                startActivity(Intent)
//                finish()

            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                Log.d("실패!", t.toString());
            }

        })
    }
}
fun showBottomSheet(context: Context){
//    val bottomSheetView = CheckPolicyBottomSheet()
//    Log.d("bottomsheet","작동되긴하니?")
//    bottomSheetView.show(fragmentManager, bottomSheetView.tag)
    val dialog = BottomSheetDialog(context)
    val dialogView = BottomsheetCheckPolicyBinding.inflate(LayoutInflater.from(context))
    var policyGlobal:Boolean = false
    var policyPersonal:Boolean = false
    var policyMarketing:Boolean = false
    dialog.setContentView(dialogView.root)
    dialog.show()
    // all check
    dialogView.btnPolicyAllCheck.setOnClickListener{
            it.setBackgroundResource(R.drawable.border_account_primary)
        dialogView.btnPolicyGlobal.setBackgroundResource(R.drawable.border_account_primary)
        dialogView.btnPolicyPersonal.setBackgroundResource(R.drawable.border_account_primary)
        dialogView.btnPolicyMarketing.setBackgroundResource(R.drawable.border_account_primary)
            policyGlobal = true
            policyPersonal = true
            policyMarketing = true
        }
        //individual check
    dialogView.btnPolicyGlobal.setOnClickListener{
            it.setBackgroundResource(R.drawable.border_account_primary)
            policyGlobal = true

        }
    dialogView.btnPolicyPersonal.setOnClickListener{
            it.setBackgroundResource(R.drawable.border_account_primary)
            policyPersonal = true

        }
    dialogView.btnPolicyMarketing.setOnClickListener{
            it.setBackgroundResource(R.drawable.border_account_primary)
            policyMarketing = true
        }
    dialogView.btnPolicyNext.setOnClickListener{
            if(policyGlobal && policyPersonal){
                dialog.dismiss()
            }
        }
}
