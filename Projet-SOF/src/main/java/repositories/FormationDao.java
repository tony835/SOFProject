package repositories;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Formation;

@Repository
public interface FormationDao extends JpaRepository<Formation, String> {

	@Query("select c from FieldObject o where o.fo.field.name = ?1")
	Collection<Formation> findByUserAccountId(String id, String typeFormat);
	
}
