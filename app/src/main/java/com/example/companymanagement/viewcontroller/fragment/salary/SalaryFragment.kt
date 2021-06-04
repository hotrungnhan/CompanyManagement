package com.example.companymanagement.viewcontroller.fragment.salary

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anychart.AnyChart
import com.anychart.charts.Cartesian
import com.example.companymanagement.R
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_navigation.*
import kotlinx.android.synthetic.main.fragment_salary.*

class SalaryFragment : Fragment() {
    lateinit var barSalary: ArrayList<BarEntry>
    lateinit var barDataSet: BarDataSet
    lateinit var barData:BarData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_salary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addData()
    }

    fun addData(){

        //set number of column bar and the value(x,y) of each bar
        barSalary = ArrayList()
        barSalary.add(BarEntry(0f,500f))
        barSalary.add(BarEntry(1f,300f))
        barSalary.add(BarEntry(2f,600f))
        barSalary.add(BarEntry(3f,200f))
        barSalary.add(BarEntry(4f,800f))
        barSalary.add(BarEntry(5f,900f))
        barSalary.add(BarEntry(6f,1000f))
        barSalary.add(BarEntry(7f,100f))
        barSalary.add(BarEntry(8f,1100f))
        barSalary.add(BarEntry(9f,1005f))
        barSalary.add(BarEntry(10f,500f))
        barSalary.add(BarEntry(11f,000f))

        //add month to the chart


        barDataSet = BarDataSet(barSalary,"salary (dollar unit)")

        barData = BarData(barDataSet)
        salary_chart.data = barData


        barDataSet.setColors(R.color.purple_200)
        barDataSet.valueTextColor=Color.BLACK
        barDataSet.valueTextSize=10f
        barDataSet.barBorderWidth=0.5f

        salary_chart.animateY(2000)
        salary_chart.xAxis.valueFormatter = axisFormater()
        salary_chart.xAxis.setDrawAxisLine(true)
        salary_chart.xAxis.textSize=12f
        salary_chart.xAxis.textColor = Color.RED
        salary_chart.description=null
    }

}

class axisFormater:ValueFormatter(){
    private val months = arrayOf("Jan", "Feb", "Mar",
    "Apr", "May", "Jun", "July", "Aug", "Sep", "Oct", "Nov", "Dec")
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return months.getOrNull(value.toInt()) ?: value.toString()
    }
}