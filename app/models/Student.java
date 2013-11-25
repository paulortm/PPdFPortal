package models;

import javax.persistence.*;
import play.db.ebean.*;
import play.db.ebean.Model.Finder;

import com.avaje.ebean.*;

@Entity
public class Student extends User {

	public String guardianName;
	public String guardianContact;

	public Student(String name, String password, String contact,
			String address, String guardianName, String guardianContact) {
		super(name, password, contact, address);
		this.guardianName = guardianName;
		this.guardianContact = guardianContact;
	}

	public static Finder<Integer, Student> find = new Finder<Integer, Student>(
			Integer.class, Student.class);

}
