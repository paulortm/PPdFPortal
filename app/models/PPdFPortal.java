package models;

import javax.persistence.*;
import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import com.avaje.ebean.*;

import java.util.LinkedList;
import java.util.List;

@Entity
public class PPdFPortal extends Model {

	@Id
	public Integer id;
	public Integer nextAdminId;
	public Integer nextStudentId;
	public Integer nextYear;
	@OneToOne
	public Year currentYear;

	public PPdFPortal(Integer id, Integer nextAdminId, Integer nextStudentId,
			Integer nextYear, Year currentYear) {
		this.id = id;
		this.nextAdminId = nextAdminId;
		this.nextStudentId = nextStudentId;
		this.nextYear = nextYear;
		this.currentYear = currentYear;
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

	private String generateYearId() {
		String yearId = this.nextYear + "/" + ++this.nextYear;
		this.update();
		return yearId;
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

		if (userType.equals("adm"))
			return Administrator.find.where().eq("userId", userId).findUnique();
		else
			return Student.find.where().eq("userId", userId).findUnique();
	}

	public List<Volume> getCurrentVolumes() {
		return this.currentYear.volumes;
	}
	
	public Volume getVolume(Integer id) {
		return Volume.find.byId(id);
	}
	
	public List<Student> getStudents(Integer volumeId) {
		return Volume.find.byId(volumeId).students;
	}

	public List<Student> getNotEnrolledStudents() {
		List<Student> students = Student.find.all();
		List<Student> notEnrolled = new LinkedList<Student>();

		for (Student s : students) {
			if (s.isActive && s.currentVolume == null) {
				notEnrolled.add(s);
			}
		}

		return notEnrolled;
	}

	public void enrollStudent(String userId, Integer volumeId) {
		if (!this.isAdmin(userId)) {
			Student student = (Student) this.getUser(userId);
			Volume volume = Volume.find.byId(volumeId);
			volume.students.add(student);
			student.currentVolume = volume;
			volume.update();
			student.update();
		}
	}

	public void unenrollStudent(String userId) {
		if (!this.isAdmin(userId)
				&& ((Student) this.getUser(userId)).currentVolume != null) {
			Student student = (Student) this.getUser(userId);
			Volume volume = student.currentVolume;
			volume.students.remove(student);
			student.currentVolume = null;
			volume.update();
			student.update();
		}
	}

	public boolean isAdmin(String userId) {
		String userType = userId.substring(0, 3);

		if (userType.equals("adm"))
			return true;
		else
			return false;
	}

	public void createAdministrator(String name, String password,
			String contact, String address) {
		new Administrator(this.generateAdminId(), name, password, contact,
				address).save();
	}

	public void createStudent(String name, String password, String contact,
			String address, String birthDate, String baptismDate,
			String baptismParish, String firstCommunionDate,
			String firstCommunionParish, Integer volumeDegree,
			String guardianName, String guardianContact) {
		new Student(this.generateStudentId(), name, password, contact, address,
				birthDate, baptismDate, baptismParish, firstCommunionDate,
				firstCommunionParish, volumeDegree, guardianName,
				guardianContact).save();
	}

	public static Finder<Integer, PPdFPortal> find = new Finder<Integer, PPdFPortal>(
			Integer.class, PPdFPortal.class);
}
