package test_unitaire;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import controllers.LoginControlerSingleton;
import domain.User;


@ContextConfiguration (locations ="file:src/main/webapp/WEB-INF/servlet-context.xml")
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class LoginControlerSingletonTest {

	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private LoginControlerSingleton controler;
	
	private MockMvc mockMvc;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log("############ INITIALISE TEST: LoginControlerSingleton ############\n");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		log("############ FIN  TEST: LoginControlerSingleton ############\n");
	}

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@After
	public void tearDown() throws Exception {
		mockMvc= null;
	}

	@Test
	public void testLogin() throws Exception {
		log("----------------------------------");
		mockMvc.perform(get("/auth/login").accept(
						MediaType.TEXT_PLAIN)).andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void testLoginUserBindingResultErrors() {
		ModelAndView result;
		User u = new User();
		
		BindingResult bindingResult = mock(BindingResult.class);
	    when(bindingResult.hasErrors()).thenReturn(true);
	    
	    result = controler.login(u, bindingResult);

	    assertEquals("authentification/login", result.getViewName());
	    assertEquals("", result.getModel().get("error"));  
	}
	
	@Test
	public void testLoginUserAuthServiceErrors() {
		ModelAndView result;
		User user = new User();
		user.setLogin("MockUser");
		user.setPassword("");
		
		BindingResult bindingResult = mock(BindingResult.class);
	    when(bindingResult.hasErrors()).thenReturn(false);
	    
	    result = controler.login(user, bindingResult);

	    assertEquals("authentification/login", result.getViewName());
	    assertEquals("erreur.login.logInv", result.getModel().get("error"));  
	}
	
	@Test
	public void testLoginUserAuthServiceNoErrors() {
		ModelAndView result;
		User user = new User();
		user.setLogin("toto");
		user.setPassword("toto");
		
		BindingResult bindingResult = mock(BindingResult.class);
	    when(bindingResult.hasErrors()).thenReturn(false);
	    
	    result = controler.login(user, bindingResult);

	    assertEquals("authentification/login", result.getViewName());
	    assertEquals("", result.getModel().get("error"));  
	}

	@Test
	public void testLogout() throws Exception {
		log("----------------------------------");
		mockMvc.perform(get("/auth/logout").accept(
						MediaType.TEXT_PLAIN)).andDo(print())
				.andExpect(status().isMovedTemporarily());
	}
	
	private static void log(String msg){
		System.out.println(msg);
	}

}
