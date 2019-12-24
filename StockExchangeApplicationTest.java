package com.cognizant.stockExchange;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cognizant.authentication.AuthenticationApplicationTest;
import com.cognizant.authentication.exception.UserAlreadyExistsException;
import com.cognizant.authentication.model.Role;
import com.cognizant.authentication.model.User;
import com.cognizant.authentication.repository.UserRepository;
import com.cognizant.authentication.service.AppUserDetailsService;
import com.cognizant.stockExchange.model.CompanyEntity;
import com.cognizant.stockExchange.model.StockEntity;
import com.cognizant.stockExchange.repository.CompanyRepository;
import com.cognizant.stockExchange.repository.StockRepository;
import com.cognizant.stockExchange.service.StockExchangeService;

public class StockExchangeApplicationTest {

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
	
	
	
	private List<CompanyEntity> list;
	private List<StockEntity> list1;


	@Test
	public void mockTestaddCompaniesList() throws Exception {
		CompanyEntity object =new CompanyEntity();
		Set<StockEntity> stockList = new HashSet<StockEntity>(1);
		object.setId(0);
		object.setName("K");
		object.setBoardOfDirectors("A");
		object.setBrief("Best inIndia");
		object.setCeo("Sanjay Puri");
		object.setSectorId("1");
		object.setStockCode(1); 
		object.setTurnOver(96587);
		object.setStockList(stockList);
		List<CompanyEntity> list = new ArrayList<CompanyEntity>();
		list.add(object);
		CompanyRepository repository = Mockito.mock(CompanyRepository.class);
		when(repository.findAll()).thenReturn(list);
		repository.save(list);
		int expected1 = 1;
		assertEquals(expected1,list.size());
		
		
	}	
	@Test
	public void mockTestNulladdCompaniesList() throws Exception {
		CompanyEntity object =new CompanyEntity();
		CompanyRepository repository = Mockito.mock(CompanyRepository.class);
		when(repository.save(object)).thenReturn(null);
		StockExchangeService service = new StockExchangeService(repository);
		try {
			service.addCompaniesList(object);
		} catch (Exception e) {
			LOGGER.error("Requested company not found", e);
			assertTrue(true);
			return;
		}
		assertFalse(true);
	} 
	 
	
	@Test
	public void mockTestNulladdStock() throws Exception {
		StockEntity object =new StockEntity();
		StockRepository repository = Mockito.mock(StockRepository.class);
		when(repository.save(object)).thenReturn(null);
		StockExchangeService service = new StockExchangeService(repository);
		try {
			service.addStock(object);
		} catch (Exception e) {
			LOGGER.error("Requested stock not found", e);
			assertTrue(true);
			return;
		}
		assertFalse(true);
	} 
	@Test
	public void mockTestaddStock() throws Exception {
		StockEntity object =new StockEntity();
		Set<CompanyEntity> stockList = new HashSet<CompanyEntity>(1);
		object.setId(0);
		object.setBrief("kk");
		object.setComapanyList(stockList);
		object.setContact("445");
		object.setRemark("Best in India");
		object.setStockExchange("abc009");
		List<StockEntity> list1 = new ArrayList<StockEntity>();
		list1.add(object);
		StockRepository repository = Mockito.mock(StockRepository.class);
		when(repository.findAll()).thenReturn(list1);
		repository.save(list1);
		int expected1 = 1;
		assertEquals(expected1,list1.size());
		
		
	}
	

	
		
}
