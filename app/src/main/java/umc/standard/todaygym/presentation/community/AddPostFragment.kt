package umc.standard.todaygym.presentation.community


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.TypefaceCompatUtil.getTempFile
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.standard.todaygym.R
import umc.standard.todaygym.data.api.CommunityService
import umc.standard.todaygym.data.model.BoardData
import umc.standard.todaygym.data.model.Lock
import umc.standard.todaygym.data.model.RequestAddPost
import umc.standard.todaygym.data.util.RetrofitClient
import umc.standard.todaygym.databinding.FragmentAddPostBinding
import java.io.File
import java.io.IOException

class AddPostFragment: Fragment() {
    private lateinit var viewBinding: FragmentAddPostBinding
    private lateinit var requestAddPost : RequestAddPost
    var data : Lock? = null
    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val imageUrl = it.data?.data
//            viewBinding.imgCamera.setImageURI(imageUrl)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = FragmentAddPostBinding.inflate(layoutInflater)

        viewBinding.apply {
            //비공계
            if(data?.result?.locked == true){
                tvAdd.setTextColor(909090)
                btnExrecord.setImageResource(R.drawable.question_mark)
                btnExrecord.setOnClickListener {
                    tvLock.visibility = View.VISIBLE
                }


            }
            //공계
            else{
                btnExrecord.setOnClickListener {
                    findNavController().navigate(R.id.action_addPostFragment_to_addExFragment)

                }


            }
        }


        viewBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }


        var recordId = findNavController().currentBackStackEntry?.savedStateHandle?.get<Int>("recordId")
        viewBinding.btnAdd.setOnClickListener {
            requestAddPost = RequestAddPost(arguments?.getInt("categoryId")!!,viewBinding.editTitle.text.toString(), viewBinding.editContent.text.toString(),recordId)
            request(requestAddPost)
            findNavController().popBackStack(R.id.boardFragment,false)

        }


//        viewBinding.viewExrecord.visibility=View.GONE


        val navController = findNavController()
        if(navController?.currentBackStackEntry?.savedStateHandle?.contains("content") == true){
            viewBinding.tvExcontent.text = navController.currentBackStackEntry?.savedStateHandle?.get<String>("content")
            viewBinding.tvExdate.text = navController.currentBackStackEntry?.savedStateHandle?.get<String>("data")
            var imgUrl = navController.currentBackStackEntry?.savedStateHandle?.get<String>("url")
            if(imgUrl != ""){
                Glide.with(this)
                    .load(imgUrl)
                    .into(viewBinding.imgExrecord)
            }

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

    fun move_gallery() {
        var photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startForResult.launch(photoPickerIntent)

    }

    private fun requestLock(){
        val communityInterface: CommunityService? =
            RetrofitClient.getClient()?.create(CommunityService::class.java)
        val call = communityInterface?.getLock()
        call?.enqueue(object : Callback<Lock> {
            override fun onResponse(call: Call<Lock>, response: Response<Lock>) {
                if(response.isSuccessful){
                    data = response.body()
                }

            }

            override fun onFailure(call: Call<Lock>, t: Throwable) {

            }

        })
    }

}