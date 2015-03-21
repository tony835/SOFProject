package test_unitaire;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import services.ObjectService;

@ContextConfiguration (locations ="file:src/main/webapp/WEB-INF/servlet-context.xml")
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ObjectServiceTest {

	@Autowired
	private ObjectService service;
	
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
	public void testFindOne() {
		Assert.assertNotNull(service.findOne("FORM1"));
	}

	
	@Test
	public void testObjectsNonLiee() {
		Assert.assertNotNull(service.objectsNonLiee("FORM1"));
	}

	
	@Test
	public void testFindNonFLinkedObject() {
		Assert.assertNotNull(service.findNonFLinkedObject("FORM1","FORM1"));
	}
	
	@Test
	public void testGetChild() {
		Assert.assertNotNull(service.getChild("FORM1"));
	}

	
	@Test
	public void testCheckContentModel() {
		Assert.assertEquals("", service.checkContentModel(new domain.Object()));
	}

	
	@Test
	public void testIsContributorOfObject() {
		Assert.assertNotNull(service.isContributorOfObject("toto","FORM1"));
	}
	
	private static void log(String msg){
		System.out.println(msg);
	}

}
