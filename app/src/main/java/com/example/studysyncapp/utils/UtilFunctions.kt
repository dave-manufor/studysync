package com.example.studysyncapp.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getSystemService
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun String.toColor(): Color {
    val formatted = this.removePrefix("#")
    // Parse the hex string as an integer and add alpha if needed
    val colorInt = if (formatted.length == 6) {
        // Add full alpha (FF) if only RGB is provided
        ("FF$formatted").toLong(16).toInt()
    } else {
        formatted.toLong(16).toInt()
    }
    return Color(colorInt)
}

fun Color.toHexString(): String {
    return String.format("%08X", this.toArgb())
}

fun String.toPrettyDateTimeFormat(): String{
    // Define the input format, including the time zone
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC") // Input is in UTC
    }

    // Parse the input date string into a Date object
    val date: Date? = try {
        inputFormat.parse(this)
    } catch (e: Exception) {
        null // Handle invalid date strings gracefully
    }

    // Define the output format
    val outputFormat = SimpleDateFormat("h:mm a, EEE d MMM", Locale.getDefault()).apply {
        timeZone = TimeZone.getDefault() // Convert to local time zone
    }

    // Return the formatted string or a fallback if parsing failed
    return date?.let { outputFormat.format(it) } ?: "Invalid date"
}

fun getDateFromUTCString(utcString: String): Date {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("UTC") // Set the timezone to UTC
    return dateFormat.parse(utcString) ?: throw IllegalArgumentException("Invalid date string")
}

fun getTimeFromUTCString(utcString: String): LocalTime {
    // Parse the input string as OffsetTime
    val offsetTime = OffsetTime.parse("$utcString:00", DateTimeFormatter.ofPattern("HH:mm:ssXXX"))

    // Convert OffsetTime to LocalTime in the system's default timezone
    val systemZone = ZoneId.systemDefault()
    val localTime = offsetTime.atDate(LocalDate.now())
        .atZoneSameInstant(systemZone)
        .toLocalTime()

    return localTime
}

fun LocalDateTime.toUTC(): String{

    // Convert LocalDateTime to UTC ZonedDateTime
    val utcZonedDateTime: ZonedDateTime = this.atZone(ZoneId.systemDefault()) // Start in local time zone
        .withZoneSameInstant(ZoneId.of("UTC")) // Convert to UTC

    // Format as a readable string (optional)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z")
    val formattedUTC = utcZonedDateTime.format(formatter)

    return formattedUTC
}

fun LocalTime.toUTC(): String{

// Assume the local time is based on a specific time zone (e.g., system's default offset)
    val localOffset = ZoneOffset.systemDefault().rules.getOffset(this.atDate(java.time.LocalDate.now()))

    // Combine the LocalTime with the local offset to get an OffsetTime
    val offsetTime = OffsetTime.of(this, localOffset)

    // Convert the OffsetTime to UTC
    val utcTime = offsetTime.withOffsetSameInstant(ZoneOffset.UTC)

    // Format the UTC time to a string
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss'Z'")
    return utcTime.format(formatter)
}

fun LocalTime.formatToTimeString(): String {
    val formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)
    return this.format(formatter)
}

fun String.capitalizeWords(): String =
    this.split(" ") // Split the string into words
        .joinToString(" ") { it.lowercase().replaceFirstChar { char -> char.uppercaseChar() } }

fun Date.formatToCalendarDay(): String = SimpleDateFormat("d", Locale.getDefault()).format(this)

fun Int.getDayOfWeek3Letters(): String? = Calendar.getInstance().apply {
    set(Calendar.DAY_OF_WEEK, this@getDayOfWeek3Letters)
}.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())

fun Date.getDayOfWeekString(): String = SimpleDateFormat("EEEE", Locale.getDefault()).format(this)

fun Date.getNextMonth(): Date{
    val localDate = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    val nextMonth = localDate.plusMonths(1) // Add one month
    return Date.from(nextMonth.atStartOfDay(ZoneId.systemDefault()).toInstant())
}

fun Date.getPrevMonth(): Date{
    val localDate = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    val previousMonth = localDate.minusMonths(1) // Subtract one month
    return Date.from(previousMonth.atStartOfDay(ZoneId.systemDefault()).toInstant())
}

fun Date.getDaysInMonth(): List<Date>{
    val localDate = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    val firstDayOfMonth = localDate.withDayOfMonth(1)
    val lastDayOfMonth = localDate.withDayOfMonth(localDate.lengthOfMonth())

    val daysInMonth = mutableListOf<Date>()
    var currentDay = firstDayOfMonth
    while (currentDay <= lastDayOfMonth) {
        val dateEquivalent = Date.from(currentDay.atStartOfDay(ZoneId.systemDefault()).toInstant())
        daysInMonth.add(dateEquivalent)
        currentDay = currentDay.plusDays(1)
    }
    return daysInMonth
}

fun Date.formatToTimeString(): String = SimpleDateFormat("h:mm a", Locale.getDefault()).format(this)

fun Date.formatToMonthString(): String = SimpleDateFormat("MMMM", Locale.getDefault()).format(this)

fun Date.formatToYearString(): String = SimpleDateFormat("yyyy", Locale.getDefault()).format(this)

fun Date.getFullWeeksForMonth(): List<Date> {
    val calendar = Calendar.getInstance()
    calendar.time = this

    // Move to the first day of the given month
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    val firstDayOfMonth = calendar.time

    // Find the first day of the week of the first day of the month
    val firstWeekDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK)
    calendar.add(Calendar.DAY_OF_MONTH, -(firstWeekDayOfMonth - Calendar.SUNDAY))
    val startOfRange = calendar.time

    // Move to the last day of the month
    calendar.time = firstDayOfMonth
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
    val lastDayOfMonth = calendar.time

    // Find the last day of the week of the last day of the month
    val lastWeekDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK)
    calendar.add(Calendar.DAY_OF_MONTH, (Calendar.SATURDAY - lastWeekDayOfMonth))
    val endOfRange = calendar.time

    // Generate the list of dates
    calendar.time = startOfRange
    val dates = mutableListOf<Date>()
    while (!calendar.time.after(endOfRange)) {
        dates.add(calendar.time)
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    }

    return dates
}

fun Date.isInSameMonth(date: Date): Boolean {
    val localDate1 = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    val localDate2 = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

    return localDate1.year == localDate2.year && localDate1.month == localDate2.month
}

fun Date.isSameDay(date: Date): Boolean {
    val localDate1 = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    val localDate2 = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

    return localDate1 == localDate2
}

fun LocalDate.getFormat(format: String): String{
    val formatter = DateTimeFormatter.ofPattern(format)
    return formatter.format(this)
}

fun copyTextThenToast(context: Context, text:String) {
    val clipboardManager =
        context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Classroom Code", text)
    clipboardManager.setPrimaryClip(clip)
    // Only show a toast for Android 12 and lower.
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2)
        Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
}

fun Uri.toByteArray(context: Context): ByteArray? {
    return try {
        val contentResolver = context.contentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(this)
        val byteArrayOutputStream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var bytesRead: Int

        while (inputStream?.read(buffer).also { bytesRead = it ?: -1 } != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead)
        }

        inputStream?.close()
        byteArrayOutputStream.toByteArray()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Uri.getFileNameFromUri(context: Context): String? {
    var fileName: String? = null

    // If the URI scheme is "content"
    if (this.scheme == "content") {
        context.contentResolver.query(this, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1 && cursor.moveToFirst()) {
                fileName = cursor.getString(nameIndex)
            }
        }
    }

    // If the URI scheme is "file"
    if (fileName == null) {
        fileName = this.path?.let { path ->
            val lastSlashIndex = path.lastIndexOf("/")
            if (lastSlashIndex != -1) path.substring(lastSlashIndex + 1) else path
        }
    }

    return fileName
}

