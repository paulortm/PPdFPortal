package controllers;

import models.PPdFPortal;
import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import views.html.*;

public class Application extends Controller {
	
	public static PPdFPortal portal = PPdFPortal.find.byId(1);

	public static class Login {

		public String userId;
		public String password;
	}
	
	public static class VolumeChoice {
		
		public String volume;
	}

	@Security.Authenticated(Secured.class)
	public static Result index() {
		String loggedUser = request().username();
		if (portal.isAdmin(loggedUser)) {
			return ok(indexAdm.render(portal.getUser(loggedUser), portal.getCurrentVolumes()));
		} else {
			return ok(indexCat.render("PPdFPortal"));
		}
	}
	
	@Security.Authenticated(Secured.class)
	public static Result showVolume() {
		return ok(indexCat.render("PPdFPortal"));
	}

	public static Result login() {
		return ok(login.render());
	}

	public static Result logout() {
		session().clear();
		flash("success", "Logout efectuado");
		return redirect(routes.Application.login());
	}

	public static Result authenticate() {
		Form<Login> loginForm = form(Login.class).bindFromRequest();
		if (portal.authenticate(loginForm.get().userId, loginForm.get().password) == null) {
			flash("failedLogin", "Utilizador ou password errados");
			return redirect(routes.Application.login());
		} else {
			session().clear();
			session("userId", loginForm.get().userId);
			return redirect(routes.Application.index());
		}
	}
}
