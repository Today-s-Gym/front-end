package umc.standard.todaygym.data.model

import com.prolificinteractive.materialcalendarview.CalendarDay

data class Record(
    var date: CalendarDay,
    var content: String,
    var pictures: ArrayList<String>,
    var tags: ArrayList<String>
) : java.io.Serializable
