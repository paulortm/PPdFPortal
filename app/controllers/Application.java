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

	@Security.Authenticated(Secured.class)
	public static Result index() {
		String loggedUser = request().username();
		if (portal.isAdmin(loggedUser)) {
			String prevYearId = portal.getPrevYearId(portal.currentYear.id);
			String nextYearId = portal.getNextYearId(portal.currentYear.id);
			return ok(indexAdm.render(portal.getUser(loggedUser),
					portal.getYear(prevYearId), portal.currentYear,
					portal.getYear(nextYearId),
					portal.getYearCurrentVolumes(portal.currentYear.id)));
		} else {
			return ok(indexCat.render("PPdFPortal"));
		}
	}

	public static Result login() {
		return ok(login.render());
	}

	public static Result logout() {
		session().clear();
		flash("successLogout", "Logout efectuado");
		return redirect(routes.Application.login());
	}

	public static Result authenticate() {
		Form<Login> loginForm = form(Login.class).bindFromRequest();
		if (portal.authenticate(loginForm.get().userId,
				loginForm.get().password) == null) {
			flash("failedLogin", "Utilizador ou password errados");
			return redirect(routes.Application.login());
		} else {
			session().clear();
			session("userId", loginForm.get().userId);
			return redirect(routes.Application.index());
		}
	}
}
