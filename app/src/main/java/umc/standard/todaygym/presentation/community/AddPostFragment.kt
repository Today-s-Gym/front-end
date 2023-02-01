package umc.standard.todaygym.presentation.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import umc.standard.todaygym.R
import umc.standard.todaygym.databinding.FragmentAddPostBinding

class AddPostFragment: Fragment() {
    private lateinit var viewBinding: FragmentAddPostBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAddPostBinding.inflate(layoutInflater)

        viewBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewBinding.btnExrecord.setOnClickListener {
            findNavController().navigate(R.id.action_addPostFragment_to_addExFragment)
        }

        viewBinding.btnAdd.setOnClickListener {
            findNavController().popBackStack()
        }


        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}