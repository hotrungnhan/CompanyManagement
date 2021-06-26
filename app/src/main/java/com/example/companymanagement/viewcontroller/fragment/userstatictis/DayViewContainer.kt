package com.example.companymanagement.viewcontroller.fragment.userstatictis

import android.view.View
import android.widget.CalendarView
import android.widget.TextView
import com.example.companymanagement.R
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer

class DayViewContainer(view: View) : ViewContainer(view) {
    val textView = view.findViewById<TextView>(R.id.calendarDayText)

//    // With ViewBinding
//    // val textView = CalendarDayLayoutBinding.bind(view).calendarDayText
//    calendarView.dayBinder = object: DayBinder<UserStatictis.DayViewContainer> {
//        override fun create(view: View) = UserStatictis.DayViewContainer(view)
//        override fun bind(container: UserStatictis.DayViewContainer, day: CalendarDay) {
//            // Set the calendar day for this container.
//            container.day = day
//            // Set the date text
//            container.textView.text = day.date.dayOfMonth.toString()
//            // Other binding logic
//        }
//    }

}
