package umc.standard.todaygym.presentation.mypage

import android.R
import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment

import androidx.navigation.fragment.findNavController
import umc.standard.todaygym.databinding.FragmentMypageBinding


class MypageFragment: Fragment() {
    private lateinit var binding : FragmentMypageBinding
    private lateinit var bundle: Bundle
    private lateinit var dialog: Dialog
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(layoutInflater)
        dialog = Dialog(requireContext())
        dialog.setContentView(umc.standard.todaygym.R.layout.dialog_avatar_level)
        val params : WindowManager.LayoutParams? = dialog.window?.attributes;
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        if (params != null) {
            dialog.window?.setLayout(params.width,params.height)
        }
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        val display = windowManager
//        val size = Point()
//        display.getSize(size
//        dialog.window?.setLayout(291,593)

        binding.ivMypageProfile.setOnClickListener{
            showDialog()
        }
        binding.btnProfileToPost.setOnClickListener{
           //내 게시글 보러가기
            navBoard()
        }

        return binding.root
    }
    private fun showDialog() {
        dialog.show() // 다이얼로그 띄우기

        // 아니오 버튼
        var noBtn : Button = dialog.findViewById(umc.standard.todaygym.R.id.bt_avatar_close)
        noBtn.setOnClickListener {
            // 다이얼로그 닫기
            dialog.dismiss()
        }

    }
    private fun navBoard(){
        bundle.putString("category","주짓수")
        findNavController().navigate(umc.standard.todaygym.R.id.action_mypageFragment_to_boardFragment)
    }
}