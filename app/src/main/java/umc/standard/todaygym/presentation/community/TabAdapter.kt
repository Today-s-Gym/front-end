package umc.standard.todaygym.presentation.community

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import umc.standard.todaygym.presentation.calendar.TabCalFragment

class TabAdapter(fragmentActivity: AddExFragment): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int =2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> TabNewFragment()
            1 -> TabCalFragment()
            else -> TabNewFragment()
        }
    }



}