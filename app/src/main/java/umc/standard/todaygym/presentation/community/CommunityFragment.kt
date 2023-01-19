package umc.standard.todaygym.presentation.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import umc.standard.todaygym.R
import umc.standard.todaygym.databinding.FragmentCommunityBinding

class CommunityFragment: Fragment() {
    private lateinit var bindng : FragmentCommunityBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindng = FragmentCommunityBinding.inflate(layoutInflater)

        bindng.imgJiujitsu.setOnClickListener {
            findNavController().navigate(R.id.action_communityFragment_to_boardFragment)
        }

        return bindng.root
    }
}