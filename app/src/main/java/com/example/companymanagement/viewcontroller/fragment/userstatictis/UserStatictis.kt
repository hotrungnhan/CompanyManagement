package com.example.companymanagement.viewcontroller.fragment.userstatictis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.companymanagement.R
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.text.SimpleDateFormat
import java.util.*

class UserStatictis : Fragment() {

    var calendarView = view?.findViewById<CalendarView>(R.id.calendarView)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_statictis, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = view.findViewById<TextView>(R.id.textview_title)
        val c = Calendar.getInstance().time

        val df = SimpleDateFormat("MM-yyyy", Locale.getDefault())
        val formattedDate = df.format(c)
        title.text = "Thống kê Checkin tháng " + formattedDate.toString()
    }
    class DayViewContainer(view: View) : ViewContainer(view) {
        val textView = view.findViewById<TextView>(R.id.calendarDayText)
        // Will be set when this container is bound
        lateinit var day: CalendarDay

        init {
            view.setOnClickListener {
                // Use the CalendarDay associated with this container.
            }
        }
    }
//    calendarView.dayBinder = object: DayBinder<DayViewContainer> {
//        override fun create(view: View) = DayViewContainer(view)
//        override fun bind(container: DayViewContainer, day: CalendarDay) {
//            // Set the calendar day for this container.
//            container.day = day
//            // Set the date text
//            container.textView.text = day.date.dayOfMonth.toString()
//            // Other binding logic
//        }
//    }
}


