package experiment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LibraryClass {
	public static final String currentFormat= "MM/dd/yyyy";
	public static final String SQLFormat = "yyyy-MM-dd";
	
	public String getTodayDataFileName() {
	    Date date = Calendar.getInstance().getTime();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    String file_todatDate = "C:\\Users\\hp\\git\\RoomSoom\\experiment\\RemoteExcelData\\" + sdf.format(date);
	    return file_todatDate;
	}

	public String  convertDateFornat(String dateString) {
		DateFormat originalFormat = new SimpleDateFormat(this.currentFormat);
		DateFormat targetFormat = new SimpleDateFormat(this.SQLFormat);
		Date date = null;
		try {
			date = originalFormat.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (String) targetFormat.format(date);
	}
}
