import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) { pattern = "%d{yyyy-MM-dd HH:mm:ss.SSS} %level %thread %logger{50} %X{F_THREAD_ID} - %msg%n" }
}

root(INFO, ["CONSOLE"])
logger("com.perfect", DEBUG, ["CONSOLE"], false)