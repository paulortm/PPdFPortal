package models;

import java.util.LinkedList;
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
	@ManyToMany
	public List<Student> students = new LinkedList<Student>();

	public Volume(Integer degree) {
		super();
		this.degree = degree;
		this.year = null;
	}

	public void changeYear() {
		for (Student st : this.students) {
			st.changeYear();
		}
	}

	public static Finder<Integer, Volume> find = new Finder<Integer, Volume>(
			Integer.class, Volume.class);
}
