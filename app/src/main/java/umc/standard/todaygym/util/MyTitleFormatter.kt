package umc.standard.todaygym.util

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.format.TitleFormatter

// 캘린더 상단 '월 년' 포맷으로 수정하는 클래스
class MyTitleFormatter : TitleFormatter {
    override fun format(day: CalendarDay?): CharSequence {
        val inputText = day?.date
        var calendarHeaderElement = inputText.toString().split("-")
        val calendarHeaderBuilder = java.lang.StringBuilder()
        calendarHeaderBuilder.append(calendarHeaderElement[0])
            .append(". ")
            .append(calendarHeaderElement[1])
        return calendarHeaderBuilder.toString()
    }
}