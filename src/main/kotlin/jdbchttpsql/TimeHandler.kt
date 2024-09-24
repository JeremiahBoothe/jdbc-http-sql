package jdbchttpsql

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

// TimeHandler: Encapsulates time-related logic.
class TimeHandler(private val formatter: DateTimeFormatter) {
    fun parseTargetTime(targetTimeString: String): ZonedDateTime {
        return ZonedDateTime.parse(targetTimeString, formatter)
    }

    fun getSecondsUntilTarget(targetTime: ZonedDateTime): Long {
        val currentTime = ZonedDateTime.now(ZoneId.systemDefault())
        return ChronoUnit.SECONDS.between(currentTime, targetTime)
    }

    fun updateTargetTime(currentTime: ZonedDateTime, durationInSeconds: Long): ZonedDateTime {
        return currentTime.plusSeconds(durationInSeconds)
    }
}