package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.User;
import services.AuthentificationServiceDB;

@Controller
@RequestMapping("auth")
public class LoginControlerSingleton extends AbstractController {

	@Autowired()
	User user;

	@Autowired()
	AuthentificationServiceDB authService;

	/**
	 * Retourne la vue du formulaire d'authentification correspondant à l'URL /Annuaire/auth/login.hmt en méthode GET.
	 *
	 * @return La vue du formulaire d'authentification.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "authentification/login";
	}

	/**
	 * Méthode appelée lors de la validation du formulaire d'authentification. Vérifie si le login et le mot de passe
	 * sont corrects, puis place les informations de la personne en session via la classe User.
	 *
	 * @see User
	 * @param u
	 *            L'utilisateur en session.
	 * @param result
	 *            Le résultat du binding.
	 * @return La vue du formulaire d'authentification avec un message en cas d'erreur(s), la vue correspondant à la
	 *         liste des personnes de l'annuaire sinon.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute User u, BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("authentification/login", "error", "");
		}
		try {
			if (!authService.login(u)) {
				return new ModelAndView("authentification/login", "error", "erreur.login.logInv");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("authentification/login", "error", "erreur.BD");
		}
		return new ModelAndView("authentification/login", "error", "");
	}

	/**
	 * 
	 * Vide la session utilisateur puis retourne la vue du formulaire d'authentification.
	 *
	 * @return Redirige vers la page d'authentification.
	 */
	@RequestMapping(value = "/logout")
	public String logout() {
		authService.flush();
		return "redirect:login.htm";
	}

	@ModelAttribute("user")
	public User newUser() {
		return user;
	}
}
