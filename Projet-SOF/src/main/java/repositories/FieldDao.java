package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Field;
import domain.Person;

@Repository
public interface FieldDao extends JpaRepository<Field, String> {

	
}
