package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@MappedSuperclass
public abstract class User extends Model {

	@Id
	public String userId;
	public String name;
	public String password;
	public String contact;
	public String email;
	public String address;

	public User(String userId, String name, String password, String contact,
			String email, String address) {
		this.userId = userId;
		this.name = name;
		this.password = password;
		this.contact = contact;
		this.email = email;
		this.address = address;
	}
}