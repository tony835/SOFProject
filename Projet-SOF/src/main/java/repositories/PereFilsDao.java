package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Fils;


@Repository
public interface PereFilsDao extends JpaRepository<Fils, Integer> {
	
}
