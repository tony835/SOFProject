package services;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;


@Service("ServiceObject")
public class ServiceObject implements IService<domain.Object, String> {

	@PersistenceContext
	// (unitName = "unitSOF")
	EntityManager em;

	public Collection<domain.Object> findAll() {
		return em.createNamedQuery(domain.Object.FIND_ALL, domain.Object.class)
				.getResultList();
	}

	public boolean add(domain.Object object) {
		try {
			em.getTransaction().begin();
			em.persist(object);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean delete(domain.Object object) {
		try {
			em.getTransaction().begin();
			em.remove(object);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void update(domain.Object object) {
		em.getTransaction().begin();
		em.merge(object);
		em.getTransaction().commit();
	}

	public String getById(domain.Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@PostConstruct
	public void init() {
		System.out.println("INIT EJB = " + this);
	}

}
