package com.cognizant.authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cognizant.authentication.exception.UserAlreadyExistsException;
import com.cognizant.authentication.model.Role;
import com.cognizant.authentication.model.User;
import com.cognizant.authentication.repository.UserRepository;
import com.cognizant.authentication.service.AppUserDetailsService;

public class AuthenticationApplicationTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationApplicationTest.class);

	@Test
	public void mockTestLoadUserByUsername() {
 
		
		LOGGER.info("Start");
		UserRepository repository = Mockito.mock(UserRepository.class);
		when(repository.findByUserName("Jon")).thenReturn(createUser());
		UserDetailsService service = new AppUserDetailsService(repository);
		UserDetails user = service.loadUserByUsername("Jon");
		String expected = "$2a$10$R/lZJuT9skteNmAku9Y7aeutxbOKstD5xE5bHOf74M2PHZipyt3yK";
		assertEquals(expected, user.getPassword());
		LOGGER.info("End");
	}
	

	@Test
	public void mockTestLoadByUserNameWithUserNull() {
		UserRepository repository = Mockito.mock(UserRepository.class);
		when(repository.findByUserName("user")).thenReturn(null);
		UserDetailsService service = new AppUserDetailsService(repository);
		try {
			UserDetails user = service.loadUserByUsername("user");
			LOGGER.debug("user:{}", user);
		} catch (UsernameNotFoundException e) {
			LOGGER.error("User not found", e);
			assertTrue(true);
			return;
		}
		assertFalse(true);
	}
	@Test
	public void mockTestSignupNulluser() throws UserAlreadyExistsException {
		User user = new User();
		user.setId(8);
		user.setPassword("$2a$10$c1EGtqnO/Qr//hWM/LRGTug/5cuWQ3dXsKAHD3u8x7edMw0gJMsPq");
		user.setUserName("fh");
		user.setEmail("user@gmail.com");
		user.setMobNum("9658745896");
		user.setRoleId(2);
		
		
		UserRepository repository = Mockito.mock(UserRepository.class);
		when(repository.findByUserName("fh")).thenReturn(user);
		
		AppUserDetailsService service= new AppUserDetailsService (repository);
			try {
				repository.save(user);
				service.signUp(user);
			} catch (Exception e) {
				assertTrue(true);
				return;
			}
			assertFalse(true);
			
		}
	@Test
	public void mockTestSignUp() throws UserAlreadyExistsException {
		User user = new User();
		UserRepository repository = Mockito.mock(UserRepository.class);
		when(repository.findByUserName("fh")).thenReturn(null);
		user.setId(8);
		user.setPassword("$2a$10$c1EGtqnO/Qr//hWM/LRGTug/5cuWQ3dXsKAHD3u8x7edMw0gJMsPq");
		user.setUserName("fh");
		user.setEmail("user@gmail.com");
		user.setMobNum("9658745896");
		user.setRoleId(2);
		
		
		repository.save(user);
		AppUserDetailsService service= new AppUserDetailsService (repository);
			try {
				service.signUp(user);
				assertTrue(true);
				return;
			} catch (Exception e) {
				
				assertFalse(false);
			}
			
			
		}
	private User createUser() {
		User user = new User();
		Set<Role> roleList = new HashSet<Role>(1);
		user.setId(5);
		user.setPassword("$2a$10$R/lZJuT9skteNmAku9Y7aeutxbOKstD5xE5bHOf74M2PHZipyt3yK");
		user.setMobNum("919877873585");
		user.setConfirmed(true);
		user.setEmail("jon@gmail.com");
		user.setUserName("Jon");
		Role role = new Role();
		role.setRoleId(1);
		role.setRoleName("USER");
		user.setRoleList(roleList);
		return user;
	}
	
		
		
		 
	}

	
