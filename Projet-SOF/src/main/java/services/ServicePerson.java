package services;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import domain.Person;


@Service("PersonneManager")
public class ServicePerson implements IService<Person, String> {

	@PersistenceContext
	// (unitName = "unitSOF")
	EntityManager em;

	public Collection<Person> findAll() {
		return em.createNamedQuery(Person.FIND_ALL, Person.class)
				.getResultList();
	}

	public boolean add(Person object) {
		try {
			em.getTransaction().begin();
			em.persist(object);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean delete(Person object) {
		try {
			em.getTransaction().begin();
			em.remove(object);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void update(Person object) {
		em.getTransaction().begin();
		em.merge(object);
		em.getTransaction().commit();
	}

	public String getById(Person object) {
		// TODO Auto-generated method stub
		return null;
	}

	@PostConstruct
	public void init() {
		System.out.println("INIT EJB = " + this);
	}

}
