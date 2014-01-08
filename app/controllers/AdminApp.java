package controllers;

import controllers.Application.Login;
import models.PPdFPortal;
import models.Student;
import models.Volume;
import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import util.Date;
import views.html.*;

public class AdminApp extends Controller {

	public static PPdFPortal portal = PPdFPortal.find.byId(1);

	public static class CreateStudent {

		public String name;
		public String address;
		public String contact;
		public String email;
		public String birthDate;
		public String guardianName;
		public String guardianContact;
		public String baptismDate;
		public String baptismParish;
		public String firstCommunionDate;
		public String firstCommunionParish;
		public Integer volumeDegree;
	}

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
	public static Result createStudent() {
		String loggedUser = request().username();
		if (portal.isAdmin(loggedUser)) {
			return ok(createStudent.render(portal.getUser(loggedUser)));
		} else {
			return badRequest("Não tem permições");
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result storeStudent() {
		String loggedUser = request().username();
		if (portal.isAdmin(loggedUser)) {
			Form<CreateStudent> createStudentForm = form(CreateStudent.class)
					.bindFromRequest();
			return redirect(routes.AdminApp.showStudent(portal.createStudent(
					createStudentForm.get().name, "password",
					createStudentForm.get().contact,
					createStudentForm.get().email,
					createStudentForm.get().address,
					createStudentForm.get().birthDate,
					createStudentForm.get().baptismDate,
					createStudentForm.get().baptismParish,
					createStudentForm.get().firstCommunionDate,
					createStudentForm.get().firstCommunionParish,
					createStudentForm.get().volumeDegree,
					createStudentForm.get().guardianName,
					createStudentForm.get().guardianContact)));
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
