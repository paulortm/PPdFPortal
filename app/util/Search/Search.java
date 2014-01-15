package util.Search;

import java.util.List;
import models.Student;

public abstract class Search {

	private Search search;

	public Search() {
		super();
		this.search = null;
	}

	public Search(Search search) {
		super();
		this.search = search;
	}

	public Search getSearch() {
		return search;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

	public abstract List<Student> execute();
}
