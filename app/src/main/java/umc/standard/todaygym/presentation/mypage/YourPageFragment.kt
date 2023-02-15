package umc.standard.todaygym.presentation.mypage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.standard.todaygym.R
import umc.standard.todaygym.data.api.CommunityService
import umc.standard.todaygym.data.api.UserInterface
import umc.standard.todaygym.data.model.AddChat
import umc.standard.todaygym.data.model.Report
import umc.standard.todaygym.data.model.YourProfile
import umc.standard.todaygym.data.util.RetrofitClient
import umc.standard.todaygym.databinding.DialogBottomYoursBinding
import umc.standard.todaygym.databinding.FragmentMypageBinding

class YourPageFragment: Fragment() {
    private lateinit var viewBinding: FragmentMypageBinding
    var data : YourProfile? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMypageBinding.inflate(layoutInflater)
        viewBinding.imgBack.setOnClickListener {
                        findNavController().popBackStack()
        }

        var userId = arguments?.getInt("userId")
        if (userId != null) {
            yourProfile(userId)

            viewBinding.apply {

                var dialog = context?.let { it1 -> BottomSheetDialog(it1) }
                var bottomSheetView = DialogBottomYoursBinding.inflate(LayoutInflater.from(context))
                dialog?.setContentView(bottomSheetView.root)

                btnSetting.setOnClickListener {
                    dialog?.show()
                    bottomSheetView.groupEdit.visibility = View.GONE
                    bottomSheetView.apply {
                        groupEdit.visibility = View.GONE
                        tvUser.text = "신고하기"
                        tvUser.setOnClickListener {
                            if (userId != null) {
                                reportUser(userId)
                            }
                            dialog?.dismiss()
                        }
                    }
                }
            }
        }



        return viewBinding.root
    }

    private fun yourProfile(userId: Int){
        val userInterface: UserInterface? =
            RetrofitClient.getClient()?.create(UserInterface::class.java)
        val call = userInterface?.yourProfile(userId)
        call?.enqueue(object : Callback<YourProfile> {
            override fun onResponse(call: Call<YourProfile>, response: Response<YourProfile>) {
                if (response.isSuccessful) {
                    data = response.body()

                    viewBinding.apply {
                        var data2 = data?.result
                        btnSetting.setImageResource(R.drawable.more_vert)
                        ivMypageProfilePrivate.visibility = View.GONE
                        //공개 계정
                        Log.d("lock", data2?.locked.toString())
                        if (data2?.locked == false) {
                            Glide.with(this@YourPageFragment).load(data2.avatarImgUrl)
                                .into(ivMypageProfile)
                            tvMypageProfile.text = data2.nickname
                            tvProfileType.text = data2.categoryName
                            tvProfileIntroduce.text = data2.introduce
                            tvThisMonth.text =
                                data2.userRecordCount.thisMonthRecord.toString() + " 회"
                            tvUpgrade.text =
                                data2.userRecordCount.remainUpgradeCount.toString() + " 회"
                            tvCumulative.text =
                                data2.userRecordCount.cumulativeCount.toString() + " 회"
                            btnProfileToPost.text = "캘린더 보러가기"
                        }
                        //비공개 계정
                        else {
                            layoutProfileRecord.visibility = View.GONE
                            btnProfileToPost.visibility = View.GONE
                            tvAccount.visibility = View.VISIBLE
                            imgUnlock.visibility = View.VISIBLE
                        }

                    }
                }
            }
            override fun onFailure(call: Call<YourProfile>, t: Throwable) {

            }
        })
    }

    private fun reportUser(reportId:Int){
        val communityInterface: CommunityService? =
            RetrofitClient.getClient()?.create(CommunityService::class.java)
        val call = communityInterface?.reportUser(reportId)
        call?.enqueue(object : Callback<Report> {
            override fun onResponse(call: Call<Report>, response: Response<Report>) {
                if(response.isSuccessful){
                    var reportUser = response.body()

                }

            }

            override fun onFailure(call: Call<Report>, t: Throwable) {

            }

        })
    }

}