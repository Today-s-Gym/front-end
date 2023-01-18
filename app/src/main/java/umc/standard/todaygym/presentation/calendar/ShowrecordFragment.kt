package umc.standard.todaygym.presentation.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import umc.standard.todaygym.R
import umc.standard.todaygym.databinding.FragmentShowrecordBinding

class ShowrecordFragment : Fragment() {
    private lateinit var binding: FragmentShowrecordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowrecordBinding.inflate(layoutInflater)
        return binding.root
        // 1. viewpager2 연결
        // 2. 상단바 버튼 기능 구현
        // 3. 사용자 정보 및 기록 정보들 값 넣기
    }
}