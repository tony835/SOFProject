package fr.amu.sof.manager;

import java.util.Collection;

public interface IService <T,ID>{

	boolean add(T object) ;
	boolean delete(T object);
	void update(T object);
	
	Collection<T> findAll() ;
	ID getById(T object) ;
	
}
