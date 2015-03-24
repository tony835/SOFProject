package test_unitaire;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import services.AuthentificationServiceDB;
import domain.User;


@ContextConfiguration (locations ="file:src/main/webapp/WEB-INF/servlet-context.xml")
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class AuthentificationServiceDBTest {
	
	@Autowired
	private AuthentificationServiceDB service;
	
	private User user;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log("############ INITIALISE TEST: AuthentificationServiceDBTest ############\n");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		log("############ FIN  TEST: AuthentificationServiceDBTest ############\n");
	}

	@Before
	public void setUp() throws Exception {
		user = new User();
	}

	@After
	public void tearDown() throws Exception {
		user = null;
	}

	@Test
	public void testLoginSucced() {
		user.setLogin("IN10388");
		user.setPassword("IN103880");
		
		Assert.assertTrue(service.login(user));
	}
	
	@Test
	public void testLoginFailed() {
		user.setLogin("toto");
		user.setPassword("Faild");
		
		Assert.assertEquals(false, service.login(user));
	}
	
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testLoginInvalidDataAccessApiUsageException() {
		user.setLogin(null);
		user.setPassword(null);
		service.login(user);
	}
	
	@Test
	public void testEmptyLoginNullPassword() {
		user.setLogin("");
		user.setPassword(null);
		
		Assert.assertEquals(false, service.login(user));
	}
	
	@Test
	public void testLoginNullPassword() {
		user.setLogin("toto");
		user.setPassword(null);
		
		Assert.assertEquals(false, service.login(user));
	}
	


	@Test
	public void testFlush() {
		user = service.newUser();
		user.setLogin("IN10082");
		user.setPassword("IN100820");
		
		service.login(user);
		Assert.assertTrue(service.login(user));
		
		service.flush();
		Assert.assertEquals(false, user.isConnected());
		
		
	}
	
	private static void log(String msg){
		System.out.println(msg);
	}

}
