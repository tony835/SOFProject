package domain;


import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;




@Entity
@Access(AccessType.PROPERTY)
public class Person{
	@Id
	private int id;

	
}
