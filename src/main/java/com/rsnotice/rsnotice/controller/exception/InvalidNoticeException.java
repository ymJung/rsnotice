package com.rsnotice.rsnotice.controller.exception;

public class InvalidNoticeException extends  RuntimeException {
    public InvalidNoticeException(String message) {
        super(message);
    }
}
