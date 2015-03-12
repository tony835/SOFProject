package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FormationDao;
import domain.Fils;
import domain.Formation;

@Transactional
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
	 * Sauvegarde/mise à jour de la formation dont les informations sont positionnées dans formation.
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
	public Collection<Formation> findAll() {
		System.out.println("passeeeee");
		return formationDao.findAll();
	}

	/**
	 * Une formation via son id
	 */
	public Formation findOne(String code) {
		return formationDao.findOne(code);
	}

	public Collection<String> findAllDistinctDiplome(){
		return formationDao.findAllDistinctDiplome();
	}
	
	public List<Pair<domain.Object, Integer>> getListFormationIndente(String code) {
		Formation f = findOne(code);
		if(f == null) return null;
		List<Pair<domain.Object, Integer>> list = new ArrayList<Pair<domain.Object, Integer>>();
		formationIndentShild(list, f.getAllFils(), 0);
		return list;
	}
	
	public List<Fils> getShild(String code) {
		Formation formation = findOne(code);
		List<Fils> list = new ArrayList<Fils>();
		for (Fils fils : formation.getAllFils()){
			if(list.size() == 0){
				list.add(fils);
				continue;
			}
			for (int i = 0; i >= list.size(); i++){
				if(fils.getRang() > list.get(i).getRang()){
					list.add(i, fils);
				}
			}
		}
		return list;
	}

	private void formationIndentShild(List<Pair<domain.Object, Integer>> list, Collection<Fils> l, int padding) {
		for (Fils f : l) {
			list.add(new Pair<domain.Object, Integer>(f.getFils(), padding));
			formationIndentShild(list, f.getFils().getAllFils(), padding + 1);
		}
	}

	public Collection<Formation> findByResponsable(String login) {
		return formationDao.findbyResponsable(login);
	}
	
	public Collection<Formation> findbyDomaineByDiplomeAndByType(String diplome, String domaine){
		return formationDao.findbyDomaineByDiplomeAndByType(diplome, domaine);
	}
	
	public Collection<String> findbyDomaineByDiplome(String diplome){
		return formationDao.findbyDomaineByDiplome(diplome);
	}
	
	public Collection<Formation> getVisibleFormation(){
		return formationDao.getVisibleFormation();
	}
}
