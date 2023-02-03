package umc.standard.todaygym.presentation.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.standard.todaygym.data.api.CommunityService
import umc.standard.todaygym.data.model.ChatData
import umc.standard.todaygym.data.model.PostData
import umc.standard.todaygym.data.util.RetrofitClient
import umc.standard.todaygym.databinding.FragmentPostBinding

class PostFragment: Fragment() {
    private lateinit var viewBinding: FragmentPostBinding
    val JWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE3LCJpc3MiOiJ0ZXN0IiwiaWF0IjoxNjc0OTY5MzY4LCJleHAiOjE3MDY1MDUzNjh9.wME-N31YIrjAtr7Y1usIIQZwG_cHZcmZqB8hBtgq5lk"
    var data: PostData? = null
    var data2: ChatData? = null
    var postList: List<PostData.Result>? = null
    var chatList: List<ChatData.Result>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentPostBinding.inflate(layoutInflater)

        load(8)
        load2(10)
        viewBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }


        return viewBinding.root
    }

    private fun load(postId: Int){
        val communityInterface: CommunityService? =
            RetrofitClient.getClient()?.create(CommunityService::class.java)
        val call = communityInterface?.getPost(postId)
        call?.enqueue(object : Callback<PostData>{
            override fun onResponse(call: Call<PostData>, response: Response<PostData>) {
                if(response.isSuccessful){
                    data = response.body()
                }
            }
            override fun onFailure(call: Call<PostData>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun load2(postId: Int){
        val communityInterface: CommunityService? =
            RetrofitClient.getClient()?.create(CommunityService::class.java)
        val call = communityInterface?.getChat(postId)
        call?.enqueue(object : Callback<ChatData>{
            override fun onResponse(call: Call<ChatData>, response: Response<ChatData>) {
                if(response.isSuccessful){
                    data2 = response.body()
                    data2?.let {
                        data?.result?.let { it1 -> listOf(it1.getPostRes) }?.let { it2 ->
                            chatAdapter(it.result,
                                it2)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ChatData>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun chatAdapter(chatList: List<ChatData.Result>,postList: List<PostData.Result.GetPostRes>){
        val dataRVAdapter = PostRVAdapter(chatList,postList)

        viewBinding.recyclerChat.adapter = dataRVAdapter
        viewBinding.recyclerChat.layoutManager = LinearLayoutManager(context)
        dataRVAdapter.notifyDataSetChanged()
        viewBinding.recyclerChat.setHasFixedSize(true)

    }
}


