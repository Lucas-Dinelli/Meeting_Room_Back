package com.next.meetingroom.controller.exceptions;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultError {
	
	private LocalDate currentDate;
	private Integer statusError;
	private String message;

}
