package apps.mrosystem.utils;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import apps.mrosystem.domain.Part;

import com.vaadin.ui.Tree;

public class Utils {
	
	
	public static String getMySqlDateTime(Date date){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return sdf.format(date);
		
	}

	
	
	public static HashMap<String, String[]> arrayListToHashMap(ArrayList<ArrayList<String>> array) throws SQLException {

		HashMap<String,String[]> hashMap = new HashMap<String,String[]>();
		
		
        for (int i = 0; i < array.size(); i++) {
        	
        	String key = (String) array.get(i).get(0);
        	
        	String[] value = array.get(i).get(1).split(",");
            
            hashMap.put(key, value);
        }
		return hashMap;
	}
	
	
	public static boolean isNull(Object obj){
		if(obj == null)
			return true;
		
		return false;
	}

	public static boolean contains(Object[] haystack, Object needle) {
		for(Object item : haystack){
			if(item.equals(needle)){
				return true;
			}
		}
		return false;
	}


	public static Date addDaysToDate(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date); 
		c.add(Calendar.DATE, days); 
		return c.getTime();
	}

	public static Date addHoursToDate(Date start, int hours) {
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		c.add(Calendar.HOUR_OF_DAY, hours); 		
		return c.getTime();
	}
	
	public static Date addMinsToDate(Date start, int mins) {
	
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		c.add(Calendar.MINUTE, mins); 	
		return c.getTime();
	}
	
	
	public static long dateDifference(Date date1, Date date2, TimeUnit timeUnit) {
	    long difference = date2.getTime() - date1.getTime();
	    return timeUnit.convert(difference, TimeUnit.MILLISECONDS);
	}

	public static String getFormattedDate(Date date, String format){
		if(date == null){
			return "";
		}
		return new SimpleDateFormat(format).format(date);
	}



	public static Date unixTimestampToDate(String mySqlDatetime) {
		if(mySqlDatetime != null){
			return new Date(Long.parseLong(mySqlDatetime) * 1000);
		}
		return new Date();
	}
	
	
	public static double getDistance(double lat1, double lon1, double lat2, double lon2){
		int R = 6371000; // metres
		double lat1Rad = Math.toRadians(lat1);
		double lat2Rad = Math.toRadians(lat2);
		double delta1 = Math.toRadians(lat2-lat1);
		double delta2 = Math.toRadians(lon2-lon1);

		double a = Math.sin(delta1/2) * Math.sin(delta1/2) +
		        Math.cos(lat1Rad) * Math.cos(lat2Rad) *
		        Math.sin(delta2/2) * Math.sin(delta2/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

		double d = R * c;
		
		return d;
	}



}
