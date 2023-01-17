package umc.standard.todaygym.presentation.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import umc.standard.todaygym.databinding.FragmentAddExBinding

class AddExFragment: Fragment() {
    private lateinit var viewBinding:FragmentAddExBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAddExBinding.inflate(layoutInflater)
        return viewBinding.root
    }
}