package umc.standard.todaygym.presentation.mypage

import android.R
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment

import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.standard.todaygym.MainActivity
import umc.standard.todaygym.data.api.UserInterface
import umc.standard.todaygym.data.model.AddSignResponse
import umc.standard.todaygym.data.model.BoardData
import umc.standard.todaygym.data.model.MyPageResponse
import umc.standard.todaygym.data.util.RetrofitClient
import umc.standard.todaygym.databinding.FragmentMypageBinding
import umc.standard.todaygym.util.APIPreferences
import umc.standard.todaygym.util.SharePreferences
import umc.standard.todaygym.util.SharePreferences.Companion.prefs


class MypageFragment: Fragment() {
    private lateinit var binding : FragmentMypageBinding
    private lateinit var bundle: Bundle
    private lateinit var dialog: Dialog
    var data: MyPageResponse? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(layoutInflater)
        bundle = Bundle()
        getData()
        dialog = Dialog(requireContext())
        dialog.setContentView(umc.standard.todaygym.R.layout.dialog_avatar_level)
        val params : WindowManager.LayoutParams? = dialog.window?.attributes;
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT

        if (params != null) {
            dialog.window?.setLayout(params.width,params.height)
        }

        binding.ivMypageProfile.setOnClickListener{
            showDialog()
        }
        binding.btnProfileToPost.setOnClickListener{
           //??? ????????? ????????????
            navBoard()
        }
        var stirng = prefs.getSharedPreference(
            APIPreferences.SHARED_PREFERENCE_NAME_NICKNAME,"")
        var msg = prefs.getSharedPreference(
            APIPreferences.SHARED_PREFERENCE_NAME_NICKNAME,"")
        binding.tvMypageProfile.text =stirng
        binding.tvProfileIntroduce.text =msg
        return binding.root
    }
    fun getData(){
        val userInterface: UserInterface? = RetrofitClient.getClient()?.create(UserInterface::class.java)
        //?????? ?????????????????? ??????..
        val call = userInterface?.getMyPage()
        call?.enqueue(object: Callback<MyPageResponse> {
            override fun onResponse(
                call: Call<MyPageResponse>,
                response: Response<MyPageResponse>
            ) {
                data = response.body()
//                binding.apply {
//                    tvMypageProfile.text = data?.nickName
//                    Glide.with(this@MypageFragment)
//                        .load(data?.avatarImgeUrl)
//                        .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
//                        .into(binding.ivMypageProfile)
//                    tvProfileType.text = data?.categoryName
//                    tvProfileIntroduce.text = data?.introduce
//                    tvThisMonth.text = data?.userRecordCount?.thisMonthRecord.toString()
//                    tvUpgrade.text = data?.userRecordCount?.remainUpgradeCount.toString()
//                    tvCumulative.text = data?.userRecordCount?.cumulativeCount.toString()
//                    if(data?.locked == true){
//                        ivMypageProfilePrivate.visibility = View.GONE
//                    }else{
//                        ivMypageProfilePrivate.visibility = View.VISIBLE
//                    }
//                }
            }

            override fun onFailure(call: Call<MyPageResponse>, t: Throwable) {
                Log.d("Error","????????????")
            }

        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("view","????????????");
        super.onViewCreated(view, savedInstanceState)

    }
    private fun showDialog() {
        dialog.show() // ??????????????? ?????????

        // ????????? ??????
        var noBtn : Button = dialog.findViewById(umc.standard.todaygym.R.id.bt_avatar_close)
        noBtn.setOnClickListener {
            // ??????????????? ??????
            dialog.dismiss()
        }

    }

    override fun onResume() {
        super.onResume()
//        binding.apply {
//            tvMypageProfile.text = data?.nickName
//            Glide.with(this@MypageFragment)
//                .load(data?.avatarImgeUrl)
//                .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
//                .into(binding.ivMypageProfile)
//            tvProfileType.text = data?.categoryName
//            tvProfileIntroduce.text = data?.introduce
//            tvThisMonth.text = data?.userRecordCount?.thisMonthRecord.toString()
//            tvUpgrade.text = data?.userRecordCount?.remainUpgradeCount.toString()
//            tvCumulative.text = data?.userRecordCount?.cumulativeCount.toString()
//            if(data?.locked == true){
//                ivMypageProfilePrivate.visibility = View.GONE
//            }else{
//                ivMypageProfilePrivate.visibility = View.VISIBLE
//            }
//        }
    }
    private fun navBoard(){
        bundle.putString("category","?????????")
        findNavController().navigate(umc.standard.todaygym.R.id.action_mypageFragment_to_boardFragment)
    }
}