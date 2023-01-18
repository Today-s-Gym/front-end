package umc.standard.todaygym.presentation.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recordDate = arrayListOf<Int>()
        binding.apply {
            recordDate.add(arguments?.getInt("recordYear") as Int)
            recordDate.add(arguments?.getInt("recordMonth") as Int)
            recordDate.add(arguments?.getInt("recordDay") as Int)

            tvRecorddate.text = "${recordDate[0]}.${recordDate[1]}.${recordDate[2]}"
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnDeleterecord.setOnClickListener {
                // 해당 기록 정보 서버에서 삭제하기
                findNavController().popBackStack()
            }
            btnModifyrecord.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("recordYear",recordDate[0])
                bundle.putInt("recordMonth",recordDate[1])
                bundle.putInt("recordDay",recordDate[2])
                bundle.putInt("check",1)
                findNavController().navigate(R.id.addrecordFragment, bundle)
            }
        }
    }
}