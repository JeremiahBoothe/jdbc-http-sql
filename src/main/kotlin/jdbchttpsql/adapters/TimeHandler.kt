package jdbchttpsql.adapters

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 * A utility class for handling time-related operations such as parsing
 * target times, calculating the duration until a target time, and updating
 * target times by a specified duration.
 *
 * @property formatter The DateTimeFormatter used for parsing date and time strings.
 */
class TimeHandler(private val formatter: DateTimeFormatter) {
    fun parseTargetTime(targetTimeString: String): ZonedDateTime {
        return ZonedDateTime.parse(targetTimeString, formatter)
    }

    /**
     * Calculates the number of seconds from the current time to a specified target time.
     *
     * @param targetTime The ZonedDateTime instance representing the target time.
     * @return The number of seconds remaining until the target time. If the target time is in the past, the result will be negative.
     */
    fun getSecondsUntilTarget(targetTime: ZonedDateTime): Long {
        val currentTime = ZonedDateTime.now(ZoneId.systemDefault())
        return ChronoUnit.SECONDS.between(currentTime, targetTime)
    }

    /**
     * Updates the target time by adding a specified duration in seconds to the current time.
     *
     * @param currentTime The current ZonedDateTime instance from which the update will be made.
     * @param durationInSeconds The duration in seconds to be added to the current time.
     * @return A ZonedDateTime object representing the updated target time.
     */
    fun updateTargetTime(currentTime: ZonedDateTime, durationInSeconds: Long): ZonedDateTime {
        return currentTime.plusSeconds(durationInSeconds)
    }
}