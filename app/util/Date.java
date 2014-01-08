package util;

public class Date {

	public static String makeStringToStore(Integer day, Integer month, Integer year) {
		if (day != null && month != null && year != null)
			return year + "-" + month + "-" + day;
		else
			return null;
	}
	
	public static String storeStringtoDisplayString(String date) {
		String[] splittedDate = date.split("-");
		return splittedDate[2] + "/" + splittedDate[1] + "/" + splittedDate[0];
	}

	public static Integer getYear(String date) {
		String[] splittedDate = date.split("-");
		return Integer.parseInt(splittedDate[0]);
	}

	public static Integer getMonth(String date) {
		String[] splittedDate = date.split("-");
		return Integer.parseInt(splittedDate[1]);
	}

	public static Integer getDay(String date) {
		String[] splittedDate = date.split("-");
		return Integer.parseInt(splittedDate[2]);
	}

}
