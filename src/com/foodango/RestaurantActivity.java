package com.foodango;

import com.foodango.R;
import com.foodango.adapter.RestaurantAdapter;
import com.foodango.constant.ApplicationConstants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class RestaurantActivity extends Activity {
	TextView text;
	Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_screen);
		text=(TextView)findViewById(R.id.restaurant_title);
		intent=getIntent();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		text.setText(intent.getStringExtra("resName"));
		ListView listView1 = (ListView) findViewById(R.id.ListViewId);

		ApplicationConstants.addRes();
		listView1.setCacheColorHint(0);
		listView1.setFastScrollEnabled(false);
		listView1.setAdapter(new RestaurantAdapter(RestaurantActivity.this,
				ApplicationConstants.resdatadetails));
		listView1.setOnItemClickListener(listener);
	}

	OnItemClickListener listener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			System.out.println("**"+arg1);
			//Intent intent = new Intent(HomeActivity.this, MenuActivity.class);
           // startActivity(intent);
		}
	};

}