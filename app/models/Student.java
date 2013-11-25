package models;

import javax.persistence.*;
import play.db.ebean.*;
import play.db.ebean.Model.Finder;

import com.avaje.ebean.*;

@Entity
public class Student extends User {

	public String guardianName;
	public String guardianContact;

	public Student(String userId, String name, String password, String contact,
			String address, String guardianName, String guardianContact) {
		super(userId, name, password, contact, address);
		this.guardianName = guardianName;
		this.guardianContact = guardianContact;
	}

	public static Finder<String, Student> find = new Finder<String, Student>(
			String.class, Student.class);

}
