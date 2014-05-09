package com.foodango;

import java.util.HashMap;

import org.json.JSONObject;

import com.foodango.constant.APIConstants;
import com.foodango.constant.ApplicationConstants;
import com.foodango.net.JSONClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class DashboardActivity extends Activity{

	private final String TAG = "DashboardActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.dashboard_screen);
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	public void onClickDineinLayout(View view)
	{
		Log.d(TAG," ****** Dine in ******* ");
		
		Intent intent = new Intent(DashboardActivity.this, QRActivity.class);
		startActivity(intent);
		// Fetch the restaurant values and save it in database
		JSONObject holder = new JSONObject();
		HashMap map = new HashMap();
	//	map.put(ApplicationConstants.LOGIN_USER_VALUE, userName);
	//	JSONClient client = new JSONClient(this, this, holder,
	//			APIConstants.SERVER_URL_TEST, true, map,
	//			ApplicationConstants.LOGIN_CODE);
	//	client.execute();
	}
	
	public void onClickTakeawayLayout(View view)
	{
		Log.d(TAG," ****** Take away ******* ");
	}
	
	public void onClickRewardsLayout(View view)
	{
		Log.d(TAG," ****** Rewards ******* ");
	}
	
	public void onClickSearchLayout(View view)
	{
		Log.d(TAG," ****** Search ******* ");
	}

}
