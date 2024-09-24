package jdbchttpsql.data

import jdbchttpsql.adapters.TimeHandler
import kotlinx.coroutines.delay
import java.time.ZonedDateTime

/**
 * A handler that manages retry logic based on a target time.
 *
 * @property logger The logger instance used for logging retry attempts.
 */
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
