package controllers;

import controllers.Application.Login;
import models.Administrator;
import models.PPdFPortal;
import models.Student;
import models.Volume;
import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import util.Date;
import util.Search.Search;
import util.Search.SearchByName;
import views.html.*;

public class AdminApp extends Application {

	public static class StudentForm {

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

	public static class AdministratorForm {

		public String name;
		public String address;
		public String contact;
		public String email;
	}

	public static class ChangePasswordForm {

		public String oldPassword;
		public String newPassword;
		public String newPasswordConf;
	}

	public static class ChangeYearForm {

		public String password;
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
					portal.getVolumesToEnroll(userId)));
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
			Form<StudentForm> createStudentForm = form(StudentForm.class)
					.bindFromRequest();
			return redirect(routes.AdminApp.showStudent(portal.createStudent(
					createStudentForm.get().name,
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
	public static Result editStudent(String userId) {
		String loggedUser = request().username();
		if (portal.isAdmin(loggedUser)) {
			return ok(editStudent.render(portal.getUser(loggedUser),
					(Student) portal.getUser(userId)));
		} else {
			return badRequest("Não tem permições");
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result storeStudentEdition(String userId) {
		String loggedUser = request().username();
		if (portal.isAdmin(loggedUser)) {
			Form<StudentForm> editStudentForm = form(StudentForm.class)
					.bindFromRequest();
			portal.editStudent(userId, editStudentForm.get().name,
					editStudentForm.get().contact, editStudentForm.get().email,
					editStudentForm.get().address,
					editStudentForm.get().birthDate,
					editStudentForm.get().baptismDate,
					editStudentForm.get().baptismParish,
					editStudentForm.get().firstCommunionDate,
					editStudentForm.get().firstCommunionParish,
					editStudentForm.get().guardianName,
					editStudentForm.get().guardianContact);
			return redirect(routes.AdminApp.showStudent(userId));
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
	public static Result showAdministrators() {
		String loggedUser = request().username();
		if (portal.isAdmin(loggedUser)) {
			return ok(administrators.render(portal.getUser(loggedUser),
					portal.getAdministrators()));
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

	@Security.Authenticated(Secured.class)
	public static Result showAdministrator(String userId) {
		String loggedUser = request().username();
		if (portal.isAdmin(loggedUser)) {
			return ok(administrator.render(portal.getUser(loggedUser),
					(Administrator) portal.getUser(userId)));
		} else {
			return badRequest("Não tem permições");
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result createAdministrator() {
		String loggedUser = request().username();
		if (portal.isAdmin(loggedUser)) {
			return ok(createAdministrator.render(portal.getUser(loggedUser)));
		} else {
			return badRequest("Não tem permições");
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result storeAdministrator() {
		String loggedUser = request().username();
		if (portal.isAdmin(loggedUser)) {
			Form<AdministratorForm> createAdministratorForm = form(
					AdministratorForm.class).bindFromRequest();
			return redirect(routes.AdminApp.showAdministrator(portal
					.createAdministrator(createAdministratorForm.get().name,
							createAdministratorForm.get().contact,
							createAdministratorForm.get().email,
							createAdministratorForm.get().address)));
		} else {
			return badRequest("Não tem permições");
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result editAdministrator() {
		String loggedUser = request().username();
		if (portal.isAdmin(loggedUser)) {
			return ok(editAdministrator.render(portal.getUser(loggedUser)));
		} else {
			return badRequest("Não tem permições");
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result storeAdministratorEdition() {
		String loggedUser = request().username();
		if (portal.isAdmin(loggedUser)) {
			Form<AdministratorForm> editAdministratorForm = form(
					AdministratorForm.class).bindFromRequest();
			portal.editAdministrator(loggedUser,
					editAdministratorForm.get().name,
					editAdministratorForm.get().contact,
					editAdministratorForm.get().email,
					editAdministratorForm.get().address);
			return redirect(routes.AdminApp.showAdministrator(loggedUser));
		} else {
			return badRequest("Não tem permições");
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result changePasswordPage() {
		String loggedUser = request().username();
		if (portal.isAdmin(loggedUser)) {
			return ok(changePassword.render(portal.getUser(loggedUser)));
		} else {
			return badRequest("Não tem permições");
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result changePassword() {
		String loggedUser = request().username();
		if (portal.isAdmin(loggedUser)) {
			Form<ChangePasswordForm> changePasswordForm = form(
					ChangePasswordForm.class).bindFromRequest();
			if (portal.authenticate(loggedUser,
					changePasswordForm.get().oldPassword) == null
					|| !changePasswordForm.get().newPassword
							.equals(changePasswordForm.get().newPasswordConf)) {
				flash("failedChangePassword", "Password errada");
				return redirect(routes.AdminApp.changePasswordPage());
			} else {
				portal.changePassword(loggedUser,
						changePasswordForm.get().newPassword);
				return redirect(routes.Application.index());
			}
		} else {

			return badRequest("Não tem permições");
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result changeYearPage() {
		String loggedUser = request().username();
		if (portal.isAdmin(loggedUser)) {
			return ok(changeYear.render(portal.getUser(loggedUser),
					portal.currentYear));
		} else {
			return badRequest("Não tem permições");
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result changeYear() {
		String loggedUser = request().username();
		if (portal.isAdmin(loggedUser)) {
			Form<ChangeYearForm> changeYearForm = form(ChangeYearForm.class)
					.bindFromRequest();
			if (portal.authenticate(loggedUser, changeYearForm.get().password) == null) {
				flash("failedChangeYear", "Password errada");
				return redirect(routes.AdminApp.changeYearPage());
			} else {
				portal.changeYear();
				return redirect(routes.Application.index());
			}
		} else {

			return badRequest("Não tem permições");
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result showYear(String yearId) {
		String loggedUser = request().username();
		if (portal.isAdmin(loggedUser)) {
			String prevYearId = portal.getPrevYearId(yearId);
			String nextYearId = portal.getNextYearId(yearId);
			return ok(indexAdm.render(portal.getUser(loggedUser),
					portal.getYear(prevYearId), portal.getYear(yearId),
					portal.getYear(nextYearId),
					portal.getYearCurrentVolumes(yearId)));
		} else {
			return badRequest("Não tem permições");
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result search(String catName) {
		String loggedUser = request().username();
		if (portal.isAdmin(loggedUser)) {
			Search search = new SearchByName(catName);
			return ok(searchResults.render(portal.getUser(loggedUser),
					search.execute()));
		} else {
			return badRequest("Não tem permições");
		}
	}
}
