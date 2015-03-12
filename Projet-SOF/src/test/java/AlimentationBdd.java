import java.io.File;
import java.io.IOException;
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
			// execute the preparedstatement
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

		 List<Element> ObjetEnseigement = racine.getChildren("enseignement");
		 Iterator<Element> i3 = ObjetEnseigement.iterator();
		  
		  while (i3.hasNext()) 
		  { 
			  Element courant = (Element) i3.next(); 
		  String Code_Objet = courant.getAttributeValue("code"); 
		  String nom = courant.getChild("nom").getText();
		  int muta;
		  String mut = courant.getChild("mutualisable").getText();
		  String version = courant.getChild("version").getText(); 
		  String contexte =  courant.getChild("code_mention").getText();
		  if(contexte.length()<0){
			  
			  String query = " insert into Objet " +
					  " values (?,?, ?,?,null,'ENS')";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
		  switch(mut) {
		  case "N":
			  muta=0;
					// the mysql insert statement
				  
					// create the mysql insert preparedstatement
					 preparedStmt.setString  (1, Code_Objet); 
					  preparedStmt.setString (3, nom);
					  preparedStmt.setInt (2, muta);
					  preparedStmt.setString (4, version); 
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
					  preparedStmt.setString (4, version); 
					// execute the preparedstatement
					preparedStmt.execute();
				}}
				
			  else {
				  
			  // the mysql insert statement 
			   String query = " insert into Objet " +
			  " values (?,?, ?,?,?,'ENS')";
			  
			  // create the mysql insert preparedstatement 
			  PreparedStatement  preparedStmt = conn.prepareStatement(query); 
			  switch(mut) {
			  case "N":
				  muta=0;					  
						// create the mysql insert preparedstatement
						 preparedStmt.setString  (1, Code_Objet); 
						  preparedStmt.setString (3, nom);
						  preparedStmt.setInt (2, muta);
						  preparedStmt.setString (4, version);
						  preparedStmt.setString (5, contexte); 

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
						  preparedStmt.setString (4, version); 
						  preparedStmt.setString (5, contexte); 

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
		  }}
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
						  " values (?,?, ?,1,?,'SEM')";
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
						   " values (?,?, ?,1,?,'SEM')";
				  
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
					  " values (?,?, ?,1,?,'OPT')";
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
						  " values (?,?, ?,1,?,'OPT')";
			  
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
							||Id.equals("contributeurs")){}
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
					try{preparedStmt.execute();}
					catch(Exception e1){
						}				}}		

			}
			
			Element ObjetPr = racine.getChild("programme");
			List<Element> listFieldPr = ObjetPr.getChildren();
			Iterator<Element> z = listFieldPr.iterator();
			while (z.hasNext()) {
				Element courant = (Element)z.next();
				    courant.getChildren();
					String Id = courant.getName();
					while(Rs.next()) {
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
						}					}}}				

			
			Element ObjetOp = racine.getChild("option");
			List<Element> listFieldOp = ObjetOp.getChildren();
			Iterator<Element> e = listFieldOp.iterator();
			while (e.hasNext()) {
				Element courant = (Element)e.next();
				    courant.getChildren();
					String Id = courant.getName();
					while(Rs.next()) {
						 if(Id.equals(Rs)){ System.out.println("erreur");}
						 else {
					String name = courant.getName();

					// the mysql insert statement
					String query = " insert into Descripteur_champs "
							+ " values (?,?,100,?,0,null,'STRING','OPT')";
					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.setString(1, Id.toUpperCase());
					preparedStmt.setString(2, name);
					preparedStmt.setString(3, name);
					
					// execute the preparedstatement
					try{preparedStmt.execute();}
					catch(Exception e1){
						}	
				}}}				

			
			Element ObjetSem = racine.getChild("semestre");
			List<Element> listFieldSe = ObjetSem.getChildren();
			Iterator<Element> r = listFieldSe.iterator();
			while (r.hasNext()) {
				Element courant = (Element)r.next();
				    courant.getChildren();
					String Id = courant.getName();
					while(Rs.next()) {
						 if(Id.equals(Rs)){ System.out.println("erreur");}
						 else {
					String name = courant.getName();

					// the mysql insert statement
					String query = " insert into Descripteur_champs "
							+ " values (?,?,100,?,0,null,'STRING','SEM')";
					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.setString(1, Id.toUpperCase());
					preparedStmt.setString(2, name);
					preparedStmt.setString(3, name);
					
					// execute the preparedstatement
					try{preparedStmt.execute();}
					catch(Exception e1){
						}	
				}}}			

			Element ObjetEns = racine.getChild("enseignement");
			List<Element> listFieldEn = ObjetEns.getChildren();
			Iterator<Element> t = listFieldEn.iterator();
			while (t.hasNext()) {
				Element courant = (Element)t.next();
				    courant.getChildren();
					String Id = courant.getName();
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
				}}}				
			Rs.close();	

			/**
			 * alimmentation des fieldObjet 
			 * */
			
			List<Element> Objet1 = racine.getChildren("mention");
			Iterator<Element> j11 = Objet1.iterator();
			
			while (j11.hasNext()) {
				
					Element courant = (Element)j11.next();
				
				    String Code_objet = courant.getAttributeValue("code");
				
				    List<Element> listField1 = courant.getChildren();
				    Iterator<Element> k = listField1.iterator();
				    
				    while (k.hasNext()) {	
					Element courant1 = (Element)k.next();
					String Id = courant1.getName();
					
					if(Id.equals("err")||Id.equals("nom")||Id.equals("responsables")
							||Id.equals("contributeurs")){ }
					else {
					String content_type = ""; 
				    Iterator<Element> h = courant1.getChildren().iterator();
				    for(;h.hasNext();){
						Element courant2 = (Element)h.next();
						content_type+=courant2.getValue()+',';
				    }
					// the mysql insert statement
					String query = " insert into A_pour_champs "
							+ "values (?,?,?)";
					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.setString(1, Id);
					preparedStmt.setString(2, Code_objet);
					preparedStmt.setString(3, content_type);
					
					// execute the preparedstatement
					try{preparedStmt.execute();}
					catch(Exception e1){
						}
					}
					}
				}
			
			 List<Element> ObjetEn = racine.getChildren("Enseignement");
				Iterator<Element> r1 = ObjetEn.iterator();
				while (r1.hasNext()) {
					Element courant = (Element)r1.next();
					    String Code_objet = courant.getAttributeValue("code");
					    
					    List<Element> listField1 = courant.getChildren();
					    Iterator<Element> k = listField1.iterator();
					    
					    while (k.hasNext()) {	
						Element courant1 = (Element)k.next();
						String Id = courant1.getName();
						if(Id.equals("err")||Id.equals("nom")||Id.equals("responsables")
								||Id.equals("contributeurs")){}
						else {
							String content_type = ""; 
						    Iterator<Element> h = courant1.getChildren().iterator();
						    for(;h.hasNext();){
								Element courant2 = (Element)h.next();
								content_type+=courant2.getValue()+',';
						    }
						// the mysql insert statement
						String query = " insert into A_pour_champs "
								+ "values (?,?,?)";
						// create the mysql insert preparedstatement
						PreparedStatement preparedStmt = conn.prepareStatement(query);
						preparedStmt.setString(1, Id);
						preparedStmt.setString(2, Code_objet);
						preparedStmt.setString(3, content_type);
						
						// execute the preparedstatement
						try{preparedStmt.execute();}
						catch(Exception e1){
							}
						}}
					}
				
				List<Element> ObjetPr1 = racine.getChildren("programme");
					Iterator<Element> e1 = ObjetPr1.iterator();
					while (e1.hasNext()) {
						Element courant = (Element)e1.next();
						    String Code_objet = courant.getAttributeValue("code");
						    
						    List<Element> listField1 = courant.getChildren();
						    Iterator<Element> k = listField1.iterator();
						    
						    while (k.hasNext()) {	
							Element courant1 = (Element)k.next();
							String Id = courant1.getName();
							if(Id.equals("err")||Id.equals("nom")||Id.equals("responsables")
									||Id.equals("contributeurs")){}
							else {
								String content_type = ""; 
							    Iterator<Element> h = courant1.getChildren().iterator();
							    for(;h.hasNext();){
									Element courant2 = (Element)h.next();
									content_type+=courant2.getValue()+',';
							    }

							// the mysql insert statement
							String query = " insert into A_pour_champs "
									+ "values (?,?,?)";
							// create the mysql insert preparedstatement
							PreparedStatement preparedStmt = conn.prepareStatement(query);
							preparedStmt.setString(1, Id);
							preparedStmt.setString(2, Code_objet);
							preparedStmt.setString(3, content_type);
							
							// execute the preparedstatement
							try{preparedStmt.execute();}
							catch(Exception e11){
								}
							}}
						}
			
			Element ObjetOp1 = racine.getChild("option");
			List<Element> listFieldOp1 = ObjetOp1.getChildren();
			Iterator<Element> s = listFieldOp1.iterator();
			while (s.hasNext()) {
				Element courant = (Element)s.next();
				    courant.getChildren();
				    String Id = courant.getName();
				    String Code_objet = courant.getAttributeValue("code");
						String content_type = ""; 
					    Iterator<Element> h = courant.getChildren().iterator();
					    for(;h.hasNext();){
							Element courant2 = (Element)h.next();
							content_type+=courant2.getValue()+',';
					    }
					// the mysql insert statement
					String query = " insert into A_pour_champs "
							+ "  values (?,?,?)";
					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.setString(1, Id);
					preparedStmt.setString(2, Code_objet);
					preparedStmt.setString(3, content_type);
					
					// execute the preparedstatement
					try{preparedStmt.execute();}
					catch(Exception e111){
						}
				}

			Element ObjetSem1 = racine.getChild("semestre");
			List<Element> listFieldSe1 = ObjetSem1.getChildren();
			Iterator<Element> r11 = listFieldSe1.iterator();
			while (r11.hasNext()) {
				Element courant = (Element)r11.next();
				    courant.getChildren();
				    String Id = courant.getName();
				    String Code_objet = courant.getAttributeValue("code");
				    String content_type = ""; 
				    Iterator<Element> h = courant.getChildren().iterator();
				    for(;h.hasNext();){
						Element courant2 = (Element)h.next();
						content_type+=courant2.getValue()+',';
				    }
					// the mysql insert statement
					String query = " insert into A_pour_champs "
							+ "  values (?,?,?)";
					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.setString(1, Id);
					preparedStmt.setString(2, Code_objet);
					preparedStmt.setString(3, content_type);
					
					// execute the preparedstatement
					try{preparedStmt.execute();}
					catch(Exception e1111){
						}					}

			
			Element ObjetEns1 = racine.getChild("enseignement");
			List<Element> listFieldEn1 = ObjetEns1.getChildren();
			Iterator<Element> t1 = listFieldEn1.iterator();
			while (t1.hasNext()) {
				Element courant = (Element)t1.next();
				    courant.getChildren();
				    String Id = courant.getName();
				    String Code_objet = courant.getAttributeValue("code");
				    String content_type = ""; 
				    Iterator<Element> h = courant.getChildren().iterator();
				    for(;h.hasNext();){
						Element courant2 = (Element)h.next();
						content_type+=courant2.getValue()+',';
				    }
					// the mysql insert statement
					String query = " insert into A_pour_champs "
							+ " values (?,?,?)";
					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.setString(1, Id);
					preparedStmt.setString(2, Code_objet);
					preparedStmt.setString(3, content_type);
					
					// execute the preparedstatement
					try{preparedStmt.execute();}
					catch(Exception e12){
						}					
					}
			/**
			 * alimmentation des fils 
			 * */
			
			String ref;
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
				  List<Element> ObjetPere = Objetp.getChildren("tree");

				 Iterator<Element> i7 = ObjetPere.iterator(); 
				  while (i7.hasNext()) 
				  { 
					  Element Objetf = (Element)i7.next();
					  String code_objet = Objetf.getAttributeValue("ref");
							
				List<Element> ObjetSp = Objetf.getChildren("tree");
				Iterator<Element> j111 = ObjetSp.iterator();
				while (j111.hasNext()) {
					Element courant = (Element)j111.next();
					if(ObjetPere.isEmpty()){System.out.println("pas de fils");}
					else {
					ref = courant.getAttributeValue("ref");
					rang =1;
					
								preparedStmt.setInt(1, i11);
								preparedStmt.setInt(2, rang);
								preparedStmt.setString(3, ref);
								try {preparedStmt.execute();}catch(Exception e11){}
								preparedStmt11.setString(1, code_objet);
								preparedStmt11.setInt(2, i11);
								try {preparedStmt11.execute();}catch(Exception e11){}
								i11++;
						 
								List<Element> ObjetPr11 = courant.getChildren("tree");
								Iterator<Element> j2 = ObjetPr11.iterator();
								while (j2.hasNext()) {
									Element courant1 = (Element)j2.next();
									ref = courant1.getAttributeValue("ref");
									rang =2;

									preparedStmt.setInt(1, i11);
									preparedStmt.setInt(2, rang);
									preparedStmt.setString(3, ref);
									try {preparedStmt.execute();}catch(Exception g){}
									preparedStmt11.setString(1, code_objet);
									preparedStmt11.setInt(2, i11);
									try {preparedStmt11.execute();}catch(Exception g){}
									i11++;
									
									List<Element> ObjetSem11 = courant1.getChildren("tree");
									Iterator<Element> j3 = ObjetSem11.iterator();
									while (j3.hasNext()) {
										Element courant2 = (Element)j3.next();
										ref = courant2.getAttributeValue("ref");
										rang =3;
										
										preparedStmt.setInt(1, i11);
										preparedStmt.setInt(2, rang);
										preparedStmt.setString(3, ref);
										try {preparedStmt.execute();}catch(Exception o){}
										preparedStmt11.setString(1, code_objet);
										preparedStmt11.setInt(2, i11);
										try {preparedStmt11.execute();}catch(Exception o){}
										i11++;
										
										List<Element> ObjetEn1 = courant2.getChildren("tree");
										Iterator<Element> j4 = ObjetEn1.iterator();
										while (j4.hasNext()) {
											Element courant3 = (Element)j4.next();
											ref = courant3.getAttributeValue("ref");
											rang =4;
											
											preparedStmt.setInt(1, i11);
											preparedStmt.setInt(2, rang);
											preparedStmt.setString(3, ref);
											try {preparedStmt.execute();}catch(Exception p){}
											preparedStmt11.setString(1, code_objet);
											preparedStmt11.setInt(2, i11);
											try {preparedStmt11.execute();}catch(Exception p){}
											i11++;
											
											List<Element> ObjetEns11 = courant3.getChildren("tree");
											Iterator<Element> j5 = ObjetEns11.iterator();
											while (j5.hasNext()) {
												Element courant4 = (Element)j5.next();
												ref = courant4.getAttributeValue("ref");
												rang =5;
							
												preparedStmt.setInt(1, i11);
												preparedStmt.setInt(2, rang);
												preparedStmt.setString(3, ref);
												try {preparedStmt.execute();}catch(Exception c){}
												preparedStmt11.setString(1, code_objet);
												preparedStmt11.setInt(2, i11);
												try {preparedStmt11.execute();}catch(Exception c){}
									}
								}	
					}
					}

				  }}
				}
			}
			  
			  List<Element> Ense = racine.getChildren("specialite");
				 Iterator<Element> i54 = Ense.iterator(); 
				  while (i54.hasNext()) 
				  { 
					  Element ObjetE = (Element) i54.next(); 
					  String code_objet =  ObjetE.getAttributeValue("code");

						Element ObjetPE = ObjetE.getChild("structure");
						List<Element> ObjetR =ObjetPE.getChildren("ref-programme");
						
						Iterator<Element> i7 = ObjetR.iterator(); 
						  while (i7.hasNext()) 
						  { 
							  Element Objetf = (Element)i7.next(); 
							  if(ObjetR.isEmpty()){System.out.println("pas de fils");}
								else {
								ref = Objetf.getAttributeValue("ref");
								rang =1;
											preparedStmt.setInt(1, i11);
											preparedStmt.setInt(2, rang);
											preparedStmt.setString(3, ref);
											preparedStmt.execute();
											preparedStmt11.setString(1, code_objet);
											preparedStmt11.setInt(2, i11);
											preparedStmt11.execute();
											i11++;
										
												
				  }}}
			   
				  List<Element> Pro = racine.getChildren("programme");
					 Iterator<Element> i541 = Pro.iterator(); 
					  while (i541.hasNext()) 
					  { 
						  Element ObjetE = (Element) i541.next(); 
						  String code_objet =  ObjetE.getAttributeValue("code");

							Element ObjetPE = ObjetE.getChild("structure");
							List<Element> ObjetR =ObjetPE.getChildren("ref-semestre");
							
							Iterator<Element> i7 = ObjetR.iterator(); 
							  while (i7.hasNext()) 
							  { 
								  Element Objetf = (Element)i7.next(); 
								  if(ObjetR.isEmpty()){System.out.println("pas de fils");}
									else {
									ref = Objetf.getAttributeValue("ref");
									rang =1;
												preparedStmt.setInt(1, i11);
												preparedStmt.setInt(2, rang);
												preparedStmt.setString(3, ref);
												preparedStmt.execute();
												preparedStmt11.setString(1, code_objet);
												preparedStmt11.setInt(2, i11);
												preparedStmt11.execute();	
												i11++;
												
											
					  }
								  }
							  }
					  List<Element> Opt = racine.getChildren("option");
						 Iterator<Element> i5411 = Opt.iterator(); 
						  while (i5411.hasNext()) 
						  { 
							  Element ObjetE = (Element) i5411.next(); 
							  String code_objet =  ObjetE.getAttributeValue("code");
								Element ObjetPE = ObjetE.getChild("structure");
								List<Element> ObjetR =ObjetPE.getChildren("ref-enseignement");
								
								Iterator<Element> i7 = ObjetR.iterator(); 
								  while (i7.hasNext()) 
								  { 
									  Element Objetf = (Element)i7.next(); 
									  if(ObjetR.isEmpty()){System.out.println("pas de fils");}
										else {
										ref = Objetf.getAttributeValue("ref");
										rang =1;
													preparedStmt.setInt(1, i11);
													preparedStmt.setInt(2, rang);
													preparedStmt.setString(3, ref);
													preparedStmt.execute();
													preparedStmt11.setString(1, code_objet);
													preparedStmt11.setInt(2, i11);
													try {preparedStmt11.execute();}catch(Exception n){}
													i11++;						
						  }}}
						  
						  List<Element> Sem = racine.getChildren("semestre");
							 Iterator<Element> i54111 = Sem.iterator(); 
							  while (i54111.hasNext()) 
							  { 
								  Element ObjetE = (Element) i54111.next(); 
								  String code_objet =  ObjetE.getAttributeValue("code");
									Element ObjetPE = ObjetE.getChild("structure");
									List<Element> ObjetR =ObjetPE.getChildren("ref-enseignement");
									
									Iterator<Element> i7 = ObjetR.iterator(); 
									  while (i7.hasNext()) 
									  { 
										  Element Objetf = (Element)i7.next(); 
										  if(ObjetR.isEmpty()){System.out.println("pas de fils");}
											else {
											ref = Objetf.getAttributeValue("ref");
											rang =1;
														preparedStmt.setInt(1, i11);
														preparedStmt.setInt(2, rang);
														preparedStmt.setString(3, ref);
														try {preparedStmt.execute();}catch(Exception b){}
														preparedStmt11.setString(1, code_objet);
														preparedStmt11.setInt(2, i11);
														try {preparedStmt11.execute();}catch(Exception b){}
														i11++;
											}
									}
							  }
						  
			  conn.close(); 
			conn.close();
	}
}
