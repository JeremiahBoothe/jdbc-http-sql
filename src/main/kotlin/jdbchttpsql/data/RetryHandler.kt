package jdbchttpsql.data

import jdbchttpsql.TimeHandler
import kotlinx.coroutines.delay
import java.time.ZonedDateTime

// RetryHandler: Handles retrying the process in case of exceptions.
class RetryHandler(private val logger: org.slf4j.Logger) {
    suspend fun handleRetries(targetTime: ZonedDateTime, timeHandler: TimeHandler) {
        val secondsUntilTarget = timeHandler.getSecondsUntilTarget(targetTime)
        if (secondsUntilTarget > 0) {
            logger.info("Retrying after $secondsUntilTarget seconds")
            delay(secondsUntilTarget * 1000)
        } else {
            logger.info("Retrying after 5000 ms")
            delay(5000) // Default retry delay if target time has passed
        }
    }
}
