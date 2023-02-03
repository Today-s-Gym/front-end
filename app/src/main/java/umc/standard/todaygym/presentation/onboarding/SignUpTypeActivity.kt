package umc.standard.todaygym.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.standard.todaygym.MainActivity
import umc.standard.todaygym.data.api.CategoryInterface
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
                TODO("Not yet implemented")
            }

        })
       binding.tvType1.setOnClickListener{
           selectType = 1
       }
        val tabLayout = binding.layoutType as TabLayout
        tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                categoryList.indexOf(tab?.text)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

        })


        binding.btSingupNickname.setOnClickListener{

                SharePreferences.prefs.putSharedPreference(
                    APIPreferences.SHARED_PREFERENCE_NAME_TYPE,
                    selectType)
                val Intent = Intent(this@SignUpTypeActivity, MainActivity::class.java)
                startActivity(Intent)
                finish()


        }
    }




}
