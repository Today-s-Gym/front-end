package umc.standard.todaygym.presentation.calendar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import umc.standard.todaygym.R
import umc.standard.todaygym.databinding.FragmentAddrecordBinding
import umc.standard.todaygym.databinding.FragmentCalendarBinding

class AddrecordFragment : Fragment() {
    private lateinit var binding: FragmentAddrecordBinding
    // 수정이면 1 / 추가면 0
    var check : Int = -1
    var tagList = arrayListOf<String>()
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

        val recordDate = arrayListOf<Int>()
        binding.apply {
            recordDate.add(arguments?.getInt("recordYear") as Int)
            recordDate.add(arguments?.getInt("recordMonth") as Int)
            recordDate.add(arguments?.getInt("recordDay") as Int)
            check = arguments?.getInt("check") as Int
            tvRecorddate.text = "${recordDate[0]}.${recordDate[1]}.${recordDate[2]}"
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnAddtag.setOnClickListener {
                val bundle = Bundle()
                // 수정이면 기존 태그값 넘겨주기
                if(check == 1) {

                }
                findNavController().navigate(R.id.addtagFragment, bundle)
            }
        }

        // tag추가 화면에서 돌아왔을 때
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<ArrayList<String>>("tag")?.observe(viewLifecycleOwner){
            tagList = it
            Log.d("test", "${tagList}")
        }
    }
}