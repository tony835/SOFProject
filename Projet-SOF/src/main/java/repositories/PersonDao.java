package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Person;

@Repository
public interface PersonDao extends JpaRepository<Person, String> {


	
//	@Query("select COUNT(p.login) from Person p where p.login=?1 and p.contributeurDesFormations.Code_objet = ?2 ")
//	Integer isContributorOfObject(String person, String object);
}
