package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Formation;
import domain.Object;


@Repository
public interface ObjectDao extends JpaRepository<domain.Object, String> {
	
	
	@Query("select o from Object o where o.contexte.code = ?1 and o.code <> o.contexte.code and not exists (select c from Fils c where c.fils.code=o.code and c.fils.contexte.code=o.contexte.code)")
	Collection<Object> findNonLinkedObject(String contextCode);
	
	@Query("select o from Object o, Object o2 where o.contexte.code = ?1 and o2.code = ?2 "
		+ "and o.code "
		+ "NOT IN (SELECT af.fils.code FROM o2.allFils af) "
		+ "AND o.code <> o.contexte.code)")
	Collection<Object> findNonFLinkedObject(String contextCode, String fCode);
	
	@Query("select o from Object o, Object o2 where o.contexte.code = ?1 and o.typeObject.code = ?2 and o2.code = ?3 "
			+ "and o.code "
			+ "NOT IN (SELECT af.fils.code FROM o2.allFils af) "
			+ "AND o.code <> o.contexte.code)")
	Collection<Object> findTypedNonLinkedObject(String contextCode, String Code_type, String cobject);
		
	@Query("select o from Object o, Object o2 where o.contexte.code <> ?1 and o2.code = ?2 and o.mutualisable = true "
			+ "and o.code "
			+ "NOT IN (SELECT af.fils.code FROM o2.allFils af) "
			+ "AND o.code <> o.contexte.code)")
	Collection<Object> findOtherMutualisableObject(String contextCode, String cobject);

	@Query("select o from Object o, Object o2 where o.contexte.code <> ?1 and o.typeObject.code = ?2 "
			+ "and o2.code = ?3 and o.mutualisable = true "
			+ "and o.code "
			+ "NOT IN (SELECT af.fils.code FROM o2.allFils af) "
			+ "AND o.code <> o.contexte.code)")
	Collection<Object> findOtherTypesMutualisableObject(String contextCode, String Code_type, String cobject);
	
	@Query("select o from Object o where o.contexte.code = ?2  and  o.typeObject.code= ?1  and o.code <> ?3")
	Collection<Object> findOtheObjectSameTypeInContext(String codeTypeObject,String codecotext, String codeObject);
	
	@Query("select o.code from Object o where o.code=?2 and ?1 in (select c.login from o.contexte.contributeurs c) ")
	Collection<String> isContributorOfObject(String login, String object);
	
	@Query("select count(*) from Fils f WHERE f.fils.code = ?1 AND f.fils.mutualisable = false")
		Collection<Object> countNbFathers(String code);
}
