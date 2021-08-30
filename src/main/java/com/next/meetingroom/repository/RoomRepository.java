package com.next.meetingroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.next.meetingroom.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
	
}
