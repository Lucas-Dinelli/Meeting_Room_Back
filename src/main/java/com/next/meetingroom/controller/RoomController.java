package com.next.meetingroom.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.next.meetingroom.dto.RoomDTO;
import com.next.meetingroom.service.RoomService;

@RestController
@RequestMapping("/v1/room")
@CrossOrigin(origins = "http://localhost:4200")
public class RoomController {
	
	@Autowired
	private RoomService roomService;
	
	@PostMapping
	public ResponseEntity<RoomDTO> create(@Valid @RequestBody RoomDTO roomDTO) {
		roomDTO = roomService.create(roomDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(roomDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(roomDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RoomDTO> findById(@PathVariable Long id) {
		RoomDTO roomDTO = roomService.findById(id);
		return ResponseEntity.ok().body(roomDTO);
	}
	
	@GetMapping
	public ResponseEntity<List<RoomDTO>> findAll() {
		return ResponseEntity.ok().body(roomService.findAll());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<RoomDTO> update(@PathVariable Long id, @Valid @RequestBody RoomDTO roomDTOUpdated){
		RoomDTO roomDTO = roomService.update(id, roomDTOUpdated);
		return ResponseEntity.ok().body(roomDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		roomService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
