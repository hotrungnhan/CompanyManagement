package com.example.companymanagement.viewcontroller.fragment.salary

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.companymanagement.R
import com.example.companymanagement.model.performance.PerformanceModel
import com.example.companymanagement.model.salary.SalaryModel
import com.example.companymanagement.utils.BarEntryConverter
import com.example.companymanagement.utils.VNeseDateConverter
import com.example.companymanagement.utils.VietnamDong
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
import java.math.BigDecimal
import java.time.Year
import java.time.YearMonth
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)

class SalaryFragment : Fragment() {

    var auth = FirebaseAuth.getInstance()
    val uuid = auth.currentUser?.uid!!.toString()

    //thing needed for salary detail
    lateinit var salarymodel : SalaryViewModel

    //thing needed for the chart
    lateinit var listsalarymodel : SalaryListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        salarymodel = ViewModelProvider(requireActivity()).get(SalaryViewModel::class.java)
        listsalarymodel = ViewModelProvider(requireActivity()).get(SalaryListViewModel::class.java)

        return inflater.inflate(R.layout.fragment_salary, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val salaryChart = view.findViewById<BarChart>(R.id.salary_chart)


        val yearNextButton = view.findViewById<ImageButton>(R.id.salary_button_year_next)
        val yearBackButton = view.findViewById<ImageButton>(R.id.salary_button_year_back)
        val yearDisplay = view.findViewById<TextView>(R.id.salary_display_year)

        yearDisplay.text = Year.now().toString()

        yearNextButton.setOnClickListener {
            val temp = yearDisplay.text.toString().toInt()
            if(temp < Year.now().toString().toInt()) {
                yearDisplay.text = (temp + 1).toString()
                salarymodel.retrieveSalary(uuid, yearDisplay.text.toString(), YearMonth.now().month.toString())
                listsalarymodel.retrieveMonthlyDetailSalaryInAYear(uuid, yearDisplay.text.toString())
            }
        }
        yearBackButton.setOnClickListener {
            val temp = yearDisplay.text.toString().toInt()
            yearDisplay.text = (temp - 1).toString()
            salarymodel.retrieveSalary(uuid, yearDisplay.text.toString(), YearMonth.now().month.toString())
            listsalarymodel.retrieveMonthlyDetailSalaryInAYear(uuid, yearDisplay.text.toString())
        }


        ///// test area
        /*salarymodel.addDummy(generateDummy())
        salarymodel.updateSalary(uuid)
        salarymodel.updateSalaryByMonth(uuid, "JULY")
        salarymodel.updateSalaryByMonth(uuid, "JUNE")
        salarymodel.updateSalaryByMonth(uuid, "JANUARY")
        salarymodel.updateSalary(uuid, "2020", "JUNE")
        salarymodel.updateSalary(uuid, "2020", "AUGUST")
        salarymodel.updateSalary(uuid, "2019", "AUGUST")
        salarymodel.updateSalary(uuid, "2019", "JUNE")*/

        //test with performance
/*        salarymodel.updatePerformance(uuid, generateDummy2())
        salarymodel.updatePerformance(uuid, YearMonth.of(2021, 7),generateDummy2())
        salarymodel.updatePerformance(uuid, YearMonth.of(2021, 8),generateDummy4())
        salarymodel.updatePerformance("emp001", YearMonth.of(2021, 7),generateDummy3())
        salarymodel.updatePerformance("emp002", YearMonth.of(2021, 7),generateDummy4())*/

        //update this month salary from this month performance
/*        salarymodel.updateSalaryFromPerformance(uuid)
        salarymodel.updateSalaryFromPerformance(uuid, "2021", "JULY")
        salarymodel.updateSalaryFromPerformance(uuid, "2021", "AUGUST")
        salarymodel.updateSalaryFromPerformance("emp001", "2021", "JULY")
        salarymodel.updateSalaryFromPerformance("emp002", "2021", "JULY")*/



        val salaryTime = view.findViewById<TextView>(R.id.salary_time)
        val basicSalary = view.findViewById<TextView>(R.id.salary_basic)
        val checkinFaultCharge = view.findViewById<TextView>(R.id.salary_checkin_fault)
        val taskBonus = view.findViewById<TextView>(R.id.salary_task_bonus)
        val overTimeBonus = view.findViewById<TextView>(R.id.salary_overtime_bonus)
        val rankBonus = view.findViewById<TextView>(R.id.salary_rank_bonus)
        val taxDeduction = view.findViewById<TextView>(R.id.salary_tax_deduction)
        val totalBonus = view.findViewById<TextView>(R.id.salary_total_bonus)
        val totalSalary = view.findViewById<TextView>(R.id.salary_total)

        //Show detail salary of a chosen month -- start with current month
        salarymodel.retrieveSalary(uuid,
            YearMonth.now().year.toString(),
            YearMonth.now().month.toString())


        salarymodel.salary.observe(viewLifecycleOwner, Observer {
            salaryTime.text = VNeseDateConverter.vnConvertMonth(it.uid.toString())

            basicSalary.text = VietnamDong(BigDecimal(it.BasicSalary)).toString()
            checkinFaultCharge.text = VietnamDong(BigDecimal(it.CheckinFaultCharge)).toString()
            taskBonus.text = VietnamDong(BigDecimal(it.TaskBonus)).toString()
            overTimeBonus.text = VietnamDong(BigDecimal(it.OverTimeBonus)).toString()
            rankBonus.text = VietnamDong(BigDecimal(it.RankBonus)).toString()
            taxDeduction.text = VietnamDong(BigDecimal(it.TaxDeduction)).toString()
            totalBonus.text = VietnamDong(BigDecimal(it.TotalBonus)).toString()
            totalSalary.text = VietnamDong(BigDecimal(it.TotalSalary)).toString()
        })

        //Show Salary Chart of a chosen Year

        listsalarymodel.retrieveMonthlyDetailSalaryInAYear(uuid, YearMonth.now().year.toString())
        listsalarymodel.detailSalary.observe(viewLifecycleOwner, Observer {
            val list = arrayListOf<BarEntry>()
            for(i in 0 until 12){
                list.add(BarEntryConverter.convert(i, (it[i].TotalSalary / 1000).toString()))
            }
            val nowBarData = BarDataSet(list, "Salary (K VND").apply {
                //valueTextColor = Color.BLACK
                color = ContextCompat.getColor(requireContext(), R.color.purple_200)
                //valueTextSize = 10f
                barBorderWidth = 0.5f
                setDrawValues(false)

            }

            salaryChart.data = BarData(nowBarData)
            //set some attributes for the chart
            salaryChart.animateY(1000) //set the animation for the yAxis (raise up in 0.1 second)
            salaryChart.xAxis.valueFormatter = AxisFormater() //format the xAxis to string with a class
            salaryChart.xAxis.textSize = 12f //set text size of xAxis label
            salaryChart.xAxis.textColor = Color.RED //set text color
            salaryChart.xAxis.labelRotationAngle = 35f //set rotation for xAxis label
            salaryChart.xAxis.position = XAxis.XAxisPosition.BOTTOM //Move xAxis label to bottom
            salaryChart.description = null //turn off the useless label description
            salaryChart.setScaleEnabled(false) //disable scalability
            salaryChart.axisLeft.axisMinimum = 0f
            salaryChart.axisRight.axisMinimum = 0f
            salaryChart.extraBottomOffset = 35f // add space from x-Axis
            salaryChart.invalidate()
        })


        salaryChart.setOnChartValueSelectedListener(object :
            OnChartValueSelectedListener {
            override fun onNothingSelected() {
                //when nothing is selected
                //no idea yet
            }
            //tap on a bar to show detail of that month's salary
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                if (e != null) {
                    salarymodel.retrieveSalary(uuid,
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
    fun generateDummy2() : PerformanceModel{
        var dummy = PerformanceModel()
        dummy.AbsenceA = 3
        dummy.AbsenceNA = 1
        dummy.Late = 2
        dummy.TaskDone = 5
        dummy.MonthOverTime = 20
        dummy.computeBasicPoint()
        return dummy
    }
    fun generateDummy3() : PerformanceModel{
        var dummy = PerformanceModel()
        dummy.AbsenceA = 1
        dummy.AbsenceNA = 0
        dummy.Late = 0
        dummy.TaskDone = 5
        dummy.MonthOverTime = 30
        dummy.computeBasicPoint()
        return dummy
    }
    fun generateDummy4() : PerformanceModel{
        var dummy = PerformanceModel()
        dummy.AbsenceA = 7
        dummy.AbsenceNA = 0
        dummy.Late = 4
        dummy.TaskDone = 15
        dummy.MonthOverTime = 30
        dummy.computeBasicPoint()
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