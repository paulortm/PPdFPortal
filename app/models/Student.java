package models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;
import play.db.ebean.*;
import play.db.ebean.Model.Finder;

import com.avaje.ebean.*;

@Entity
public class Student extends User {

	public String birthDate;
	public String baptismDate;
	public String baptismParish;
	public String firstCommunionDate;
	public String firstCommunionParish;
	public Integer volumeDegree;
	public String guardianName;
	public String guardianContact;
	@ManyToMany
	public List<Volume> previousVolumes = new LinkedList<Volume>();
	@ManyToOne
	public Volume currentVolume;
	public boolean isActive;

	public Student(String userId, String name, String password, String contact,
			String email, String address, String birthDate, String baptismDate,
			String baptismParish, String firstCommunionDate,
			String firstCommunionParish, Integer volumeDegree,
			String guardianName, String guardianContact) {
		super(userId, name, password, contact, email, address);
		this.birthDate = birthDate;
		this.baptismDate = baptismDate;
		this.baptismParish = baptismParish;
		this.firstCommunionDate = firstCommunionDate;
		this.firstCommunionParish = firstCommunionParish;
		this.volumeDegree = volumeDegree;
		this.guardianName = guardianName;
		this.guardianContact = guardianContact;
		this.currentVolume = null;
		this.isActive = true;
	}

	public void changeYear() {
		this.previousVolumes.add(this.currentVolume);
		this.volumeDegree = this.currentVolume.degree;
		this.currentVolume = null;
		this.update();
	}

	public static Finder<String, Student> find = new Finder<String, Student>(
			String.class, Student.class);

}
