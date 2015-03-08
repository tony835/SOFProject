package repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Field;

@Repository
public interface FieldDao extends JpaRepository<Field, String> {

	
}
