package umc.standard.todaygym.presentation.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import umc.standard.todaygym.R
import umc.standard.todaygym.databinding.FragmentAddrecordBinding
import umc.standard.todaygym.databinding.FragmentAddtagBinding

class AddtagFragment : Fragment() {

    private lateinit var binding: FragmentAddtagBinding
    // 수정이면 1 / 추가면 0
    var check : Int = -1
    var tagList = arrayListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddtagBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        check = arguments?.getInt("check") as Int

        // 수정일 경우 기존 태그 값 받기
        /*
        if(check == 1) {
            tagList = arguments?.getStringArrayList("tagData") as ArrayList<String>
        }
         */
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().apply {
                    previousBackStackEntry?.savedStateHandle?.set("tag", tagList)
                    popBackStack()
                }
            }
            btnCompletetag.setOnClickListener {

            }
            btnMaketag.setOnClickListener {

            }
        }
    }
}