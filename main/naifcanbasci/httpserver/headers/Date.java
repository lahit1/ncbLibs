package naifcanbasci.httpserver.headers;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public final class Date
{
	public static String getDate(Date date) {
		final String ISO_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";
		final SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);
		final TimeZone utc = TimeZone.getTimeZone("UTC");
		sdf.setTimeZone(utc);
		return sdf.format(date);
	}
	
	public static String getDate() {
		return getDate(new Date());
	}
}
