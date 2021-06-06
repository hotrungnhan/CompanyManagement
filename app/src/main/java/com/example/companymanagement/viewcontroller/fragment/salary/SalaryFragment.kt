package com.example.companymanagement.viewcontroller.fragment.salary

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.companymanagement.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.android.synthetic.main.fragment_salary.*

class SalaryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_salary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val salaryChart = view.findViewById<BarChart>(R.id.salary_chart)
        val barSalary: ArrayList<BarEntry> = ArrayList()
        val barData: BarData

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
        val barDataSet = BarDataSet(barSalary, "salary (dollar unit)")
        barData = BarData(barDataSet)
        salary_chart.data = barData

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