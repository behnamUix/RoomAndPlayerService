package com.behnamuix.myapplication04.utils


import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

fun dateTimeFormat(unixTimeMillis: Long): String {
    val tehranTimeZone = TimeZone.getTimeZone("Asia/Tehran")
    val formatter = SimpleDateFormat(
        "EEE dd",
    )

    formatter.timeZone = tehranTimeZone
    val date = Date(unixTimeMillis)
    return formatter.format(date)
}
//fun dateTimeFormat(unixTimeMillis: Long): String {
//    // تبدیل میلی‌ثانیه به شیء PersianDate
//    val persianDate = PersianDate(unixTimeMillis)
//
//    // گرفتن نام روز هفته و شماره روز (مثلا: شنبه ۱۲)
//    val dayName = persianDate.dayName()
//    val dayOfMonth = persianDate.dayOfMonth
//
//    return "$dayName $dayOfMonth"
//}