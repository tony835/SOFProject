import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
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
			//preparedStmt.setString(2, code_regex);
			preparedStmt.setString(2, Erreur_descritpion);
			preparedStmt.setString(3, content_model);
			preparedStmt.setString(4, Nom);
			// execute the preparedstatement
			preparedStmt.execute();
		}
		/**
		 * alimmentation des fields depuis le fichier de configuration
		 * */
		
		List<Element> listFieldObjet = racine2.getChildren("object_type");
		Iterator<Element> j = listFieldObjet.iterator();

		while (j.hasNext()) {
			Element courant = (Element) j.next();
			String code_type = courant.getChild("code").getText();
			
			List<Element> listFields = courant.getChildren("field");
			Iterator<Element> k = listFields.iterator();
			while (k.hasNext()) {
				Element field = (Element) k.next();
				String Id = field.getChild("Id").getText();
				String name = field.getChild("name").getText();
				String description = field.getChild("description").getText();
				String content_type = field.getChild("content_type").getText();
				String required = field.getChild("required").getText();
				int length = field.getChild("length").getText().hashCode();
				String tab_name = field.getChild("tab_name").getText();
				
				// the mysql insert statement
				String query = " insert into Descripteur_champs "
						+ " values (?,?,?,?,?,?,?,?)";
				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, Id);
				preparedStmt.setString(4, name);
				preparedStmt.setString(2, description);
				preparedStmt.setString(7, content_type);
				preparedStmt.setString(5, required);
				preparedStmt.setInt(3, (int)length);
				preparedStmt.setString(6, tab_name);
				preparedStmt.setString(8, code_type);

				// execute the preparedstatement
				preparedStmt.execute();
			}
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
		  " values (?,null, ?,?,null,'FOR')";
		  
		  // create the mysql insert preparedstatement 
		  PreparedStatement  preparedStmt = conn.prepareStatement(query); 
		  preparedStmt.setString  (1, Code_Objet); 
		  preparedStmt.setString (2, nom);
		  preparedStmt.setString (3, version); 
		  //preparedStmt.setString (4,  contexte);
		  
		  // execute the preparedstatement 
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
				//String domaine;
		/*
				switch(Domaine.getAttributeValue("ref")) {
				  case "ALL":
					   domaine="Arts, Lettres, Langues";
				  case "SS":
					   domaine="Sciences de la santé";
				  case "DEG":
					   domaine="Droit, Économie et Gestion";
				  case "SHS":
					   domaine="Sciences humaines et sociales";
				  case "STAPS":
					   domaine="Sciences et techniques des activités physiques et sportives";
				  case "ST":
					   domaine="Sciences et Technologies";*/
				
				Element Responsable = courant.getChild("responsables");
				if(Responsable.getChildren().isEmpty()){
					// the mysql insert statement
					String query = " insert into Formation "
							+ " values (?,?,null,null,?,'TOTO')";
					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.setString(1, type_diplome);
					preparedStmt.setString(2, domaine);
					preparedStmt.setString(3, Code_Formation);
					// execute the preparedstatement
					preparedStmt.execute();
				}
				else {
				Element RefResponsable = Responsable.getChild("ref-personne");
				if(RefResponsable.getAttributeValue("ref").length()>0){
					String Resp = RefResponsable.getAttributeValue("ref");
					// the mysql insert statement
					String query = " insert into Formation "
							+ " values (?,?,null,null,?,?)";
					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.setString(1, type_diplome);
					preparedStmt.setString(2, domaine);
					preparedStmt.setString(3, Code_Formation);
					preparedStmt.setString(4, Resp);
					// execute the preparedstatement
					preparedStmt.execute();
				}
				}
			}
			//}
		
			/**
			 * alimenter la table objet
			 */

		 List<Element> ObjetEnseigement = racine.getChildren("enseignement");
		 Iterator<Element> i3 = ObjetEnseigement.iterator();
		  
		  while (i3.hasNext()) 
		  { Element courant = (Element) i3.next(); 
		  String Code_Objet = courant.getAttributeValue("code"); 
		  String nom = courant.getChild("nom").getText(); 
		  String version = courant.getChild("version").getText(); 
		  String contexte =  courant.getChild("code_mention").getText();
		  if(contexte.length()>0){
				// the mysql insert statement
			  String query = " insert into Objet " +
					  " values (?,null, ?,?,null,'ENS')";
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
		  " values (?,null, ?,?,?,'ENS')";
		  
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
		  if(contexte.length()>0){
				// the mysql insert statement
			  String query = " insert into Objet " +
					  " values (?,null, ?,?,null,'PRO')";
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
		  " values (?,null, ?,?,?,'PRO')";
		  
		  // create the mysql insert preparedstatement 
		  PreparedStatement  preparedStmt = conn.prepareStatement(query); 
		  preparedStmt.setString  (1, Code_Objet); 
		  preparedStmt.setString (2, nom);
		  preparedStmt.setString (3, version); 
		  preparedStmt.setString (4,  contexte);
		  
		  // execute the preparedstatement 
		  preparedStmt.execute(); 
		  }}
		  /**
			 * alimenter la table objet
			 */
		  List<Element> ObjetSpécialite = racine.getChildren("specialite");
		  Iterator<Element> i5 = ObjetSpécialite.iterator();
		  
		  while (i5.hasNext()) 
		  { Element courant = (Element) i5.next(); 
		  String Code_Objet = courant.getAttributeValue("code"); 
		  String nom = courant.getChild("nom").getText(); 
		  String version = courant.getChild("version").getText(); 
		  String contexte =  courant.getChild("code_mention").getText();
		  if(contexte.length()>0){
				// the mysql insert statement
			  String query = " insert into Objet " +
					  " values (?,null, ?,?,null,'SPE')";
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
		  " values (?,null, ?,?,?,'SPE')";
		  
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
		  /**
			 * alimenter la table objet
			 */
		  List<Element> ObjetSemestre = racine.getChildren("semestre");
		  Iterator<Element> i6 = ObjetSemestre.iterator();
		  
		  while (i6.hasNext()) 
		  { Element courant = (Element) i6.next(); 
		  String Code_Objet = courant.getAttributeValue("code"); 
		  String nom = courant.getChild("nom").getText(); 
		  //String version = courant.getChild("version").getText(); 
		  String contexte =  courant.getChild("code_mention").getText();
		  
		  if(contexte.length()>0){
				// the mysql insert statement
			  String query = " insert into Objet " +
					  " values (?,null, ?,1,null,'SEM')";
				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				 preparedStmt.setString  (1, Code_Objet); 
				  preparedStmt.setString (2, nom);
				  //preparedStmt.setString (3, version); 
				// execute the preparedstatement
				preparedStmt.execute();
			}
		  else {
		  // the mysql insert statement 
		   String query = " insert into Objet " +
		  " values (?,null, ?,1,?,'SEM')";
		  
		  // create the mysql insert preparedstatement 
		  PreparedStatement  preparedStmt = conn.prepareStatement(query); 
		  preparedStmt.setString  (1, Code_Objet); 
		  preparedStmt.setString (2, nom);
		 // preparedStmt.setString (3, version); 
		  preparedStmt.setString (3,  contexte);
		  
		  // execute the preparedstatement 
		  preparedStmt.execute(); 
		  }
		  }
		  
		  /**
			 * alimenter la table objet
			 */
		  List<Element> ObjetOption = racine.getChildren("option");
		  Iterator<Element> i7 = ObjetOption.iterator();
		  
		  while (i7.hasNext()) 
		  { Element courant = (Element) i7.next(); 
		  String Code_Objet = courant.getAttributeValue("code"); 
		  String nom = courant.getChild("nom").getText(); 
		  //String version = courant.getChild("version").getText(); 
		  String contexte =  courant.getChild("code_mention").getText();
		  
		  if(contexte.length()>0){
				// the mysql insert statement
			  String query = " insert into Objet " +
					  " values (?,null, ?,1,null,'OPT')";
				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				 preparedStmt.setString  (1, Code_Objet); 
				  preparedStmt.setString (2, nom);
				  //preparedStmt.setString (3, version); 
				// execute the preparedstatement
				preparedStmt.execute();
			}
		  else {
		  // the mysql insert statement 
		   String query = " insert into Objet " +
		  " values (?,null, ?,1,?,'OPT')";
		  
		  // create the mysql insert preparedstatement 
		  PreparedStatement  preparedStmt = conn.prepareStatement(query); 
		  preparedStmt.setString  (1, Code_Objet); 
		  preparedStmt.setString (2, nom);
		  //preparedStmt.setString (3, version); 
		  preparedStmt.setString (3,  contexte);
		  
		  // execute the preparedstatement 
		  preparedStmt.execute(); 
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
					//System.out.println("pas de fils");
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
		conn.close();
	}
}
