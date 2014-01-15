package util.Search;

import java.util.LinkedList;
import java.util.List;

import models.Student;

public class SearchByName extends Search {
	
	private String name;
	
	public SearchByName(String name) {
		super();
		this.name = name;
	}

	public SearchByName(Search search, String name) {
		super(search);
		this.name = name;
	}

	@Override
	public List<Student> execute() {
		List<Student> searched = new LinkedList<Student>();
		List<Student> all;
		
		if (this.getSearch() == null)
			all = Student.find.all();
		else
			all = this.getSearch().execute();
		
		for (Student st : all) {
			if (st.name.contains(this.name))
				searched.add(st);
		}
		
		return searched;
	}

}
