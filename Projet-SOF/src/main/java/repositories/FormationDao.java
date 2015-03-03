package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Formation;
import domain.Person;

@Repository
public interface FormationDao extends JpaRepository<Formation, Integer> {

	
}
