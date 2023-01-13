package umc.standard.todaygym.presentation.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import umc.standard.todaygym.databinding.FragmentMypageBinding

class MypageFragment: Fragment() {
    private lateinit var bindng : FragmentMypageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindng = FragmentMypageBinding.inflate(layoutInflater)
        return bindng.root
    }
}