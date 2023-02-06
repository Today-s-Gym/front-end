//package umc.standard.todaygym.util
//
//import android.Manifest
//import android.app.Activity
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.graphics.Bitmap
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.provider.MediaStore
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.core.content.FileProvider
//import androidx.databinding.DataBindingUtil
//import androidx.fragment.app.Fragment
//import com.bumptech.glide.Glide
//import com.gun0912.tedpermission.PermissionListener
//import com.gun0912.tedpermission.normal.TedPermission
//import okio.IOException
//import umc.standard.todaygym.R
//import umc.standard.todaygym.databinding.FragmentAddPostBinding
//import java.io.File
//import java.io.FileOutputStream
//
//class CameraActivity: AppCompatActivity(), View.OnClickListener {
////카메라 관련 권한을 설정해주는 함수
//// permis_num
//// -1번 -> 갤러리 권한
//// -2번 -> 카메라 권한
//    private lateinit var binding: FragmentAddPostBinding
//    private var picture_flag = 0
//    private var fileAbsolutePath: String? = null
//    onc
//    fun settingPermission(permis_num: Int,applicationContext:Context) {
//        val permis = object : PermissionListener {
//            //어떠한 형식을 상속받는 익명 클래스의 객체를 생성하기 위해 다음과 같이 작성
//            override fun onPermissionGranted() {
//                if (permis_num == 1) {
//                    //갤러리로 이동
//                    move_gallery()
//                } else if (permis_num == 2) {
//                    //카메라로 이동
//                    move_camera()
//                }
//            }
//            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
//                Toast.makeText(applicationContext, "권한 거부", Toast.LENGTH_SHORT).show()
//            }
//        }
//        //1번 -> 갤러리 권한
//        if (permis_num == 1) {
//            checkPer_gallery(permis)
//        }
//        //2번 -> 카메라 권한
//        if (permis_num == 2) {
//            checkPer_camera(permis)
//        }
//}
//    //intent를 이용해서 카메라로 이동하는 함수
//    fun move_camera() {
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
//            takePictureIntent.resolveActivity(packageManager)?.also {
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
//            //2번 -> 카메라
//            picture_flag = 2
//        }
//    }
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
//    //이미지의 확장자를 추출하는 메서드
//    fun getExtension(fileStr: String): String {
//        val fileExtension = fileStr.substring(fileStr.lastIndexOf(".") + 1, fileStr.length);
//        return fileExtension
//    }
//    //갤러리에 찍은 사진을 저장하는 메서드
//    fun saveImageFile(filename: String, mimeType: String, bitmap: Bitmap): Uri? {
//        //이미지 Uri 생성
//        //contentValues는 ContentResolver가 사용하는 데이터 정보이다.
//        var values = ContentValues()
//        //contentValues의 이름, 타입을 정한다.
//        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
//        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            // 파일 저장을 완료하기 전까지 다른 곳에서 해당 데이터를 요청하는 것을 무시
//            values.put(MediaStore.Images.Media.IS_PENDING, 1)
//        }
//
//
//        // MediaStore에 파일 등록
//        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//        try {
//            if (uri != null) {
//                // 파일 디스크립터 획득
//                val descriptor = contentResolver.openFileDescriptor(uri, "w")
//                if (descriptor != null) {
//                    // FileOutputStream으로 비트맵 파일 저장. 숫자는 압축률
//                    val fos = FileOutputStream(descriptor.fileDescriptor)
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
//                    fos.close()
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                        // 데이터 요청 무시 해제
//                        values.clear()
//                        values.put(MediaStore.Images.Media.IS_PENDING, 0)
//                        contentResolver.update(uri, values, null, null)
//                    }
//                }
//            }
//        } catch (e: java.lang.Exception) {
//            Log.e("File", "error=")
//        }
//        return uri
//    }
//    //갤러리 관련 권한 체크
//    fun checkPer_gallery(permis: PermissionListener) {
//        TedPermission.with(applicationContext)
//            .setPermissionListener(permis)
//            .setDeniedMessage("[설정] > [권한] 에서 권한을 허용할 수 있습니다.")
//            .setPermissions(
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE
//            ).check()
//    }
//    //1번 ->  갤러리
//    if (picture_flag == 1) {
//        //갤러리에서 갖고온 이미지가 있는 경우
//        it.data?.data?.let { uri ->
//            //이미지 uri
//            val imageUri: Uri? = it.data?.data
//            //image가 있는 경우에만
//            if (imageUri != null) {
//                Glide.with(applicationContext).load(imageUri).override(500, 500)
//                    .into(binding.ivProfileImgRegister)
//            }
//        }
//    }
//

//
//    //checkPermission() 에서 ActivityCompat.requestPermissions 을 호출한 다음 사용자가 권한 허용여부를 선택하면 해당 메소드로 값이 전달 됩니다.
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            PERM_STORAGE -> {
//                for (grant in grantResults) {
//                    if (grant != PackageManager.PERMISSION_GRANTED) {
//                        Toast.makeText(this, "저장소 권한을 승인하지않으면 앱을 실행할수없습니다", Toast.LENGTH_SHORT)
//                            .show()
//                        finish()
//                        return
//                    }
//                }
//                //카메라 호출 메소드
//                setViews()
//            }
//            PERM_CAMERA -> {
//                for (grant in grantResults) {
//                    if (grant != PackageManager.PERMISSION_GRANTED) {
//                        Toast.makeText(this, "카메라 권한을 승인해야지만 카메라를 사용할 수 있습니다.", Toast.LENGTH_SHORT)
//                            .show()
//                        return
//                    }
//                }
//                openCamera()
//            }
//        }
//    }
//
//    //startActivityForResult 을 사용한 다음 돌아오는 결과값을 해당 메소드로 호출합니다.
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK) {
//            when (requestCode) {
//                FLAG_REQ_CAMERA -> {
//                    if (data?.extras?.get("data") != null) {
//                        //카메라로 방금 촬영한 이미지를 미리 만들어 놓은 이미지뷰로 전달 합니다.
//                        val bitmap = data?.extras?.get("data") as Bitmap
//                        binding.ivPicture.setImageBitmap(bitmap)
//                    }
//                }
//            }
//        }
//    }
//}
//
//
