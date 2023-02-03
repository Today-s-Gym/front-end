package umc.standard.todaygym.presentation.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.standard.todaygym.data.api.CommunityService
import umc.standard.todaygym.data.model.PostData
import umc.standard.todaygym.data.model.PostModel
import umc.standard.todaygym.data.util.RetrofitClient
import umc.standard.todaygym.databinding.FragmentPostBinding

class PostFragment: Fragment() {
    private lateinit var viewBinding: FragmentPostBinding
    val okCode: MutableLiveData<Boolean> = MutableLiveData()
    val JWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE3LCJpc3MiOiJ0ZXN0IiwiaWF0IjoxNjc0OTY5MzY4LCJleHAiOjE3MDY1MDUzNjh9.wME-N31YIrjAtr7Y1usIIQZwG_cHZcmZqB8hBtgq5lk"
    

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentPostBinding.inflate(layoutInflater)

        viewBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }


        val dataList: ArrayList<PostData> = arrayListOf()
        dataList.apply {
            add(PostData("멍","냥"))
            add(PostData("냥","멍"))
            add(PostData("잉","하"))
            add(PostData("멍","냥"))
            add(PostData("냥","멋져"))
            add(PostData("잉","하"))
        }

        val dataRVAdapter = PostRVAdapter(dataList)

        viewBinding.recyclerChat.adapter = dataRVAdapter
        viewBinding.recyclerChat.layoutManager = LinearLayoutManager(context)

        return viewBinding.root
    }

    private fun load(postId: Int){
        val communityInterface: CommunityService? =
            RetrofitClient.getClient()?.create(CommunityService::class.java)
        val call = communityInterface?.getPost(JWT,postId)
        call?.enqueue(object : Callback<List<PostModel>>{
            override fun onResponse(
                call: Call<List<PostModel>>,
                response: Response<List<PostModel>>,
            ) {
                if(response.isSuccessful){
                   val body = response.body()

                }

            }
            override fun onFailure(call: Call<List<PostModel>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}


