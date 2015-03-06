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
import domain.Object;

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

	public List<Pair<domain.Object, Integer>> getListFormationIndente(String code) {
		Formation f = findOne(code);
		System.out.println(f.getAllFils().size());
		List<Pair<domain.Object, Integer>> list = new ArrayList<Pair<domain.Object, Integer>>();
		formationIndentShild(list, f.getAllFils(), 0);
		return list;
	}

	private void formationIndentShild(List<Pair<domain.Object, Integer>> list, Collection<Fils> l, int padding) {
		for (Fils f : l) {
			list.add(new Pair<domain.Object, Integer>(f.getFils(), padding));
			formationIndentShild(list, f.getFils().getAllFils(), padding + 1);
		}
	}
}
