package umc.standard.todaygym.presentation.onboarding

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import umc.standard.todaygym.databinding.DialogPermissionBinding
import umc.standard.todaygym.util.APIPreferences.SHARED_PREFERENCE_NAME_COOKIE
import umc.standard.todaygym.util.SharePreferences.Companion.prefs

class SplashActivity:AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    //권한 가져오기
//    val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)
    val STORAGE_PERMISSION = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    //권한 플래그값 정의
//    val PERM_CAMERA = 98
    val PERM_STORAGE = 99

    //카메라와 갤러리를 호출하는 플래그
    val FLAG_REQ_CAMERA = 101
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
                showBottomSheet(this@SplashActivity)

            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                Log.d("실패!", t.toString());
            }

        })
    }

    fun showBottomSheet(context: Context){

        val dialog = BottomSheetDialog(context)
        val dialogView = BottomsheetCheckPolicyBinding.inflate(LayoutInflater.from(context))
        var policayAllCheck:Boolean = false
        var policyGlobal:Boolean = false
        var policyPersonal:Boolean = false
        var policyMarketing:Boolean = false
        dialog.setContentView(dialogView.root)
        dialog.show()
        // all check
        dialogView.btnPolicyAllCheck.setOnClickListener{
            if(policayAllCheck){
                it.setBackgroundResource(R.drawable.border_account)
                dialogView.btnPolicyGlobal.setBackgroundResource(R.drawable.border_account)
                dialogView.btnPolicyPersonal.setBackgroundResource(R.drawable.border_account)
                dialogView.btnPolicyMarketing.setBackgroundResource(R.drawable.border_account)
                policayAllCheck = false
                policyGlobal = false
                policyPersonal = false
                policyMarketing = false
            }else{
                it.setBackgroundResource(R.drawable.border_account_primary)
                dialogView.btnPolicyGlobal.setBackgroundResource(R.drawable.border_account_primary)
                dialogView.btnPolicyPersonal.setBackgroundResource(R.drawable.border_account_primary)
                dialogView.btnPolicyMarketing.setBackgroundResource(R.drawable.border_account_primary)
                policayAllCheck = true
                policyGlobal = true
                policyPersonal = true
                policyMarketing = true
            }


        }
        //individual check
        dialogView.btnPolicyGlobal.setOnClickListener{
            if(policyGlobal){
                it.setBackgroundResource(R.drawable.border_account)
                policyGlobal = false;
            }else{
                it.setBackgroundResource(R.drawable.border_account_primary)
                policyGlobal = true
            }


        }
        dialogView.btnPolicyPersonal.setOnClickListener{
            if(policyPersonal){
                it.setBackgroundResource(R.drawable.border_account)
                policyPersonal = false;
            }else{
                it.setBackgroundResource(R.drawable.border_account_primary)
                policyPersonal = true
            }

        }
        dialogView.btnPolicyMarketing.setOnClickListener{
            if(policyMarketing){
                it.setBackgroundResource(R.drawable.border_account)
                policyMarketing = false;
            }else{
                it.setBackgroundResource(R.drawable.border_account_primary)
                policyMarketing = true
            }
        }
        dialogView.btnPolicyNext.setOnClickListener{
//        Log.d("bottomsheet","fddds")
            if(policyGlobal && policyPersonal){
//                Log.d("bottomsheet","true")
                dialog.dismiss()
                permissionDialog()

            }else{
//                Log.d("bottomsheet","false")
                Toast.makeText(this@SplashActivity,"약관에 동의해야 서비스를 이용할수있습니다",Toast.LENGTH_LONG).show()
            }
        }
        dialogView.btnCloseTap.setOnClickListener{
            Toast.makeText(this@SplashActivity,"약관에 동의해야 서비스를 이용할수있습니다",Toast.LENGTH_LONG).show()
//        dialog.dismiss()
        }
    }


    fun permissionDialog(){
        val dialog = Dialog(this@SplashActivity)
        val dialogView = DialogPermissionBinding.inflate(LayoutInflater.from(this@SplashActivity))
        dialog.setContentView(dialogView.root)
        val params : WindowManager.LayoutParams? = dialog.window?.attributes;
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        if (params != null) {
            dialog.window?.setLayout(params.width,params.height)
        }
        dialog.show()
        dialogView.btnPermissionNext.setOnClickListener{
            Log.d("fddfffd","dsfsfs")
            checkPermission(STORAGE_PERMISSION, PERM_STORAGE)
//            checkPermission(CAMERA_PERMISSION,PERMm_CAMERA)
        }
    }

    fun checkPermission(permissions: Array<out String>, flag: Int): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("permission","1")
            for (permission in permissions) {
                //만약 권한이 승인되어 있지 않다면 권한승인 요청을 사용에 화면에 호출합니다.
                if (ContextCompat.checkSelfPermission(
                        this@SplashActivity,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    Log.d("permission","isGrandted?")
                    ActivityCompat.requestPermissions(this, permissions, flag)
                    Log.d("permission","isGrandted?")
                    return false
                }
            }
        }
        Log.d("permission","1.5")
        val Intent = Intent(this@SplashActivity, SignUpNicknameActivity::class.java)
        startActivity(Intent)
        finish()
        return true
    }
    //checkPermission() 에서 ActivityCompat.requestPermissions 을 호출한 다음 사용자가 권한 허용여부를 선택하면 해당 메소드로 값이 전달 됩니다.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val Intent = Intent(this@SplashActivity, SignUpNicknameActivity::class.java)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERM_STORAGE -> {
                Log.d("permission","2")
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "저장소 권한을 승인하지않으면 앱을 실행할수없습니다", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                        return
                    }
                }
                startActivity(Intent)
                finish()


            }

        }
    }
}


