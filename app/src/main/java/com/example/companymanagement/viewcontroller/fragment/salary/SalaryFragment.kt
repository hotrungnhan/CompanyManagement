package com.example.companymanagement.viewcontroller.fragment.salary

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.companymanagement.R
import com.example.companymanagement.model.salary.SalaryModel
import com.example.companymanagement.utils.VNeseDateConverter
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.firebase.auth.FirebaseAuth
import java.time.Year
import java.time.YearMonth
import java.util.*
import kotlin.collections.ArrayList


@RequiresApi(Build.VERSION_CODES.O)

class SalaryFragment : Fragment() {

    var auth = FirebaseAuth.getInstance()
    val uuid = auth.currentUser?.uid!!.toString()

    //thing needed for salary detail
    lateinit var salarymodel : SalaryViewModel

    //thing needed for the chart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        salarymodel = ViewModelProvider(requireActivity()).get(SalaryViewModel::class.java)

        return inflater.inflate(R.layout.fragment_salary, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val salaryChart = view.findViewById<BarChart>(R.id.salary_chart)
        val barSalary: ArrayList<BarEntry> = ArrayList()
        val barData: BarData

        val yearNextButton = view.findViewById<ImageButton>(R.id.salary_button_year_next)
        val yearBackButton = view.findViewById<ImageButton>(R.id.salary_button_year_back)
        val yearDisplay = view.findViewById<TextView>(R.id.salary_display_year)

        yearNextButton.setOnClickListener {
            val temp = yearDisplay.text.toString().toInt()
            if(temp < Year.now().toString().toInt()) {
                yearDisplay.text = (temp + 1).toString()
                salarymodel.updateSalaryByYear(uuid, yearDisplay.text.toString())
            }
        }
        yearBackButton.setOnClickListener {
            val temp = yearDisplay.text.toString().toInt()
            yearDisplay.text = (temp - 1).toString()
            salarymodel.updateSalaryByYear(uuid, yearDisplay.text.toString())
        }

        ///// test area
        salarymodel.addDummy(generateDummy())
        salarymodel.updateSalary(uuid)
        salarymodel.updateSalaryByMonth(uuid, "JULY")
        salarymodel.updateSalary(uuid, "2022", "JUNE")

        salarymodel.retriveSalary(uuid,
            YearMonth.now().year.toString(),
            YearMonth.now().month.toString())
        /////

        val salaryTime = view.findViewById<TextView>(R.id.salary_time)
        val basicSalary = view.findViewById<TextView>(R.id.salary_basic)
        val performanceBonus = view.findViewById<TextView>(R.id.salary_performance_bonus)
        val taskBonus = view.findViewById<TextView>(R.id.salary_task_bonus)
        val overTimeBonus = view.findViewById<TextView>(R.id.salary_overtime_bonus)
        val rankBonus = view.findViewById<TextView>(R.id.salary_rank_bonus)
        val taxDeduction = view.findViewById<TextView>(R.id.salary_tax_deduction)
        val totalBonus = view.findViewById<TextView>(R.id.salary_total_bonus)
        val totalSalary = view.findViewById<TextView>(R.id.salary_total)

        salarymodel.salary.observe(viewLifecycleOwner, Observer {
            salaryTime.text = VNeseDateConverter.vnConvertMonth(it.uid.toString())
            basicSalary.text = it.BasicSalary.toString()
            performanceBonus.text = it.PerformBonus.toString()
            taskBonus.text = it.TaskBonus.toString()
            overTimeBonus.text = it.OverTimeBonus.toString()
            rankBonus.text = it.RankBonus.toString()
            taxDeduction.text = it.TaxDeduction.toString()
            totalBonus.text = it.TotalBonus.toString()
            totalSalary.text = it.TotalSalary.toString()
        })

        //Input bar entry
        //Bar entry requires 2 values xAxis and yAxis
        //2 values must be float
        //xAxis must start from 0
        barSalary.add(BarEntry(0f, 500f))
        barSalary.add(BarEntry(1f, 300f))
        barSalary.add(BarEntry(2f, 600f))
        barSalary.add(BarEntry(3f, 200f))
        barSalary.add(BarEntry(4f, 800f))
        barSalary.add(BarEntry(5f, 900f))
        barSalary.add(BarEntry(6f, 1000f))
        barSalary.add(BarEntry(7f, 100f))
        barSalary.add(BarEntry(8f, 1100f))
        barSalary.add(BarEntry(9f, 1005f))
        barSalary.add(BarEntry(10f, 500f))
        barSalary.add(BarEntry(11f, 700f))

        //set data for the bar with barSalary list data above
        //the label is used to describe the meaning of the bar column
        val barDataSet = BarDataSet(barSalary, "Lương (VND)")
        barData = BarData(barDataSet)
        salaryChart.data = barData

        //set some attributes for each bar
        barDataSet.setColors(R.color.purple_200) //set the color of bars
        barDataSet.valueTextColor = Color.BLACK //set color for the value text (ex: 500f)
        barDataSet.valueTextSize = 10f //set text size for the value text
        barDataSet.barBorderWidth = 0.5f //set border width for each bar

        //set some attributes for the chart
        salaryChart.animateY(2000) //set the animation for the yAxis (raise up in 0.2 second)
        salaryChart.xAxis.valueFormatter = AxisFormater() //format the xAxis to string with a class
        salaryChart.xAxis.textSize = 12f //set text size of xAxis label
        salaryChart.xAxis.textColor = Color.RED //set text color
        salaryChart.xAxis.labelRotationAngle = 35f //set rotation for xAxis label
        salaryChart.xAxis.position = XAxis.XAxisPosition.BOTTOM //Move xAxis label to bottom
        salaryChart.description = null //turn off the useless label description
        salaryChart.setScaleEnabled(false) //disable scalability

        salaryChart.setOnChartValueSelectedListener(object :
            OnChartValueSelectedListener {
            override fun onNothingSelected() {
                //when nothing is selected
                //no idea yet
            }
            //tap on a bar to show detail of that month's salary
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                if (e != null) {
                    salarymodel.retriveSalary(uuid,
                        YearMonth.now().year.toString(),
                        VNeseDateConverter.convertMonthFloatToString(e.x + 1f))
                }
                salaryChart.highlightValue(h)
            }
        })
    }
    //Dummy for test
    fun generateDummy() : SalaryModel{
        var dummy = SalaryModel()
        dummy.BasicSalary = 100
        dummy.OverTimeBonus = 10
        dummy.RankBonus = 50
        dummy.TaskBonus = 10
        dummy.compute(YearMonth.now())
        return dummy
    }


    //This class is used to reformat the xAxis label from float to string
    class AxisFormater : ValueFormatter() {
        private val months = arrayOf("Jan", "Feb", "Mar",
            "Apr", "May", "June", "July", "Aug",
            "Sep", "Oct", "Nov", "Dec")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return months.getOrNull(value.toInt()) ?: value.toString()
        }
    }




}