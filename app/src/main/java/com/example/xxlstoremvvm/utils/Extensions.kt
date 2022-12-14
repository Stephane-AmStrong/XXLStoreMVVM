package com.example.xxlstoremvvm.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

fun <A : Activity> Activity.startNewActivity(activity: Class<A>){
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun <A : Activity> Activity.startActivity(activity: Class<A>){
    Intent(this, activity).also {
        startActivity(it)
    }
}

fun Int.toDecimalFormat() : String{
    return NumberFormat.getInstance(Locale.FRENCH).format(this)
//    val _: Locale = getResources().getConfiguration().locale
//    return NumberFormat.getInstance(Locale.getDefault()).format(this)
}

fun Float.toDecimalFormat() : String{
    return NumberFormat.getInstance(Locale.FRENCH).format(this)
//    val _: Locale = getResources().getConfiguration().locale
//    return NumberFormat.getInstance(Locale.getDefault()).format(this)
}

fun Long.toDecimalFormat() : String{
    return NumberFormat.getInstance(Locale.FRENCH).format(this)
//    val _: Locale = getResources().getConfiguration().locale
//    return NumberFormat.getInstance(Locale.getDefault()).format(this)
}

fun String.reverseDecimalFormat(): String {
    return this.replace(DecimalFormatSymbols(Locale.FRENCH).groupingSeparator.toString().toRegex(), "")
}


fun Date.formatToPattern(pattern: String = "d MMM yyyy HH:mm:ss") : String{
    val sdfDate = SimpleDateFormat(pattern, Locale.getDefault())

    return sdfDate.format(this)
//    val _: Locale = getResources().getConfiguration().locale
//    return NumberFormat.getInstance(Locale.getDefault()).format(this)
}



@RequiresApi(Build.VERSION_CODES.O)
fun Long.toLocalDate() : LocalDate {
    return Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toLocalDateTime() : LocalDateTime {
    return Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDateTime()
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toDate() : Date{
    return Date.from(this.atStartOfDay()
        .atZone(ZoneId.systemDefault())
        .toInstant())
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toDate() : Date{
    return Date.from(this
        .atZone(ZoneId.systemDefault())
        .toInstant())
}

@RequiresApi(Build.VERSION_CODES.O)
fun Date.toLocalDate() : LocalDate{
    return this.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}


fun Context.runningOnTablet() : Boolean{
    val manager = applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    return (Objects.requireNonNull(manager).phoneType == TelephonyManager.PHONE_TYPE_NONE)
}


inline fun <T> Iterable<T>.sumByLong(selector: (T) -> Long): Long {
    var sum = 0L
    for (element in this) {
        sum += selector(element)
    }
    return sum
}


inline fun <T> Iterable<T>.sumByFloat(selector: (T) -> Float): Float {
    var sum = 0F
    for (element in this) {
        sum += selector(element)
    }
    return sum
}