package umc.standard.todaygym.data.mdoel

import com.prolificinteractive.materialcalendarview.CalendarDay

data class Record(
    val date: CalendarDay,
    var content: String,
    var pictures: String, //ArrayList<String>,
    var tags: String, // ArrayList<String>
)
