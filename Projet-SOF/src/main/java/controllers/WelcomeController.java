package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import domain.User;

/**
 * Cette class sert de controleur pour ce qui concerne l'accueil de l'application
 * 
 * @author LOUKAH Maria
 * @author BATTOUCHI Asmaa
 * @author LEMAIRE Alexandre
 * @version 1.0
 */
@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {
	
	@Autowired()
	User user;

	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------

	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView result;
		result = new ModelAndView("welcome/index");
		return result;
	}
	
	@ModelAttribute("user")
	public User newUser() {
		return user;
	}
}