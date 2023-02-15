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
import umc.standard.todaygym.data.model.*
import umc.standard.todaygym.data.util.RetrofitClient
import umc.standard.todaygym.databinding.FragmentPostBinding

class PostFragment: Fragment() {
    private lateinit var viewBinding: FragmentPostBinding
    var data: PostData? = null
    var data2: ChatData? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentPostBinding.inflate(layoutInflater)
        var postId = arguments?.getInt("id") as Int
        load(postId)
        loadChat(postId)
        viewBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewBinding.btnChat.setOnClickListener {
            var chat=AddChat(postId,viewBinding.editChat.text.toString())
            addChat(chat,postId)
            viewBinding.editChat.text = null
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
                    data?.result?.let { listOf(it) }
                        ?.let { data2?.let { it1 -> chatAdapter(it1.result, it, postId) } }
                }
            }
            override fun onFailure(call: Call<PostData>, t: Throwable) {
            }

        })
    }

    fun loadChat(postId: Int){
        val communityInterface: CommunityService? =
            RetrofitClient.getClient()?.create(CommunityService::class.java)
        val call = communityInterface?.getChat(postId)
        call?.enqueue(object : Callback<ChatData>{
            override fun onResponse(call: Call<ChatData>, response: Response<ChatData>) {
                if(response.isSuccessful){
                    data2 = response.body()
                    data2?.let {
                        data?.result?.let { it1 -> listOf(it1) }?.let { it2 ->
                            chatAdapter(it.result,
                                it2, postId)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ChatData>, t: Throwable) {

            }

        })
    }

    private fun chatAdapter(chatList: List<ChatData.Result>, postList: List<PostData.Result>, postId: Int){
        val dataRVAdapter =
            arguments?.getInt("categoryId")?.let { PostRVAdapter(chatList,postList, it,postId) }

        viewBinding.recyclerChat.adapter = dataRVAdapter
        viewBinding.recyclerChat.layoutManager = LinearLayoutManager(context)
        if (dataRVAdapter != null) {
            dataRVAdapter.notifyDataSetChanged()
        }
        viewBinding.recyclerChat.setHasFixedSize(true)

    }

    private fun addChat(addChat: AddChat,postId: Int){
        val communityInterface: CommunityService? =
            RetrofitClient.getClient()?.create(CommunityService::class.java)
        val call = communityInterface?.addChat(addChat)
        call?.enqueue(object : Callback<AddChat> {
            override fun onResponse(call: Call<AddChat>, response: Response<AddChat>) {
                if(response.isSuccessful){
                    var body = response.body()
                    loadChat(postId)
                }
            }
            override fun onFailure(call: Call<AddChat>, t: Throwable) {

            }
        })
    }
}


