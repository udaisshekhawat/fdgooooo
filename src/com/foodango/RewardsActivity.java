package com.foodango;

import com.foodango.R;
import com.foodango.adapter.DishDisplayAdapter;
import com.foodango.adapter.ResAdapter;
import com.foodango.constant.ApplicationConstants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class RewardsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rewards_screen);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ListView listView1 = (ListView) findViewById(R.id.ListViewId);

		ApplicationConstants.addRes();
		listView1.setCacheColorHint(0);
		listView1.setFastScrollEnabled(false);
		listView1.setAdapter(new ResAdapter(RewardsActivity.this,
				ApplicationConstants.resdata));
		listView1.setOnItemClickListener(listener);
	}

	OnItemClickListener listener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			TextView title=(TextView)arg1.findViewById(R.id.rewardstitle);
			//System.out.println("**"+arg1.getT);
			Intent intent = new Intent(RewardsActivity.this, RestaurantActivity.class);
			intent.putExtra("resName", title.toString());
            startActivity(intent);
		}
	};

}