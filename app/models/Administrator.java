package models;

import javax.persistence.*;
import play.db.ebean.*;
import play.db.ebean.Model.Finder;

import com.avaje.ebean.*;

@Entity
public class Administrator extends User {

	public Administrator(String name, String password, String contact,
			String address) {
		super(name, password, contact, address);
	}

	public static Finder<Integer, Administrator> find = new Finder<Integer, Administrator>(
			Integer.class, Administrator.class);

}
