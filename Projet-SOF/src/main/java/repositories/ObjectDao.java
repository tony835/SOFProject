package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Object;


@Repository
public interface ObjectDao extends JpaRepository<domain.Object, String> {
	
	
	@Query("select o from Object o where o.contexte.code = ?1 and o.code <> o.contexte.code and not exists (select c from Fils c where c.fils.code=o.code and c.fils.contexte.code=o.contexte.code)")
	Collection<Object> findNonLinkedObject(String contextCode);
	
	@Query("select o from Object o where o.contexte.code = ?1 and o.code <> o.contexte.code and o.typeObject.code = ?2 and not exists (select c from Fils c where c.fils.code=o.code and c.fils.contexte.code=o.contexte.code)")
	Collection<Object> findTypedNonLinkedObject(String contextCode, String Code_type);
	
	@Query("select o from Object o where o.contexte.code <> ?1 and o.code <> o.contexte.code and mutualisable = true and not exists (select c from Fils c where c.fils.code=o.code and c.fils.contexte.code <> o.contexte.code)")
	Collection<Object> findOtherMutualisableObject(String contextCode);

	@Query("select o from Object o where o.contexte.code <> ?1 and o.code <> o.contexte.code and o.typeObject.code = ?2 and mutualisable = true and not exists (select c from Fils c where c.fils.code=o.code and c.fils.contexte.code <> o.contexte.code)")
	Collection<Object> findOtherTypesMutualisableObject(String contextCode, String Code_type);
	
}
