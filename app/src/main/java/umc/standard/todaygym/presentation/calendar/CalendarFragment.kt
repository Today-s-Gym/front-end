package umc.standard.todaygym.presentation.calendar


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.format.TitleFormatter
import umc.standard.todaygym.R
import umc.standard.todaygym.data.mdoel.Record
import umc.standard.todaygym.databinding.FragmentCalendarBinding
import umc.standard.todaygym.util.HasRecordDayDecorator
import umc.standard.todaygym.util.MyTitleFormatter
import java.lang.String.format
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CalendarFragment: Fragment() {
    private lateinit var binding : FragmentCalendarBinding
    private lateinit var mycalendar : MaterialCalendarView
    lateinit var selectedDate: CalendarDay
    var userRecords = arrayListOf<Record>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mycalendar = binding.calendarview
        // 최초 시작 시 오늘 날짜로 지정
        mycalendar.setDateSelected(CalendarDay.today(), true)
        // 캘린더 Title 연. 월 형식으로 변경
        mycalendar.setTitleFormatter(MyTitleFormatter())

        // 오늘날짜로 변수 지정
        selectedDate = CalendarDay.today()
        // 오늘 이후 날짜 지정 불가

        // 기록이 있는 날짜 표시(화면 넘어올 때마다 갱신해줘야함)
        val calList = ArrayList<CalendarDay>()
        calList.add(CalendarDay.from(2022,12,5))
        calList.add(CalendarDay.from(2023, 1, 4))
        calList.add(CalendarDay.from(2023, 1, 5))
        calList.add(CalendarDay.from(2023, 1, 10))
        calList.add(CalendarDay.from(2023, 1, 17))
        for (calDay in calList){
            mycalendar.addDecorator(HasRecordDayDecorator(this, calDay))
        }
        // 사용자 기록 정보 배열에 넣어주기
        userRecords.apply{
            add(Record(CalendarDay.from(2022,12,5), "하핫 1일차다","사진1","태그1"))
            add(Record(CalendarDay.from(2023, 1, 4), "하핫 2일차다","사진2","태그2"))
            add(Record(CalendarDay.from(2023, 1, 5),"하핫 3일차다","사진3","태그3"))
            add(Record(CalendarDay.from(2023, 1, 10), "하핫 4일차다","사진4","태그4"))
            add(Record(CalendarDay.from(2023, 1, 17),"하핫 5일차다","사진5","태그5"))
        }

        // 최초 오늘날짜 기록이 있는 경우 미리보기 구성
        binding.apply {
            var judge = false
            for (record in userRecords) {
                if (selectedDate == record.date) {
                    judge = true
                    layoutPreview.visibility = View.VISIBLE
                    tvNorecord.visibility = View.INVISIBLE
                    btnAddrecord.visibility = View.INVISIBLE
                    tvRecorddate.text = "${selectedDate.year}/${selectedDate.month}/${selectedDate.day}"
                    tvRecordcontent.text = record.content
                    break
                    // 미리보기 사진은 나중에
                }
            }
            if (!judge) {
                layoutPreview.visibility = View.INVISIBLE
                tvNorecord.visibility = View.VISIBLE
                btnAddrecord.visibility = View.VISIBLE
            }
        }

        // 선택된 날짜에 따른 미리보기 구성
        binding.apply {
            mycalendar.setOnDateChangedListener { widget, date, selected ->
                selectedDate = date
                var judge = false
                for (record in userRecords) {
                    if (selectedDate == record.date) {
                        judge = true
                        layoutPreview.visibility = View.VISIBLE
                        tvNorecord.visibility = View.INVISIBLE
                        btnAddrecord.visibility = View.INVISIBLE
                        tvRecorddate.text = "${selectedDate.year}/${selectedDate.month}/${selectedDate.day}"
                        tvRecordcontent.text = record.content
                        break
                        // 미리보기 사진은 나중에
                    }
                }
                if (!judge) {
                    layoutPreview.visibility = View.INVISIBLE
                    tvNorecord.visibility = View.VISIBLE
                    btnAddrecord.visibility = View.VISIBLE
                }
            }
            val bundle = Bundle()
            // 기록 보기 화면으로 전환
            btnShowrecord.setOnClickListener {
                bundle.putInt("recordYear",selectedDate.year)
                bundle.putInt("recordMonth",selectedDate.month)
                bundle.putInt("recordDay",selectedDate.day)
                findNavController().navigate(R.id.showrecordFragment, bundle)
            }
            // 기록 추가 또는 수정 화면으로 전환
            btnAddrecord.setOnClickListener {
                bundle.putInt("recordYear",selectedDate.year)
                bundle.putInt("recordMonth",selectedDate.month)
                bundle.putInt("recordDay",selectedDate.day)
                bundle.putInt("check",0)
                findNavController().navigate(R.id.addrecordFragment, bundle)
            }
        }
    }
}