package services;



import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FormationDao;
import domain.Formation;
import domain.Object;



@Service
public class FormationService {


	@Autowired
	private FormationDao formationDao;


	/**
	 * Création d'une formation
	 * 
	 * @return Renvoit uen instance de la création de la formation.
	 */	
	public Formation create() {

		Formation result;
		result = new Formation();	
		return result;
	}

	/**
	 * Sauvegarde/mise à jour de la formation dont les informations sont
	 * positionnées dans formation.
	 * 
	 * @param formationForm
	 *            les informations de la formation à sauvegarder
	 */
	public void save(Formation formation) {
		Assert.notNull(formation);
		formationDao.save(formation);
	}
	
	/**
	 * Toutes les formations
	 */
	public Collection<Formation> findAll(){
		return formationDao.findAll();
	}
	
	/**
	 * Une formation via son id
	 */
	public Formation findOne(String code){
		return formationDao.findOne(code);
	}
	
	public List<Object> getListFormationIndente(String code){
		return null;
	}
	
}
