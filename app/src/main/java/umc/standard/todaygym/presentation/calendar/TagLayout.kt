package umc.standard.todaygym.presentation.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginRight


class TagLayout : ViewGroup {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs : AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context,attrs,defStyle)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // PaddingLeft : 자신의 왼쪽 패딩값
        var childLeft = paddingLeft
        // PaddingTop : 자신의 위쪽 패딩값
        var childTop = paddingTop

        var lowestBottom = 0
        var lineHeight = 0
        var myWidth = resolveSize(100, widthMeasureSpec)
        var wantedHeight = 0
        for(i in 0 until childCount ) {
            val child : View = getChildAt(i)
            if(child.visibility == View.GONE) {
                continue
            }
            //getChildMeasureSpec : measureChildren()에서 사용하는 하나의 메소드이다. 이 메소드를 통해 특정 자식 view의 MeasureSpce을 만들수 있다.
            child.measure(getChildMeasureSpec(widthMeasureSpec, 0, child.layoutParams.width),
                getChildMeasureSpec(heightMeasureSpec, 0, child.layoutParams.height))
            ////child.getMeasuredWidth() : 자식 뷰의 넓이를 가져온다. child.measure() 호출 후 호출한다.
            var childWidth = child.measuredWidth

            //child.getMeasuredHeight() : 자식 뷰의 길이를 가져온다. child.measure() 호출 후 호출한다.
            var childHeight = child.measuredHeight

            lineHeight = Math.max(childHeight, lineHeight)

            var lp = MarginLayoutParams(child.layoutParams)
            childLeft += lp.leftMargin
            childTop += lp.topMargin

            // 감싸는 줄라인
            if (childLeft + childWidth + lp.rightMargin + paddingRight > myWidth) {
                childLeft = paddingLeft + lp.leftMargin

                // Spaced below the previous lowest point (이전 최저점 미만 간격)
                childTop = lowestBottom + lp.topMargin
                lineHeight = childHeight
            }
            childLeft += childWidth + lp.rightMargin

            if (childTop + childHeight + lp.bottomMargin > lowestBottom) { // New lowest point
                lowestBottom = childTop + childHeight + lp.bottomMargin
            }
        }
        // childTop + lineHeight + getPaddingBottom()
        wantedHeight += lowestBottom + paddingBottom
        //setMeasuredDimension : onMeasure() 은 리턴값이 없는 대신  setMeasuredDimension() 을 호출합니다. 이 메서드를 호출 안하는 경우 Exception이 발생합니다.
        setMeasuredDimension(myWidth, resolveSize(wantedHeight, heightMeasureSpec))
    }

    override fun onLayout(changed : Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        //getPaddingLeft() : 자신의 왼쪽 패딩값
        var childLeft = paddingLeft
        //getPaddingTop() : 자신의 위쪽 패딩값
        var childTop = paddingTop
        var lowestBottom = 0
        var myWidth = right - left
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility == View.GONE) {
                continue
            }
            //child.getMeasuredWidth() : 자식 뷰의 넓이를 가져온다. child.measure() 호출 후 호출한다.
            var childWidth = child.measuredWidth

            //child.getMeasuredHeight() : 자식 뷰의 길이를 가져온다. child.measure() 호출 후 호출한다.
            var childHeight = child.measuredHeight
            var lp = MarginLayoutParams(child.layoutParams)
            childLeft += lp.leftMargin
            childTop += lp.topMargin
            if (childLeft + childWidth + lp.rightMargin + paddingRight > myWidth) { // Wrap this line
                childLeft = paddingLeft + lp.leftMargin
                childTop = lowestBottom + lp.topMargin // Spaced below the previous lowest point
            }

            //child.layout() : 자식 뷰의 위치를 지정한다.
            child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight)
            childLeft += childWidth + child.marginRight
            if (childTop + childHeight + lp.bottomMargin > lowestBottom) { // New lowest point
                lowestBottom = childTop + childHeight + lp.bottomMargin
            }
        }
    }
}