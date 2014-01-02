package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import com.avaje.ebean.*;

@Entity
public class Year extends Model {

	@Id
	public String id;
	@OneToMany(mappedBy = "year")
	public List<Volume> volumes = new ArrayList<Volume>();
	public boolean isCurrent;

	public Year(String id, boolean isCurrent) {
		super();
		this.id = id;
		this.isCurrent = isCurrent;
	}

	public static Finder<String, Year> find = new Finder<String, Year>(
			String.class, Year.class);

}
