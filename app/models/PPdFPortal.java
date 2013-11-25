package models;

import javax.persistence.*;
import play.db.ebean.*;
import play.db.ebean.Model.Finder;

import com.avaje.ebean.*;

@Entity
public class PPdFPortal extends Model {

	@Id
	public Integer id;
	public Integer nextAdminId;
	public Integer nextStudentId;

	public PPdFPortal(Integer id, Integer nextAdminId, Integer nextStudentId) {
		this.id = id;
		this.nextAdminId = nextAdminId;
		this.nextStudentId = nextStudentId;
	}

	private String generateAdminId() {
		String adminId = "adm" + this.nextAdminId;
		this.nextAdminId++;
		this.update();
		return adminId;
	}

	private String generateStudentId() {
		String studentId = "cat" + this.nextStudentId;
		this.nextStudentId++;
		this.update();
		return studentId;
	}
	
	public User authenticate(String userId, String password) {
		User userAdmin = Administrator.find.where().eq("userId", userId)
				.eq("password", password).findUnique();
		User userStudent = Student.find.where().eq("userId", userId)
				.eq("password", password).findUnique();

		if (userAdmin != null)
			return userAdmin;
		else
			return userStudent;
	}
	
	public User getUser(String userId) {
		String userType = userId.substring(0, 3);
		
		if(userType.equals("adm")) return Administrator.find.where().eq("userId", userId).findUnique();
		else return Student.find.where().eq("userId", userId).findUnique();
	}

	public void createAdministrator(String name, String password,
			String contact, String address) {
		new Administrator(this.generateAdminId(), name, password, contact,
				address).save();
	}

	public void createStudent(String name, String password, String contact,
			String address, String guardianName, String guardianContact) {
		new Student(this.generateStudentId(), name, password, contact, address,
				guardianName, guardianContact).save();
	}

	public static Finder<Integer, PPdFPortal> find = new Finder<Integer, PPdFPortal>(
			Integer.class, PPdFPortal.class);
}
