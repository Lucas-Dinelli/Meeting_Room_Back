package com.next.meetingroom.dto;

import static com.next.meetingroom.utils.RoomUtils.createFakeRoomDTO;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoomDTOTest {
	
	private static final String NAME_IS_BLANK = "The name field must be filled";
	
	private static final String NAME_INCORRECT_SIZE = "The name field must be between 2 and 100 characters";
	
	private static final String DATE_IS_NULL = "The date field must be filled";
	
	private static final String START_HOUR_IS_NULL = "The startHour field must be filled";
	
	private static final String END_HOUR_IS_NULL = "The endHour field must be filled";
	
	private Validator validator;
	
	
	@BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
	
	@Test
    void test_NoViolations_WhenSuccessful() {
		RoomDTO roomDTO =  createFakeRoomDTO();

		Set<ConstraintViolation<RoomDTO>> violations = validator.validate(roomDTO);
		
		assertTrue(violations.isEmpty());
    }
	
	@Test
    void test_ExistsViolation_WhenNameIsBlank() {
		RoomDTO roomDTO =  createFakeRoomDTO();
		roomDTO.setName("   ");

		Set<ConstraintViolation<RoomDTO>> violations = validator.validate(roomDTO);
				
		assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(NAME_IS_BLANK)));
    }
	
	@Test
    void test_ExistsViolation_WhenNameIncorrectSize() {
		RoomDTO roomDTO =  createFakeRoomDTO();
		roomDTO.setName("F");

		Set<ConstraintViolation<RoomDTO>> violations = validator.validate(roomDTO);
				
		assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(NAME_INCORRECT_SIZE)));
    }
	
	@Test
    void test_ExistsViolation_WhenDateIsNull() {
		RoomDTO roomDTO =  createFakeRoomDTO();
		roomDTO.setDate(null);

		Set<ConstraintViolation<RoomDTO>> violations = validator.validate(roomDTO);
				
		assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(DATE_IS_NULL)));
    }
	
	@Test
    void test_ExistsViolation_WhenStartHourIsNull() {
		RoomDTO roomDTO =  createFakeRoomDTO();
		roomDTO.setStartHour(null);

		Set<ConstraintViolation<RoomDTO>> violations = validator.validate(roomDTO);
				
		assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(START_HOUR_IS_NULL)));
    }
	
	@Test
    void test_ExistsViolation_WhenEndHourIsNull() {
		RoomDTO roomDTO =  createFakeRoomDTO();
		roomDTO.setEndHour(null);

		Set<ConstraintViolation<RoomDTO>> violations = validator.validate(roomDTO);
				
		assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(END_HOUR_IS_NULL)));
    }

}
