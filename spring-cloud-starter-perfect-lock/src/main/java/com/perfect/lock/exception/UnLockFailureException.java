package com.perfect.lock.exception;

public class UnLockFailureException extends PerfectLockException {
    public UnLockFailureException() {
    }

    public UnLockFailureException(String message) {
        super(message);
    }

    public UnLockFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnLockFailureException(Throwable cause) {
        super(cause);
    }

    public UnLockFailureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
