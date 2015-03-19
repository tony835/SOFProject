package test_unitaire;


import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.jdom2.JDOMException;
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

import controllers.FormationController;
import domain.Formation;

@ContextConfiguration (locations ="file:src/main/webapp/WEB-INF/servlet-context.xml")
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class FormationControllerTest {

	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private FormationController controler;
	
	private MockMvc mockMvc;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log("############ INITIALISE TEST: FormationController ############\n");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		log("############ FIN  TEST: FormationController ############\n");
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
	public void testAllFormation() throws Exception {
		log("----------------------------------");
		mockMvc.perform(get("/formation/list").accept(
						MediaType.TEXT_PLAIN)).andDo(print())
				.andExpect(status().isOk());
	}


	@Test
	public void testCreate() throws JDOMException, IOException {
		
		ModelAndView result1,result2;
		
		result1 = controler.create("Masters");
		result2 = controler.create(null);
		
		assertNotNull(result1);
		assertNotNull(result2);
		
	}

	@Test
	public void testSave() {
		ModelAndView result1;
		Formation f = new Formation();
		f.setName("TEST");
		f.setVisible(false);
		
		BindingResult bindingResult = mock(BindingResult.class);
	    when(bindingResult.hasErrors()).thenReturn(false);
	
		result1 = controler.save("Test_Objet", f, bindingResult);
		assertNotNull(result1);
		
	}
	
	private static void log(String msg){
		System.out.println(msg);
	}

}
