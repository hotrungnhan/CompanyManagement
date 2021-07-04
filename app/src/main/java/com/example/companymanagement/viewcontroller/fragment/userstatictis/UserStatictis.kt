package com.example.companymanagement.viewcontroller.fragment.userstatictis

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
import androidx.lifecycle.ViewModelProvider
import com.example.companymanagement.R
import com.example.companymanagement.databinding.CalendarDayBinding
import com.example.companymanagement.databinding.CalendarHeaderBinding
import com.example.companymanagement.model.checkin.CheckinModel
import com.example.companymanagement.viewcontroller.fragment.user.CheckinViewModel
import com.example.companymanagement.viewcontroller.fragment.user.PerformanceViewModel
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
    private var selectedDate: LocalDate? = null


    val user = FirebaseAuth.getInstance().currentUser
    private lateinit var performancemodel: PerformanceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performancemodel =
            ViewModelProvider(this.requireActivity()).get(PerformanceViewModel::class.java)
        performancemodel.retrivePerformance(user?.uid!!, month.format(c), year.format(c))
    }

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
        val dateviolate = view.findViewById<TextView>(R.id.dateviolate)

        title.text = "Thống kê Checkin tháng " + month.format(c).toString() + " / " + year.format(c)
            .toString()

        performancemodel.per.observe(viewLifecycleOwner) {
            work.text =
                (dayofwork - (it.Late + it.AbsenceA + it.AbsenceNA)).toString() + "/" + dayofwork.toString()
            absent.text = (it.AbsenceA + it.AbsenceNA).toString()
            late.text = it.Late.toString()
            dateviolate.text = ((it.AbsenceA + it.AbsenceNA + it.Late)).toString()
        }

        //-----------Calendar------------//

        var checkinmodel = CheckinViewModel()
        lateinit var listLate: Map<String?, List<CheckinModel>>
        lateinit var listWork: Map<String?, List<CheckinModel>>
        val lifeCycle = this.viewLifecycleOwner

        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        val nextMonthImage = view.findViewById<ImageView>(R.id.nextMonthImage)
        val previousMonthImage = view.findViewById<ImageView>(R.id.previousMonthImage)
        val monthYearText = view.findViewById<TextView>(R.id.monthYearText)


        val currentMonth = YearMonth.now()
        val daysOfWeek = WeekFields.of(Locale.getDefault())
        calendarView.setup(currentMonth.minusMonths(50),
            currentMonth.plusMonths(50),
            daysOfWeek.firstDayOfWeek)
        calendarView.scrollToMonth(currentMonth)

        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay
            var binding = CalendarDayBinding.bind(view)

            init {
                view.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {
                        if (selectedDate != day.date) {
                            val oldDate = selectedDate
                            selectedDate = day.date
                            calendarView.notifyDateChanged(day.date)
                            oldDate?.let { calendarView.notifyDateChanged(it) }
                        }
                    }
                }
            }
        }
        calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.binding.dayText
                val layout = container.binding.dayLayout
                textView.text = day.date.dayOfMonth.toString()

                val absentStatus = container.binding.absentStatus

                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.setTextColor(resources.getColor(R.color.black))
                    layout.setBackgroundResource(if (selectedDate == day.date) R.drawable.selected_bg else 0)


                    var df = SimpleDateFormat("yyyy-MM-dd")
                    checkinmodel.listLate.observe(lifeCycle) { it ->
                        listLate = it.groupBy { df.format(it.checked_date?.time) }
                        Log.d("DataIt", listLate.toString())

                        val checkin = listLate[day.date.toString()]
                        if (checkin != null) {
                            absentStatus.setBackgroundColor(resources.getColor(R.color.yellow))
                        }
                    }
                    checkinmodel.listWork.observe(lifeCycle) { it ->
                        listWork = it.groupBy { df.format(it.checked_date?.time) }
                        val checkin = listWork[day.date.toString()]
                        if (checkin != null) {
                            absentStatus.setBackgroundColor(resources.getColor(R.color.green))
                        }
                    }
                } else {
                    textView.setTextColor(resources.getColor(R.color.text_grey_light))
                }
            }
        }


        class MonthViewContainer(view: View) : ViewContainer(view) {
            val legendLayout = CalendarHeaderBinding.bind(view).legendLayout.root
        }
        calendarView.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                // Setup each header day text if we have not done that already.
                if (container.legendLayout.tag == null) {
                    container.legendLayout.tag = month.yearMonth
                    container.legendLayout.children.map { it as TextView }
                        .forEachIndexed { index, tv ->
                            tv.text = DayOfWeek.of(index + 1)
                                .getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                                .toUpperCase(Locale.ENGLISH)
                            tv.setTextColor(resources.getColor(R.color.text_color))
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                        }
                    month.yearMonth
                }
            }
        }

        calendarView.monthScrollListener = { month ->
            val title = "${monthTitleFormatter.format(month.yearMonth)} ${month.yearMonth.year}"
            monthYearText.text = title

            selectedDate?.let {
                // Clear selection if we scroll to a new month.
                selectedDate = null
                calendarView.notifyDateChanged(it)
            }
        }

        nextMonthImage.setOnClickListener {
            calendarView.findFirstVisibleMonth()?.let {
                calendarView.smoothScrollToMonth(it.yearMonth.next)
            }
        }

        previousMonthImage.setOnClickListener {
            calendarView.findFirstVisibleMonth()?.let {
                calendarView.smoothScrollToMonth(it.yearMonth.previous)
            }
        }
    }
}