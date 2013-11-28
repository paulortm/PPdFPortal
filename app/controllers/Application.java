package controllers;

import models.PPdFPortal;
import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import views.html.*;

public class Application extends Controller {

	public static class Login {

		public String userId;
		public String password;

		public String validate() {
			PPdFPortal portal = PPdFPortal.find.byId(1);

			if (portal.authenticate(userId, password) == null) {
				return "Utilizador ou password errados";
			}
			return null;
		}
	}

	public static Result index() {
		return ok(index.render("PPdFPortal"));
	}

	public static Result login() {
		return ok(login.render(form(Login.class)));
	}

	public static Result authenticate() {
		Form<Login> loginForm = form(Login.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			return badRequest(login.render(loginForm));
		} else {
			session().clear();
			session("userId", loginForm.get().userId);
			return redirect(routes.Application.index());
		}
	}
}
