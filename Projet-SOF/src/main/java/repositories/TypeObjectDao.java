package repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.TypeObject;

@Repository
public interface TypeObjectDao extends JpaRepository<TypeObject, String> {

	@Query("select o from TypeObject o where o.code <> 'FOR' ")
	List<TypeObject> findTypeFils();
	
}
