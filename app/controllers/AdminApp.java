package controllers;

import models.PPdFPortal;
import models.Student;
import models.Volume;
import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import views.html.*;

public class AdminApp extends Controller {

	public static PPdFPortal portal = PPdFPortal.find.byId(1);

	@Security.Authenticated(Secured.class)
	public static Result showVolume(Integer id) {
		String loggedUser = request().username();
		if (portal.isAdmin(loggedUser)) {
			return ok(volumeStudents.render(portal.getUser(loggedUser),
					portal.getVolume(id), portal.getStudents(id)));
		} else {
			return badRequest("Não tem permições");
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result showNotEnrolled() {
		String loggedUser = request().username();
		if (portal.isAdmin(loggedUser)) {
			return ok(notEnrolled.render(portal.getUser(loggedUser),
					portal.currentYear, portal.getNotEnrolledStudents()));
		} else {
			return badRequest("Não tem permições");
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result showStudent(String userId) {
		String loggedUser = request().username();
		if (portal.isAdmin(loggedUser)) {
			return ok(student.render(portal.getUser(loggedUser),
					(Student) portal.getUser(userId),
					portal.getCurrentVolumes()));
		} else {
			return badRequest("Não tem permições");
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result enroll(String userId, Integer volumeId) {
		String loggedUser = request().username();
		if (portal.isAdmin(loggedUser)) {
			portal.enrollStudent(userId, volumeId);
			return redirect(routes.AdminApp.showStudent(userId));
		} else {
			return badRequest("Não tem permições");
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result unenroll(String userId) {
		String loggedUser = request().username();
		if (portal.isAdmin(loggedUser)) {
			portal.unenrollStudent(userId);
			return redirect(routes.AdminApp.showStudent(userId));
		} else {
			return badRequest("Não tem permições");
		}
	}

}
