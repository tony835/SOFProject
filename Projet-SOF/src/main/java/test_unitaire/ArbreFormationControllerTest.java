package test_unitaire;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Locale;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import controllers.ArbreFormationController;
import domain.Formation;


@ContextConfiguration (locations ="file:src/main/webapp/WEB-INF/servlet-context.xml")
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ArbreFormationControllerTest {

	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private ArbreFormationController controler;
	
	private MockMvc mockMvc;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log("############ INITIALISE TEST: App2FormationController ############\n");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		log("############ FIN  TEST: App2FormationController ############\n");
	}

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@After
	public void tearDown() throws Exception {
		controler.newUser().flush();
		mockMvc= null;
	}

	@Test
	public void testAllFormationEmptyCode() throws Exception {
		mockMvc.perform(get("/arbreFormation/list").param("code", "")).andDo(print()).andExpect(
				MockMvcResultMatchers.flash().attribute("error", "arbreformation.ArgInvalide"));
		
	}
	
	@Test
	public void testAllFormationCodeInValide() throws Exception {
		mockMvc.perform(get("/arbreFormation/list").param("code", "DONTEXSIT")).andDo(print()).andExpect(
				MockMvcResultMatchers.flash().attribute("error", "arbreformation.unknow"));
		
	}
	
	@Test
	public void testAllFormationCodeValideNoResponsable() throws Exception {
		mockMvc.perform(get("/arbreFormation/list").param("code", "ME5SIN")).andDo(print()).andExpect(
				MockMvcResultMatchers.flash().attribute("error", "ArbreFormation.noResponsable"));
		
	}
	
	@Test
	public void testAllFormation() throws Exception {
		ModelAndView result;
		controler.newUser().setLogin("IN18");
		controler.newUser().setPassword("IN180");
		controler.newUser().setConnected(true);
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		result = controler.allFormation("ME5SIN", redirectAttributes,new Locale("english"));
		
		assertNotNull(result.getModel());
		
	}

	
	@Test
	public void testEdit() {
		ModelAndView result;
		result = controler.edit();
		assertNotNull(result.getModel().get("formation"));
	}

	
	@Test
	public void testSave() {
		ModelAndView result;
		
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		
		Formation formation = mock(Formation.class);
		result = controler.save(formation,bindingResult);
		assertEquals("arbreFormation/edit", result.getViewName());
		
	}

	
	private static void log(String msg){
		System.out.println(msg);
	}

}
