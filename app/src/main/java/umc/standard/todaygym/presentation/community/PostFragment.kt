package umc.standard.todaygym.presentation.community

import android.os.Bundle
import android.util.Log
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
import umc.standard.todaygym.data.api.UserInterface
import umc.standard.todaygym.data.mdoel.PostData
import umc.standard.todaygym.data.mdoel.PostModel
import umc.standard.todaygym.data.util.RetrofitClient
import umc.standard.todaygym.databinding.FragmentPostBinding

class PostFragment: Fragment() {
    private lateinit var viewBinding: FragmentPostBinding
    val okCode: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var postModel: PostModel

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
            add(PostData("세인","아아아"))
            add(PostData("잉","하"))
            add(PostData("멍","냥"))
            add(PostData("세인","아아아"))
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
        val call = communityInterface?.getPost(postId)
        call?.enqueue(object : Callback<List<PostModel>>{
            override fun onResponse(
                call: Call<List<PostModel>>,
                response: Response<List<PostModel>>,
            ) {
                if(response.isSuccessful){
                    Log.d("addPost 요청",response.body().toString())
                    var data = response.body()
                }

            }

            override fun onFailure(call: Call<List<PostModel>>, t: Throwable) {
                Log.d("addPost 요청","실패")
            }

        })
    }
}
