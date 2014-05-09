package com.foodango.constant;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.foodango.data.CategoryData;
import com.foodango.data.ResData;
import com.foodango.data.RestaurantData;



import android.content.Context;
import android.location.Location;
import android.widget.Toast;

public class ApplicationConstants {
	public static File cacheDir;

	
	public static final String NETWORK_NOT_FOUND = "Please make sure Internet connection is available.";
	public static final String GPS_NOT_FOUND = "Please enable your GPS or Network Service.";
	public static final String EMPTY_STRING="";

	public static final String MSG_OK="OK";	
	public static final String ERROR_CODE_UNKNOW_HOST = "404";
	public static final String ERROR_CODE_TIME_OUT = "408";
	public static final int INT_ERROR_CODE_UNKNOW_HOST = 404;
	public static final int INT_ERROR_CODE_TIME_OUT = 408;
	public static final int SPLASH_WAIT_MILLISEC = 3000;
	public static final int GET_DISH_CATEGORY = 1000;
	public static final int GET_DISH_DETAILS = 1001;
	
	  public static final String[] sMenuStrings = {"Home","Menu","My Order","Call Waiter","Ask for Bill","Rewards","Feedback","Logout"};
	public static int[] colors = new int[] { 0x30ffffff, 0x30808080 };


	public static float dps;
	public static int density;
	
	// Login parameters
	public static final String LOGIN_USER_VALUE="UserValue";
	public static final int LOGIN_CODE = 200;

	public static int getDps(int pixes) {

		int dps = pixes * (160 / density);
		return dps;
	}

	public static String MAP_IMAGE_API = "http://www.dc2go.net/api/dbs/showNearByImg.php?s=";// 320x200&poi=1.289755,103.861313
	// http://www.dc2go.net/api/dbs/showNearByImg.php?s=[IMAGE
	// SIZE]&poi=[LATITUDE],[LONGITUDE]"


	/*------------------------------*/
	// ---------- Database Table
	/*---------------------------------*/
	public static final String DB_NAME = "DBS_INDULGE";
	public static final String TABLE_MERCHANT = "MERCHANTINFO";
	public static final int ITEMS_PER_PAGE = 20;
	public static final String DEFAULT_SHARE_DATA = "DEFAULT_SHARE_DATA";
	public static final String COPY_DEFAULT_SHARE_DATA = "COPY_DEFAULT_SHARE_DATA";
//	public static final String NETWORK_NOT_FOUND = "Please make sure Internet connection is available.";
	public static final String DATA_NOT_FOUND = "Data not found.";
	public static final String NO_PROMOTION_AVAILABLE="Sorry, no promotions currently available. Please check in later.";
	public static final String CAMVIEW_NOTIFICATION ="Scan your surroundings by selecting the 'Cam View'!";
	public static final String DBS_SHARE_TEXT="Download DBS Indulge @Android Market for details.";
	// public static ArrayList<NewDealsData> couponsMainListData = null;
	//public static ArrayList<NewDealsData> merchantNamesListData = null;
	public static  ArrayList<CategoryData> data=null;
	public static  ArrayList<ResData> resdata=null;
	public static  ArrayList<RestaurantData> resdatadetails=null;
	
	public static String dateConversion(String promo_start_date) {
		try

		{
			if(promo_start_date.equalsIgnoreCase("0000-00-00 00:00:00")){
				promo_start_date ="";
			}else{
				SimpleDateFormat sdfSource = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
				Date date = sdfSource.parse(promo_start_date);
				SimpleDateFormat sdfDestination = new SimpleDateFormat(
						"dd-MMM-yyyy");
				promo_start_date = sdfDestination.format(date);
			}
			

		}

		catch (ParseException pe)

		{

			//System.out.println("Parse Exception : " + pe);

		}
		return promo_start_date;

	}

	public static String now() {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(
				" EEEE MMMM-dd-yyyy '@' hh:mm ");
		return sdf.format(cal.getTime());

	}

	public static int dateCompare(String promo_end_date) {
		Date date_old = new Date(); 
		Date date_present = new Date();
		SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String form = sdf.format(cal.getTime());
		try {
			date_old = sdfSource.parse(promo_end_date);
			date_present = sdf.parse(form);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int results = date_old.compareTo(date_present);
		 
	   // if(results > 0)
	    //  System.out.println("First Date is after second");
	   // else if (results < 0)
	   //   System.out.println("First Date is before second");
	   // else
	   //   System.out.println("Both dates are equal");
	    
	    return results;
	}

	public static String convertDistance(String dist) {
		if (dist != null && dist.length() != 0) {
			double distance = Double.parseDouble(dist);
			DecimalFormat twoDForm = new DecimalFormat("#.##");
			return twoDForm.format(distance) + "km";
		}
		return "";
	}
	
	public static void addRes()
	{
	
		resdata=new ArrayList<ResData>();
		resdata.add(new ResData(0, "Rain Tree", "3000"));
		resdata.add(new ResData(1, "GRT", "250"));
		resdata.add(new ResData(2, "SVS", "2000"));

	}
	
	public static void addResDetails()
	{
	
		resdatadetails=new ArrayList<RestaurantData>();
		resdatadetails.add(new RestaurantData(0, "20% off on amazing products. Only on Getit Bazaar. Buy now!", "3000","10"));
		resdatadetails.add(new RestaurantData(1, "Enjoy flat Rs.150 off on a purchase of Rs.200 & above! Limited time offer", "250","10"));
		resdatadetails.add(new RestaurantData(2, "Rs. 250 Cash Back on Credit Card", "2000","20"));

	}
	
	public static void addData()
	{
	
    data=new ArrayList<CategoryData>();
	   data.add(new CategoryData(0, "Chicken 65", "205.00"));
	   data.add(new CategoryData(1, "Chicken Fry", "125.00"));
	   data.add(new CategoryData(2, "Chicken Manchurian", "25.00"));
	   data.add(new CategoryData(3, "Chicken gongura", "250.00"));
	   data.add(new CategoryData(4, "Chicken gravy", "205.00"));
	   data.add(new CategoryData(5, "Chicken rice", "125.00"));
	}
	public static void toast(Context context, String message) {
		Toast.makeText(context, message, 3000).show();
	}

	public static void toast(Context context, int message) {
		Toast.makeText(context, message, 3000).show();
	}

}
