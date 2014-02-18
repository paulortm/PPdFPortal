package models;

import javax.persistence.*;
import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import util.Constants;
import util.Date;

import com.avaje.ebean.*;

import java.util.LinkedList;
import java.util.List;

/**
 * This class works like a Facade (design pattern).
 */
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

	/**
	 * This method is used to generate passwords to the new users.
	 * 
	 * @return the password
	 */
	private String generatePassword() {
		// TODO Make something that generates good random passwords
		return "password";
	}

	/**
	 * This method is used to generate an ID for the new administrator.
	 * 
	 * @return the Id
	 */
	private String generateAdminId() {
		String adminId = "adm" + this.nextAdminId;
		this.nextAdminId++;
		this.update();
		return adminId;
	}

	/**
	 * This method is used to generate an ID for the new student.
	 * 
	 * @return the Id
	 */
	private String generateStudentId() {
		String studentId = "cat" + this.nextStudentId;
		this.nextStudentId++;
		this.update();
		return studentId;
	}

	/**
	 * This method is used to generate an ID for the new year (for example:
	 * "2013/2014", "2014/2015", ...).
	 * 
	 * @return the Id
	 */
	private String generateYearId() {
		String yearId = this.nextYear + Constants.YEAR_DIV_TOKEN
				+ ++this.nextYear;
		this.update();
		return yearId;
	}

	/**
	 * This method is used when, given an ID of an year, we want the ID of the
	 * previous year (for example, given the ID "2013/2014", the previous year
	 * ID is "2012/2013").
	 * 
	 * @param yearId the year ID
	 * @return the ID of the previous year.
	 */
	public String getPrevYearId(String yearId) {
		String[] splittedYear = yearId.split(Constants.YEAR_DIV_TOKEN);
		Integer year1 = Integer.parseInt(splittedYear[0]);
		Integer year2 = Integer.parseInt(splittedYear[1]);
		return --year1 + Constants.YEAR_DIV_TOKEN + --year2;
	}

	/**
	 * This method is used when, given an ID of an year, we want the ID of the
	 * next year (for example, given the ID "2013/2014", the next year
	 * ID is "2014/2015").
	 * 
	 * @param yearId the year ID
	 * @return the ID of the previous year.
	 */
	public String getNextYearId(String yearId) {
		String[] splittedYear = yearId.split(Constants.YEAR_DIV_TOKEN);
		Integer year1 = Integer.parseInt(splittedYear[0]);
		Integer year2 = Integer.parseInt(splittedYear[1]);
		return ++year1 + Constants.YEAR_DIV_TOKEN + ++year2;
	}

	public User authenticate(String userId, String password) {
		User userAdmin = Administrator.find.where().eq("userId", userId)
				.eq("password", password).findUnique();
		User userStudent = Student.find.where().eq("userId", userId)
				.eq("password", password).findUnique();

		if (userAdmin != null) {
			if (((Administrator) userAdmin).isActive)
				return userAdmin;
			else
				return null;
		} else if (userStudent != null) {
			if (((Student) userStudent).isActive)
				return userStudent;
			else
				return null;
		} else
			return null;
	}

	public Year getYear(String yearId) {
		return Year.find.byId(yearId);
	}

	public User getUser(String userId) {
		String userType = userId.substring(0, 3);

		if (userType.equals("adm"))
			return Administrator.find.where().eq("userId", userId).findUnique();
		else
			return Student.find.where().eq("userId", userId).findUnique();
	}

	public List<Administrator> getAdministrators() {
		return Administrator.find.all();
	}

	public List<Volume> getYearCurrentVolumes(String yearId) {
		return this.getYear(yearId).volumes;
	}

	public List<Volume> getVolumesToEnroll(String userId) {
		Student st = (Student) this.getUser(userId);
		List<Volume> volumes = new LinkedList<Volume>();

		for (Volume v : this.currentYear.volumes) {
			if (v.degree > st.volumeDegree) {
				volumes.add(v);
			}
		}

		return volumes;
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

	public String createAdministrator(String name, String contact,
			String email, String address) {
		String userId = this.generateAdminId();
		new Administrator(userId, name, this.generatePassword(), contact,
				email, address).save();
		return userId;
	}

	public void editAdministrator(String userId, String name, String contact,
			String email, String address) {
		Administrator admin = (Administrator) this.getUser(userId);
		admin.name = name;
		admin.contact = contact;
		admin.email = email;
		admin.address = address;
		admin.update();
	}

	public String createStudent(String name, String contact, String email,
			String address, String birthDate, String baptismDate,
			String baptismParish, String firstCommunionDate,
			String firstCommunionParish, Integer volumeDegree,
			String guardianName, String guardianContact) {
		String userId = this.generateStudentId();
		new Student(userId, name, this.generatePassword(), contact, email,
				address, birthDate, baptismDate, baptismParish,
				firstCommunionDate, firstCommunionParish, volumeDegree,
				guardianName, guardianContact).save();
		return userId;
	}

	public void editStudent(String userId, String name, String contact,
			String email, String address, String birthDate, String baptismDate,
			String baptismParish, String firstCommunionDate,
			String firstCommunionParish, String guardianName,
			String guardianContact) {
		Student st = (Student) this.getUser(userId);
		st.name = name;
		st.contact = contact;
		st.email = email;
		st.address = address;
		st.birthDate = birthDate;
		st.baptismDate = baptismDate;
		st.baptismParish = baptismParish;
		st.firstCommunionDate = firstCommunionDate;
		st.firstCommunionParish = firstCommunionParish;
		st.guardianName = guardianName;
		st.guardianContact = guardianContact;
		st.update();
	}

	public void changePassword(String userId, String password) {
		User user = this.getUser(userId);
		user.password = password;
		user.update();
	}

	public void changeYear() {
		String newYearId = this.generateYearId();
		Year newYear, oldYear;
		new Year(newYearId).save();
		newYear = Year.find.byId(newYearId);
		oldYear = this.currentYear;
		oldYear.makeOld();
		newYear.makeNew();
		this.currentYear = newYear;
		this.update();
	}

	public static Finder<Integer, PPdFPortal> find = new Finder<Integer, PPdFPortal>(
			Integer.class, PPdFPortal.class);
}
