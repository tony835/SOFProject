package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.javatuples.Pair;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
	
    @Autowired  
    private MessageSource messageSource;
	
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
		return formationDao.isFormation(code) != null;
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

	private void getArbreLi(domain.Object obj, String codeContext, Formation formation, Locale loc) {

		String codeO = obj.getCode();
		if (!objectService.checkContentModel(obj).equals("")) {
			arbreRetour += "<li name=\"" + codeO + "\"> <a id=\"" + codeO + "\">"/* "\"><a>" */+ codeO + " "
					+ obj.getName() + "  <img src=\"images/error.png\" alt=\"Erreur\" title=\"Erreur\" /> </a>" + addactionsObj(obj, codeContext, loc);
			formation.incrNbError();
		} else {
			arbreRetour += "<li name=\"" + codeO + "\"> <a id=\"" + codeO + "\">"/* "\"><a>" */+ codeO + " "
					+ obj.getName() + " </a>" + addactionsObj(obj, codeContext, loc);
		}

		if (obj.getAllFils().size() != 0)
			getArbreUl(obj, codeContext, formation, loc);
		arbreRetour += "</li>\n";
	}

	private void getArbreUl(domain.Object obj, String codeContext, Formation formation, Locale loc) {
		arbreRetour += "<ul>\n";
		for (Fils o : orderByRang(obj.getAllFils()))
			getArbreLi(o.getFils(), codeContext, formation, loc);
		arbreRetour += "</ul>\n";
	}
	
	public void getArbre(Formation formation, Locale loc) {
		String codeF = formation.getCode();
		if (!objectService.checkContentModel(formation).equals("")) {
			formation.setNumError(1);
			arbreRetour = "<ul id=\"list\"><li name=\"" + codeF + "\"> <a id=\"" + codeF + "\">"/* "\"><a>" */+ codeF
					+ " " + formation.getName() + " <img src=\"images/error.png\" alt=\"Erreur\" title=\"Erreur\" /> </a>"
					+ addactionsFormation(codeF, loc);
		} else {
			formation.setNumError(0);
			arbreRetour = "<ul id=\"list\"><li name=\"" + codeF + "\"> <a id=\"" + codeF + "\">"/* "\"><a>" */+ codeF
					+ " " + formation.getName() + "</a> " + addactionsFormation(codeF, loc);
		}
		getArbreUl(formation, codeF, formation, loc);
		arbreRetour += "</li></ul>";
	}
	
	private String addactionsObj(domain.Object o, String context, Locale loc) {
		if (o.getContexte() == null)
			return "";
		if (!o.getContexte().getCode().equals(context) || !user.isResponsable(o.getContexte())) {
			return "";
		}
		return "<div style=\"display:inline\"> <a class=\"btn btn-default btn-xs\" href=\"arbreFormation/gestionFils.htm?cobject=" + o.getCode()
				+ "\">" + messageSource.getMessage("objet.modifierFils", null, loc) +"</a>"
				+ " <a class=\"btn btn-default btn-xs\" href=\"arbreFormation/create.htm?context=" + context
				+ "&amp;cobject=" + o.getCode() + "\">" + messageSource.getMessage("objet.modifier", null, loc) +"</a></div>";
	}
	private String addactionsFormation(String context, Locale loc) {
		return "<div style=\"display:inline\"> <a class=\"btn btn-default btn-xs\" href=\"arbreFormation/gestionFils.htm?cobject=" + context
				+ "\">"+ messageSource.getMessage("objet.modifierFils", null, loc) + "</a></div>";
	}
	
	
	private List<Fils> orderByRang(Collection<Fils> allFils) {
		List<Fils> list = new ArrayList<Fils>();
		list.addAll(allFils);
		Collections.sort(list);
		return list;
	}
	
}
