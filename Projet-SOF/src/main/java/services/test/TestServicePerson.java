package services.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import services.ServicePerson;
import domain.Person;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)

public class TestServicePerson {
	@Autowired
	ServicePerson manager;
	Person p;
	
	@Before
	public void setUp() throws Exception {
		p= new Person();
		p.setName("Nom");
		p.setFirstName("prenom");
		p.setMail("email");
	}
	
	
	public void testAjout()
	{
		//manager.add(p);
		//Personne p2=manager.findPerson(p.getNom());
		//assertNotNull(p2);
	}
	
}
