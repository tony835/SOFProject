package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Object;


@Repository
public interface ObjectDao extends JpaRepository<domain.Object, String> {
	
	
	@Query("select o from Object o where o.contexte.code = ?1 and not exists (select c from Fils c where c.fils.code=o.code and c.fils.contexte.code=o.contexte.code)")
	Collection<Object> findObjectnNonLiée(String contextCode);
	
}
