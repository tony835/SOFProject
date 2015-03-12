package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.FieldObject;
import domain.FieldObjectId;

public interface FieldObjectDao extends JpaRepository<FieldObject, FieldObjectId> {

}
