package umc.standard.todaygym.presentation.calendar


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import retrofit2.Call
import retrofit2.Response
import umc.standard.todaygym.R
import umc.standard.todaygym.data.api.RecordInterface
import umc.standard.todaygym.data.model.Record
import umc.standard.todaygym.data.model.RecordByMonth
import umc.standard.todaygym.data.util.RetrofitClient
import umc.standard.todaygym.databinding.FragmentTabCalBinding
import umc.standard.todaygym.util.HasRecordDayDecorator
import umc.standard.todaygym.util.MyTitleFormatter
import java.text.DecimalFormat

class TabCalFragment: Fragment() {
    private lateinit var binding : FragmentTabCalBinding
    private lateinit var mycalendar : MaterialCalendarView
    private val today = CalendarDay.today()
    private var selectedDate = today
    val JWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE3LCJpc3MiOiJ0ZXN0IiwiaWF0IjoxNjc0OTY5MzY4LCJleHAiOjE3MDY1MDUzNjh9.wME-N31YIrjAtr7Y1usIIQZwG_cHZcmZqB8hBtgq5lk"
    var userRecords = arrayListOf<Record>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabCalBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            // 1. 기록 보기 또는 추가 화면으로 전환
            val bundle = Bundle()
            // 기록 보기 화면으로 전환
            btnShowrecord.setOnClickListener {
                // 선택된 날짜 기록 넘겨주기
                var tmp = findRecord(selectedDate)
                if(tmp != null) {
                    bundle.putSerializable("recordData", tmp)
                    findNavController().navigate(R.id.action_calendarFragment_to_showrecordFragment, bundle)
                } else {
                    Exception("데이터를 찾을 수 없습니다.")
                }
            }


//            // 이 화면으로 돌아왔을 때 수행할 것
//            findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<CalendarDay>("Calendar")?.observe(viewLifecycleOwner) {
//                // 선택된 날짜를 받아온 날짜로
//                selectedDate = it
//            }

            // 2. 서버에서 선택된 월에 대한 기록 데이터 받기
            // 선택된 월에 대한 기록 데이터 받기
            setMonthData(selectedDate.year, selectedDate.month)

            // 3. 캘린더 기능
            // calendarView 객체 생성
            mycalendar = calendarview
            // 캘린더 선택된 날짜(최초에는 오늘)로 선택
            mycalendar.setDateSelected(selectedDate, true)
            // 캘린더 Title 연. 월 형식으로 변경
            mycalendar.setTitleFormatter(MyTitleFormatter())

            // 4. 선택 날짜 변경에 따른 미리보기 화면 구성
            mycalendar.setOnDateChangedListener { widget, date, selected ->
                selectedDate = date
                setPreview()

            }
        }

        // 3. 새로운 월로 변경될 때 서버에서 해당 월 정보 받기
        mycalendar.setOnMonthChangedListener { widget, date ->
            // 오늘이 포함된 달 이전 달일 경우
            if(date.year < today.year ||
                (date.year == today.year && date.month <= today.month)) {
                // 서버에서 해당 월 기록 정보 받기
                setMonthData(date.year, date.month)
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
                    tvRecorddate.text = "${selectedDate.year}/${selectedDate.month}/${selectedDate.day}"
                    tvRecordcontent.text = record.content
                    //
                    if(record.pictures.size > 0) {
                        Glide.with(this@TabCalFragment).load(record.pictures[0]).into(ivRecordpicture)
                    }
                    break
                }
            }
            if (!judge) {
                layoutPreview.visibility = View.INVISIBLE
                tvNorecord.visibility = View.VISIBLE

            }
        }
    }

    // 배열에서 기록 찾아주는 함수
    private fun findRecord(date : CalendarDay) : Record? {
        for(record in userRecords) {
            if(record.date == date) {
                return record
            }
        }
        return null
    }

    // 서버에서 월 단위로 데이터 받아와서 userRecords에 넣어주는 함수
    private fun setMonthData(nowYear: Int, nowMonth: Int) {
        val recordeInterface: RecordInterface? =
            RetrofitClient.getClient()?.create(RecordInterface::class.java)
        val df1 = DecimalFormat("00")
        val call = recordeInterface?.getRecordByMonth(JWT,"${nowYear}-${df1.format(nowMonth)}")
        call?.enqueue(object : retrofit2.Callback<RecordByMonth>{
            override fun onResponse(call: Call<RecordByMonth>, response: Response<RecordByMonth>) {
                if(response.isSuccessful) {
                    val body = response.body()
                    if(body?.isSuccess == true) {
                        val records = body.result
                        var tempRecords = arrayListOf<Record>()
                        mycalendar.removeDecorators()
                        for(record in records) {
                            var year = record.createdTime.substring(0 until 4)
                            var month = record.createdTime.substring(5 until 7)
                            var day = record.createdTime.substring(8 until 10)

                            var photos = arrayListOf<String>()
                            for(photo in record.recordPhotos) {
                                photos.add(photo.img_url)
                            }
                            var tags = arrayListOf<String>()
                            for(tag in record.tags) {
                                tags.add(tag.name)
                            }
                            tempRecords.add(Record(CalendarDay.from(year.toInt(),month.toInt(),day.toInt()), record.content, photos, tags))
                            mycalendar.addDecorator(HasRecordDayDecorator(this@TabCalFragment, CalendarDay.from(year.toInt(),month.toInt(),day.toInt())))
                        }
                        userRecords = tempRecords
                        // 선택된 날짜에 대한 미리보기 화면 구성
                        setPreview()
                    } else {
                        Log.d("test","${response.body()?.code}, ${response.body()?.message}")
                    }
                } else {
                    Log.d("test","Response Not Successful ${response.body()}")
                }
            }
            override fun onFailure(call: Call<RecordByMonth>, t: Throwable) {
                Log.d("test","Error!",t)
            }
        })
    }
}