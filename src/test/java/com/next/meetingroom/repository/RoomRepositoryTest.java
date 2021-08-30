package com.next.meetingroom.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.next.meetingroom.model.Room;
import static com.next.meetingroom.utils.RoomUtils.createFakeRoomModel;

@DataJpaTest
public class RoomRepositoryTest {
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Test
	void save_ReturnsPersistedRoom_WhenSuccessful() {
		Room expectedRoom = createFakeRoomModel();
		
		Room roomSaved = this.roomRepository.save(expectedRoom);
		
		expectedRoom.setId(roomSaved.getId());
		
		assertNotNull(roomSaved);
		assertEquals(expectedRoom, roomSaved);
	}
	
	@Test
	void save_ReturnsUpdatedRoom_WhenExistingRoomIsEdited() {
		
		Room roomSaved = this.roomRepository.save(createFakeRoomModel());
		
		Long oldId = roomSaved.getId();
		String oldName = roomSaved.getName();
		LocalDate oldDate = roomSaved.getDate();
		LocalTime oldStartHour = roomSaved.getStartHour();
		LocalTime oldEndHour = roomSaved.getEndHour();
		
		Room roomToBeUpdated = roomSaved;
		roomToBeUpdated.setName("Updated Meeting Room");
		roomToBeUpdated.setDate(LocalDate.of(2001, 12, 25));
		roomToBeUpdated.setStartHour(LocalTime.of(12, 40));
		roomToBeUpdated.setEndHour(LocalTime.of(15, 10));
		
		Room updatedRoom = this.roomRepository.save(roomToBeUpdated);
		
		assertEquals(oldId, updatedRoom.getId());
		
		assertNotEquals(oldName, updatedRoom.getName());
		assertEquals("Updated Meeting Room", updatedRoom.getName());
		
		assertNotEquals(oldDate, updatedRoom.getDate());
		assertEquals(LocalDate.of(2001, 12, 25), updatedRoom.getDate());
		
		assertNotEquals(oldStartHour, updatedRoom.getStartHour());
		assertEquals(LocalTime.of(12, 40), updatedRoom.getStartHour());
		
		assertNotEquals(oldEndHour, updatedRoom.getEndHour());
		assertEquals(LocalTime.of(15, 10), updatedRoom.getEndHour());
	}
	
	@Test
	void findById_ReturnsRoom_WhenSuccessful() {
		
		Room roomSaved = this.roomRepository.save(createFakeRoomModel());
		
		Optional<Room> roomReturned = this.roomRepository.findById(roomSaved.getId());
		
		assertEquals(true, roomReturned.isPresent());
		assertEquals(roomSaved, roomReturned.get());
	}
	
	@Test
	void findById_ReturnsOptionalEmpty_WhenNotExistsId() {
		
		Room roomSaved = this.roomRepository.save(createFakeRoomModel());
		
		Optional<Room> roomReturned = this.roomRepository.findById(roomSaved.getId() + 100);
		
		assertEquals(false, roomReturned.isPresent());
	}
	
	@Test
	void findAll_ReturnsListOfRooms_WhenSuccessful() {
		Room firstRoom = createFakeRoomModel();
		Room secondRoom = createFakeRoomModel();
		
		firstRoom.setName("First Meeting Room");
		secondRoom.setName("Second Meeting Room");
		
		firstRoom = roomRepository.save(firstRoom);
		secondRoom = roomRepository.save(secondRoom);
		
		List<Room> rooms = this.roomRepository.findAll();
		
		assertEquals(2, rooms.size());
		assertEquals(firstRoom, rooms.get(0));
		assertEquals(secondRoom, rooms.get(1));
	}
	
	@Test
	void findAll_ReturnsEmptyList_WhenNoRoomExists() {
		
		List<Room> rooms = this.roomRepository.findAll();
		
		assertEquals(true, rooms.isEmpty());
	}
	
	@Test
	void delete_ReturnsVoid_WhenSuccessful() {
		
		Room roomSaved = this.roomRepository.save(createFakeRoomModel());
		
		this.roomRepository.deleteById(roomSaved.getId());
		
		Optional<Room> roomReturned = this.roomRepository.findById(roomSaved.getId());
		
		assertEquals(false, roomReturned.isPresent());
	}

}
