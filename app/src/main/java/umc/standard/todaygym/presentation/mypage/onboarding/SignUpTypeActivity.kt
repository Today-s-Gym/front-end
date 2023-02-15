package umc.standard.todaygym.presentation.mypage.onboarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.standard.todaygym.MainActivity
import umc.standard.todaygym.data.api.CategoryInterface
import umc.standard.todaygym.data.api.UserInterface
import umc.standard.todaygym.data.model.AddSignResponse
import umc.standard.todaygym.data.model.CategoryResponse
import umc.standard.todaygym.data.util.API_CONSTNATS.BASE_URL
import umc.standard.todaygym.data.util.RetrofitClient
import umc.standard.todaygym.databinding.ActivitySignupTypeBinding
import umc.standard.todaygym.util.APIPreferences
import umc.standard.todaygym.util.SharePreferences

class SignUpTypeActivity:AppCompatActivity(){

    private lateinit var binding: ActivitySignupTypeBinding
    var selectType :Int = 0

    lateinit var categoryList:List<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val categoryInterface: CategoryInterface? = RetrofitClient.getClient()?.create(CategoryInterface::class.java)
        val call = categoryInterface?.CategoryRequest()
        call?.enqueue(object: Callback<CategoryResponse>{
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
//                if(response.body()?.code)
                categoryList = response.body()?.result!!
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Log.d("Error","서버오류")
            }

        })

        val tabLayout = binding.layoutType as TabLayout
        tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                selectType = categoryList.indexOf(tab?.text)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                selectType = -1
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                selectType = categoryList.indexOf(tab?.text)
            }

        })


        binding.btSingupNickname.setOnClickListener{
            val userInterface: UserInterface? = RetrofitClient.getClient()?.create(UserInterface::class.java)
            val call = userInterface?.addSports(categoryId =selectType)
            call?.enqueue(object: Callback<AddSignResponse>{
                override fun onResponse(
                    call: Call<AddSignResponse>,
                    response: Response<AddSignResponse>
                ) {
                    val Intent = Intent(this@SignUpTypeActivity, MainActivity::class.java)
                    startActivity(Intent)
                    finish()
                }

                override fun onFailure(call: Call<AddSignResponse>, t: Throwable) {
                    Log.d("Error","서버오류")
                }

            })



        }
    }




}
