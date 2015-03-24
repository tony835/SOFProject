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
		String myUrl = "jdbc:mysql://localhost:3306/l1001540";
		Class.forName(myDriver);
		Connection conn = DriverManager.getConnection(myUrl, "l1001540", "ZEX9ia");

		/** declarer les fichiers fichiers xml */
		SAXBuilder sxb = new SAXBuilder();
		Document document = sxb.build(new File("/Users/alexandre/Dropbox/fac/projet_sof/Documents_annexes/offre.xml"));
		Element racine = document.getRootElement();
		// the mysql insert statement

		// the mysql insert statement

		String query2 = " UPDATE Objet SET contexte = (select code_objet from formation where code_objet = ?) where code_objet=?";
		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt2 = conn.prepareStatement(query2);

		/**
		 * alimmentation des typesobjets depuis le fichier de configuration
		 * */

		/*
		 * List<Element> listTypeObjet = racine2.getChildren("object_type");
		 * Iterator<Element> i = listTypeObjet.iterator();
		 * 
		 * while (i.hasNext()) { Element courant = (Element) i.next(); String
		 * code_type = courant.getChild("code").getText(); String
		 * Erreur_descritpion = courant.getChild("Erreur_descritpion")
		 * .getText(); String content_model =
		 * courant.getChild("content_model").getText(); String Nom =
		 * courant.getChild("name").getText();
		 * 
		 * // the mysql insert statement String query =
		 * " insert into TypesObjets " + " values (?,?,?,?)"; // create the
		 * mysql insert preparedstatement PreparedStatement preparedStmt =
		 * conn.prepareStatement(query); preparedStmt.setString(1, code_type);
		 * preparedStmt.setString(2, Erreur_descritpion);
		 * preparedStmt.setString(3, content_model); preparedStmt.setString(4,
		 * Nom); preparedStmt.execute(); }
		 */

		/**
		 * alimenter la table personne
		 */

		List<Element> listPerson = racine.getChildren("personne");
		Iterator<Element> i1 = listPerson.iterator();

		while (i1.hasNext()) {
			Element courant = (Element) i1.next();
			String

			Code = courant.getAttribute("code").getValue();
			String Nom = courant.getChild("nom").getText();
			String mail = courant.getChild("mail").getText();
			String prenom = courant.getChild("prenom").getText();

			// the mysql insert statement
			String query = " insert into Person " + " values (?, ?, ?, ?, ?)";

			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, Code);
			preparedStmt.setString(2, prenom);
			preparedStmt.setString(3, mail);
			preparedStmt.setString(4, Nom);
			preparedStmt.setString(5, (Code+'0'));

			// execute the preparedstatement
			try {
				preparedStmt.execute();
			} catch (Exception e) {
				// e.//e.printStackTrace();();
			}

		}

		/**
		 * alimenter la table objet
		 */
		List<Element> ObjetFormation = racine.getChildren("mention");
		Iterator<Element> i2 = ObjetFormation.iterator();

		while (i2.hasNext()) {
			Element courant = (Element) i2.next();
			String Code_Objet = courant.getAttributeValue("code");
			String nom = courant.getChild("nom").getText();
			String version = courant.getChild("version").getText();
			// String contexte = courant.getChild("code_mention").getText();

			// the mysql insert statement
			String query = " insert into Objet "
					+ " values (?,0, ?,?,null,'FOR')";

			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, Code_Objet);
			preparedStmt.setString(2, nom);
			preparedStmt.setString(3, version);
			// preparedStmt.setString (4, Code_Objet);
			try {
				preparedStmt.execute();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.//e.printStackTrace();();
			}
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
			String domaine = "";
			switch (Domaine.getAttributeValue("ref")) {
			case "SS":
				domaine = "Sciences de la santé";
				break;

			case "ALL":
				domaine = "Arts, Lettres, Langues";
				break;
			case "STAPS":
				domaine = "Sciences et techniques des activités physiques et sportives";
				break;
			case "DEG":
				domaine = "Droit, Économie et Gestion";
				break;
			case "SHS":
				domaine = "Sciences humaines et sociales";
				break;
			case "ST":
				domaine = "Sciences et Technologies";
				break;
			}
			Element Responsable = courant.getChild("responsables");
			if (Responsable.getChildren().isEmpty()) {
				// the mysql insert statement
				String query = " insert into Formation "
						+ " values (?,?,0,1,?,'toto')";
				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, type_diplome);
				preparedStmt.setString(2, domaine);
				preparedStmt.setString(3, Code_Formation);
				// execute the preparedstatement
				try {
					preparedStmt.execute();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.//e.printStackTrace();();
				}

			} else {
				Element RefResponsable = Responsable.getChild("ref-personne");
				if (RefResponsable.getAttributeValue("ref").length() > 0) {
					String Resp = RefResponsable.getAttributeValue("ref");
					// the mysql insert statement
					String query = " insert into Formation "
							+ " values (?,?,0,1,?,?)";
					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = conn
							.prepareStatement(query);
					preparedStmt.setString(1, type_diplome);
					preparedStmt.setString(2, domaine);
					preparedStmt.setString(3, Code_Formation);
					preparedStmt.setString(4, Resp);
					// execute the preparedstatement
					try {
						preparedStmt.execute();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						// e.//e.printStackTrace();();
					}

				}

			}

			preparedStmt2.setString(1, Code_Formation);
			preparedStmt2.setString(2, Code_Formation);
			preparedStmt2.execute();
		}

		/**
		 * alimenter la table objet mise a jour
		 */
		/*
		 * List<Element> ObjetFormation2 = racine.getChildren("mention");
		 * Iterator<Element> i21 = ObjetFormation2.iterator();
		 * 
		 * while (i21.hasNext()) { Element courant = (Element) i21.next();
		 * String Code_Objet = courant.getAttributeValue("code");
		 * 
		 * // the mysql insert statement String query2 =
		 * " UPDATE Objet SET contexte = ?"; // create the mysql insert
		 * preparedstatement PreparedStatement preparedStmt2 =
		 * conn.prepareStatement(query2);
		 * 
		 * preparedStmt2.setString(1, Code_Objet); preparedStmt2.execute(); }
		 */

		/**
		 * alimenter la table objet
		 */

		List<Element> ObjetSpecialite = racine.getChildren("specialite");
		Iterator<Element> i5 = ObjetSpecialite.iterator();

		while (i5.hasNext()) {
			Element courant = (Element) i5.next();
			String Code_Objet = courant.getAttributeValue("code");
			String nom = courant.getChild("nom").getText();
			String version = courant.getChild("version").getText();
			String contexte = courant.getChild("code_mention").getText();
			if (contexte.length() < 0) {
				// the mysql insert statement
				String query = " insert into Objet "
						+ " values (?,0, ?,?,null,'SPE')";
				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, Code_Objet);
				preparedStmt.setString(2, nom);
				preparedStmt.setString(3, version);
				// execute the preparedstatement
				preparedStmt.execute();
			} else {
				// the mysql insert statement
				String query = " insert into Objet "
						+ " values (?,0, ?,?,?,'SPE')";

				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, Code_Objet);
				preparedStmt.setString(2, nom);
				preparedStmt.setString(3, version);
				preparedStmt.setString(4, contexte);

				// execute the preparedstatement
				try {
					preparedStmt.execute();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.//e.printStackTrace();();
				}
			}
		}

		List<Element> ObjetSemestre = racine.getChildren("semestre");
		Iterator<Element> i6 = ObjetSemestre.iterator();

		while (i6.hasNext()) {
			Element courant = (Element) i6.next();
			String Code_Objet = courant.getAttributeValue("code");
			String nom = courant.getChild("nom").getText();
			int muta;
			String mut = courant.getChild("mutualisable").getText();
			String contexte = courant.getChild("code_mention").getText();
			if (contexte.length() < 0) {

				String query = " insert into Objet "
						+ " values (?,?, ?,1,?,'COM')";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				switch (mut) {
				case "N":
					muta = 0;

					preparedStmt.setString(1, Code_Objet);
					preparedStmt.setString(3, nom);
					preparedStmt.setInt(2, muta);
					preparedStmt.setString(4, contexte);
					// execute the preparedstatement
					preparedStmt.execute();
					break;
				case "O":
					muta = 1;
					// the mysql insert statement

					// create the mysql insert preparedstatement
					preparedStmt.setString(1, Code_Objet);
					preparedStmt.setString(3, nom);
					preparedStmt.setInt(2, muta);
					preparedStmt.setString(4, contexte);
					// execute the preparedstatement
					preparedStmt.execute();
				}
			}

			else {
				// the mysql insert statement
				String query = " insert into Objet "
						+ " values (?,?, ?,1,?,'COM')";

				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				switch (mut) {
				case "N":
					muta = 0;
					// create the mysql insert preparedstatement
					preparedStmt.setString(1, Code_Objet);
					preparedStmt.setString(3, nom);
					preparedStmt.setInt(2, muta);
					// preparedStmt.setString (4, version);
					preparedStmt.setString(4, contexte);

					// execute the preparedstatement
					try {
						preparedStmt.execute();
					} catch (Exception e) {
					}
					break;
				case "O":
					muta = 1;
					// the mysql insert statement

					// create the mysql insert preparedstatement
					preparedStmt.setString(1, Code_Objet);
					preparedStmt.setString(3, nom);
					preparedStmt.setInt(2, muta);
					// preparedStmt.setString (4, version);
					preparedStmt.setString(4, contexte);

					// execute the preparedstatement
					try {
						preparedStmt.execute();
					} catch (Exception e) {
					}
				}

			}
		}

		List<Element> ObjetEnseigement = racine.getChildren("enseignement");
		Iterator<Element> i3 = ObjetEnseigement.iterator();

		while (i3.hasNext()) {
			Element courant = (Element) i3.next();
			String Code_Objet = courant.getAttributeValue("code");
			String nom = courant.getChild("nom").getText();
			String mut = courant.getChild("mutualisable").getText();
			String version = courant.getChild("version").getText();
			String contexte = courant.getChild("code_mention").getText();
			if (contexte.length() < 0) {
				if (mut.equals("N")) {
					String query = " insert into Objet "
							+ " values (?,0, ?,?,null,'ENS')";
					PreparedStatement preparedStmt = conn
							.prepareStatement(query);
					preparedStmt.setString(1, Code_Objet);
					preparedStmt.setString(2, nom);
					preparedStmt.setString(3, version);
					preparedStmt.execute();
				} else if (mut.equals("O")) {
					String query = " insert into Objet "
							+ " values (?,1, ?,?,null,'ENS')";
					PreparedStatement preparedStmt = conn
							.prepareStatement(query);
					preparedStmt.setString(1, Code_Objet);
					preparedStmt.setString(2, nom);
					preparedStmt.setString(3, version);
					preparedStmt.execute();
				} else {
					String query = " insert into Objet "
							+ " values (?,0, ?,?,null,'ENS')";
					PreparedStatement preparedStmt = conn
							.prepareStatement(query);
					preparedStmt.setString(1, Code_Objet);
					preparedStmt.setString(2, nom);
					preparedStmt.setString(3, version);

					preparedStmt.execute();
				}
			}

			else {
				if (mut.equals("N")) {
					String query = " insert into Objet "
							+ " values (?,0, ?,?,?,'ENS')";

					PreparedStatement preparedStmt = conn
							.prepareStatement(query);
					preparedStmt.setString(1, Code_Objet);
					preparedStmt.setString(2, nom);
					preparedStmt.setString(3, version);
					preparedStmt.setString(4, contexte);
					try {
						preparedStmt.execute();
					} catch (Exception e) {
					}
				} else if (mut.equals("O")) {
					String query = " insert into Objet "
							+ " values (?,1, ?,?,?,'ENS')";

					PreparedStatement preparedStmt = conn
							.prepareStatement(query);

					preparedStmt.setString(1, Code_Objet);
					preparedStmt.setString(2, nom);
					// preparedStmt.setInt (2, 1);
					preparedStmt.setString(3, version);
					preparedStmt.setString(4, contexte);

					// execute the preparedstatement
					try {
						preparedStmt.execute();
					} catch (Exception e) {
					}
				} else {
					String query = " insert into Objet "
							+ " values (?,0, ?,?,?,'ENS')";

					PreparedStatement preparedStmt = conn
							.prepareStatement(query);

					preparedStmt.setString(1, Code_Objet);
					preparedStmt.setString(2, nom);
					// preparedStmt.setInt (2, 1);
					preparedStmt.setString(3, version);
					preparedStmt.setString(4, contexte);

					// execute the preparedstatement
					try {
						preparedStmt.execute();
					} catch (Exception e) {
					}
				}
			}
		}

		/**
		 * alimenter la table objet
		 */

		List<Element> ObjetProgramme = racine.getChildren("programme");
		Iterator<Element> i4 = ObjetProgramme.iterator();

		while (i4.hasNext()) {
			Element courant = (Element) i4.next();
			String Code_Objet = courant.getAttributeValue("code");
			String nom = courant.getChild("nom").getText();
			String version = courant.getChild("version").getText();
			String contexte = courant.getChild("code_mention").getText();
			if (contexte.length() < 0) {
				// the mysql insert statement
				String query = " insert into Objet "
						+ " values (?,0, ?,?,null,'PRO')";
				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, Code_Objet);
				preparedStmt.setString(2, nom);
				preparedStmt.setString(3, version);
				// execute the preparedstatement
				preparedStmt.execute();
			} else {
				// the mysql insert statement
				String query = " insert into Objet "
						+ " values (?,0, ?,?,?,'PRO')";

				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, Code_Objet);
				preparedStmt.setString(2, nom);
				preparedStmt.setString(3, version);
				preparedStmt.setString(4, contexte);

				// execute the preparedstatement
				try {
					preparedStmt.execute();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.//e.printStackTrace();();
				}
			}
		}

		List<Element> ObjetOption = racine.getChildren("option");
		Iterator<Element> i31 = ObjetOption.iterator();

		while (i31.hasNext()) {
			Element courant = (Element) i31.next();
			String Code_Objet = courant.getAttributeValue("code");
			String nom = courant.getChild("nom").getText();
			int muta;
			String mut = courant.getChild("mutualisable").getText();
			String contexte = courant.getChild("code_mention").getText();
			if (contexte.length() < 0) {

				String query = " insert into Objet "
						+ " values (?,?, ?,1,?,'COM')";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				switch (mut) {
				case "N":
					muta = 0;
					// the mysql insert statement

					// create the mysql insert preparedstatement
					preparedStmt.setString(1, Code_Objet);
					preparedStmt.setString(3, nom);
					preparedStmt.setInt(2, muta);
					preparedStmt.setString(4, contexte);
					// execute the preparedstatement
					try {
						preparedStmt.execute();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						// e.//e.printStackTrace();();
					}
					break;
				case "O":
					muta = 1;
					// the mysql insert statement

					// create the mysql insert preparedstatement
					preparedStmt.setString(1, Code_Objet);
					preparedStmt.setString(3, nom);
					preparedStmt.setInt(2, muta);
					preparedStmt.setString(4, contexte);
					// execute the preparedstatement
					try {
						preparedStmt.execute();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						// e.//e.printStackTrace();();
					}
				}
			}

			else {
				// the mysql insert statement
				String query = " insert into Objet "
						+ " values (?,?, ?,1,?,'COM')";

				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				switch (mut) {
				case "N":
					muta = 0;
					// create the mysql insert preparedstatement
					preparedStmt.setString(1, Code_Objet);
					preparedStmt.setString(3, nom);
					preparedStmt.setInt(2, muta);
					preparedStmt.setString(4, contexte);

					// execute the preparedstatement
					try {
						preparedStmt.execute();
					} catch (Exception e) {
					}
					break;
				case "O":
					muta = 1;
					// the mysql insert statement

					// create the mysql insert preparedstatement
					preparedStmt.setString(1, Code_Objet);
					preparedStmt.setString(3, nom);
					preparedStmt.setInt(2, muta);
					preparedStmt.setString(4, contexte);

					// execute the preparedstatement
					try {
						preparedStmt.execute();
					} catch (Exception e) {
					}
				}

			}
		}

		List<Element> ObjetAnne = racine.getChildren("annee");
		Iterator<Element> i3111 = ObjetAnne.iterator();

		while (i3111.hasNext()) {
			Element courant = (Element) i3111.next();
			String Code_Objet = courant.getAttributeValue("code");
			String nom = courant.getChild("nom").getText();
			int muta;
			String mut = courant.getChild("mutualisable").getText();
			String contexte = courant.getChild("code_mention").getText();
			if (contexte.length() < 0) {
				String query = " insert into Objet "
						+ " values (?,?, ?,1,?,'COM')";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				switch (mut) {
				case "N":
					muta = 0;
					// the mysql insert statement

					// create the mysql insert preparedstatement
					preparedStmt.setString(1, Code_Objet);
					preparedStmt.setString(3, nom);
					preparedStmt.setInt(2, muta);
					preparedStmt.setString(4, contexte);
					// execute the preparedstatement
					try {
						preparedStmt.execute();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						// e.//e.printStackTrace();();
					}
					break;
				case "O":
					muta = 1;
					// the mysql insert statement

					// create the mysql insert preparedstatement
					preparedStmt.setString(1, Code_Objet);
					preparedStmt.setString(3, nom);
					preparedStmt.setInt(2, muta);
					preparedStmt.setString(4, contexte);
					// execute the preparedstatement
					try {
						preparedStmt.execute();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						// e.//e.printStackTrace();();
					}
				}
			}

			else {
				// the mysql insert statement
				String query = " insert into Objet "
						+ " values (?,?, ?,1,?,'COM')";

				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				switch (mut) {
				case "N":
					muta = 0;
					// create the mysql insert preparedstatement
					preparedStmt.setString(1, Code_Objet);
					preparedStmt.setString(3, nom);
					preparedStmt.setInt(2, muta);
					preparedStmt.setString(4, contexte);

					// execute the preparedstatement
					try {
						preparedStmt.execute();
					} catch (Exception e) {
					}
					break;
				case "O":
					muta = 1;
					// the mysql insert statement

					// create the mysql insert preparedstatement
					preparedStmt.setString(1, Code_Objet);
					preparedStmt.setString(3, nom);
					preparedStmt.setInt(2, muta);
					preparedStmt.setString(4, contexte);

					// execute the preparedstatement
					try {
						preparedStmt.execute();
					} catch (Exception e) {
					}
				}

			}
		}

		List<Element> ObjetGroupe = racine.getChildren("groupe");
		Iterator<Element> i31111 = ObjetGroupe.iterator();

		while (i31111.hasNext()) {
			Element courant = (Element) i31111.next();
			String Code_Objet = courant.getAttributeValue("code");
			String nom = courant.getChild("nom").getText();
			int muta;
			String mut = courant.getChild("mutualisable").getText();
			String contexte = courant.getChild("code_mention").getText();
			if (contexte.length() < 0) {
				String query = " insert into Objet "
						+ " values (?,?, ?,1,?,'COM')";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				switch (mut) {
				case "N":
					muta = 0;
					preparedStmt.setString(1, Code_Objet);
					preparedStmt.setString(3, nom);
					preparedStmt.setInt(2, muta);
					preparedStmt.setString(4, contexte);
					// execute the preparedstatement
					try {
						preparedStmt.execute();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						// e.//e.printStackTrace();();
					}
					break;
				case "O":
					muta = 1;
					preparedStmt.setString(1, Code_Objet);
					preparedStmt.setString(3, nom);
					preparedStmt.setInt(2, muta);
					preparedStmt.setString(4, contexte);
					// execute the preparedstatement
					try {
						preparedStmt.execute();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						// e.//e.printStackTrace();();
					}
				}
			}

			else {
				// the mysql insert statement
				String query = " insert into Objet "
						+ " values (?,?, ?,1,?,'COM')";

				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				switch (mut) {
				case "N":
					muta = 0;
					// create the mysql insert preparedstatement
					preparedStmt.setString(1, Code_Objet);
					preparedStmt.setString(3, nom);
					preparedStmt.setInt(2, muta);
					preparedStmt.setString(4, contexte);

					try {
						preparedStmt.execute();
					} catch (Exception e) {
					}
					break;
				case "O":
					muta = 1;
					preparedStmt.setString(1, Code_Objet);
					preparedStmt.setString(3, nom);
					preparedStmt.setInt(2, muta);
					preparedStmt.setString(4, contexte);

					// execute the preparedstatement
					try {
						preparedStmt.execute();
					} catch (Exception e) {
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
			if (Contributeur.getChildren().isEmpty()) {
			} else {
				List<Element> RefContributeur = Contributeur
						.getChildren("ref-personne");
				Iterator<Element> i11 = RefContributeur.iterator();
				while (i11.hasNext()) {
					Element ref = (Element) i11.next();
					if (ref.getAttributeValue("ref").length() > 0) {
						String Cont = ref.getAttributeValue("ref");
						// the mysql insert statement
						String query = " insert into A_pour_contributeur "
								+ " values (?,?)";
						// create the mysql insert preparedstatement
						PreparedStatement preparedStmt = conn
								.prepareStatement(query);
						preparedStmt.setString(1, Code_Formation);
						preparedStmt.setString(2, Cont);
						// execute the preparedstatement
						try {
							preparedStmt.execute();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							// e.//e.printStackTrace();();
						}
					}
				}
			}
		}

		String query1 = " select Id from Descripteur_champs";
		PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
		ResultSet Rs = preparedStmt1.executeQuery();

		/**
		 * alimmentation des fields
		 * */

		List<Element> Objet = racine.getChildren("mention");
		Iterator<Element> k1 = Objet.iterator();
		while (k1.hasNext()) {
			Element courant = (Element) k1.next();
			List<Element> listField = courant.getChildren();
			Iterator<Element> j1 = listField.iterator();
			while (j1.hasNext()) {
				Element courant1 = (Element) j1.next();
				// courant1.getChildren();
				String contents = "";
				String Id = courant1.getName();
				if (Id.equals("err") || Id.equals("nom")
						|| Id.equals("responsables")
						|| Id.equals("contributeurs") || Id.equals("tree")
						|| Id.equals("structure") || Id.equals("liste-objets")
						|| Id.equals("refs-composante")
						|| Id.equals("etat-rof")
						|| Id.equals("type_diplome_rof")
						|| Id.equals("responsable-rof")) {
					continue;
				} else if (Id.equals("nb_credits") || Id.equals("version")) {

					contents = "INT";
				} else if (courant1.getChildren().size() > 0
						&& courant1.getChildren().get(0).getName()
								.equals("body")) {
					contents = "STRUCTURE";
					//System.out.println(courant1.getChildren().get(0).getName()
					//		+ " --" + Id + "---" + contents);

				} else {

					contents = "STRING";
				}
				String name = courant1.getName();
				String query = " insert into Descripteur_champs "
						+ " values (?,?,100,?,0,null,?,'FOR')";
				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, Id.toUpperCase() + "FOR");
				preparedStmt.setString(2, name);
				preparedStmt.setString(3, name);
				preparedStmt.setString(4, contents);

				try {
					preparedStmt.execute();
				} catch (Exception e) {
					// e.//e.printStackTrace();();
				}
			}
		}

		List<Element> ObjetSpe = racine.getChildren("specialite");
		Iterator<Element> h1 = ObjetSpe.iterator();
		while (h1.hasNext()) {
			Element courant1 = (Element) h1.next();
			List<Element> listFieldSp = courant1.getChildren();
			Iterator<Element> a = listFieldSp.iterator();
			while (a.hasNext()) {
				Element courant11 = (Element) a.next();
				String contents = "";
				courant11.getChildren();
				String Id = courant11.getName();
				if (Id.equals("err") || Id.equals("nom")
						|| Id.equals("responsables")
						|| Id.equals("contributeurs") || Id.equals("tree")
						|| Id.equals("structure") || Id.equals("liste-objets")
						|| Id.equals("refs-composante")
						|| Id.equals("etat-rof")
						|| Id.equals("type_diplome_rof")
						|| Id.equals("responsable-rof")) {
					continue;
				} else {
					while (Rs.next()) {
						if (Id.equals(Rs)) {
							System.out.println("erreur");
						} else if (Id.equals("nb_credits")
								|| Id.equals("version")) {

							contents = "INT";
						} else if (courant1.getChildren().size() > 0
								&& courant1.getChildren().get(0).getName()
										.equals("body")) {

							contents = "STRUCTURE";

						}

						else {
							contents = "STRING";

						}
						String name = courant11.getName();
						String query = " insert into Descripteur_champs "
								+ " values (?,?,100,?,0,null,?,'SPE')";
						// create the mysql insert preparedstatement
						PreparedStatement preparedStmt = conn
								.prepareStatement(query);
						preparedStmt.setString(1, Id.toUpperCase() + "SPE");
						preparedStmt.setString(2, name);
						preparedStmt.setString(3, name);
						preparedStmt.setString(4, contents);

						// execute the preparedstatement
						try {
							preparedStmt.execute();
						} catch (Exception e1) {
							// e.printStackTrace();();
						}
					}

				}
			}
		}
		List<Element> ObjetPR = racine.getChildren("programme");
		Iterator<Element> h11 = ObjetPR.iterator();
		while (h11.hasNext()) {
			Element courant1 = (Element) h11.next();
			List<Element> listFieldSp = courant1.getChildren();
			Iterator<Element> a = listFieldSp.iterator();
			while (a.hasNext()) {
				Element courant11 = (Element) a.next();
				String contents = "";
				courant11.getChildren();
				String Id = courant11.getName();
				if (Id.equals("err") || Id.equals("nom")
						|| Id.equals("responsables")
						|| Id.equals("contributeurs") || Id.equals("tree")
						|| Id.equals("structure") || Id.equals("liste-objets")
						|| Id.equals("refs-composante")
						|| Id.equals("etat-rof")
						|| Id.equals("type_diplome_rof")
						|| Id.equals("responsable-rof")) {
					continue;
				} else {
					while (Rs.next()) {
						if (Id.equals(Rs)) {
							System.out.println("erreur");
						} else if (Id.equals("nb_credits")
								|| Id.equals("version")) {

							contents = "INT";
						} else if (courant1.getChildren().size() > 0
								&& courant1.getChildren().get(0).getName()
										.equals("body")) {

							contents = "STRUCTURE";

						}

						else {
							contents = "STRING";

						}
						String name = courant11.getName();
						String query = " insert into Descripteur_champs "
								+ " values (?,?,100,?,0,null,?,'PRO')";
						// create the mysql insert preparedstatement
						PreparedStatement preparedStmt = conn
								.prepareStatement(query);
						preparedStmt.setString(1, Id.toUpperCase() + "PRO");
						preparedStmt.setString(2, name);
						preparedStmt.setString(3, name);
						preparedStmt.setString(4, contents);

						// execute the preparedstatement
						try {
							preparedStmt.execute();
						} catch (Exception e1) {
							// e.printStackTrace();();
						}
					}

				}
			}
		}
		List<Element> ObjetEn = racine.getChildren("enseignement");
		Iterator<Element> h111 = ObjetEn.iterator();
		while (h111.hasNext()) {
			Element courant1 = (Element) h111.next();
			List<Element> listFieldSp = courant1.getChildren();
			Iterator<Element> a = listFieldSp.iterator();
			while (a.hasNext()) {
				Element courant11 = (Element) a.next();
				String contents = "";
				courant11.getChildren();
				String Id = courant11.getName();
				if (Id.equals("err") || Id.equals("nom")
						|| Id.equals("responsables")
						|| Id.equals("contributeurs") || Id.equals("tree")
						|| Id.equals("structure") || Id.equals("liste-objets")
						|| Id.equals("refs-composante")
						|| Id.equals("etat-rof")
						|| Id.equals("type_diplome_rof")
						|| Id.equals("responsable-rof")) {
					continue;

				} else {
					while (Rs.next()) {
						if (Id.equals(Rs)) {
							System.out.println("erreur");
						} else if (Id.equals("nb_credits")
								|| Id.equals("version")) {

							contents = "INT";
						} else if (courant1.getChildren().size() > 0
								&& courant1.getChildren().get(0).getName()
										.equals("body")) {
							contents = "STRUCTURE";
						}

						else {
							contents = "STRING";

						}
						String name = courant11.getName();
						String query = " insert into Descripteur_champs "
								+ " values (?,?,100,?,0,null,?,'ENS')";
						// create the mysql insert preparedstatement
						PreparedStatement preparedStmt = conn
								.prepareStatement(query);
						preparedStmt.setString(1, Id.toUpperCase() + "ENS");
						preparedStmt.setString(2, name);
						preparedStmt.setString(3, name);
						preparedStmt.setString(4, contents);

						// execute the preparedstatement
						try {
							preparedStmt.execute();
						} catch (Exception e1) {
							// e.printStackTrace();();
						}
					}

				}
			}
		}

		List<Element> ObjetOp = racine.getChildren("option");
		Iterator<Element> h1111 = ObjetOp.iterator();
		while (h1111.hasNext()) {
			Element courant1 = (Element) h1111.next();
			List<Element> listFieldSp = courant1.getChildren();
			Iterator<Element> a = listFieldSp.iterator();
			while (a.hasNext()) {
				Element courant11 = (Element) a.next();
				String contents = "";
				courant11.getChildren();
				String Id = courant11.getName();
				if (Id.equals("err") || Id.equals("nom")
						|| Id.equals("responsables")
						|| Id.equals("contributeurs") || Id.equals("tree")
						|| Id.equals("structure") || Id.equals("liste-objets")
						|| Id.equals("refs-composante")
						|| Id.equals("etat-rof")
						|| Id.equals("type_diplome_rof")
						|| Id.equals("responsable-rof")) {
					continue;

				} else {
					while (Rs.next()) {
						if (Id.equals(Rs)) {
							System.out.println("erreur");
						} else if (Id.equals("nb_credits")
								|| Id.equals("version")) {

							contents = "INT";
						} else if (courant1.getChildren().size() > 0
								&& courant1.getChildren().get(0).getName()
										.equals("body")) {

							contents = "STRUCTURE";

						}

						else {
							contents = "STRING";

						}
						String name = courant11.getName();
						String query = " insert into Descripteur_champs "
								+ " values (?,?,100,?,0,null,?,'COM')";
						// create the mysql insert preparedstatement
						PreparedStatement preparedStmt = conn
								.prepareStatement(query);
						preparedStmt.setString(1, Id.toUpperCase() + "COM");
						preparedStmt.setString(2, name);
						preparedStmt.setString(3, name);
						preparedStmt.setString(4, contents);

						// execute the preparedstatement
						try {
							preparedStmt.execute();
						} catch (Exception e1) {
							// e.printStackTrace();();
						}
					}

				}
			}
		}

		List<Element> ObjetAn = racine.getChildren("annee");
		Iterator<Element> ke = ObjetAn.iterator();
		while (ke.hasNext()) {
			Element courant1 = (Element) ke.next();
			List<Element> listFieldSp = courant1.getChildren();
			Iterator<Element> a = listFieldSp.iterator();
			while (a.hasNext()) {
				Element courant11 = (Element) a.next();
				String contents = "";
				courant11.getChildren();
				String Id = courant11.getName();
				if (Id.equals("err") || Id.equals("nom")
						|| Id.equals("responsables")
						|| Id.equals("contributeurs") || Id.equals("tree")
						|| Id.equals("structure") || Id.equals("liste-objets")
						|| Id.equals("refs-composante")
						|| Id.equals("etat-rof")
						|| Id.equals("type_diplome_rof")
						|| Id.equals("responsable-rof")) {
					continue;

				} else {
					while (Rs.next()) {
						if (Id.equals(Rs)) {
							System.out.println("erreur");
						} else if (Id.equals("nb_credits")
								|| Id.equals("version")) {

							contents = "INT";
						} else if (courant1.getChildren().size() > 0
								&& courant1.getChildren().get(0).getName()
										.equals("body")) {

							contents = "STRUCTURE";

						}

						else {
							contents = "STRING";

						}
						String name = courant11.getName();
						String query = " insert into Descripteur_champs "
								+ " values (?,?,100,?,0,null,?,'COM')";
						// create the mysql insert preparedstatement
						PreparedStatement preparedStmt = conn
								.prepareStatement(query);
						preparedStmt.setString(1, Id.toUpperCase() + "COM");
						preparedStmt.setString(2, name);
						preparedStmt.setString(3, name);
						preparedStmt.setString(4, contents);

						// execute the preparedstatement
						try {
							preparedStmt.execute();
						} catch (Exception e1) {
							// e.printStackTrace();();
						}
					}

				}
			}
		}

		List<Element> ObjetGr = racine.getChildren("groupe");
		Iterator<Element> KI = ObjetGr.iterator();
		while (KI.hasNext()) {
			Element courant1 = (Element) KI.next();
			List<Element> listFieldSp = courant1.getChildren();
			Iterator<Element> a = listFieldSp.iterator();
			while (a.hasNext()) {
				Element courant11 = (Element) a.next();
				String contents = "";
				courant11.getChildren();
				String Id = courant11.getName();
				if (Id.equals("err") || Id.equals("nom")
						|| Id.equals("responsables")
						|| Id.equals("contributeurs") || Id.equals("tree")
						|| Id.equals("structure") || Id.equals("liste-objets")
						|| Id.equals("refs-composante")
						|| Id.equals("etat-rof")
						|| Id.equals("type_diplome_rof")
						|| Id.equals("responsable-rof")) {
					continue;

				} else {
					while (Rs.next()) {
						if (Id.equals(Rs)) {
							System.out.println("erreur");
						} else if (Id.equals("nb_credits")
								|| Id.equals("version")) {

							contents = "INT";
						} else if (courant1.getChildren().size() > 0
								&& courant1.getChildren().get(0).getName()
										.equals("body")) {

							contents = "STRUCTURE";

						}

						else {
							contents = "STRING";

						}
						String name = courant11.getName();
						String query = " insert into Descripteur_champs "
								+ " values (?,?,100,?,0,null,?,'COM')";
						// create the mysql insert preparedstatement
						PreparedStatement preparedStmt = conn
								.prepareStatement(query);
						preparedStmt.setString(1, Id.toUpperCase() + "COM");
						preparedStmt.setString(2, name);
						preparedStmt.setString(3, name);
						preparedStmt.setString(4, contents);

						// execute the preparedstatement
						try {
							preparedStmt.execute();
						} catch (Exception e1) {
							// e.printStackTrace();();
						}
					}

				}
			}
		}

		List<Element> ObjetSem = racine.getChildren("semestre");
		Iterator<Element> ku = ObjetSem.iterator();
		while (ku.hasNext()) {
			Element courant1 = (Element) ku.next();
			List<Element> listFieldSp = courant1.getChildren();
			Iterator<Element> a = listFieldSp.iterator();
			while (a.hasNext()) {
				Element courant11 = (Element) a.next();
				String contents = "";
				courant11.getChildren();
				String Id = courant11.getName();
				if (Id.equals("err") || Id.equals("nom")
						|| Id.equals("responsables")
						|| Id.equals("contributeurs") || Id.equals("tree")
						|| Id.equals("structure") || Id.equals("liste-objets")
						|| Id.equals("refs-composante")
						|| Id.equals("etat-rof")
						|| Id.equals("type_diplome_rof")
						|| Id.equals("responsable-rof")) {
					continue;

				} else {
					while (Rs.next()) {
						if (Id.equals(Rs)) {
							System.out.println("erreur");
						} else if (Id.equals("nb_credits")
								|| Id.equals("version")) {

							contents = "INT";
						} else if (courant1.getChildren().size() > 0
								&& courant1.getChildren().get(0).getName()
										.equals("body")) {

							contents = "STRUCTURE";

						}

						else {
							contents = "STRING";

						}
						String name = courant11.getName();
						String query = " insert into Descripteur_champs "
								+ " values (?,?,100,?,0,null,?,'COM')";
						// create the mysql insert preparedstatement
						PreparedStatement preparedStmt = conn
								.prepareStatement(query);
						preparedStmt.setString(1, Id.toUpperCase() + "COM");
						preparedStmt.setString(2, name);
						preparedStmt.setString(3, name);
						preparedStmt.setString(4, contents);

						// execute the preparedstatement
						try {
							preparedStmt.execute();
						} catch (Exception e1) {
							// e.printStackTrace();();
						}
					}

				}
			}
		}
		Rs.close();

		/**
		 * alimmentation des fieldObjet
		 */

		List<Element> Objet1 = racine.getChildren("mention");
		Iterator<Element> j11 = Objet1.iterator();

		while (j11.hasNext()) {

			Element courant4 = (Element) j11.next();
			String Code_objet = courant4.getAttributeValue("code");

			List<Element> listField1 = courant4.getChildren();
			Iterator<Element> k = listField1.iterator();
			while (k.hasNext()) {
				Element courant3 = (Element) k.next();

				if (!courant3.getValue().isEmpty()) {
					String Id = courant3.getName();

					if (Id.equals("err") || Id.equals("nom")
							|| Id.equals("responsables")
							|| Id.equals("contributeurs") || Id.equals("tree")
							|| Id.equals("structure")
							|| Id.equals("liste-objets")
							|| Id.equals("refs-composante")
							|| Id.equals("etat-rof")
							|| Id.equals("type_diplome_rof")) {
					} else {
						String content_type = "";
						// quant c'esst un body on prends toutes la
						// descendance
						if (courant3.getChildren().size() > 0) {
							Element content = courant3.getChild("body");
							if (courant3.getChildren().get(0).getName()
									.equals("body")) {

								XMLOutputter outp = new XMLOutputter();

								outp.setFormat(Format.getCompactFormat());

								StringWriter sw = new StringWriter();
								outp.output(content.getContent(), sw);
								StringBuffer sb = sw.getBuffer();
								content_type = sb.toString();

							} else {
								Iterator<Element> h = courant3.getChildren()
										.iterator();
								for (; h.hasNext();) {
									Element courant5 = (Element) h.next();
									if (courant5.getValue().isEmpty()) {
										break;
									}
									content_type += courant5.getValue();
									if (h.hasNext())
										content_type += ',';
								}
							}
						} else if (!courant3.getValue().isEmpty()) {
							content_type = courant3.getValue();
						}
						if (content_type.isEmpty())
							break;
						// the mysql insert statement

						String query = " insert into A_pour_champs "
								+ "values (?,?,?,0)";
						// create the mysql insert preparedstatement
						PreparedStatement preparedStmt = conn
								.prepareStatement(query);
						preparedStmt.setString(1, Id);
						preparedStmt.setString(2, Code_objet);
						preparedStmt.setString(3, content_type);

						// execute the preparedstatement
						try {
							preparedStmt.execute();
						}

						catch (Exception e11) {
							// e.printStackTrace();();

						}
					}
				}
			}
		}

		List<Element> ObjetEN = racine.getChildren("enseignement");
		Iterator<Element> j2 = ObjetEN.iterator();

		while (j2.hasNext()) {

			Element courant7 = (Element) j2.next();
			String Code_objet = courant7.getAttributeValue("code");

			List<Element> listField1 = courant7.getChildren();
			Iterator<Element> k = listField1.iterator();

			while (k.hasNext()) {
				Element courant6 = (Element) k.next();

				if (!courant6.getValue().isEmpty()) {
					String Id = courant6.getName();

					if (Id.equals("err") || Id.equals("nom")
							|| Id.equals("responsables")
							|| Id.equals("contributeurs") || Id.equals("tree")
							|| Id.equals("structure")
							|| Id.equals("liste-objets")
							|| Id.equals("refs-composante")
							|| Id.equals("etat-rof")
							|| Id.equals("type_diplome_rof")) {
					} else {
						String content_type = "";
						// quant c'esst un body on prends toutes la descendance
						if (courant6.getChildren().size() > 0) {
							Element content = courant6.getChild("body");
							if (courant6.getChildren().get(0).getName()
									.equals("body")) {
								XMLOutputter outp = new XMLOutputter();

								outp.setFormat(Format.getCompactFormat());

								StringWriter sw = new StringWriter();
								outp.output(content.getContent(), sw);
								StringBuffer sb = sw.getBuffer();
								content_type = sb.toString();
							} else {
								Iterator<Element> h = courant6.getChildren()
										.iterator();
								for (; h.hasNext();) {
									Element courant8 = (Element) h.next();
									if (courant8.getValue().isEmpty())
										break;
									content_type += courant8.getValue();
									if (h.hasNext())
										content_type += ',';
								}
							}

						} else if (!courant6.getValue().isEmpty()) {
							content_type = courant6.getValue();
						}

						if (content_type.isEmpty())
							break;
						// the mysql insert statement
						String query = " insert into A_pour_champs "
								+ "values (?,?,?,0)";
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

			Element courant10 = (Element) j3.next();
			String Code_objet = courant10.getAttributeValue("code");

			List<Element> listField1 = courant10.getChildren();
			Iterator<Element> k = listField1.iterator();

			while (k.hasNext()) {
				Element courant9 = (Element) k.next();

				if (!courant9.getValue().isEmpty()) {
					String Id = courant9.getName();

					if (Id.equals("err") || Id.equals("nom")
							|| Id.equals("responsables")
							|| Id.equals("contributeurs") || Id.equals("tree")
							|| Id.equals("structure")
							|| Id.equals("liste-objets")
							|| Id.equals("refs-composante")
							|| Id.equals("etat-rof")
							|| Id.equals("type_diplome_rof")) {
					} else {
						String content_type = "";
						// quant c'esst un body on prends toutes la descendance
						if (courant9.getChildren().size() > 0) {
							Element content = courant9.getChild("body");
							if (courant9.getChildren().get(0).getName()
									.equals("body")) {
								XMLOutputter outp = new XMLOutputter();

								outp.setFormat(Format.getCompactFormat());

								StringWriter sw = new StringWriter();
								outp.output(content.getContent(), sw);
								StringBuffer sb = sw.getBuffer();
								content_type = sb.toString();
							} else {
								Iterator<Element> h = courant9.getChildren()
										.iterator();
								for (; h.hasNext();) {
									Element courant12 = (Element) h.next();
									if (courant12.getValue().isEmpty())
										break;
									content_type += courant12.getValue();
									if (h.hasNext())
										content_type += ',';
								}
							}

						} else if (!courant9.getValue().isEmpty()) {
							content_type = courant9.getValue();
						}

						if (content_type.isEmpty())
							break;
						// the mysql insert statement
						String query = " insert into A_pour_champs "
								+ "values (?,?,?,0)";
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
		List<Element> ObjetPR1 = racine.getChildren("programme");
		Iterator<Element> j4 = ObjetPR1.iterator();

		while (j4.hasNext()) {

			Element courant13 = (Element) j4.next();
			String Code_objet = courant13.getAttributeValue("code");

			List<Element> listField1 = courant13.getChildren();
			Iterator<Element> k = listField1.iterator();

			while (k.hasNext()) {
				Element courant14 = (Element) k.next();

				if (!courant14.getValue().isEmpty()) {
					String Id = courant14.getName();

					if (Id.equals("err") || Id.equals("nom")
							|| Id.equals("responsables")
							|| Id.equals("contributeurs") || Id.equals("tree")
							|| Id.equals("structure")
							|| Id.equals("liste-objets")
							|| Id.equals("refs-composante")
							|| Id.equals("etat-rof")
							|| Id.equals("type_diplome_rof")) {
					} else {
						String content_type = "";
						// quant c'esst un body on prends toutes la descendance
						if (courant14.getChildren().size() > 0) {
							Element content = courant14.getChild("body");
							if (courant14.getChildren().get(0).getName()
									.equals("body")) {
								XMLOutputter outp = new XMLOutputter();

								outp.setFormat(Format.getCompactFormat());

								StringWriter sw = new StringWriter();
								outp.output(content.getContent(), sw);
								StringBuffer sb = sw.getBuffer();
								content_type = sb.toString();
							} else {
								Iterator<Element> h = courant14.getChildren()
										.iterator();
								for (; h.hasNext();) {
									Element courant16 = (Element) h.next();
									if (courant16.getValue().isEmpty())
										break;
									content_type += courant16.getValue();
									if (h.hasNext())
										content_type += ',';
								}
							}

						} else if (!courant14.getValue().isEmpty()) {
							content_type = courant14.getValue();
						}

						if (content_type.isEmpty())
							break;
						// the mysql insert statement
						String query = " insert into A_pour_champs "
								+ "values (?,?,?,0)";
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

			Element courant17 = (Element) j5.next();
			String Code_objet = courant17.getAttributeValue("code");

			List<Element> listField1 = courant17.getChildren();
			Iterator<Element> k = listField1.iterator();

			while (k.hasNext()) {
				Element courant18 = (Element) k.next();

				if (!courant18.getValue().isEmpty()) {
					String Id = courant18.getName();

					if (Id.equals("err") || Id.equals("nom")
							|| Id.equals("responsables")
							|| Id.equals("contributeurs") || Id.equals("tree")
							|| Id.equals("structure")
							|| Id.equals("liste-objets")
							|| Id.equals("refs-composante")
							|| Id.equals("etat-rof")
							|| Id.equals("type_diplome_rof")) {
					} else {
						String content_type = "";
						// quant c'esst un body on prends toutes la descendance
						if (courant18.getChildren().size() > 0) {
							Element content = courant18.getChild("body");
							if (courant18.getChildren().get(0).getName()
									.equals("body")) {
								XMLOutputter outp = new XMLOutputter();

								outp.setFormat(Format.getCompactFormat());

								StringWriter sw = new StringWriter();
								outp.output(content.getContent(), sw);
								StringBuffer sb = sw.getBuffer();
								content_type = sb.toString();
							} else {
								Iterator<Element> h = courant18.getChildren()
										.iterator();
								for (; h.hasNext();) {
									Element courant19 = (Element) h.next();
									if (courant19.getValue().isEmpty())
										break;
									content_type += courant19.getValue();
									if (h.hasNext())
										content_type += ',';
								}
							}

						} else if (!courant18.getValue().isEmpty()) {
							content_type = courant18.getValue();
						}

						if (content_type.isEmpty())
							break;
						// the mysql insert statement
						String query = " insert into A_pour_champs "
								+ "values (?,?,?,0)";
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

		List<Element> ObjetOP1 = racine.getChildren("option");
		Iterator<Element> j6 = ObjetOP1.iterator();

		while (j6.hasNext()) {

			Element courant20 = (Element) j6.next();
			String Code_objet = courant20.getAttributeValue("code");

			List<Element> listField1 = courant20.getChildren();
			Iterator<Element> k = listField1.iterator();

			while (k.hasNext()) {
				Element courant24 = (Element) k.next();

				if (!courant24.getValue().isEmpty()) {
					String Id = courant24.getName();

					if (Id.equals("err") || Id.equals("nom")
							|| Id.equals("responsables")
							|| Id.equals("contributeurs") || Id.equals("tree")
							|| Id.equals("structure")
							|| Id.equals("liste-objets")
							|| Id.equals("refs-composante")
							|| Id.equals("etat-rof")
							|| Id.equals("type_diplome_rof")) {
					} else {
						String content_type = "";
						// quant c'esst un body on prends toutes la descendance
						if (courant24.getChildren().size() > 0) {
							Element content = courant24.getChild("body");
							if (courant24.getChildren().get(0).getName()
									.equals("body")) {
								XMLOutputter outp = new XMLOutputter();

								outp.setFormat(Format.getCompactFormat());

								StringWriter sw = new StringWriter();
								outp.output(content.getContent(), sw);
								StringBuffer sb = sw.getBuffer();
								content_type = sb.toString();
							} else {
								Iterator<Element> h = courant24.getChildren()
										.iterator();
								for (; h.hasNext();) {
									Element courant27 = (Element) h.next();
									if (courant27.getValue().isEmpty())
										break;
									content_type += courant27.getValue();
									if (h.hasNext())
										content_type += ',';
								}
							}

						} else if (!courant24.getValue().isEmpty()) {
							content_type = courant24.getValue();
						}

						if (content_type.isEmpty())
							break;
						// the mysql insert statement
						String query = " insert into A_pour_champs "
								+ "values (?,?,?,0)";
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

			Element courant78 = (Element) j7.next();
			String Code_objet = courant78.getAttributeValue("code");

			List<Element> listField1 = courant78.getChildren();
			Iterator<Element> k = listField1.iterator();

			while (k.hasNext()) {
				Element courant67 = (Element) k.next();

				if (!courant67.getValue().isEmpty()) {
					String Id = courant67.getName();

					if (Id.equals("err") || Id.equals("nom")
							|| Id.equals("responsables")
							|| Id.equals("contributeurs") || Id.equals("tree")
							|| Id.equals("structure")
							|| Id.equals("liste-objets")
							|| Id.equals("refs-composante")
							|| Id.equals("etat-rof")
							|| Id.equals("type_diplome_rof")) {
					} else {
						String content_type = "";
						// quant c'esst un body on prends toutes la descendance
						if (courant67.getChildren().size() > 0) {
							Element content = courant67.getChild("body");
							if (courant67.getChildren().get(0).getName()
									.equals("body")) {
								XMLOutputter outp = new XMLOutputter();

								outp.setFormat(Format.getCompactFormat());

								StringWriter sw = new StringWriter();
								outp.output(content.getContent(), sw);
								StringBuffer sb = sw.getBuffer();
								content_type = sb.toString();
							} else {
								Iterator<Element> h = courant67.getChildren()
										.iterator();
								for (; h.hasNext();) {
									Element courant98 = (Element) h.next();
									if (courant98.getValue().isEmpty())
										break;
									content_type += courant98.getValue();
									if (h.hasNext())
										content_type += ',';
								}
							}

						} else if (!courant67.getValue().isEmpty()) {
							content_type = courant67.getValue();
						}

						if (content_type.isEmpty())
							break;
						// the mysql insert statement
						String query = " insert into A_pour_champs "
								+ "values (?,?,?,0)";
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

			Element courant90 = (Element) j8.next();
			String Code_objet = courant90.getAttributeValue("code");

			List<Element> listField1 = courant90.getChildren();
			Iterator<Element> k = listField1.iterator();

			while (k.hasNext()) {
				Element courant56 = (Element) k.next();

				if (!courant56.getValue().isEmpty()) {
					String Id = courant56.getName();

					if (Id.equals("err") || Id.equals("nom")
							|| Id.equals("responsables")
							|| Id.equals("contributeurs") || Id.equals("tree")
							|| Id.equals("structure")
							|| Id.equals("liste-objets")
							|| Id.equals("refs-composante")
							|| Id.equals("etat-rof")
							|| Id.equals("type_diplome_rof")) {
					} else {
						String content_type = "";
						// quant c'esst un body on prends toutes la descendance
						if (courant56.getChildren().size() > 0) {
							Element content = courant56.getChild("body");
							if (courant56.getChildren().get(0).getName()
									.equals("body")) {
								XMLOutputter outp = new XMLOutputter();

								outp.setFormat(Format.getCompactFormat());

								StringWriter sw = new StringWriter();
								outp.output(content.getContent(), sw);
								StringBuffer sb = sw.getBuffer();
								content_type = sb.toString();
							} else {
								Iterator<Element> h = courant56.getChildren()
										.iterator();
								for (; h.hasNext();) {
									Element courant98 = (Element) h.next();
									if (courant98.getValue().isEmpty())
										break;
									content_type += courant98.getValue();
									if (h.hasNext())
										content_type += ',';
								}
							}

						} else if (!courant56.getValue().isEmpty()) {
							content_type = courant56.getValue();
						}

						if (content_type.isEmpty())
							break;
						// the mysql insert statement
						String query = " insert into A_pour_champs "
								+ "values (?,?,?,0)";
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
		int i11 = 1;

		String query = " insert into fils " + " values (?,?,?)";
		String query11 = " insert into objet_fils " + " values (?,?)";
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		PreparedStatement preparedStmt11 = conn.prepareStatement(query11);

		List<Element> Objet11 = racine.getChildren("mention");
		Iterator<Element> i51 = Objet11.iterator();
		while (i51.hasNext()) {
			Element Objetp = (Element) i51.next();
			String codeObjet = Objetp.getAttributeValue("code");
			Element objetStructure = Objetp.getChild("structure");
			List<Element> ObjetRef = objetStructure.getChildren();
			Iterator<Element> i7 = ObjetRef.iterator();
			while (i7.hasNext()) {
				Element Objetf = (Element) i7.next();
				String code_objet = Objetf.getAttributeValue("ref");

				if (ObjetRef.isEmpty()) {
					System.out.println("pas de fils");
				} else {
					rang = 1;

					preparedStmt.setInt(1, i11);
					preparedStmt.setInt(2, rang);
					preparedStmt.setString(3, code_objet);
					try {
						preparedStmt.execute();
					} catch (Exception e11) {
					}

					preparedStmt11.setString(1, codeObjet);
					preparedStmt11.setInt(2, i11);
					try {
						preparedStmt11.execute();
					} catch (Exception e11) {
					}
					i11++;
				}
			}
		}

		List<Element> Objet111 = racine.getChildren("specialite");
		Iterator<Element> i511 = Objet111.iterator();
		while (i511.hasNext()) {
			Element Objetp = (Element) i511.next();
			String codeObjet = Objetp.getAttributeValue("code");
			Element objetStructure = Objetp.getChild("structure");
			List<Element> ObjetRef = objetStructure.getChildren();
			Iterator<Element> i7 = ObjetRef.iterator();
			while (i7.hasNext()) {
				Element Objetf = (Element) i7.next();
				String code_objet = Objetf.getAttributeValue("ref");

				if (ObjetRef.isEmpty()) {
					System.out.println("pas de fils");
				} else {
					rang = 1;

					preparedStmt.setInt(1, i11);
					preparedStmt.setInt(2, rang);
					preparedStmt.setString(3, code_objet);
					try {
						preparedStmt.execute();
					} catch (Exception e11) {
					}

					preparedStmt11.setString(1, codeObjet);
					preparedStmt11.setInt(2, i11);
					try {
						preparedStmt11.execute();
					} catch (Exception e11) {
					}
					i11++;
				}
			}
		}

		List<Element> Objet1111 = racine.getChildren("programme");
		Iterator<Element> i5111 = Objet1111.iterator();
		while (i5111.hasNext()) {
			Element Objetp = (Element) i5111.next();
			String codeObjet = Objetp.getAttributeValue("code");
			Element objetStructure = Objetp.getChild("structure");
			List<Element> ObjetRef = objetStructure.getChildren();
			Iterator<Element> i7 = ObjetRef.iterator();
			while (i7.hasNext()) {
				Element Objetf = (Element) i7.next();
				String code_objet = Objetf.getAttributeValue("ref");

				if (ObjetRef.isEmpty()) {
					System.out.println("pas de fils");
				} else {
					rang = 1;

					preparedStmt.setInt(1, i11);
					preparedStmt.setInt(2, rang);
					preparedStmt.setString(3, code_objet);
					try {
						preparedStmt.execute();
					} catch (Exception e11) {
					}

					preparedStmt11.setString(1, codeObjet);
					preparedStmt11.setInt(2, i11);
					try {
						preparedStmt11.execute();
					} catch (Exception e11) {
					}
					i11++;
				}
			}
		}

		List<Element> ObjetOP11 = racine.getChildren("option");
		Iterator<Element> i53 = ObjetOP11.iterator();
		while (i53.hasNext()) {
			Element Objetp = (Element) i53.next();
			String codeObjet = Objetp.getAttributeValue("code");
			Element objetStructure = Objetp.getChild("structure");
			List<Element> ObjetRef = objetStructure.getChildren();
			Iterator<Element> i7 = ObjetRef.iterator();
			while (i7.hasNext()) {
				Element Objetf = (Element) i7.next();
				String code_objet = Objetf.getAttributeValue("ref");

				if (ObjetRef.isEmpty()) {
					System.out.println("pas de fils");
				} else {
					rang = 1;

					preparedStmt.setInt(1, i11);
					preparedStmt.setInt(2, rang);
					preparedStmt.setString(3, code_objet);
					try {
						preparedStmt.execute();
					} catch (Exception e11) {
					}

					preparedStmt11.setString(1, codeObjet);
					preparedStmt11.setInt(2, i11);
					try {
						preparedStmt11.execute();
					} catch (Exception e11) {
					}
					i11++;
				}
			}
		}

		List<Element> ObjetSEM = racine.getChildren("semestre");
		Iterator<Element> i54 = ObjetSEM.iterator();
		while (i54.hasNext()) {
			Element Objetp = (Element) i54.next();
			String codeObjet = Objetp.getAttributeValue("code");
			Element objetStructure = Objetp.getChild("structure");
			List<Element> ObjetRef = objetStructure.getChildren();
			Iterator<Element> i7 = ObjetRef.iterator();
			while (i7.hasNext()) {
				Element Objetf = (Element) i7.next();
				String code_objet = Objetf.getAttributeValue("ref");

				if (ObjetRef.isEmpty()) {
					System.out.println("pas de fils");
				} else {
					rang = 1;

					preparedStmt.setInt(1, i11);
					preparedStmt.setInt(2, rang);
					preparedStmt.setString(3, code_objet);
					try {
						preparedStmt.execute();
					} catch (Exception e11) {
					}

					preparedStmt11.setString(1, codeObjet);
					preparedStmt11.setInt(2, i11);
					try {
						preparedStmt11.execute();
					} catch (Exception e11) {
					}
					i11++;
				}
			}
		}

		List<Element> ObjetGR1 = racine.getChildren("groupe");
		Iterator<Element> i55 = ObjetGR1.iterator();
		while (i55.hasNext()) {
			Element Objetp = (Element) i55.next();
			String codeObjet = Objetp.getAttributeValue("code");
			Element objetStructure = Objetp.getChild("structure");
			List<Element> ObjetRef = objetStructure.getChildren();
			Iterator<Element> i7 = ObjetRef.iterator();
			while (i7.hasNext()) {
				Element Objetf = (Element) i7.next();
				String code_objet = Objetf.getAttributeValue("ref");

				if (ObjetRef.isEmpty()) {
					System.out.println("pas de fils");
				} else {
					rang = 1;

					preparedStmt.setInt(1, i11);
					preparedStmt.setInt(2, rang);
					preparedStmt.setString(3, code_objet);
					try {
						preparedStmt.execute();
					} catch (Exception e11) {
					}

					preparedStmt11.setString(1, codeObjet);
					preparedStmt11.setInt(2, i11);
					try {
						preparedStmt11.execute();
					} catch (Exception e11) {
					}
					i11++;
				}
			}
		}

		List<Element> ObjetAN11 = racine.getChildren("annee");
		Iterator<Element> i56 = ObjetAN11.iterator();
		while (i56.hasNext()) {
			Element Objetp = (Element) i56.next();
			String codeObjet = Objetp.getAttributeValue("code");
			Element objetStructure = Objetp.getChild("structure");
			List<Element> ObjetRef = objetStructure.getChildren();
			Iterator<Element> i7 = ObjetRef.iterator();
			while (i7.hasNext()) {
				Element Objetf = (Element) i7.next();
				String code_objet = Objetf.getAttributeValue("ref");

				if (ObjetRef.isEmpty()) {
					System.out.println("pas de fils");
				} else {
					rang = 1;

					preparedStmt.setInt(1, i11);
					preparedStmt.setInt(2, rang);
					preparedStmt.setString(3, code_objet);
					try {
						preparedStmt.execute();
					} catch (Exception e11) {
					}

					preparedStmt11.setString(1, codeObjet);
					preparedStmt11.setInt(2, i11);
					try {
						preparedStmt11.execute();
					} catch (Exception e11) {
					}
					i11++;
				}
			}
		}
		conn.close();

	}
}
