package util;

public class Date {

	public static String makeString(Integer day, Integer month, Integer year) {
		if (day != null && month != null && year != null)
			return day + "/" + month + "/" + year;
		else return null;
	}

	public static Integer getDay(String date) {
		String[] splittedDate = date.split("/");
		return Integer.parseInt(splittedDate[0]);
	}

	public static Integer getMonth(String date) {
		String[] splittedDate = date.split("/");
		return Integer.parseInt(splittedDate[1]);
	}

	public static Integer getYear(String date) {
		String[] splittedDate = date.split("/");
		return Integer.parseInt(splittedDate[2]);
	}

}
