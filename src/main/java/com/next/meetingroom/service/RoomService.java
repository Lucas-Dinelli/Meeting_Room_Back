package com.next.meetingroom.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.next.meetingroom.dto.RoomDTO;
import com.next.meetingroom.model.Room;
import com.next.meetingroom.repository.RoomRepository;
import com.next.meetingroom.service.exceptions.ObjectNotFoundException;
import com.next.meetingroom.service.exceptions.StartHourNoLessThanEndHourException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RoomService {
	
	private RoomRepository roomRepository;
	
	private ModelMapper modelMapper;
	
	public RoomDTO create(RoomDTO roomDTO) {
		Room room = modelMapper.map(roomDTO, Room.class);
		this.checkHours(room.getStartHour(), room.getEndHour());
		roomDTO = modelMapper.map(roomRepository.save(room), RoomDTO.class);
		return roomDTO;
	}
	
	public RoomDTO findById(Long id) {
		Optional<Room> objRoom = roomRepository.findById(id);
		return modelMapper.map(objRoom.orElseThrow(() -> new ObjectNotFoundException("Meeting room not found!")), RoomDTO.class);
	}
	
	public List<RoomDTO> findAll(){
		List<Room> rooms = roomRepository.findAll();
		return rooms.stream().map(room -> modelMapper.map(room, RoomDTO.class)).collect(Collectors.toList());
	}
	
	public RoomDTO update(Long id, RoomDTO roomDTOUpdated) {
		findById(id);
		Room roomToUpdate = modelMapper.map(roomDTOUpdated, Room.class);
		roomToUpdate.setId(id);
		this.checkHours(roomToUpdate.getStartHour(), roomToUpdate.getEndHour());
		Room roomUpdated = roomRepository.save(roomToUpdate);
		return modelMapper.map(roomUpdated, RoomDTO.class);
	}
	
	public void delete(Long id) {
		findById(id);
		roomRepository.deleteById(id);
	}
	
	private void checkHours(LocalTime startHour, LocalTime endHour) {
		int valueOfComparison = startHour.compareTo(endHour);
		if(valueOfComparison > 0 || valueOfComparison == 0) {
			throw new StartHourNoLessThanEndHourException("The start hour must be less than end hour");
		}
	}

}
