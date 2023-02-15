package umc.standard.todaygym.presentation.community

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.TypefaceCompatUtil.getTempFile
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.standard.todaygym.R
import umc.standard.todaygym.data.api.CommunityService
import umc.standard.todaygym.data.model.RequestAddPost
import umc.standard.todaygym.data.util.RetrofitClient
import umc.standard.todaygym.databinding.FragmentAddPostBinding
import java.io.File
import java.io.IOException

class AddPostFragment: Fragment() {
    private lateinit var viewBinding: FragmentAddPostBinding
    private lateinit var requestAddPost : RequestAddPost
//    val JWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE3LCJpc3MiOiJ0ZXN0IiwiaWF0IjoxNjc0OTY5MzY4LCJleHAiOjE3MDY1MDUzNjh9.wME-N31YIrjAtr7Y1usIIQZwG_cHZcmZqB8hBtgq5lk"
    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            val imageUrl = it.data?.data
            viewBinding.imgCamera.setImageURI(imageUrl)
        }
}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAddPostBinding.inflate(layoutInflater)

        viewBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }


        requestAddPost = RequestAddPost(9,"d", listOf(),"title")

        viewBinding.btnAdd.setOnClickListener {
            requestAddPost.categoryId = 9
            requestAddPost.title = viewBinding.editTitle.text.toString()
            requestAddPost.content = viewBinding.editContent.text.toString()
            requestAddPost.postPhotos  = listOf()
            Log.d("addPost 요청","u----------------------------------------------------------")
            Toast.makeText(context,"화나네 ",Toast.LENGTH_SHORT)
            request(requestAddPost)
        }
        viewBinding.imgCamera.setOnClickListener{
            move_gallery()
        }

        viewBinding.btnExrecord.setOnClickListener {
            findNavController().navigate(R.id.action_addPostFragment_to_addExFragment)
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
//    //엑티비티에서 받아온 데이터를 반환하는 메서드
//    resultLauncher =
//    registerForActivityResult(ActivityResultContracts.StartActivityForResult())
//    {
//        //엑티비티에서 데이터를 갖고왔을 때만 실행
//        if (it.resultCode == RESULT_OK) {
//            //1번 ->  갤러리
//            if (picture_flag == 1) {
//                //갤러리에서 갖고온 이미지가 있는 경우
//                it.data?.data?.let { uri ->
//                    //이미지 uri
//                    val imageUri: Uri? = it.data?.data
//                    //image가 있는 경우에만
//                    if (imageUri != null) {
//                        Glide.with(applicationContext).load(imageUri).override(500, 500)
//                            .into(binding.ivProfileImgRegister)
//                    }
//                }
//            }
//            //2번 ->  카메라
//            else if (picture_flag == 2) {
//                val file = File(fileAbsolutePath)
//                var bitmap: Bitmap? = null
//                //SDK 28버전 미만인 경우 getBitMap 사용
//                if (Build.VERSION.SDK_INT < 28) {
//                    //카메라에서 찍은 사진을 비트맵으로 변환
//                    bitmap = MediaStore.Images.Media
//                        .getBitmap(contentResolver, Uri.fromFile(file))
//                    //이미지뷰에 이미지 로딩
//                    binding.ivProfileImgRegister.setImageBitmap(bitmap)
//                } else {
//                    //SDK 28버전 이상인 경우 setImageBitmap 사용
//                    //카메라에서 찍은 사진을 디코딩
//                    val decode = ImageDecoder.createSource(this.contentResolver,
//                        Uri.fromFile(file.absoluteFile))
//                    //디코딩한  사진을 비트맵으로 변환
//                    bitmap = ImageDecoder.decodeBitmap(decode)
//                    //이미지뷰에 이미지 로딩
//                    binding.ivProfileImgRegister.setImageBitmap(bitmap)
//                    //갤러리에 저장
//                }
//
//                if (bitmap != null) {
//                    saveImageFile(file.name, getExtension(file.name), bitmap)
//                }
//            }
//
//        }
//    }
    fun move_gallery() {
        var photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startForResult.launch(photoPickerIntent)
    }
//    fun move_camera(){
//        var camerPickerIntent = Intent(Intent.ACTION_IMAGE_CAPTURE)
//        camerPickerIntent.resolveActivity(packageManager)?.also {
//                //찍은 사진을 File형식으로 변환
//                val photoFile: File? = try {
//                    createImageFile()
//                } catch (ex: IOException) {
//                    null
//                }
//                //File형식의 Uri를 Content형식의 Uri로 변환
//                photoFile?.also {
//                    val photoURI: Uri = FileProvider.getUriForFile(
//                        this,
//                        "com.example.sharelanguage.fileprovider",
//                        it
//                    )
//                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//                    resultLauncher.launch(takePictureIntent)
//                }
//            }
//    }

}