package models;

import java.util.List;

import javax.persistence.*;
import play.db.ebean.*;
import play.db.ebean.Model.Finder;

import com.avaje.ebean.*;

@Entity
public class Student extends User {

	public String guardianName;
	public String guardianContact;
	@ManyToMany
	public List<Volume> previousVolumes;
	@ManyToOne
	public Volume currentVolume;

	public Student(String userId, String name, String password, String contact,
			String address, String guardianName, String guardianContact) {
		super(userId, name, password, contact, address);
		this.guardianName = guardianName;
		this.guardianContact = guardianContact;
		this.previousVolumes = null;
		this.currentVolume = null;
	}

	public static Finder<String, Student> find = new Finder<String, Student>(
			String.class, Student.class);

}
