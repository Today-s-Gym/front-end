package umc.standard.todaygym.data.mdoel

import com.prolificinteractive.materialcalendarview.CalendarDay

data class Record(
    var date: CalendarDay,
    var content: String,
    var pictures: ArrayList<Int>,
    var tags: ArrayList<String>
)
