package com.next.meetingroom.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.next.meetingroom.dto.RoomDTO;
import com.next.meetingroom.model.Room;
import com.next.meetingroom.repository.RoomRepository;
import com.next.meetingroom.service.exceptions.ObjectNotFoundException;

import static com.next.meetingroom.utils.RoomUtils.createFakeRoomDTO;
import static com.next.meetingroom.utils.RoomUtils.createFakeRoomModel;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {
	
	@InjectMocks
	private RoomService roomService;
	
	@Mock
	private RoomRepository roomRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	@Test
	void create_ReturnsRoomDTO_WhenSuccessful() {
		RoomDTO roomDTO = createFakeRoomDTO();
		Room expectedRoom = createFakeRoomModel();
        
        when(modelMapper.map(roomDTO, Room.class)).thenReturn(expectedRoom);
        when(roomRepository.save(any(Room.class))).thenReturn(expectedRoom);
        when(modelMapper.map(expectedRoom, RoomDTO.class)).thenReturn(roomDTO);
        
        roomDTO = roomService.create(roomDTO);
        
        assertEquals(1L, expectedRoom.getId());
        assertEquals(expectedRoom.getName(), roomDTO.getName());
        assertEquals(expectedRoom.getDate(), roomDTO.getDate());
        assertEquals(expectedRoom.getStartHour(), roomDTO.getStartHour());
        assertEquals(expectedRoom.getEndHour(), roomDTO.getEndHour());
	}
	
	@Test
	void findById_ReturnsRoomDTO_WhenSuccessful() {
		RoomDTO roomDTO = createFakeRoomDTO();
		Room expectedRoom = createFakeRoomModel();
		
		when(roomRepository.findById(expectedRoom.getId())).thenReturn(Optional.of(expectedRoom));
		when(modelMapper.map(expectedRoom, RoomDTO.class)).thenReturn(roomDTO);
		
		roomDTO = roomService.findById(1L);
		
        assertEquals(expectedRoom.getName(), roomDTO.getName());
        assertEquals(expectedRoom.getDate(), roomDTO.getDate());
        assertEquals(expectedRoom.getStartHour(), roomDTO.getStartHour());
        assertEquals(expectedRoom.getEndHour(), roomDTO.getEndHour());
	}
	
	@Test
	void findById_ThrowsObjectNotFoundException_WhenRoomIsEmpty() {
		
		assertThrows(ObjectNotFoundException.class, () -> roomService.findById(1L));
	}
	
	@Test
	void findAll_ReturnsListOfRoomDTO_WhenSuccessful() {
		List<RoomDTO> roomsDTO = Collections.singletonList(createFakeRoomDTO());
		List<Room> expectedRooms = Collections.singletonList(createFakeRoomModel());
		
		when(roomRepository.findAll()).thenReturn(expectedRooms);
		when(modelMapper.map(expectedRooms.get(0), RoomDTO.class)).thenReturn(roomsDTO.get(0));
		
		roomsDTO = roomService.findAll();
		
		assertEquals(roomsDTO.size(), 1);
		assertEquals(expectedRooms.get(0).getId(), roomsDTO.get(0).getId());
		assertEquals(expectedRooms.get(0).getName(), roomsDTO.get(0).getName());
		assertEquals(expectedRooms.get(0).getDate(), roomsDTO.get(0).getDate());
		assertEquals(expectedRooms.get(0).getStartHour(), roomsDTO.get(0).getStartHour());
		assertEquals(expectedRooms.get(0).getEndHour(), roomsDTO.get(0).getEndHour());
	}
	
	@Test
	void findAll_ReturnsEmptyList_WhenNoRoomExists() {
		List<RoomDTO> roomsDTO;
		List<Room> expectedRooms = Collections.emptyList();
		
		when(roomRepository.findAll()).thenReturn(expectedRooms);
		
		roomsDTO = roomService.findAll();
		
		assertEquals(0, roomsDTO.size());
	}
	
	@Test
	void update_ReturnsUpdatedRoomDTO_WhenSuccessful() {
		RoomDTO roomDTO = createFakeRoomDTO();
		Room expectedRoom = createFakeRoomModel();
		
		roomDTO.setName("Updated Meeting Room");
		expectedRoom.setName("Updated Meeting Room");
		
		when(roomRepository.findById(1L)).thenReturn(Optional.of(expectedRoom));
		when(modelMapper.map(roomDTO, Room.class)).thenReturn(expectedRoom);
		when(roomRepository.save(any(Room.class))).thenReturn(expectedRoom);
		when(modelMapper.map(expectedRoom, RoomDTO.class)).thenReturn(roomDTO);
		
		roomDTO = roomService.update(1L, roomDTO);
		
		assertEquals(1L, expectedRoom.getId());
		assertEquals(expectedRoom.getName(), roomDTO.getName());
		assertEquals("Updated Meeting Room", roomDTO.getName());
		assertEquals(expectedRoom.getDate(), roomDTO.getDate());
		assertEquals(expectedRoom.getStartHour(), roomDTO.getStartHour());
		assertEquals(expectedRoom.getEndHour(), roomDTO.getEndHour());
	}
	
	@Test
	void delete_ReturnsVoid_WhenSuccessful() {
		Room expectedRoom = createFakeRoomModel();
		
		when(roomRepository.findById(1L)).thenReturn(Optional.of(expectedRoom));
		doNothing().when(roomRepository).deleteById(expectedRoom.getId());
		
		roomService.delete(1L);
	}

}
