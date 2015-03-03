package repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import domain.TypeObject;

@Repository
public interface TypeObjectDao extends JpaRepository<TypeObject, Integer> {
	
}
