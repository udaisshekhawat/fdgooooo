package com.foodango.net;



import com.foodango.constant.ApplicationConstants;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

public class NetworkMonitor {

	private static NetworkMonitor networkMonitor;
	boolean firstlaunch = true;
	Context context;
	/**
	 * Creates a network monitor instance
	 */
	private NetworkMonitor() {
		
		try {
			isAcivated(context);
		} catch (Exception e) {
			// Log.e(getClass().getName(), ApplicationConstants.EXCEPTION, e);
		}
	}

	/**
	 * Returns a networkMonitor instance
	 * 
	 * @return NetworkMonitor object
	 */
	public static NetworkMonitor getNetworkMonitor() {
		if (networkMonitor == null) {
			networkMonitor = new NetworkMonitor();
		}
		return networkMonitor;
	}

	public boolean isAcivated(Context context) {
		// Creating Connection manager.
	//	System.out.println("==========context---"+context);
		try {
	
			return(isWifiAvilable(context)||is3GAvilable(context));
		    }

		catch (Exception ex) {
			// Log.e(ApplicationConstants.EXCEPTION,ApplicationConstants.NETWORK_NOT_FOUND);
			//ex.printStackTrace();
			return false;
		}
		
	}
	@SuppressWarnings("unused")
	private boolean defaultCheck(){
		return true;
	}
	public static String getNetworkProvider(Context context) {
		String networkProvider = null;
		try {
			TelephonyManager telephonyManager;
			telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
			networkProvider = telephonyManager.getNetworkOperatorName();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return networkProvider;
	}
	
	/** Checks whether two providers are the same */
	@SuppressWarnings("unused")
	private static boolean isSameProvider(String provider1, String provider2) {
	    if (provider1 == null) {
	      return provider2 == null;
	    }
	    return provider1.equals(provider2);
	}	

	private  String getSimProvider(Context context) {
		String simProvider = null;
		try {
			TelephonyManager telephonyManager;
			telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
			simProvider = telephonyManager.getSimOperatorName();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return simProvider;
	}
	
	@SuppressWarnings("unused")
	private boolean checkNetwork(Activity activity){
		  if(!getNetworkProvider(activity).contains("Sing") && !getSimProvider(activity).contains("Sing")) 
		  {
			 // Util.showAlert(activity, Constants.APP_NAME, "This service is available only on a Singtel mobile network and at selected Wireless@SG hotspots.", Constants.MSG_OK, true);	        	
			  return false;			
          }
		  else
		  {
			return true;
           }	
	}
	
	
	private boolean isWifiAvilable(Context context) {
		final ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo wifi = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifi.isAvailable() && wifi.isConnected()) {
			return true;
		}
		return false;
	}
	

	private boolean is3GAvilable(Context context) {
		final ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobile.isAvailable() && mobile.isConnected()) {
			return true;
		}
		return false;
	}
	
}
