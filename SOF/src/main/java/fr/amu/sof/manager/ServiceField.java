package fr.amu.sof.manager;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import fr.amu.sof.model.Field;

@Stateless
@LocalBean()
@Startup
@Service("ServiceField")
public class ServiceField implements IService<Field,String>{
	@PersistenceContext	// (unitName = "unitSOF")
	EntityManager em;

	public Collection<Field> findAll() {
		return em.createNamedQuery(Field.FIND_ALL, Field.class)
				.getResultList();
	}

	public boolean add(Field object) {
		try {
			em.getTransaction().begin();
			em.persist(object);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean delete(Field object) {
		try {
			em.getTransaction().begin();
			em.remove(object);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void update(Field object) {
		em.getTransaction().begin();
		em.merge(object);
		em.getTransaction().commit();
	}
	

	public String getById(Field object) {
		// TODO Auto-generated method stub
		return null;
	}
	@PostConstruct
	public void init() {
		System.out.println("INIT EJB = " + this);
	}

}
