import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.SAXException;


public class AlimentationBdd {
	
	public static void main(String[] args) throws JDOMException, IOException,
	SAXException, ClassNotFoundException, SQLException {


// create a mysql database connection
String myDriver = "com.mysql.jdbc.Driver";
String myUrl = "jdbc:mysql://localhost:3306/isl";
Class.forName(myDriver);
Connection conn = DriverManager.getConnection(myUrl, "salah", "");

/** declarer les fichiers fichiers xml*/
SAXBuilder sxb = new SAXBuilder();
Document document2 = sxb.build(new File("src/main/resources/configApp.xml"));
Element racine2 = document2.getRootElement();
Document document = sxb.build(new File("src/main/resources/offre.xml"));
Element racine = document.getRootElement();

  // the mysql insert statement 
   String query2 = " UPDATE Objet SET contexte = ?";
  // create the mysql insert preparedstatement 
  PreparedStatement  preparedStmt2 = conn.prepareStatement(query2); 

/**
 * alimmentation des typesobjets depuis le fichier de configuration
 * */

List<Element> listTypeObjet = racine2.getChildren("object_type");
Iterator<Element> i = listTypeObjet.iterator();

while (i.hasNext()) {
	Element courant = (Element) i.next();
	String code_type = courant.getChild("code").getText();
	String Erreur_descritpion = courant.getChild("Erreur_descritpion").getText();
	String content_model = courant.getChild("content_model").getText();
	String Nom = courant.getChild("name").getText();

	// the mysql insert statement
	String query = " insert into TypesObjets "
			+ " values (?,?,?,?)";
	// create the mysql insert preparedstatement
	PreparedStatement preparedStmt = conn.prepareStatement(query);
	preparedStmt.setString(1, code_type);
	preparedStmt.setString(2, Erreur_descritpion);
	preparedStmt.setString(3, content_model);
	preparedStmt.setString(4, Nom);
	preparedStmt.execute();
}

/**
 * alimenter la table personne
 */

List<Element> listPerson = racine.getChildren("personne");
  Iterator<Element> i1 = listPerson.iterator();
 
  while (i1.hasNext()) { Element courant = (Element) i1.next(); String
  Code = courant.getAttribute("code").getValue(); String Nom =
  courant.getChild("nom").getText(); String mail =
  courant.getChild("mail").getText(); String prenom =
  courant.getChild("prenom").getText();
  
  // the mysql insert statement 
  String query = " insert into Person " +
  " values (?, ?, ?, ?, '')";
  
  // create the mysql insert preparedstatement 
  PreparedStatement  preparedStmt = conn.prepareStatement(query); preparedStmt.setString
  (1, Code); preparedStmt.setString (2, prenom); preparedStmt.setString
  (3, mail); preparedStmt.setString (4, Nom);
  
  // execute the preparedstatement 
   preparedStmt.execute(); 
   
   }
  
/**
 * alimenter la table objet
 */
  List<Element> ObjetFormation = racine.getChildren("mention");
  Iterator<Element> i2 = ObjetFormation.iterator();
  
  while (i2.hasNext()) 
  { Element courant = (Element) i2.next(); 
  String Code_Objet = courant.getAttributeValue("code"); 
  String nom = courant.getChild("nom").getText(); 
  String version = courant.getChild("version").getText(); 
  //String contexte =  courant.getChild("code_mention").getText();
  
  // the mysql insert statement 
   String query = " insert into Objet " +
  " values (?,0, ?,?,null,'FOR')";
  
  // create the mysql insert preparedstatement 
  PreparedStatement  preparedStmt = conn.prepareStatement(query); 
  preparedStmt.setString  (1, Code_Objet); 
  preparedStmt.setString (2, nom);
  preparedStmt.setString (3, version); 
  //preparedStmt.setString (4,  Code_Objet);
  preparedStmt.execute(); 
  }
  
	/**
	 * alimenter la table formation
	 */
  
List<Element> listFormation2 = racine.getChildren("mention");
	Iterator<Element> i8 = listFormation2.iterator();

	while (i8.hasNext()) {
		Element courant = (Element) i8.next();
		String Code_Formation = courant.getAttributeValue("code");
		String type_diplome = courant.getChild("type_diplome").getText();
		Element Domaine = courant.getChild("ref-domaine");
		String domaine = Domaine.getAttributeValue("ref");
		Element Responsable = courant.getChild("responsables");
		if(Responsable.getChildren().isEmpty()){
			// the mysql insert statement
			String query = " insert into Formation "
					+ " values (?,?,0,1,?,'TOTO')";
			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, type_diplome);
			preparedStmt.setString(2, domaine);
			preparedStmt.setString(3, Code_Formation);
			// execute the preparedstatement
			preparedStmt.execute();
			
			preparedStmt2.setString(1, Code_Formation);
			preparedStmt2.execute();

		}
		else {
		Element RefResponsable = Responsable.getChild("ref-personne");
		if(RefResponsable.getAttributeValue("ref").length()>0){
			String Resp = RefResponsable.getAttributeValue("ref");
			// the mysql insert statement
			String query = " insert into Formation "
					+ " values (?,?,0,1,?,?)";
			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, type_diplome);
			preparedStmt.setString(2, domaine);
			preparedStmt.setString(3, Code_Formation);
			preparedStmt.setString(4, Resp);
			// execute the preparedstatement
			preparedStmt.execute();
			preparedStmt2.setString(1, Code_Formation);
			preparedStmt2.execute();
		}
		}
	}

		 /**
		 * alimenter la table objet
		 */
		
	  List<Element> ObjetSpecialite = racine.getChildren("specialite");
	  Iterator<Element> i5 = ObjetSpecialite.iterator();
	  
	  while (i5.hasNext()) 
	  { Element courant = (Element) i5.next(); 
	  String Code_Objet = courant.getAttributeValue("code"); 
	  String nom = courant.getChild("nom").getText(); 
	  String version = courant.getChild("version").getText(); 
	  String contexte =  courant.getChild("code_mention").getText();
	  if(contexte.length()<0){
			// the mysql insert statement
		  String query = " insert into Objet " +
				  " values (?,0, ?,?,null,'SPE')";
			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			 preparedStmt.setString  (1, Code_Objet); 
			  preparedStmt.setString (2, nom);
			  preparedStmt.setString (3, version); 
			// execute the preparedstatement
			preparedStmt.execute();
		}
	  else {
	  // the mysql insert statement 
	   String query = " insert into Objet " +
	  " values (?,0, ?,?,?,'SPE')";
	  
	  // create the mysql insert preparedstatement 
	  PreparedStatement  preparedStmt = conn.prepareStatement(query); 
	  preparedStmt.setString  (1, Code_Objet); 
	  preparedStmt.setString (2, nom);
	  preparedStmt.setString (3, version); 
	  preparedStmt.setString (4,  contexte);
	  
	  // execute the preparedstatement 
	  preparedStmt.execute(); 
	  }
	  }

	  List<Element> ObjetSemestre = racine.getChildren("semestre");
	  Iterator<Element> i6 = ObjetSemestre.iterator();
	  
	  while (i6.hasNext()) 
	  { 
		  Element courant = (Element) i6.next(); 
		  String Code_Objet = courant.getAttributeValue("code"); 
		  String nom = courant.getChild("nom").getText();
		  int muta;
		  String mut = courant.getChild("mutualisable").getText();
		  String contexte =  courant.getChild("code_mention").getText();
		  if(contexte.length()<0){
			  
			  String query = " insert into Objet " +
					  " values (?,?, ?,1,?,'COM')";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
		  switch(mut) {
		  case "N":
			  muta=0;
				  
					 preparedStmt.setString  (1, Code_Objet); 
					  preparedStmt.setString (3, nom);
					  preparedStmt.setInt (2, muta);
					  preparedStmt.setString (4, contexte); 
					// execute the preparedstatement
					preparedStmt.execute();
					break;
		  case "O":
			  muta=1;
					// the mysql insert statement
				  
					// create the mysql insert preparedstatement
					 preparedStmt.setString  (1, Code_Objet); 
					  preparedStmt.setString (3, nom);
					  preparedStmt.setInt (2, muta);
					  preparedStmt.setString (4, contexte); 
					// execute the preparedstatement
					preparedStmt.execute();
				}}
				
			  else {
			  // the mysql insert statement 
			   String query = " insert into Objet " +
					   " values (?,?, ?,1,?,'COM')";
			  
			  // create the mysql insert preparedstatement 
			  PreparedStatement  preparedStmt = conn.prepareStatement(query); 
			  switch(mut) {
			  case "N":
				  muta=0;					  
						// create the mysql insert preparedstatement
						 preparedStmt.setString  (1, Code_Objet); 
						  preparedStmt.setString (3, nom);
						  preparedStmt.setInt (2, muta);
						 // preparedStmt.setString (4, version);
						  preparedStmt.setString (4, contexte); 

						// execute the preparedstatement
						try {preparedStmt.execute();}catch (Exception e) {
						}
						break;
			  case "O":
				  muta=1;
						// the mysql insert statement
					  
						// create the mysql insert preparedstatement
						 preparedStmt.setString  (1, Code_Objet); 
						  preparedStmt.setString (3, nom);
						  preparedStmt.setInt (2, muta);
						 // preparedStmt.setString (4, version); 
						  preparedStmt.setString (4, contexte); 

						// execute the preparedstatement
						  try {preparedStmt.execute();}catch (Exception e) {
							}
					}
			 
			  }
	  }

List<Element> ObjetEnseigement = racine.getChildren("enseignement");
 Iterator<Element> i3 = ObjetEnseigement.iterator();
  
  while (i3.hasNext()) 
  { 
	  Element courant = (Element) i3.next(); 
  String Code_Objet = courant.getAttributeValue("code"); 
  String nom = courant.getChild("nom").getText();
  String mut = courant.getChild("mutualisable").getText();
  String version = courant.getChild("version").getText(); 
  String contexte =  courant.getChild("code_mention").getText();
  if(contexte.length()<0){
 if(mut.equals("N")){
	 String query = " insert into Objet " +
			  " values (?,0, ?,?,null,'ENS')";
		PreparedStatement preparedStmt = conn.prepareStatement(query);
	 preparedStmt.setString  (1, Code_Objet); 
	  preparedStmt.setString (2, nom);
	  preparedStmt.setString (3, version); 
	preparedStmt.execute();
 }	
 else if (mut.equals("O")){
	 String query = " insert into Objet " +
			  " values (?,1, ?,?,null,'ENS')";
		PreparedStatement preparedStmt = conn.prepareStatement(query);
	 preparedStmt.setString  (1, Code_Objet); 
	  preparedStmt.setString (2, nom);
	  preparedStmt.setString (3, version); 
	preparedStmt.execute();
  }
 else { 
	 String query = " insert into Objet " +
			  " values (?,0, ?,?,null,'ENS')";
		PreparedStatement preparedStmt = conn.prepareStatement(query);
	 preparedStmt.setString  (1, Code_Objet); 
	  preparedStmt.setString (2, nom);
	  preparedStmt.setString (3, version); 

      preparedStmt.execute();}
			}
		
	  else {
	  if(mut.equals("N")){
		  String query = " insert into Objet " +
				  " values (?,0, ?,?,?,'ENS')";
				  
				  PreparedStatement  preparedStmt = conn.prepareStatement(query); 
				  preparedStmt.setString  (1, Code_Objet); 
				  preparedStmt.setString (2, nom);
				  preparedStmt.setString (3, version); 
				  preparedStmt.setString (4, contexte); 
			  try {preparedStmt.execute();}catch (Exception e) {
				}	
			  }	
		 else if (mut.equals("O")){
			 String query = " insert into Objet " +
					  " values (?,1, ?,?,?,'ENS')";
					  
					  PreparedStatement  preparedStmt = conn.prepareStatement(query); 
			 
					  preparedStmt.setString  (1, Code_Objet); 
					  preparedStmt.setString (2, nom);
					 // preparedStmt.setInt (2, 1);
					  preparedStmt.setString (3, version); 
					  preparedStmt.setString (4, contexte); 

			// execute the preparedstatement
			  try {preparedStmt.execute();}catch (Exception e) {
				}
							  }
		 else {  
			 String query = " insert into Objet " +
					  " values (?,0, ?,?,?,'ENS')";
					  
					  PreparedStatement  preparedStmt = conn.prepareStatement(query); 		 
			 
					  preparedStmt.setString  (1, Code_Objet); 
					  preparedStmt.setString (2, nom);
					 // preparedStmt.setInt (2, 1);
					  preparedStmt.setString (3, version); 
					  preparedStmt.setString (4, contexte); 

		// execute the preparedstatement
		  try {preparedStmt.execute();}catch (Exception e) {
			}
			}
		}
	  }
 	  
  /**
	 * alimenter la table objet
  */
	
List<Element> ObjetProgramme = racine.getChildren("programme");
  Iterator<Element> i4 = ObjetProgramme.iterator();
  
  while (i4.hasNext()) 
  { Element courant = (Element) i4.next(); 
  String Code_Objet = courant.getAttributeValue("code"); 
  String nom = courant.getChild("nom").getText(); 
  String version = courant.getChild("version").getText(); 
  String contexte =  courant.getChild("code_mention").getText();
  if(contexte.length()<0){
		// the mysql insert statement
	  String query = " insert into Objet " +
			  " values (?,0, ?,?,null,'PRO')";
		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		 preparedStmt.setString  (1, Code_Objet); 
		  preparedStmt.setString (2, nom);
		  preparedStmt.setString (3, version); 
		// execute the preparedstatement
		preparedStmt.execute();
	}
  else {
  // the mysql insert statement 
   String query = " insert into Objet " +
  " values (?,0, ?,?,?,'PRO')";
  
  // create the mysql insert preparedstatement 
  PreparedStatement  preparedStmt = conn.prepareStatement(query); 
  preparedStmt.setString  (1, Code_Objet); 
  preparedStmt.setString (2, nom);
  preparedStmt.setString (3, version); 
  preparedStmt.setString (4,  contexte);
  
  // execute the preparedstatement 
  preparedStmt.execute(); 
  }
  }
 
  
 List<Element> ObjetOption = racine.getChildren("option");
  Iterator<Element> i31 = ObjetOption.iterator();
  
  while (i31.hasNext()) 
  { 
	  Element courant = (Element) i31.next(); 
  String Code_Objet = courant.getAttributeValue("code"); 
  String nom = courant.getChild("nom").getText();
  int muta;
  String mut = courant.getChild("mutualisable").getText();
  String contexte =  courant.getChild("code_mention").getText();
  if(contexte.length()<0){
	  
	  String query = " insert into Objet " +
			  " values (?,?, ?,1,?,'COM')";
		PreparedStatement preparedStmt = conn.prepareStatement(query);
  switch(mut) {
  case "N":
	  muta=0;
			// the mysql insert statement
		  
			// create the mysql insert preparedstatement
			 preparedStmt.setString  (1, Code_Objet); 
			  preparedStmt.setString (3, nom);
			  preparedStmt.setInt (2, muta);
			  preparedStmt.setString (4, contexte); 
			// execute the preparedstatement
			preparedStmt.execute();
			break;
  case "O":
	  muta=1;
			// the mysql insert statement
		  
			// create the mysql insert preparedstatement
			 preparedStmt.setString  (1, Code_Objet); 
			  preparedStmt.setString (3, nom);
			  preparedStmt.setInt (2, muta);
			  preparedStmt.setString (4, contexte); 
			// execute the preparedstatement
			preparedStmt.execute();
		}}
		
	  else {
	  // the mysql insert statement 
	   String query = " insert into Objet " +
				  " values (?,?, ?,1,?,'COM')";
	  
	  // create the mysql insert preparedstatement 
	  PreparedStatement  preparedStmt = conn.prepareStatement(query); 
	  switch(mut) {
	  case "N":
		  muta=0;					  
				// create the mysql insert preparedstatement
				 preparedStmt.setString  (1, Code_Objet); 
				  preparedStmt.setString (3, nom);
				  preparedStmt.setInt (2, muta);
				  preparedStmt.setString (4, contexte); 

				// execute the preparedstatement
				try {preparedStmt.execute();}catch (Exception e) {
				}
				break;
	  case "O":
		  muta=1;
				// the mysql insert statement
			  
				// create the mysql insert preparedstatement
				 preparedStmt.setString  (1, Code_Objet); 
				  preparedStmt.setString (3, nom);
				  preparedStmt.setInt (2, muta);
				  preparedStmt.setString (4, contexte); 

				// execute the preparedstatement
				  try {preparedStmt.execute();}catch (Exception e) {
					}
			}
	 
	  }
}
  
  List<Element> ObjetAnne = racine.getChildren("annee");
  Iterator<Element> i3111 = ObjetAnne.iterator();
  
  while (i3111.hasNext()) 
  { 
	  Element courant = (Element) i3111.next(); 
  String Code_Objet = courant.getAttributeValue("code"); 
  String nom = courant.getChild("nom").getText();
  int muta;
  String mut = courant.getChild("mutualisable").getText();
  String contexte =  courant.getChild("code_mention").getText();
  if(contexte.length()<0){
	  String query = " insert into Objet " +
			  " values (?,?, ?,1,?,'COM')";
		PreparedStatement preparedStmt = conn.prepareStatement(query);
  switch(mut) {
  case "N":
	  muta=0;
			// the mysql insert statement
		  
			// create the mysql insert preparedstatement
			 preparedStmt.setString  (1, Code_Objet); 
			  preparedStmt.setString (3, nom);
			  preparedStmt.setInt (2, muta);
			  preparedStmt.setString (4, contexte); 
			// execute the preparedstatement
			preparedStmt.execute();
			break;
  case "O":
	  muta=1;
			// the mysql insert statement
		  
			// create the mysql insert preparedstatement
			 preparedStmt.setString  (1, Code_Objet); 
			  preparedStmt.setString (3, nom);
			  preparedStmt.setInt (2, muta);
			  preparedStmt.setString (4, contexte); 
			// execute the preparedstatement
			preparedStmt.execute();
		}}
		
	  else {
	  // the mysql insert statement 
	   String query = " insert into Objet " +
				  " values (?,?, ?,1,?,'COM')";
	  
	  // create the mysql insert preparedstatement 
	  PreparedStatement  preparedStmt = conn.prepareStatement(query); 
	  switch(mut) {
	  case "N":
		  muta=0;					  
				// create the mysql insert preparedstatement
				 preparedStmt.setString  (1, Code_Objet); 
				  preparedStmt.setString (3, nom);
				  preparedStmt.setInt (2, muta);
				  preparedStmt.setString (4, contexte); 

				// execute the preparedstatement
				try {preparedStmt.execute();}catch (Exception e) {
				}
				break;
	  case "O":
		  muta=1;
				// the mysql insert statement
			  
				// create the mysql insert preparedstatement
				 preparedStmt.setString  (1, Code_Objet); 
				  preparedStmt.setString (3, nom);
				  preparedStmt.setInt (2, muta);
				  preparedStmt.setString (4, contexte); 

				// execute the preparedstatement
				  try {preparedStmt.execute();}catch (Exception e) {
					}
			}
	 
	  }
}
  
 List<Element> ObjetGroupe = racine.getChildren("groupe");
  Iterator<Element> i31111 = ObjetGroupe.iterator();
  
  while (i31111.hasNext()) 
  { 
	  Element courant = (Element) i31111.next(); 
  String Code_Objet = courant.getAttributeValue("code"); 
  String nom = courant.getChild("nom").getText();
  int muta;
  String mut = courant.getChild("mutualisable").getText();
  String contexte =  courant.getChild("code_mention").getText();
  if(contexte.length()<0){
	  String query = " insert into Objet " +
			  " values (?,?, ?,1,?,'COM')";
		PreparedStatement preparedStmt = conn.prepareStatement(query);
  switch(mut) {
  case "N":
	  muta=0;
			 preparedStmt.setString  (1, Code_Objet); 
			  preparedStmt.setString (3, nom);
			  preparedStmt.setInt (2, muta);
			  preparedStmt.setString (4, contexte); 
			// execute the preparedstatement
			preparedStmt.execute();
			break;
  case "O":
	  muta=1;
			 preparedStmt.setString  (1, Code_Objet); 
			  preparedStmt.setString (3, nom);
			  preparedStmt.setInt (2, muta);
			  preparedStmt.setString (4, contexte); 
			// execute the preparedstatement
			preparedStmt.execute();
		}}
		
	  else {
	  // the mysql insert statement 
	   String query = " insert into Objet " +
				  " values (?,?, ?,1,?,'COM')";
	  
	  // create the mysql insert preparedstatement 
	  PreparedStatement  preparedStmt = conn.prepareStatement(query); 
	  switch(mut) {
	  case "N":
		  muta=0;					  
				// create the mysql insert preparedstatement
				 preparedStmt.setString  (1, Code_Objet); 
				  preparedStmt.setString (3, nom);
				  preparedStmt.setInt (2, muta);
				  preparedStmt.setString (4, contexte); 

				try {preparedStmt.execute();}catch (Exception e) {
				}
				break;
	  case "O":
		  muta=1;
				 preparedStmt.setString  (1, Code_Objet); 
				  preparedStmt.setString (3, nom);
				  preparedStmt.setInt (2, muta);
				  preparedStmt.setString (4, contexte); 

				// execute the preparedstatement
				  try {preparedStmt.execute();}catch (Exception e) {
					}
			}
	 
	  }
}
  
  /**
	 * alimenter la table A_pour_contributeur
	 */
  
	List<Element> listContributeur = racine.getChildren("mention");
	Iterator<Element> i9 = listContributeur.iterator();

	while (i9.hasNext()) {
		Element courant = (Element) i9.next();
		String Code_Formation = courant.getAttributeValue("code");
		Element Contributeur = courant.getChild("contributeurs");
		if(Contributeur.getChildren().isEmpty()){
		}
		else {
		List<Element> RefContributeur = Contributeur.getChildren("ref-personne");
		Iterator<Element> i11 = RefContributeur.iterator();
		while (i11.hasNext()) {
		Element ref = (Element) i11.next();	
		if(ref.getAttributeValue("ref").length()>0){
			String Cont = ref.getAttributeValue("ref");
			// the mysql insert statement
			String query = " insert into A_pour_contributeur "
					+ " values (?,?)";
			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, Code_Formation);
			preparedStmt.setString(2, Cont);
			// execute the preparedstatement
			preparedStmt.execute();
		}
		}
		}
	}
	
	String query1 = " select Id from Descripteur_champs";
	PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
	final ResultSet Rs = preparedStmt1.executeQuery();
	/**
	 * alimmentation des fields 
	 * */
	
	Element Objet = racine.getChild("mention");
	List<Element> listField = Objet.getChildren();
	Iterator<Element> j1 = listField.iterator();
	while (j1.hasNext()) {
		Element courant = (Element)j1.next();
		 courant.getChildren();
			String Id = courant.getName();
			if(Id.equals("err")||Id.equals("nom")||Id.equals("responsables")
					||Id.equals("contributeurs")||Id.equals("tree")
					||Id.equals("structure")||Id.equals("liste-objets")||Id.equals("refs-composante")){}
				else {
				String name = courant.getName();
				String query = " insert into Descripteur_champs "
						+ " values (?,?,100,?,0,null,'STRING','FOR')";
				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, Id.toUpperCase());
				preparedStmt.setString(2, name);
				preparedStmt.setString(3, name);
				preparedStmt.execute();
				}
		      }  
	 
	
	Element ObjetSpe = racine.getChild("specialite");
	List<Element> listFieldSp = ObjetSpe.getChildren();
	Iterator<Element> a = listFieldSp.iterator();
	while (a.hasNext()) {
		Element courant = (Element)a.next();
		 courant.getChildren();
			String Id = courant.getName();
			if(Id.equals("err")||Id.equals("nom")||Id.equals("responsables")
					||Id.equals("contributeurs")||Id.equals("tree")
					||Id.equals("structure")||Id.equals("liste-objets")||Id.equals("refs-composante")){}
			else {
				while(Rs.next()) {
				 if(Id.equals(Rs)){ System.out.println("erreur");}
				 else {
			String name = courant.getName();
			String query = " insert into Descripteur_champs "
					+ " values (?,?,100,?,0,null,'STRING','SPE')";
			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, Id.toUpperCase());
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, name);
			// execute the preparedstatement
			try{
				preparedStmt.execute();
				}
			catch(Exception e1){
				}				}}		

	}}
	
	Element ObjetPr = racine.getChild("programme");
	List<Element> listFieldPr = ObjetPr.getChildren();
	Iterator<Element> z = listFieldPr.iterator();
	while (z.hasNext()) {
		Element courant = (Element)z.next();
		 courant.getChildren();
			String Id = courant.getName();
			if(Id.equals("err")||Id.equals("nom")||Id.equals("responsables")
					||Id.equals("contributeurs")||Id.equals("tree")
					||Id.equals("structure")||Id.equals("liste-objets")||Id.equals("refs-composante")){}
			else {while(Rs.next()) {
				 if(Id.equals(Rs)){ System.out.println("erreur");}
				 else {
			String name = courant.getName();

			// the mysql insert statement
			String query = " insert into Descripteur_champs "
					+ " values (?,?,100,?,0,null,'STRING','PRO')";
			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, Id.toUpperCase());
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, name);
			
			// execute the preparedstatement
			try{preparedStmt.execute();}
			catch(Exception e1){
							}}	}}}				
	Element ObjetEns = racine.getChild("enseignement");
	List<Element> listFieldEn = ObjetEns.getChildren();
	Iterator<Element> t = listFieldEn.iterator();
	while (t.hasNext()) {
		Element courant = (Element)t.next();
		 courant.getChildren();
			String Id = courant.getName();
			if(Id.equals("err")||Id.equals("nom")||Id.equals("responsables")
					||Id.equals("contributeurs")||Id.equals("tree")
					||Id.equals("structure")||Id.equals("liste-objets")||Id.equals("refs-composante")){}
			else {
				while(Rs.next()) {
				 if(Id.equals(Rs)){ System.out.println("erreur");}
				 else {
			String name = courant.getName();

			String query = " insert into Descripteur_champs "
					+ " values (?,?,100,?,0,null,'STRING','ENS')";
			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, Id.toUpperCase());
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, name);
			
			// execute the preparedstatement
			try{preparedStmt.execute();}
			catch(Exception e1){
				}	
		}
				 }
				}}	
	
	Element ObjetOp = racine.getChild("option");
	List<Element> listFieldOp = ObjetOp.getChildren();
	Iterator<Element> e = listFieldOp.iterator();
	while (e.hasNext()) {
		Element courant = (Element)e.next();
		 courant.getChildren();
			String Id = courant.getName();
			if(Id.equals("code_pr")||Id.equals("nom")||Id.equals("code_mention")
				||Id.equals("structure")||Id.equals("mutualisable")){}
			else {while(Rs.next()) {
				 if(Id.equals(Rs)){ System.out.println("erreur");}
				 else {
			String name = courant.getName();

			// the mysql insert statement
			String query = " insert into Descripteur_champs "
					+ " values (?,?,100,?,0,null,'STRING','COM')";
			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, Id.toUpperCase());
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, name);
			
			// execute the preparedstatement
			try{preparedStmt.execute();}
			catch(Exception e1){
				}	
		}}}		}	

	Element ObjetAN = racine.getChild("annee");
	List<Element> listFieldAN = ObjetAN.getChildren();
	Iterator<Element> e1 = listFieldAN.iterator();
	while (e1.hasNext()) {
		Element courant = (Element)e1.next();
		 courant.getChildren();
			String Id = courant.getName();
			if(Id.equals("code_pr")||Id.equals("nom")||Id.equals("code_mention")
				||Id.equals("structure")||Id.equals("mutualisable")){}
			else {while(Rs.next()) {
				 if(Id.equals(Rs)){ System.out.println("erreur");}
				 else {
			String name = courant.getName();

			// the mysql insert statement
			String query = " insert into Descripteur_champs "
					+ " values (?,?,100,?,0,null,'STRING','COM')";
			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, Id.toUpperCase());
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, name);
			
			// execute the preparedstatement
			try{preparedStmt.execute();}
			catch(Exception e11){
				}	
		}}}		}	

	Element ObjetdGR = racine.getChild("groupe");
	List<Element> listFieldGR = ObjetdGR.getChildren();
	Iterator<Element> e2 = listFieldGR.iterator();
	while (e2.hasNext()) {
		Element courant = (Element)e2.next();
		 courant.getChildren();
			String Id = courant.getName();
			if(Id.equals("code_pr")||Id.equals("nom")||Id.equals("code_mention")
				||Id.equals("structure")||Id.equals("mutualisable")){}
			else {while(Rs.next()) {
				 if(Id.equals(Rs)){ System.out.println("erreur");}
				 else {
			String name = courant.getName();

			// the mysql insert statement
			String query = " insert into Descripteur_champs "
					+ " values (?,?,100,?,0,null,'STRING','COM')";
			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, Id.toUpperCase());
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, name);
			
			// execute the preparedstatement
			try{preparedStmt.execute();}
			catch(Exception e11){
				}	
		}}}		}
	
	Element ObjetSem = racine.getChild("semestre");
	List<Element> listFieldSe = ObjetSem.getChildren();
	Iterator<Element> r = listFieldSe.iterator();
	while (r.hasNext()) {
		Element courant = (Element)r.next();
		 courant.getChildren();
			String Id = courant.getName();
			if(Id.equals("err")||Id.equals("nom")||Id.equals("responsables")
					||Id.equals("contributeurs")||Id.equals("tree")
					||Id.equals("structure")||Id.equals("liste-objets")||Id.equals("refs-composante")){}
			else {while(Rs.next()) {
				 if(Id.equals(Rs)){ System.out.println("erreur");}
				 else {
			String name = courant.getName();

			// the mysql insert statement
			String query = " insert into Descripteur_champs "
					+ " values (?,?,100,?,0,null,'STRING','COM')";
			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, Id.toUpperCase());
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, name);
			
			// execute the preparedstatement
			try{preparedStmt.execute();}
			catch(Exception e3){
				}	
		}}}	}		
	Rs.close();	 
	
	
	/**
	 * alimmentation des fieldObjet
	 */

	List<Element> Objet1 = racine.getChildren("mention");
	Iterator<Element> j11 = Objet1.iterator();

	while (j11.hasNext()) {

		Element courant = (Element) j11.next();
		String Code_objet = courant.getAttributeValue("code");

		List<Element> listField1 = courant.getChildren();
		Iterator<Element> k = listField1.iterator();

		while (k.hasNext()) {
			Element courant1 = (Element) k.next();

			if (!courant1.getValue().isEmpty()) {
				String Id = courant1.getName();

				if (Id.equals("err") || Id.equals("nom")
						|| Id.equals("responsables")
						|| Id.equals("contributeurs") || Id.equals("tree")
						|| Id.equals("structure")
						|| Id.equals("liste-objets")
						|| Id.equals("refs-composante")) {
				} else {
					String content_type = "";
					// quant c'esst un body on prends toutes la descendance
					if (courant1.getChildren().size() > 0) {
						Element content = courant1.getChild("body");
						if(courant1.getChildren().get(0).getName().equals("body")){
							XMLOutputter outp = new XMLOutputter();
							
						    outp.setFormat(Format.getCompactFormat());

						    StringWriter sw = new StringWriter();
						    outp.output(content.getContent(), sw);
						    StringBuffer sb = sw.getBuffer();
						    content_type=sb.toString();
						}
						else{
						Iterator<Element> h = courant1.getChildren().iterator();
						for (; h.hasNext();) {
							Element courant2 = (Element) h.next();
							if (courant2.getValue().isEmpty())
								break;
							content_type += courant2.getValue();
							if (h.hasNext())
								content_type += ',';
						}
						}
						
					} else if (!courant1.getValue().isEmpty()) {
						content_type = courant1.getValue();
					}

					if (content_type.isEmpty())
						break;
					// the mysql insert statement
					String query = " insert into A_pour_champs "
							+ "values (?,?,?)";
					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = conn
							.prepareStatement(query);
					preparedStmt.setString(1, Id);
					preparedStmt.setString(2, Code_objet);
					preparedStmt.setString(3, content_type);

					// execute the preparedstatement
					try {
						preparedStmt.execute();
					} catch (Exception e11) {
					}
				}
			}
		}
	}
	
	List<Element> ObjetEN = racine.getChildren("enseignement");
	Iterator<Element> j2 = ObjetEN.iterator();

	while (j2.hasNext()) {

		Element courant = (Element) j2.next();
		String Code_objet = courant.getAttributeValue("code");

		List<Element> listField1 = courant.getChildren();
		Iterator<Element> k = listField1.iterator();

		while (k.hasNext()) {
			Element courant1 = (Element) k.next();

			if (!courant1.getValue().isEmpty()) {
				String Id = courant1.getName();

				if (Id.equals("err") || Id.equals("nom")
						|| Id.equals("responsables")
						|| Id.equals("contributeurs") || Id.equals("tree")
						|| Id.equals("structure")
						|| Id.equals("liste-objets")
						|| Id.equals("refs-composante")) {
				} else {
					String content_type = "";
					// quant c'esst un body on prends toutes la descendance
					if (courant1.getChildren().size() > 0) {
						Element content = courant1.getChild("body");
						if(courant1.getChildren().get(0).getName().equals("body")){
							XMLOutputter outp = new XMLOutputter();
							
						    outp.setFormat(Format.getCompactFormat());

						    StringWriter sw = new StringWriter();
						    outp.output(content.getContent(), sw);
						    StringBuffer sb = sw.getBuffer();
						    content_type=sb.toString();
						}
						else{
						Iterator<Element> h = courant1.getChildren().iterator();
						for (; h.hasNext();) {
							Element courant2 = (Element) h.next();
							if (courant2.getValue().isEmpty())
								break;
							content_type += courant2.getValue();
							if (h.hasNext())
								content_type += ',';
						}
						}
						
					} else if (!courant1.getValue().isEmpty()) {
						content_type = courant1.getValue();
					}

					if (content_type.isEmpty())
						break;
					// the mysql insert statement
					String query = " insert into A_pour_champs "
							+ "values (?,?,?)";
					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = conn
							.prepareStatement(query);
					preparedStmt.setString(1, Id);
					preparedStmt.setString(2, Code_objet);
					preparedStmt.setString(3, content_type);

					// execute the preparedstatement
					try {
						preparedStmt.execute();
					} catch (Exception e11) {
					}
				}
			}
		}
	}
	
	List<Element> ObjetSp = racine.getChildren("specialite");
	Iterator<Element> j3 = ObjetSp.iterator();

	while (j3.hasNext()) {

		Element courant = (Element) j3.next();
		String Code_objet = courant.getAttributeValue("code");

		List<Element> listField1 = courant.getChildren();
		Iterator<Element> k = listField1.iterator();

		while (k.hasNext()) {
			Element courant1 = (Element) k.next();

			if (!courant1.getValue().isEmpty()) {
				String Id = courant1.getName();

				if (Id.equals("err") || Id.equals("nom")
						|| Id.equals("responsables")
						|| Id.equals("contributeurs") || Id.equals("tree")
						|| Id.equals("structure")
						|| Id.equals("liste-objets")
						|| Id.equals("refs-composante")) {
				} else {
					String content_type = "";
					// quant c'esst un body on prends toutes la descendance
					if (courant1.getChildren().size() > 0) {
						Element content = courant1.getChild("body");
						if(courant1.getChildren().get(0).getName().equals("body")){
							XMLOutputter outp = new XMLOutputter();
							
						    outp.setFormat(Format.getCompactFormat());

						    StringWriter sw = new StringWriter();
						    outp.output(content.getContent(), sw);
						    StringBuffer sb = sw.getBuffer();
						    content_type=sb.toString();
						}
						else{
						Iterator<Element> h = courant1.getChildren().iterator();
						for (; h.hasNext();) {
							Element courant2 = (Element) h.next();
							if (courant2.getValue().isEmpty())
								break;
							content_type += courant2.getValue();
							if (h.hasNext())
								content_type += ',';
						}
						}
						
					} else if (!courant1.getValue().isEmpty()) {
						content_type = courant1.getValue();
					}

					if (content_type.isEmpty())
						break;
					// the mysql insert statement
					String query = " insert into A_pour_champs "
							+ "values (?,?,?)";
					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = conn
							.prepareStatement(query);
					preparedStmt.setString(1, Id);
					preparedStmt.setString(2, Code_objet);
					preparedStmt.setString(3, content_type);

					// execute the preparedstatement
					try {
						preparedStmt.execute();
					} catch (Exception e11) {
					}
				}
			}
		}
	}
	List<Element> ObjetPR = racine.getChildren("programme");
	Iterator<Element> j4 = ObjetPR.iterator();

	while (j4.hasNext()) {

		Element courant = (Element) j4.next();
		String Code_objet = courant.getAttributeValue("code");

		List<Element> listField1 = courant.getChildren();
		Iterator<Element> k = listField1.iterator();

		while (k.hasNext()) {
			Element courant1 = (Element) k.next();

			if (!courant1.getValue().isEmpty()) {
				String Id = courant1.getName();

				if (Id.equals("err") || Id.equals("nom")
						|| Id.equals("responsables")
						|| Id.equals("contributeurs") || Id.equals("tree")
						|| Id.equals("structure")
						|| Id.equals("liste-objets")
						|| Id.equals("refs-composante")) {
				} else {
					String content_type = "";
					// quant c'esst un body on prends toutes la descendance
					if (courant1.getChildren().size() > 0) {
						Element content = courant1.getChild("body");
						if(courant1.getChildren().get(0).getName().equals("body")){
							XMLOutputter outp = new XMLOutputter();
							
						    outp.setFormat(Format.getCompactFormat());

						    StringWriter sw = new StringWriter();
						    outp.output(content.getContent(), sw);
						    StringBuffer sb = sw.getBuffer();
						    content_type=sb.toString();
						}
						else{
						Iterator<Element> h = courant1.getChildren().iterator();
						for (; h.hasNext();) {
							Element courant2 = (Element) h.next();
							if (courant2.getValue().isEmpty())
								break;
							content_type += courant2.getValue();
							if (h.hasNext())
								content_type += ',';
						}
						}
						
					} else if (!courant1.getValue().isEmpty()) {
						content_type = courant1.getValue();
					}

					if (content_type.isEmpty())
						break;
					// the mysql insert statement
					String query = " insert into A_pour_champs "
							+ "values (?,?,?)";
					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = conn
							.prepareStatement(query);
					preparedStmt.setString(1, Id);
					preparedStmt.setString(2, Code_objet);
					preparedStmt.setString(3, content_type);

					// execute the preparedstatement
					try {
						preparedStmt.execute();
					} catch (Exception e11) {
					}
				}
			}
		}
	}
	
	List<Element> ObjetSem1 = racine.getChildren("semestre");
	Iterator<Element> j5 = ObjetSem1.iterator();

	while (j5.hasNext()) {

		Element courant = (Element) j5.next();
		String Code_objet = courant.getAttributeValue("code");

		List<Element> listField1 = courant.getChildren();
		Iterator<Element> k = listField1.iterator();

		while (k.hasNext()) {
			Element courant1 = (Element) k.next();

			if (!courant1.getValue().isEmpty()) {
				String Id = courant1.getName();

				if (Id.equals("err") || Id.equals("nom")
						|| Id.equals("responsables")
						|| Id.equals("contributeurs") || Id.equals("tree")
						|| Id.equals("structure")
						|| Id.equals("liste-objets")
						|| Id.equals("refs-composante")) {
				} else {
					String content_type = "";
					// quant c'esst un body on prends toutes la descendance
					if (courant1.getChildren().size() > 0) {
						Element content = courant1.getChild("body");
						if(courant1.getChildren().get(0).getName().equals("body")){
							XMLOutputter outp = new XMLOutputter();
							
						    outp.setFormat(Format.getCompactFormat());

						    StringWriter sw = new StringWriter();
						    outp.output(content.getContent(), sw);
						    StringBuffer sb = sw.getBuffer();
						    content_type=sb.toString();
						}
						else{
						Iterator<Element> h = courant1.getChildren().iterator();
						for (; h.hasNext();) {
							Element courant2 = (Element) h.next();
							if (courant2.getValue().isEmpty())
								break;
							content_type += courant2.getValue();
							if (h.hasNext())
								content_type += ',';
						}
						}
						
					} else if (!courant1.getValue().isEmpty()) {
						content_type = courant1.getValue();
					}

					if (content_type.isEmpty())
						break;
					// the mysql insert statement
					String query = " insert into A_pour_champs "
							+ "values (?,?,?)";
					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = conn
							.prepareStatement(query);
					preparedStmt.setString(1, Id);
					preparedStmt.setString(2, Code_objet);
					preparedStmt.setString(3, content_type);

					// execute the preparedstatement
					try {
						preparedStmt.execute();
					} catch (Exception e11) {
					}
				}
			}
		}
	}
	
	List<Element> ObjetOP = racine.getChildren("option");
	Iterator<Element> j6 = ObjetOP.iterator();

	while (j6.hasNext()) {

		Element courant = (Element) j6.next();
		String Code_objet = courant.getAttributeValue("code");

		List<Element> listField1 = courant.getChildren();
		Iterator<Element> k = listField1.iterator();

		while (k.hasNext()) {
			Element courant1 = (Element) k.next();

			if (!courant1.getValue().isEmpty()) {
				String Id = courant1.getName();

				if (Id.equals("err") || Id.equals("nom")
						|| Id.equals("responsables")
						|| Id.equals("contributeurs") || Id.equals("tree")
						|| Id.equals("structure")
						|| Id.equals("liste-objets")
						|| Id.equals("refs-composante")) {
				} else {
					String content_type = "";
					// quant c'esst un body on prends toutes la descendance
					if (courant1.getChildren().size() > 0) {
						Element content = courant1.getChild("body");
						if(courant1.getChildren().get(0).getName().equals("body")){
							XMLOutputter outp = new XMLOutputter();
							
						    outp.setFormat(Format.getCompactFormat());

						    StringWriter sw = new StringWriter();
						    outp.output(content.getContent(), sw);
						    StringBuffer sb = sw.getBuffer();
						    content_type=sb.toString();
						}
						else{
						Iterator<Element> h = courant1.getChildren().iterator();
						for (; h.hasNext();) {
							Element courant2 = (Element) h.next();
							if (courant2.getValue().isEmpty())
								break;
							content_type += courant2.getValue();
							if (h.hasNext())
								content_type += ',';
						}
						}
						
					} else if (!courant1.getValue().isEmpty()) {
						content_type = courant1.getValue();
					}

					if (content_type.isEmpty())
						break;
					// the mysql insert statement
					String query = " insert into A_pour_champs "
							+ "values (?,?,?)";
					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = conn
							.prepareStatement(query);
					preparedStmt.setString(1, Id);
					preparedStmt.setString(2, Code_objet);
					preparedStmt.setString(3, content_type);

					// execute the preparedstatement
					try {
						preparedStmt.execute();
					} catch (Exception e11) {
					}
				}
			}
		}
	}
	List<Element> ObjetGR = racine.getChildren("groupe");
	Iterator<Element> j7 = ObjetGR.iterator();

	while (j7.hasNext()) {

		Element courant = (Element) j7.next();
		String Code_objet = courant.getAttributeValue("code");

		List<Element> listField1 = courant.getChildren();
		Iterator<Element> k = listField1.iterator();

		while (k.hasNext()) {
			Element courant1 = (Element) k.next();

			if (!courant1.getValue().isEmpty()) {
				String Id = courant1.getName();

				if (Id.equals("err") || Id.equals("nom")
						|| Id.equals("responsables")
						|| Id.equals("contributeurs") || Id.equals("tree")
						|| Id.equals("structure")
						|| Id.equals("liste-objets")
						|| Id.equals("refs-composante")) {
				} else {
					String content_type = "";
					// quant c'esst un body on prends toutes la descendance
					if (courant1.getChildren().size() > 0) {
						Element content = courant1.getChild("body");
						if(courant1.getChildren().get(0).getName().equals("body")){
							XMLOutputter outp = new XMLOutputter();
							
						    outp.setFormat(Format.getCompactFormat());

						    StringWriter sw = new StringWriter();
						    outp.output(content.getContent(), sw);
						    StringBuffer sb = sw.getBuffer();
						    content_type=sb.toString();
						}
						else{
						Iterator<Element> h = courant1.getChildren().iterator();
						for (; h.hasNext();) {
							Element courant2 = (Element) h.next();
							if (courant2.getValue().isEmpty())
								break;
							content_type += courant2.getValue();
							if (h.hasNext())
								content_type += ',';
						}
						}
						
					} else if (!courant1.getValue().isEmpty()) {
						content_type = courant1.getValue();
					}

					if (content_type.isEmpty())
						break;
					// the mysql insert statement
					String query = " insert into A_pour_champs "
							+ "values (?,?,?)";
					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = conn
							.prepareStatement(query);
					preparedStmt.setString(1, Id);
					preparedStmt.setString(2, Code_objet);
					preparedStmt.setString(3, content_type);

					// execute the preparedstatement
					try {
						preparedStmt.execute();
					} catch (Exception e11) {
					}
				}
			}
		}
	}
	
	List<Element> ObjetAN1 = racine.getChildren("annee");
	Iterator<Element> j8 = ObjetAN1.iterator();

	while (j8.hasNext()) {

		Element courant = (Element) j8.next();
		String Code_objet = courant.getAttributeValue("code");

		List<Element> listField1 = courant.getChildren();
		Iterator<Element> k = listField1.iterator();

		while (k.hasNext()) {
			Element courant1 = (Element) k.next();

			if (!courant1.getValue().isEmpty()) {
				String Id = courant1.getName();

				if (Id.equals("err") || Id.equals("nom")
						|| Id.equals("responsables")
						|| Id.equals("contributeurs") || Id.equals("tree")
						|| Id.equals("structure")
						|| Id.equals("liste-objets")
						|| Id.equals("refs-composante")) {
				} else {
					String content_type = "";
					// quant c'esst un body on prends toutes la descendance
					if (courant1.getChildren().size() > 0) {
						Element content = courant1.getChild("body");
						if(courant1.getChildren().get(0).getName().equals("body")){
							XMLOutputter outp = new XMLOutputter();
							
						    outp.setFormat(Format.getCompactFormat());

						    StringWriter sw = new StringWriter();
						    outp.output(content.getContent(), sw);
						    StringBuffer sb = sw.getBuffer();
						    content_type=sb.toString();
						}
						else{
						Iterator<Element> h = courant1.getChildren().iterator();
						for (; h.hasNext();) {
							Element courant2 = (Element) h.next();
							if (courant2.getValue().isEmpty())
								break;
							content_type += courant2.getValue();
							if (h.hasNext())
								content_type += ',';
						}
						}
						
					} else if (!courant1.getValue().isEmpty()) {
						content_type = courant1.getValue();
					}

					if (content_type.isEmpty())
						break;
					// the mysql insert statement
					String query = " insert into A_pour_champs "
							+ "values (?,?,?)";
					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = conn
							.prepareStatement(query);
					preparedStmt.setString(1, Id);
					preparedStmt.setString(2, Code_objet);
					preparedStmt.setString(3, content_type);

					// execute the preparedstatement
					try {
						preparedStmt.execute();
					} catch (Exception e11) {
					}
				}
			}
		}
	}
	
	/**
	 * alimmentation des fils 
	 * */
	
	int rang;
	int i11=1;
	
	String query = " insert into fils "
			+ " values (?,?,?)";
	String query11 = " insert into objet_fils "
			+ " values (?,?)";
	PreparedStatement preparedStmt = conn.prepareStatement(query);
	PreparedStatement preparedStmt11 = conn.prepareStatement(query11);
			
	List<Element> Objet11 = racine.getChildren("mention");
	 Iterator<Element> i51 = Objet11.iterator(); 
	  while (i51.hasNext()) 
	  { 
		  Element Objetp = (Element) i51.next(); 
		  String codeObjet = Objetp.getAttributeValue("code");
		  Element objetStructure = Objetp.getChild("structure");
		  List<Element> ObjetRef = objetStructure.getChildren();
		 Iterator<Element> i7 = ObjetRef.iterator(); 
		  while (i7.hasNext()) 
		  { 
			  Element Objetf = (Element)i7.next();
			  String code_objet = Objetf.getAttributeValue("ref");
					
			if(ObjetRef.isEmpty()){System.out.println("pas de fils");}
			else {
			rang =1;

						preparedStmt.setInt(1, i11);
						preparedStmt.setInt(2, rang);
						preparedStmt.setString(3, code_objet);
						try {preparedStmt.execute();}catch(Exception e11){}
						
						preparedStmt11.setString(1, codeObjet);
						preparedStmt11.setInt(2, i11);
						try {preparedStmt11.execute();}catch(Exception e11){}
						i11++;
			 }
			}	
			}
	
	List<Element> Objet111 = racine.getChildren("specialite");
	 Iterator<Element> i511 = Objet111.iterator(); 
	  while (i511.hasNext()) 
	  { 
		  Element Objetp = (Element) i511.next(); 
		  String codeObjet = Objetp.getAttributeValue("code");
		  Element objetStructure = Objetp.getChild("structure");
		  List<Element> ObjetRef = objetStructure.getChildren();
		 Iterator<Element> i7 = ObjetRef.iterator(); 
		  while (i7.hasNext()) 
		  { 
			  Element Objetf = (Element)i7.next();
			  String code_objet = Objetf.getAttributeValue("ref");
					
			if(ObjetRef.isEmpty()){System.out.println("pas de fils");}
			else {
			rang =1;

						preparedStmt.setInt(1, i11);
						preparedStmt.setInt(2, rang);
						preparedStmt.setString(3, code_objet);
						try {preparedStmt.execute();}catch(Exception e11){}
						
						preparedStmt11.setString(1, codeObjet);
						preparedStmt11.setInt(2, i11);
						try {preparedStmt11.execute();}catch(Exception e11){}
						i11++;
			 }
			}	
			}
	  
		List<Element> Objet1111 = racine.getChildren("programme");
		 Iterator<Element> i5111 = Objet1111.iterator(); 
		  while (i5111.hasNext()) 
		  { 
			  Element Objetp = (Element) i5111.next(); 
			  String codeObjet = Objetp.getAttributeValue("code");
			  Element objetStructure = Objetp.getChild("structure");
			  List<Element> ObjetRef = objetStructure.getChildren();
			 Iterator<Element> i7 = ObjetRef.iterator(); 
			  while (i7.hasNext()) 
			  { 
				  Element Objetf = (Element)i7.next();
				  String code_objet = Objetf.getAttributeValue("ref");
						
				if(ObjetRef.isEmpty()){System.out.println("pas de fils");}
				else {
				rang =1;

							preparedStmt.setInt(1, i11);
							preparedStmt.setInt(2, rang);
							preparedStmt.setString(3, code_objet);
							try {preparedStmt.execute();}catch(Exception e11){}
							
							preparedStmt11.setString(1, codeObjet);
							preparedStmt11.setInt(2, i11);
							try {preparedStmt11.execute();}catch(Exception e11){}
							i11++;
				 }
				}	
				}
		  
			List<Element> ObjetOP1 = racine.getChildren("option");
			 Iterator<Element> i53 = ObjetOP1.iterator(); 
			  while (i53.hasNext()) 
			  { 
				  Element Objetp = (Element) i53.next(); 
				  String codeObjet = Objetp.getAttributeValue("code");
				  Element objetStructure = Objetp.getChild("structure");
				  List<Element> ObjetRef = objetStructure.getChildren();
				 Iterator<Element> i7 = ObjetRef.iterator(); 
				  while (i7.hasNext()) 
				  { 
					  Element Objetf = (Element)i7.next();
					  String code_objet = Objetf.getAttributeValue("ref");
							
					if(ObjetRef.isEmpty()){System.out.println("pas de fils");}
					else {
					rang =1;

								preparedStmt.setInt(1, i11);
								preparedStmt.setInt(2, rang);
								preparedStmt.setString(3, code_objet);
								try {preparedStmt.execute();}catch(Exception e11){}
								
								preparedStmt11.setString(1, codeObjet);
								preparedStmt11.setInt(2, i11);
								try {preparedStmt11.execute();}catch(Exception e11){}
								i11++;
					 }
					}	
					}
				  
				List<Element> ObjetSEM = racine.getChildren("semestre");
				 Iterator<Element> i54 = ObjetSEM.iterator(); 
				  while (i54.hasNext()) 
				  { 
					  Element Objetp = (Element) i54.next(); 
					  String codeObjet = Objetp.getAttributeValue("code");
					  Element objetStructure = Objetp.getChild("structure");
					  List<Element> ObjetRef = objetStructure.getChildren();
					 Iterator<Element> i7 = ObjetRef.iterator(); 
					  while (i7.hasNext()) 
					  { 
						  Element Objetf = (Element)i7.next();
						  String code_objet = Objetf.getAttributeValue("ref");
								
						if(ObjetRef.isEmpty()){System.out.println("pas de fils");}
						else {
						rang =1;

									preparedStmt.setInt(1, i11);
									preparedStmt.setInt(2, rang);
									preparedStmt.setString(3, code_objet);
									try {preparedStmt.execute();}catch(Exception e11){}
									
									preparedStmt11.setString(1, codeObjet);
									preparedStmt11.setInt(2, i11);
									try {preparedStmt11.execute();}catch(Exception e11){}
									i11++;
						 }
						}	
						}
				  
					List<Element> ObjetGR1 = racine.getChildren("groupe");
					 Iterator<Element> i55 = ObjetGR1.iterator(); 
					  while (i55.hasNext()) 
					  { 
						  Element Objetp = (Element) i55.next(); 
						  String codeObjet = Objetp.getAttributeValue("code");
						  Element objetStructure = Objetp.getChild("structure");
						  List<Element> ObjetRef = objetStructure.getChildren();
						 Iterator<Element> i7 = ObjetRef.iterator(); 
						  while (i7.hasNext()) 
						  { 
							  Element Objetf = (Element)i7.next();
							  String code_objet = Objetf.getAttributeValue("ref");
									
							if(ObjetRef.isEmpty()){System.out.println("pas de fils");}
							else {
							rang =1;

										preparedStmt.setInt(1, i11);
										preparedStmt.setInt(2, rang);
										preparedStmt.setString(3, code_objet);
										try {preparedStmt.execute();}catch(Exception e11){}
										
										preparedStmt11.setString(1, codeObjet);
										preparedStmt11.setInt(2, i11);
										try {preparedStmt11.execute();}catch(Exception e11){}
										i11++;
							 }
							}	
							}
					  
						List<Element> ObjetAN11 = racine.getChildren("annee");
						 Iterator<Element> i56 = ObjetAN11.iterator(); 
						  while (i56.hasNext()) 
						  { 
							  Element Objetp = (Element) i56.next(); 
							  String codeObjet = Objetp.getAttributeValue("code");
							  Element objetStructure = Objetp.getChild("structure");
							  List<Element> ObjetRef = objetStructure.getChildren();
							 Iterator<Element> i7 = ObjetRef.iterator(); 
							  while (i7.hasNext()) 
							  { 
								  Element Objetf = (Element)i7.next();
								  String code_objet = Objetf.getAttributeValue("ref");
										
								if(ObjetRef.isEmpty()){System.out.println("pas de fils");}
								else {
								rang =1;

											preparedStmt.setInt(1, i11);
											preparedStmt.setInt(2, rang);
											preparedStmt.setString(3, code_objet);
											try {preparedStmt.execute();}catch(Exception e11){}
											
											preparedStmt11.setString(1, codeObjet);
											preparedStmt11.setInt(2, i11);
											try {preparedStmt11.execute();}catch(Exception e11){}
											i11++;
								 }
								}	
								}	  
	  conn.close(); 
}
}
