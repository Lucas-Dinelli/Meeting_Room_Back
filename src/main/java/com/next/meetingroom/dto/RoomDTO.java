package com.next.meetingroom.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDTO {
	
	private Long id;
	
	@NotBlank(message = "The name field must be filled")
	@Size(min = 2, max = 100, message = "The name field must be between 2 and 100 characters")
	private String name;
	
	@NotNull(message = "The date field must be filled")
	private LocalDate date;
	
	@NotNull(message = "The startHour field must be filled")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalTime startHour;
	
	@NotNull(message = "The endHour field must be filled")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalTime endHour;

}
