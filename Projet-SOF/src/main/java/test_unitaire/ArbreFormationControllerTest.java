package test_unitaire;

import static org.junit.Assert.*;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import controllers.App2FormationController;
import controllers.ArbreFormationController;


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
		mockMvc= null;
	}

	@Test
	public void testAllFormation() {
		//controler.allFormation(code, redirectAttributes)
	}

	@Test
	public void testEdit() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testSave() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGestionFils() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testSuppressionLiens() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGestionFilsEditRang() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testCreate() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testSaveNewObject() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testNewObject() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testSortNLObject() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testProductTypes() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testNlObjectList() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testMObjectList() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAddFils() {
		fail("Not yet implemented"); // TODO
	}
	
	private static void log(String msg){
		System.out.println(msg);
	}

}
