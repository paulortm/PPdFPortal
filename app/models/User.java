package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class User extends Model {

	@Id
	public Integer id;
	public String name;
	public String password;

	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	public static User authenticate(Integer id, String password) {
        return find.where().eq("id", id)
            .eq("password", password).findUnique();
    }

	public static Finder<Integer, User> find = new Finder<Integer, User>(
			Integer.class, User.class);
}