package com.example.companymanagement.utils

sealed class Result {
    data class Success<out T : Any>(val data: T) : Result() // Status success and data of the result
    data class Error(val exception: Exception) : Result() // Status Error an error message
    data class Canceled(val exception: Exception?) : Result() // Status Canceled

    // string method to display a result for debugging
    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            is Canceled -> "Canceled[exception=$exception]"
        }
    }
}



