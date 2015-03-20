package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.javatuples.Pair;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FormationDao;
import domain.Fils;
import domain.Formation;
import domain.Object;
import domain.User;

@Transactional
@Service
public class FormationService {

	@Autowired
	private FormationDao formationDao;
	
	@Autowired
	ObjectService objectService;

	@Autowired
	private User user;
	
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
	
	public boolean isContributor(String code){
		try{
			return formationDao.findOne(code).getResponsable().getLogin().equals(user.getLogin());
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
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
	
	public Collection<String> findAllDistinctDiplomeVisitor(){
		return formationDao.findAllDistinctDiplomeVisitor();
	}
	
	public Collection<String> findbyDomaineByDiplome(String diplome)
	{
		return formationDao.findbyDomaineByDiplome(diplome);
	}
	
	public Collection<String> findbyDomaineByDiplomeVisitor(String diplome)
	{
		return formationDao.findbyDomaineByDiplomeVisitor(diplome);
	}
	
	public boolean isFormation(String code){
		System.out.println(formationDao.isFormation(code));
		return formationDao.isFormation(code) != null;
	}
	
	public List<Pair<domain.Object, Integer>> getListFormationIndente(String code) {
		Formation f = findOne(code);
		if(f == null) return null;
		List<Pair<domain.Object, Integer>> list = new ArrayList<Pair<domain.Object, Integer>>();
		list.add(new Pair<domain.Object, Integer>(f, -1));
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

	public List<String> findDiplomaType() {
		SAXBuilder sxb = new SAXBuilder();
		List<String> DiplomaTypeList = new ArrayList<String>();
		
		Document document = null;
		try {
			document = sxb.build(getClass().getResource("/configApp.xml"));
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		
		Element racine = document.getRootElement();
		Element diplomaType = racine.getChild("diploma_type");
		List<Element> diplomaTypeNames = diplomaType.getChildren("name");
		
		for (Element dtName : diplomaTypeNames){
			DiplomaTypeList.add(dtName.getText());
		}
		return DiplomaTypeList;
	}

	public List<String> findFormationFieldList() {
		SAXBuilder sxb = new SAXBuilder();
		List<String> DiplomaTypeList = new ArrayList<String>();
		
		Document document = null;
		try {
			document = sxb.build(getClass().getResource("/configApp.xml"));
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		
		Element racine = document.getRootElement();
		Element formationField = racine.getChild("formation_field");
		List<Element> formationFieldNames = formationField.getChildren("name");
		
		for (Element ffName : formationFieldNames){
			DiplomaTypeList.add(ffName.getText());
		}
		return DiplomaTypeList;
	}
	
	public Collection<Formation> findbyDomaineByDiplomeAndByType(String diplome, String domaine){
		return formationDao.findbyDomaineByDiplomeAndByType(diplome, domaine);
	}
	
	public Collection<Formation> findbyDomaineByDiplomeAndByTypeVisitor(String diplome, String domaine){
		return formationDao.findbyDomaineByDiplomeAndByTypeVisitor(diplome, domaine);
	}


	public String checkContentModel(String code) {
		Formation f = findOne(code);
		int nbErrors = 0;
		String descErrors = "";
		
		List<domain.Object> loContext = new ArrayList<domain.Object>();
		loContext.add(f);
		objectService.getDescendants(loContext,f); 

		for(domain.Object o : loContext){
			String cmError = objectService.checkContentModel(o);
			if (!cmError.equals("")){
				nbErrors ++;
				descErrors += "Erreur " + o.getCode() + " : " + cmError + "<br/>";
				
			}
		}
		
		f.setNumError(nbErrors);
		return descErrors;
	}

	public Collection<Formation> findVisible() {
		return formationDao.findVisible();
	}
	
	public Collection<Formation> formationWithContributeur(String login)
	{
		return formationDao.formationWithContributeur(login);
	}
	

	public String getArbreRetour() {
		return arbreRetour;
	}


	public String arbreRetour = "";

	private void getArbreLi(domain.Object obj, String codeContext, Formation formation) {

		String codeO = obj.getCode();
		if (!objectService.checkContentModel(obj).equals("")) {
			arbreRetour += "<li name=\"" + codeO + "\"> <a id=\"" + codeO + "\">"/* "\"><a>" */+ codeO + " "
					+ obj.getName() + "  <p style=\"color: Red\">Erreur</p> </a>" + addactionsObj(obj, codeContext);
			formation.incrNbError();
		} else {
			arbreRetour += "<li name=\"" + codeO + "\"> <a id=\"" + codeO + "\">"/* "\"><a>" */+ codeO + " "
					+ obj.getName() + " </a>" + addactionsObj(obj, codeContext);
		}

		if (obj.getAllFils().size() != 0)
			getArbreUl(obj, codeContext, formation);
		arbreRetour += "</li>\n";
	}

	private void getArbreUl(domain.Object obj, String codeContext, Formation formation) {
		arbreRetour += "<ul>\n";
		for (Fils o : orderByRang(obj.getAllFils()))
			getArbreLi(o.getFils(), codeContext, formation);
		arbreRetour += "</ul>\n";
	}

	public void getArbre(Formation formation) {
		String codeF = formation.getCode();
		if (!objectService.checkContentModel(formation).equals("")) {
			formation.setNumError(1);
			arbreRetour = "<ul id=\"list\"><li name=\"" + codeF + "\"> <a id=\"" + codeF + "\">"/* "\"><a>" */+ codeF
					+ " " + formation.getName() + "  <p style=\"color: Red\">Erreur</p> </a>"
					+ addactionsFormation(codeF);
		} else {
			formation.setNumError(0);
			arbreRetour = "<ul id=\"list\"><li name=\"" + codeF + "\"> <a id=\"" + codeF + "\">"/* "\"><a>" */+ codeF
					+ " " + formation.getName() + "</a> " + addactionsFormation(codeF);
		}
		getArbreUl(formation, codeF, formation);
		arbreRetour += "</li></ul>";
	}

	private String addactionsObj(domain.Object o, String context) {
		if (o.getContexte() == null)
			return "";
		if (!o.getContexte().getCode().equals(context) || !user.isResponsable(o.getContexte())) {
			return "";
		}
		return "<div> <a class=\"btn btn-default btn-sm\" href=\"arbreFormation/gestionFils.htm?cobject=" + o.getCode()
				+ "\"> Modifier les fils </a>"
				+ " <a class=\"btn btn-default btn-sm\" href=\"arbreFormation/create.htm?context=" + context
				+ "&amp;cobject=" + o.getCode() + "\">Modifier l'objet</a></div>";
	}

	private String addactionsFormation(String context) {
		return "<div> <a class=\"btn btn-default btn-sm\" href=\"arbreFormation/gestionFils.htm?cobject=" + context
				+ "\"> Modifier les fils </a></div>";
	}

	private List<Fils> orderByRang(Collection<Fils> allFils) {
		List<Fils> list = new ArrayList<Fils>();
		list.addAll(allFils);
		Collections.sort(list);
		return list;
	}
	
}
