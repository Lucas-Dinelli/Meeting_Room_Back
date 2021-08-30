package com.next.meetingroom.service.exceptions;

public class StartHourNoLessThanEndHourException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public StartHourNoLessThanEndHourException(String message, Throwable cause) {
		super(message, cause);
	}

	public StartHourNoLessThanEndHourException(String message) {
		super(message);
	}

}
