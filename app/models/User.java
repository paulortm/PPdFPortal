package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@MappedSuperclass
public abstract class User extends Model {

	@Id
	public Integer id;
	public String name;
	public String password;
	public String contact;
	public String address;

	public User(String name, String password, String contact, String address) {
		this.name = name;
		this.password = password;
		this.contact = contact;
		this.address = address;
	}

	public static User authenticate(Integer id, String password) {
		User userAdmin = Administrator.find.where().eq("id", id)
				.eq("password", password).findUnique();
		User userStudent = Student.find.where().eq("id", id)
				.eq("password", password).findUnique();

		if (userAdmin != null)
			return userAdmin;
		else
			return userStudent;
	}
}