package repositories;



import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Formation;

@Repository
public interface FormationDao extends JpaRepository<Formation, String> {

	@Query("select o from Formation o where o.responsable.login = ?1")
	Collection<Formation> findbyResponsable(String login);
	
	
	@Query("select DISTINCT(diplomeType) from Formation")
	Collection<String> findAllDistinctDiplome();
	
	
	@Query("select DISTINCT(f.formationField) from Formation f where f.diplomeType = ?1")
	Collection<String> findbyDomaineByDiplome(String diplome);
	
	@Query("select f from Formation f where f.diplomeType = ?1 and f.formationField = ?2")
	Collection<Formation> findbyDomaineByDiplomeAndByType(String diplome, String domaine);
	
	@Query("select code from Formation where code = ?1")
	String isFormation(String code);
}
