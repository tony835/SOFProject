package test_unitaire;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import controllers.App2FormationController;
import domain.Formation;



@ContextConfiguration (locations ="file:src/main/webapp/WEB-INF/servlet-context.xml")
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class App2FormationControllerTest {

	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private App2FormationController controler;
	
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
		mockMvc= null;
	}

	@Test
	public void testAllFormationVisitorControler() throws Exception {
		String exepecteds = "formation/offre";
		Assert.assertEquals(exepecteds, controler.allFormationVisitor().getViewName());
		Assert.assertNotNull(controler.allFormationVisitor().getModel());
	
	}

	@Test
	public void testAllFormationVisitorWeb() throws Exception {
		log("----------------------------------");
		mockMvc.perform(get("/visualisation/formation/offre").accept(
						MediaType.TEXT_PLAIN)).andDo(print())
				.andExpect(status().isOk());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testAllFieldOfType() throws Exception {
		ModelAndView result1,result2;
		
		result1 = controler.allFieldOfType("Masters");
		result2 = controler.allFieldOfType("");
		
		Map<String, String> formation = (Map<String, String>) result2.getModel().get("FormationFieldExist");
		
		Assert.assertNotNull(result1.getModel().get("FormationFieldExist"));
		Assert.assertEquals(0, formation.size());
		
	}
	

	@SuppressWarnings("unchecked")
	@Test
	public void testAllFormOfFieldAndDiploma() {
		ModelAndView result1,result2;
		
		result1 = controler.allFormOfFieldAndDiploma("Masters",App2FormationController.asciiToHex("Domaine Arts, Lettres, Langues"));
		result2 = controler.allFormOfFieldAndDiploma("Masters","");
		
		Collection<Formation> result_expected = (Collection<Formation>) result2.getModel().get("FormationOfDiplomaAndField");
		
		Assert.assertNotNull(result1.getModel().get("FormationOfDiplomaAndField"));
		Assert.assertEquals(0, result_expected.size());
	}
	
	private static void log(String msg){
		System.out.println(msg);
	}

}
