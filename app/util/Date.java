package util;

public class Date {

	public static String makeStringtoDisplay(String date) {
		String[] splittedDate = date.split(Constants.S_DATE_DIV_TOKEN);
		return splittedDate[2] + Constants.D_DATE_DIV_TOKEN + splittedDate[1]
				+ Constants.D_DATE_DIV_TOKEN + splittedDate[0];
	}

	public static Integer getYear(String date) {
		String[] splittedDate = date.split(Constants.S_DATE_DIV_TOKEN);
		return Integer.parseInt(splittedDate[0]);
	}

	public static Integer getMonth(String date) {
		String[] splittedDate = date.split(Constants.S_DATE_DIV_TOKEN);
		return Integer.parseInt(splittedDate[1]);
	}

	public static Integer getDay(String date) {
		String[] splittedDate = date.split(Constants.S_DATE_DIV_TOKEN);
		return Integer.parseInt(splittedDate[2]);
	}

}
