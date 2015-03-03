package services;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import domain.TypeObject;

@Service("ServiceTypeObject")
public class ServiceTypeObject implements IService<TypeObject, String> {

	@PersistenceContext
	// (unitName = "unitSOF")
	EntityManager em;

	public Collection<TypeObject> findAll() {
		return em.createNamedQuery(TypeObject.FIND_ALL, TypeObject.class)
				.getResultList();
	}

	public boolean add(TypeObject object) {
		try {
			em.getTransaction().begin();
			em.persist(object);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean delete(TypeObject object) {
		try {
			em.getTransaction().begin();
			em.remove(object);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void update(TypeObject object) {
		em.getTransaction().begin();
		em.merge(object);
		em.getTransaction().commit();
	}

	public String getById(TypeObject object) {
		// TODO Auto-generated method stub
		return null;
	}

	@PostConstruct
	public void init() {
		System.out.println("INIT EJB = " + this);
	}

}
