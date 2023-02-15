package umc.standard.todaygym.presentation.mypage.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import umc.standard.todaygym.databinding.ActivitySignupNicknameBinding
import umc.standard.todaygym.databinding.ActivitySplashBinding
import umc.standard.todaygym.util.APIPreferences.SHARED_PREFERENCE_NAME_NICKNAME
import umc.standard.todaygym.util.SharePreferences.Companion.prefs

class SignUpNicknameActivity:AppCompatActivity(){
    private lateinit var binding: ActivitySignupNicknameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupNicknameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btSingupNickname.setOnClickListener{
            if (binding.etSignupNickname.text.toString() != "") {
                prefs.putSharedPreference(
                    SHARED_PREFERENCE_NAME_NICKNAME,
                    binding.etSignupNickname.text.toString())
                val Intent = Intent(this@SignUpNicknameActivity, SignUpTypeActivity::class.java)
                startActivity(Intent)
                finish()

            }
        }
    }

}