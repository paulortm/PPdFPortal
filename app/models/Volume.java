package models;

import java.util.List;
import javax.persistence.*;
import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import com.avaje.ebean.*;

@Entity
public class Volume extends Model {

	@Id
	public Integer id;
	public Integer degree;
	@ManyToOne
	public Year year;
	@ManyToMany(cascade = CascadeType.ALL)
	public List<Student> students;
	public boolean isCurrent;

	public Volume(Integer degree, boolean isCurrent) {
		super();
		this.degree = degree;
		this.year = null;
		this.students = null;
		this.isCurrent = isCurrent;
	}

	public static Finder<Integer, Volume> find = new Finder<Integer, Volume>(
			Integer.class, Volume.class);
}
