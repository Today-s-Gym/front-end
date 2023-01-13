package umc.standard.todaygym.presentation.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import umc.standard.todaygym.databinding.FragmentCalendarBinding

class CalendarFragment: Fragment() {
    private lateinit var bindng : FragmentCalendarBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindng = FragmentCalendarBinding.inflate(layoutInflater)
        return bindng.root
    }
}