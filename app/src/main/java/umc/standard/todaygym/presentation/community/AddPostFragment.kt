package umc.standard.todaygym.presentation.community

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.standard.todaygym.R
import umc.standard.todaygym.data.api.CommunityService
import umc.standard.todaygym.data.model.RequestAddPost
import umc.standard.todaygym.data.util.RetrofitClient
import umc.standard.todaygym.databinding.FragmentAddPostBinding

class AddPostFragment: Fragment() {
    private lateinit var viewBinding: FragmentAddPostBinding
    private lateinit var requestAddPost : RequestAddPost


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = FragmentAddPostBinding.inflate(layoutInflater)

        viewBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val mInputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mInputMethodManager.hideSoftInputFromWindow(viewBinding.editContent.windowToken,
            0)

        viewBinding.btnAdd.setOnClickListener {
            requestAddPost = RequestAddPost(arguments?.getInt("categoryId")!!,viewBinding.editTitle.text.toString(), viewBinding.editContent.text.toString(),null)
            request(requestAddPost)
        }


        viewBinding.viewExrecord.visibility=View.GONE

        viewBinding.btnExrecord.setOnClickListener {
            findNavController().navigate(R.id.action_addPostFragment_to_addExFragment)

        }

        var content = arguments?.getString("content")
        Log.d("content",content.toString())
        if (!content.equals(null)) {
            viewBinding.tvExcontent.text = content
            viewBinding.tvExdate.text = arguments?.getString("date")
            var imgUrl = arguments?.getString("url")
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

//        viewBinding.editContent.setOnEditorActionListener{ textView, action, event ->
//            var handled = false
//
//            if (action == EditorInfo.IME_ACTION_DONE) {
//                // 키보드 내리기
//                val inputMethodManager = getActivity()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                inputMethodManager.hideSoftInputFromWindow(viewBinding.editContent.windowToken, 0)
//                handled = true
//            }
//
//            handled
//        }

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun request(requestAddPost: RequestAddPost){
//        val requestFile = RequestBody.create(MultipartBody.FORM,"")
//        val body = MultipartBody.Part.createFormData("image", "", requestFile)
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

//    fun onTouchEvent(event: MotionEvent): Boolean {
//        val imm: InputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        val hideSoftInputFromWindow = imm.hideSoftInputFromWindow(view?.windowToken ?:, 0)
//        return true
//    }




}