package umc.standard.todaygym.presentation.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import umc.standard.todaygym.R
import umc.standard.todaygym.databinding.FragmentAddrecordBinding
import umc.standard.todaygym.databinding.FragmentCalendarBinding

class AddrecordFragment : Fragment() {
    private lateinit var binding: FragmentAddrecordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddrecordBinding.inflate(layoutInflater)
        return binding.root

        // 1. 상단바 버튼 및 기록 날짜 기능 구현
        // 2. 사용자 정보 값 대입
        // 3. 사진 업로드 기능 넣기
        // 4. 태그 추가 화면 넘어가기
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener("newRecordDate") { key, bundle ->
            bundle.getString("calToAdd")?.let {value ->
                binding.tvRecorddate.text = value
            }
        }
    }
}