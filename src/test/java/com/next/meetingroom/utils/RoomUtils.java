package com.next.meetingroom.utils;

import java.time.LocalDate;
import java.time.LocalTime;

import com.next.meetingroom.dto.RoomDTO;
import com.next.meetingroom.model.Room;

public class RoomUtils {
	
	private static final Long ROOM_ID = 1L;
	private static final String NAME = "Test Meeting Room";
	private static final LocalDate DATE = LocalDate.of(2000, 1, 15);
	private static final LocalTime START_HOUR = LocalTime.of(6, 30);
	private static final LocalTime END_HOUR = LocalTime.of(7, 55);
	
	public static RoomDTO createFakeRoomDTO() {
		return RoomDTO.builder()
				.id(ROOM_ID)
				.name(NAME)
				.date(DATE)
				.startHour(START_HOUR)
				.endHour(END_HOUR)
				.build();
	}
	
	public static Room createFakeRoomModel() {
		return Room.builder()
				.id(ROOM_ID)
				.name(NAME)
				.date(DATE)
				.startHour(START_HOUR)
				.endHour(END_HOUR)
				.build();
	}

}
