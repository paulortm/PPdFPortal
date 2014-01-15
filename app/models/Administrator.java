package models;

import javax.persistence.*;
import play.db.ebean.*;
import play.db.ebean.Model.Finder;

import com.avaje.ebean.*;

@Entity
public class Administrator extends User {
	
	public boolean isActive;

	public Administrator(String userId, String name, String password,
			String contact, String email, String address) {
		super(userId, name, password, contact, email, address);
		this.isActive = true;
	}

	public static Finder<String, Administrator> find = new Finder<String, Administrator>(
			String.class, Administrator.class);

}
