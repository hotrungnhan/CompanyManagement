package com.example.companymanagement.utils

import android.util.DisplayMetrics

object UtilsFuntion {

    fun convertDPToPX(dp: Float, metrics: DisplayMetrics): Float {
        return dp * metrics.density;
    }
}