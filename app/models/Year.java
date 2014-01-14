package models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import util.Constants;

import com.avaje.ebean.*;

@Entity
public class Year extends Model {

	@Id
	public String id;
	@OneToMany(mappedBy = "year")
	public List<Volume> volumes = new LinkedList<Volume>();
	public boolean isCurrent;

	public Year(String id) {
		super();
		this.id = id;
		this.isCurrent = true;
	}

	public Year(String id, boolean isCurrent) {
		super();
		this.id = id;
		this.isCurrent = isCurrent;
	}
	
	public void makeOld() {
		this.isCurrent = false;
		for (Volume v : this.volumes) {
			v.changeYear();
		}
		this.update();
	}
	
	public void makeNew() {
		Integer i;
		Volume vol;
		
		for (i = 1; i <= Constants.MAX_VOLUMES; i++) {
			vol = new Volume(i);
			vol.year = this;
			this.volumes.add(vol);
			vol.save();
			this.update();
		}
	}

	public static Finder<String, Year> find = new Finder<String, Year>(
			String.class, Year.class);

}
