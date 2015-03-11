package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import services.FormationService;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.Mapper;

import domain.Formation;
import domain.Person;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/downloadfile")
public class DownloadController {
	@Autowired
	FormationService fomService;
	private static final Logger logger = LoggerFactory
			.getLogger(DownloadController.class);

	@RequestMapping(value = "/xml", method = RequestMethod.GET)
	public void downloadnotepadfile(HttpServletRequest request,
			HttpServletResponse response) {
		String filename = "donnees.xml";
		XStream xStream = new XStream();
		Collection<Formation> formations = fomService.findAll();
		for (Formation f : formations)
			f.getResponsable().setPassword(null);

		xStream.addDefaultImplementation(
				org.hibernate.collection.internal.PersistentBag.class,
				java.util.List.class);

		Mapper mapper = xStream.getMapper();
		xStream.registerConverter(new HibernateCollectionConverter(mapper));
		xStream.alias("formation", Formation.class);
		xStream.alias("personne", Person.class);
		try {
			String path = request.getSession().getServletContext()
					.getRealPath("");
			File f = new File(path + "//" + filename);
			FileOutputStream fos;
			fos = new FileOutputStream(f);

			// chaîne à rajouter
			String enco = "<?xml version='1.0' encoding='UTF-8'?>\n";

			// ecriture chaine dans le fichier
			fos.write(enco.getBytes());
			xStream.toXML(formations, fos);

			response.setContentType("application/xml");
			response.setContentLength(new Long(f.length()).intValue());
			response.setHeader("Content-Disposition",
					"attachment; filename=donnees.xml");
			FileCopyUtils.copy(new FileInputStream(f),
					response.getOutputStream());

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}