package com.next.meetingroom.controller;

import static com.next.meetingroom.utils.RoomUtils.createFakeRoomDTO;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.next.meetingroom.dto.RoomDTO;
import com.next.meetingroom.service.RoomService;

import io.restassured.http.ContentType;

@WebMvcTest
public class RoomControllerTest {
	
	@Autowired
	private RoomController roomController;
	
	@MockBean
	private RoomService roomService;
	
	private final String PATH = "http://localhost:8080/v1/room";
	
	@BeforeEach
	public void setUp() {
		standaloneSetup(this.roomController);
	}
	
	@Test
	void create_ReturnsCreated_WhenSuccessful() {
		RoomDTO expectedRoomDTO = createFakeRoomDTO();
		expectedRoomDTO.setId(1L);
		
		when(roomService.create(any(RoomDTO.class))).thenReturn(expectedRoomDTO);
		
		given().log().all()
			.contentType(ContentType.JSON)
			.body(expectedRoomDTO)
		.when()
			.post(PATH)
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	void findById_ReturnsRoomDTO_WhenSuccessful() {
		RoomDTO expectedRoomDTO = createFakeRoomDTO();
		expectedRoomDTO.setId(2L);
		when(roomService.findById(any(Long.class))).thenReturn(expectedRoomDTO);
		
		given().log().all()
			.contentType(ContentType.JSON)
		.when()
			.get(PATH + "/{id}", expectedRoomDTO.getId())
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("id", is(2))
			.body("name", is(expectedRoomDTO.getName()))
			.body("date[0]", is(expectedRoomDTO.getDate().getYear()))
			.body("date[1]", is(expectedRoomDTO.getDate().getMonthValue()))
			.body("date[2]", is(expectedRoomDTO.getDate().getDayOfMonth()))
			.body("startHour", is(expectedRoomDTO.getStartHour().toString()))
			.body("endHour", is(expectedRoomDTO.getEndHour().toString()));
	}
	
	@Test
	void findAll_ReturnsListOfRoomDTO_WhenSuccessful() {
		RoomDTO firstExpectedRoomDTO = createFakeRoomDTO();
		firstExpectedRoomDTO.setId(3L);
		
		RoomDTO secondExpectedRoomDTO = createFakeRoomDTO();
		secondExpectedRoomDTO.setId(4L);
		secondExpectedRoomDTO.setName("Second Test Meeting Room");
		secondExpectedRoomDTO.setDate(LocalDate.of(2001, 12, 10));
		
		List<RoomDTO>roomsDTO = new ArrayList<>();
		roomsDTO.add(firstExpectedRoomDTO);
		roomsDTO.add(secondExpectedRoomDTO);
		
		when(roomService.findAll()).thenReturn(roomsDTO);
		
		given().log().all()
			.contentType(ContentType.JSON)
		.when()
			.get(PATH)
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("[0].id", is(3))
			.body("[0].name", is(firstExpectedRoomDTO.getName()))
			.body("[0].name", is("Test Meeting Room"))
			.body("[0].date[0]", is(firstExpectedRoomDTO.getDate().getYear()))
			.body("[0].date[1]", is(firstExpectedRoomDTO.getDate().getMonthValue()))
			.body("[0].date[2]", is(firstExpectedRoomDTO.getDate().getDayOfMonth()))
			.body("[0].startHour", is(firstExpectedRoomDTO.getStartHour().toString()))
			.body("[0].endHour", is(firstExpectedRoomDTO.getEndHour().toString()))
			
			.body("[1].id", is(4))
			.body("[1].name", is(secondExpectedRoomDTO.getName()))
			.body("[1].name", is("Second Test Meeting Room"))
			.body("[1].date[0]", is(secondExpectedRoomDTO.getDate().getYear()))
			.body("[1].date[1]", is(secondExpectedRoomDTO.getDate().getMonthValue()))
			.body("[1].date[2]", is(secondExpectedRoomDTO.getDate().getDayOfMonth()))
			.body("[1].startHour", is(secondExpectedRoomDTO.getStartHour().toString()))
			.body("[1].endHour", is(secondExpectedRoomDTO.getEndHour().toString()));
	}
	
	@Test
	void update_ReturnsUpdatedRoomDTO_WhenSuccessful() {
		RoomDTO roomDTOUpdated = createFakeRoomDTO();
		roomDTOUpdated.setId(5L);
		
		when(roomService.update(any(Long.class), any(RoomDTO.class))).thenReturn(roomDTOUpdated);
		
		roomDTOUpdated.setName("Updated Test Meeting Room");
		
		given().log().all()
			.contentType(ContentType.JSON)
			.body(roomDTOUpdated)
		.when()
			.put(PATH + "/{id}", 5)
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("id", is(5))
			.body("name", is(roomDTOUpdated.getName()))
			.body("name", is("Updated Test Meeting Room"))
			.body("date[0]", is(roomDTOUpdated.getDate().getYear()))
			.body("date[1]", is(roomDTOUpdated.getDate().getMonthValue()))
			.body("date[2]", is(roomDTOUpdated.getDate().getDayOfMonth()))
			.body("startHour", is(roomDTOUpdated.getStartHour().toString()))
			.body("endHour", is(roomDTOUpdated.getEndHour().toString()));
	}
	
	@Test
	void delete_ReturnsNoContent_WhenSuccessful() {
		
		doNothing().when(roomService).delete(any(Long.class));
		
		given().log().all()
			.contentType(ContentType.JSON)
		.when()
			.delete(PATH + "/{id}", 1)
		.then()
			.statusCode(HttpStatus.NO_CONTENT.value());
	}

}
