package umc.standard.todaygym.presentation.mypage.onboarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.standard.todaygym.data.api.UserInterface
import umc.standard.todaygym.data.model.AddSignResponse
import umc.standard.todaygym.data.util.RetrofitClient
import umc.standard.todaygym.databinding.ActivitySignupNicknameBinding

class SignUpNicknameActivity:AppCompatActivity(){
    private lateinit var binding: ActivitySignupNicknameBinding
    var isValid:Boolean = false
    val userInterface: UserInterface? =
        RetrofitClient.getClient()?.create(UserInterface::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupNicknameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnNicknameCheck.setOnClickListener{

            val call = userInterface?.nickNameCheck(nickname = binding.etSignupNickname.text.toString())
            call?.enqueue(object : Callback<AddSignResponse> {
                override fun onResponse(
                    call: Call<AddSignResponse>,
                    response: Response<AddSignResponse>
                ) {
                    if(response.body()?.isSuccess == true){
                        Toast.makeText(this@SignUpNicknameActivity,"사용가능한 닉네임입니다",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@SignUpNicknameActivity,response.body()?.msg,Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AddSignResponse>, t: Throwable) {
                    Log.d("실패!", t.toString());
                    Toast.makeText(this@SignUpNicknameActivity,"사용중인 닉네임입니다",Toast.LENGTH_SHORT).show()
                }

            })
        }
        binding.btSingupNickname.setOnClickListener{
            if (binding.etSignupNickname.text.toString() != "" && isValid) {
                val call = userInterface?.addLogIn(nickname = binding.etSignupNickname.text.toString(), statusMsg =binding.etSignupStatusMsg.text.toString())
                call?.enqueue(object : Callback<AddSignResponse> {
                    override fun onResponse(
                        call: Call<AddSignResponse>,
                        response: Response<AddSignResponse>
                    ) {
                        if(response.body()?.isSuccess == true){
                            Toast.makeText(this@SignUpNicknameActivity,"닉네임, 한줄소개 설정 성공!",Toast.LENGTH_SHORT).show()
                            val Intent = Intent(this@SignUpNicknameActivity, SignUpTypeActivity::class.java)
                            startActivity(Intent)
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<AddSignResponse>, t: Throwable) {
                        Log.d("실패!", t.toString());
                        Toast.makeText(this@SignUpNicknameActivity,"사용중인 닉네임입니다",Toast.LENGTH_SHORT).show()
                    }

                })
//                prefs.putSharedPreference(
//                    SHARED_PREFERENCE_NAME_NICKNAME,
//                    binding.etSignupNickname.text.toString())

            }
        }
    }

}