package umc.standard.todaygym.util

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.style.StyleSpan
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import umc.standard.todaygym.R

class HasRecordDayDecorator(context: Fragment?, currentDay: CalendarDay ) : DayViewDecorator {
    private var myDay = currentDay
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day == myDay
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(DotSpan(5F, R.color.calendarDotColor))
    }

}