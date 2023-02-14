package umc.standard.todaygym.presentation.community

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.standard.todaygym.R
import umc.standard.todaygym.data.api.CommunityService
import umc.standard.todaygym.data.model.EditPost
import umc.standard.todaygym.data.model.RequestAddPost
import umc.standard.todaygym.data.util.RetrofitClient
import umc.standard.todaygym.databinding.FragmentAddPostBinding

class EditPostFragment: Fragment() {
    private lateinit var viewBinding: FragmentAddPostBinding
    private lateinit var editPost: EditPost

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAddPostBinding.inflate(layoutInflater)

        viewBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        var recordId = findNavController().currentBackStackEntry?.savedStateHandle?.get<Int>("recordId")
        viewBinding.btnAdd.setOnClickListener {
            editPost = EditPost(arguments?.getInt("editCategoryId")!!,viewBinding.editTitle.text.toString(), viewBinding.editContent.text.toString(),
                listOf(""),recordId)
            requestEditPost(arguments?.getInt("editPostId")!!,editPost)
            Log.d("editPostID",arguments?.getInt("editPostId").toString())
            Log.d("recordId",recordId.toString())
            findNavController().popBackStack(R.id.boardFragment,false)
        }

        viewBinding.editTitle.setText(arguments?.getString("editTitle"))
        viewBinding.editContent.setText(arguments?.getString("editContent"))
        
        viewBinding.viewExrecord.visibility=View.GONE

        viewBinding.btnExrecord.setOnClickListener {
            findNavController().navigate(R.id.action_editPostFragment_to_addExFragment)

        }

        val navController = findNavController()
        if(navController?.currentBackStackEntry?.savedStateHandle?.contains("content") == true){
            viewBinding.tvExcontent.text = navController.currentBackStackEntry?.savedStateHandle?.get<String>("content")
            viewBinding.tvExdate.text = navController.currentBackStackEntry?.savedStateHandle?.get<String>("data")
            var imgUrl = navController.currentBackStackEntry?.savedStateHandle?.get<String>("url")
            Glide.with(this)
                .load(imgUrl)
                .into(viewBinding.imgExrecord)
            viewBinding.viewExrecord.visibility = View.VISIBLE
        }

        viewBinding.btnDelete.setOnClickListener {
            viewBinding.viewExrecord.visibility=View.GONE
            viewBinding.tvExcontent.text = null
            viewBinding.tvExdate.text = null
            Glide.with(this)
                .load("")
                .into(viewBinding.imgExrecord)

        }

        return viewBinding.root
    }



    private fun requestEditPost(postId:Int ,editPost: EditPost){
//        val requestFile = RequestBody.create(MultipartBody.FORM,"")
//        val body = MultipartBody.Part.createFormData("image", "", requestFile)
        val communityInterface: CommunityService? =
            RetrofitClient.getClient()?.create(CommunityService::class.java)
        val call = communityInterface?.editPost(postId,editPost)
        call?.enqueue(object : Callback<EditPost> {

            override fun onResponse(
                call: Call<EditPost>,
                response: Response<EditPost>,
            ) {
                if (response.isSuccessful){
                    var data = response.body()

                }

            }

            override fun onFailure(call: Call<EditPost>, t: Throwable) {

            }

        })
    }

}