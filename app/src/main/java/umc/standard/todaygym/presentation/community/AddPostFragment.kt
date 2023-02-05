package umc.standard.todaygym.presentation.community

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.standard.todaygym.R
import umc.standard.todaygym.data.api.CommunityService
import umc.standard.todaygym.data.model.PostData
import umc.standard.todaygym.data.model.RequestAddPost
import umc.standard.todaygym.data.util.RetrofitClient
import umc.standard.todaygym.databinding.FragmentAddPostBinding

class AddPostFragment: Fragment() {
    private lateinit var viewBinding: FragmentAddPostBinding
    private lateinit var requestAddPost : RequestAddPost

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAddPostBinding.inflate(layoutInflater)

        viewBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        var postPostReq= RequestAddPost.PostPostReq(9,"","")
        requestAddPost = RequestAddPost(listOf(),postPostReq)

        viewBinding.btnAdd.setOnClickListener {
            postPostReq.content = viewBinding.editContent.text.toString()
            postPostReq.title = viewBinding.editTitle.text.toString()
            requestAddPost.postPostReq= postPostReq
            request(requestAddPost)
        }


        viewBinding.btnExrecord.setOnClickListener {
            findNavController().navigate(R.id.action_addPostFragment_to_addExFragment)
        }

        var content = arguments?.getString("content")
        if(!content.equals("")){
            viewBinding.viewExrecord.visibility=View.VISIBLE
            viewBinding.tvExcontent.text = content
            viewBinding.tvExdate.text = arguments?.getString("date")
            var imgUrl = arguments?.getString("url")
            Glide.with(this)
                .load(imgUrl)
                .into(viewBinding.imgExrecord)

        }


        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun request(requestAddPost: RequestAddPost){
        val communityInterface: CommunityService? =
            RetrofitClient.getClient()?.create(CommunityService::class.java)
        val call = communityInterface?.addPost(requestAddPost)
        call?.enqueue(object : Callback<RequestAddPost> {

            override fun onResponse(
                call: Call<RequestAddPost>,
                response: Response<RequestAddPost>,
            ) {
                if (response.isSuccessful){
                    var data = response.body()

                }

            }

            override fun onFailure(call: Call<RequestAddPost>, t: Throwable) {

            }

        })
    }
}