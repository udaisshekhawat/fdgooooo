package com.foodango;

import com.foodango.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class HomeActivity  extends Activity{
	RelativeLayout menu;
	RelativeLayout rewards;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.home_screen);
		 menu=(RelativeLayout)findViewById(R.id.menu_button);
		 rewards=(RelativeLayout)findViewById(R.id.rewards);
		 
		super.onCreate(savedInstanceState);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent(HomeActivity.this, MenuActivity.class);
				 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		            startActivity(intent);
			}
		});
		rewards.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent(HomeActivity.this, RewardsActivity.class);
		        //    startActivity(intent);
			}
		});
	}
}
