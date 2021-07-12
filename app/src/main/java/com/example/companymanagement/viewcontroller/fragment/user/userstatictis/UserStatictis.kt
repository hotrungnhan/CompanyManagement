package com.example.companymanagement.viewcontroller.fragment.user.userstatictis

import android.graphics.Color
import android.graphics.Color.toArgb
import android.graphics.Color.valueOf
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.example.companymanagement.R
import com.example.companymanagement.databinding.CalendarDayBinding
import com.example.companymanagement.databinding.CalendarHeaderBinding
import com.example.companymanagement.utils.DateParser.Companion.toLocalDate
import com.example.companymanagement.utils.customize.DotDateView.DateEvent
import com.example.companymanagement.utils.customize.DotDateView.EventCalendarView
import com.example.companymanagement.viewcontroller.fragment.user.CheckinViewModel
import com.google.firebase.auth.FirebaseAuth
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.previous
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.*

class UserStatictis : Fragment() {

    val c = Calendar.getInstance().time

    val month = SimpleDateFormat("MM", Locale.getDefault())
    val year = SimpleDateFormat("yyyy", Locale.getDefault())

    val dayofwork = 25

    //Calendar
    private val monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM")

    val user = FirebaseAuth.getInstance().currentUser
    private var checkingModel: CheckinViewModel = CheckinViewModel()

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
        val work = view.findViewById<TextView>(R.id.datework)
        val late = view.findViewById<TextView>(R.id.datelate)
        val absent = view.findViewById<TextView>(R.id.dateabsent)

        title.text = "Thống kê Checkin tháng " + month.format(c).toString() + " / " + year.format(c)
            .toString()

        //-----------Calendar------------//

        val calendarView =
            view.findViewById<EventCalendarView>(R.id.statistics_calendar_view)

        var current = Date()
        var end = Date(current.year, current.month, 1)
        checkingModel.reTriveCheckinAll(this.user?.uid.toString(), end, current)
            .observe(viewLifecycleOwner) {
                work.text = "${it.size}/$dayofwork"
                var checklist = it.map {
                    Log.d("localDate", it.checked_date.toLocalDate().toString())
                    DateEvent(it.checked_date.toLocalDate(), valueOf(Color.GREEN))
                }
                calendarView.addAllEvent(checklist)
            }
        checkingModel.retriveOntime(this.user?.uid.toString(), end, current)
            .observe(viewLifecycleOwner) {
                work.text = "${it.size}/$dayofwork"
                var checklist = it.map {
                    Log.d("localDate", it.checked_date.toLocalDate().toString())
                    DateEvent(it.checked_date.toLocalDate(), valueOf(Color.YELLOW))
                }
                calendarView.addAllEvent(checklist)
            }
        checkingModel.retriveLate(this.user?.uid.toString(), end, current)
            .observe(viewLifecycleOwner) {
                late.text = "${it.size}"
                var checklist = it.map {

                    DateEvent(it.checked_date.toLocalDate(), valueOf(Color.RED))
                }
                calendarView.addAllEvent(checklist)
            }
    }
}