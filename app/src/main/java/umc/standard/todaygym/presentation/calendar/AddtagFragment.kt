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

        binding.apply {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnCompletetag.setOnClickListener {
                findNavController().apply {
                    previousBackStackEntry?.savedStateHandle?.set("tag", tagList)
                    popBackStack()
                }
            }
            btnMaketag.setOnClickListener {

            }
        }
    }
}