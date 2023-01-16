package umc.standard.todaygym.presentation.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.format.TitleFormatter
import umc.standard.todaygym.data.mdoel.Record
import umc.standard.todaygym.databinding.FragmentCalendarBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class CalendarFragment: Fragment() {
    private lateinit var binding : FragmentCalendarBinding
    private lateinit var mycalendar : MaterialCalendarView

    var userNickname = "벡스"
    lateinit var recordData: ArrayList<Record>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(layoutInflater)
        return binding.root
        mycalendar = binding.calendarview
        var today = CalendarDay.today()
        binding.apply {
            tvRecorddate.text = recordData[]
        }
        mycalendar.apply {

        }
    }
    // 연. 월로 변환하는 함수 (안됨)
    /*
    inner class MyTitleFormatter : TitleFormatter {
        override fun format(day: CalendarDay?): CharSequence {
            val simpleDateFormat = SimpleDateFormat("yyyy.MM", Locale.KOREA)
            return simpleDateFormat.format(Calendar.getInstance().time)
        }
    }
     */

}