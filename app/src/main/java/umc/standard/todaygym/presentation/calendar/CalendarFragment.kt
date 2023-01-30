package umc.standard.todaygym.presentation.calendar


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import umc.standard.todaygym.R
import umc.standard.todaygym.data.model.Record
import umc.standard.todaygym.databinding.FragmentCalendarBinding
import umc.standard.todaygym.util.HasRecordDayDecorator
import umc.standard.todaygym.util.MyTitleFormatter
import kotlin.collections.ArrayList

class CalendarFragment: Fragment() {
    private lateinit var binding : FragmentCalendarBinding
    private lateinit var mycalendar : MaterialCalendarView
    lateinit var selectedDate: CalendarDay
    var nowMonth: Int = 0
    var nowYear: Int = 0
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

        // 1. 서버에서 오늘이 포함된 월에 대한 기록 데이터 받기
        // 오늘이 포함된 월에 대한 기록 데이터 받기
        nowMonth = CalendarDay.today().month
        nowYear = CalendarDay.today().year
        userRecords.apply{
            val listPic = arrayListOf<Int>().apply {
                add(1)
                add(2)
            }
            val listTag = arrayListOf<String>().apply {
                add("태그1")
                add("태그2")
            }
            add(Record(CalendarDay.from(2023,1,1),"하핫 1일차다",listPic,listTag))
            add(Record(CalendarDay.from(2023, 1, 4), "하핫 2일차다",listPic,listTag))
            add(Record(CalendarDay.from(2023, 1, 5),"하핫 3일차다",listPic,listTag))
            add(Record(CalendarDay.from(2023, 1, 10), "하핫 4일차다",listPic,listTag))
            add(Record(CalendarDay.from(2023, 1, 17),"하핫 5일차다",listPic,listTag))
        }

        // 2. 캘린더 최초 설정
        // calendarView 객체 생성
        mycalendar = binding.calendarview
        // 최초 시작 시 오늘 날짜로 지정
        mycalendar.setDateSelected(CalendarDay.today(), true)
        // 캘린더 Title 연. 월 형식으로 변경
        mycalendar.setTitleFormatter(MyTitleFormatter())
        // 오늘날짜로 변수 지정
        selectedDate = CalendarDay.today()
        // 오늘 이후 날짜 지정 불가

        // 현재 달 기록 표시
        val calList = ArrayList<CalendarDay>()
        calList.add(CalendarDay.from(2023,1,1))
        calList.add(CalendarDay.from(2023, 1, 4))
        calList.add(CalendarDay.from(2023, 1, 5))
        calList.add(CalendarDay.from(2023, 1, 10))
        calList.add(CalendarDay.from(2023, 1, 17))
        for (calDay in calList){
            mycalendar.addDecorator(HasRecordDayDecorator(this, calDay))
        }
        // 최초 오늘날짜 기록이 있는 경우 미리보기 구성
        binding.apply {
            setPreview()
        }

        // 3. 새로운 월로 변경될 때 서버에서 해당 월 정보 받기
        mycalendar.setOnMonthChangedListener { widget, date ->
            nowYear = date.year
            nowMonth = date.month
            // 서버에서 해당 월 기록 정보 받기

        }

        binding.apply {
            // 4. 선택된 날짜에 따른 미리보기 구성
            mycalendar.setOnDateChangedListener { widget, date, selected ->
                selectedDate = date
                setPreview()
                // 선택한 날짜가 오늘 이후라면 추가 버튼 없애기
                if(selectedDate.isAfter(CalendarDay.today())) {
                    btnAddrecord.visibility = View.INVISIBLE
                }
            }

            // 5. 기록 보기 또는 추가 화면으로 전환
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
    // 미리보기 화면을 구성해주는 함수
    private fun setPreview() {
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
    }
}