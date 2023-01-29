package umc.standard.todaygym.presentation.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import umc.standard.todaygym.databinding.FragmentAddExBinding

class AddExFragment: Fragment() {
    private lateinit var viewBinding:FragmentAddExBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAddExBinding.inflate(layoutInflater)

        val tabAdapter = TabAdapter(this)
        viewBinding.viewpager.adapter = tabAdapter

        val tabTitleArray = arrayOf(
            "  최신순으로 보기  ",
            "  캘린더로 보기  ",
        )


        TabLayoutMediator(viewBinding.tablayout,viewBinding.viewpager){tab,position ->
            tab.text = tabTitleArray[position]
        }.attach()

        return viewBinding.root
    }
}