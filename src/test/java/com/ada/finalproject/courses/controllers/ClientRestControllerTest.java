package com.ada.finalproject.courses.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ada.finalproject.courses.models.Client;
import com.ada.finalproject.courses.services.ClientService;

@ExtendWith(MockitoExtension.class)
class ClientRestControllerTest {

	String VALID_EMAIL = "some@email.com";
	String PASSWORD = "pwd";
	String HASHED_PASSWORD = "$2a$10$RlztxV64cxJwqJEaRdJF..Cq4CvcQiTcMU3DGHVGrlzrTWp95aXDa";
	
	Client validUser;
	Optional<Client> optValidUser;
	
	@Mock
	ClientService clientService;
	
	@InjectMocks
	ClientRestController unitUnderTest;
	
	@BeforeEach
	void setUp() {
		validUser = new Client(null, VALID_EMAIL, PASSWORD, null, null, null, null);
		optValidUser = clientService.findByEmail(VALID_EMAIL);
	}

	@Test
	void register_Account_SuccessfulResponseReceived() {
		when(clientService.save(any(Client.class))).thenReturn(validUser);

		assertAll(
			() -> assertDoesNotThrow(() -> unitUnderTest.throwExceptionIfUserAlreadyExists(optValidUser)),
			() -> assertNotNull(clientService.save(validUser)),
			() -> assertEquals(clientService.save(validUser), validUser)
		);
	}
	
	@Test
	void register_AccountAlreadyExists_ThrowException() {
		when(clientService.save(any(Client.class))).thenReturn(validUser);
					
		assertAll(
			() -> assertNotNull(clientService.save(validUser)),
			() -> assertThrows(Exception.class, () -> 
				unitUnderTest.throwExceptionIfUserAlreadyExists(Optional.of(clientService.save(validUser))))
		);
	}
	
	@Test
	void login_AccountRegistered_SuccessfulResponseReceived() throws Exception {
		Client storedUser = new Client(null, VALID_EMAIL, HASHED_PASSWORD, null, null, null, null);
		
		when(clientService.save(any(Client.class))).thenReturn(validUser);
		when(clientService.isValidPassword(anyString(), any(Client.class))).thenReturn(true);
		
		assertAll(
			() -> assertDoesNotThrow(() -> unitUnderTest.throwExceptionIfUserNotRegistered(Optional.of(clientService.save(validUser)))),
			() -> assertNotNull(clientService.isValidPassword(PASSWORD, storedUser)),
			() -> assertEquals(clientService.save(validUser), validUser)
		);
	}
	
	@Test
	void login_AccountNotRegistered_ThrowException() {
		 assertThrows(Exception.class, () -> unitUnderTest.throwExceptionIfUserNotRegistered(optValidUser));
	}

}
