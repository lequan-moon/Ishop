package test;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TestCalendar {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Calendar c = new GregorianCalendar();
		Calendar cal = Calendar.getInstance();
		System.out.println(cal.getTime());
	}

}
