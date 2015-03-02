package fr.amu.sof.manager;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import fr.amu.sof.model.Object;

@Stateless
@LocalBean()
@Startup
@Service("ServiceObject")
public class ServiceObject implements IService<fr.amu.sof.model.Object, String> {

	@PersistenceContext
	// (unitName = "unitSOF")
	EntityManager em;

	public Collection<Object> findAll() {
		return em.createNamedQuery(Object.FIND_ALL, Object.class)
				.getResultList();
	}

	public boolean add(Object object) {
		try {
			em.getTransaction().begin();
			em.persist(object);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean delete(Object object) {
		try {
			em.getTransaction().begin();
			em.remove(object);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void update(Object object) {
		em.getTransaction().begin();
		em.merge(object);
		em.getTransaction().commit();
	}

	public String getById(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@PostConstruct
	public void init() {
		System.out.println("INIT EJB = " + this);
	}

}
