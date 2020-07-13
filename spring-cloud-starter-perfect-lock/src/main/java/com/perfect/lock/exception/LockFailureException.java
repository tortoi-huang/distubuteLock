package com.perfect.lock.exception;

public class LockFailureException extends PerfectLockException {
    public LockFailureException() {
    }

    public LockFailureException(String message) {
        super(message);
    }

    public LockFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public LockFailureException(Throwable cause) {
        super(cause);
    }

    public LockFailureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
