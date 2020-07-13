package com.perfect.lock.exception;

public class PerfectLockException extends RuntimeException {
    public PerfectLockException() {
    }

    public PerfectLockException(String message) {
        super(message);
    }

    public PerfectLockException(String message, Throwable cause) {
        super(message, cause);
    }

    public PerfectLockException(Throwable cause) {
        super(cause);
    }

    public PerfectLockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
