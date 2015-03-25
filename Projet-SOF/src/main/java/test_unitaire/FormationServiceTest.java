package test_unitaire;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import services.FormationService;

@ContextConfiguration (locations ="file:src/main/webapp/WEB-INF/servlet-context.xml")
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class FormationServiceTest {

	@Autowired
	private FormationService service;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log("############ INITIALISE TEST: FormationServiceTest ############\n");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		log("############ FIN  TEST: FormationServiceTest ############\n");
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	
	
	@Test
	public void testFindAll() {
		Assert.assertNotNull(service.findAll());
	}


	@Test
	public void testFindOne() {
		Assert.assertNotNull(service.findOne("FH2WLT"));
	}


	@Test
	public void testFindAllDistinctDiplome() {
		Assert.assertNotNull(service.findAllDistinctDiplome());
	}

	
	@Test
	public void testFindAllDistinctDiplomeVisitor() {
		Assert.assertNotNull(service.findAllDistinctDiplomeVisitor());
	}


	@Test
	public void testFindbyDomaineByDiplome() {
		Assert.assertNotNull(service.findbyDomaineByDiplome("Masters"));
	}

	
	@Test
	public void testFindbyDomaineByDiplomeVisitor() {
		Assert.assertNotNull(service.findbyDomaineByDiplomeVisitor("Masters"));
	}

	

	@Test
	public void testGetShild() {
		Assert.assertEquals(true, service.getShild("FH2WCH").isEmpty());
	}

	
	@Test
	public void testFindDiplomaType() {
		Assert.assertNotNull(service.findDiplomaType());
	}

	
	@Test
	public void testFindFormationFieldList() {
		Assert.assertNotNull(service.findFormationFieldList());
	}



	@Test
	public void testFindVisible() {
		Assert.assertNotNull(service.findVisible());
	}


	private static void log(String msg){
		System.out.println(msg);
	}

}
