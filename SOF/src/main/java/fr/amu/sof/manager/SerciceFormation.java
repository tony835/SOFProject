package fr.amu.sof.manager;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import fr.amu.sof.model.Formation;

@Stateless
@LocalBean()
@Startup
@Service("SerciceFormation")
public class SerciceFormation implements IService<Formation,String>{

	@PersistenceContext//(unitName = "unitSOF")
    EntityManager em;

	public Collection<Formation> findAll() {
        return em.createNamedQuery(Formation.FIND_ALL, Formation.class)
                .getResultList();
	}

	public boolean add(Formation object) {
		try {
			em.getTransaction().begin();
			em.persist(object);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean delete(Formation object) {
		try {
			em.getTransaction().begin();
			em.remove(object);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void update(Formation object) {
		em.getTransaction().begin();
		em.merge(object);
		em.getTransaction().commit();
	}

	public String getById(Formation object) {
		// TODO Auto-generated method stub
		return null;
	}
	@PostConstruct
	public void init() {
		System.out.println("INIT EJB = " + this);
	}

}
