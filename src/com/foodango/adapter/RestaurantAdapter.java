package com.foodango.adapter;

import java.util.ArrayList;

import com.foodango.R;
import com.foodango.data.CategoryData;
import com.foodango.data.ResData;
import com.foodango.data.RestaurantData;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RestaurantAdapter extends BaseAdapter {
	

	private static LayoutInflater inflater = null;
    private ArrayList<RestaurantData> categoryList;


	public RestaurantAdapter(Activity activity,
			ArrayList<RestaurantData> categoryList) {
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
		this.categoryList = categoryList;
	}

	public int getCount() {
		return categoryList.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public class ViewHolder {
		TextView desc;
		TextView points;
		TextView cash;
		int id;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// View vi=convertView;
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.restaurant_item, null);
			holder = new ViewHolder();
			holder.desc = (TextView) convertView.findViewById(R.id.resdesc);
			holder.points = (TextView) convertView.findViewById(R.id.restpoints);
			holder.cash=(TextView) convertView.findViewById(R.id.restcash);
			holder.id = position;
			convertView.setTag(holder);

		} else
		holder = (ViewHolder) convertView.getTag();
		holder.desc.setText(categoryList.get(position).getDesc().toString());
		holder.points.setText(categoryList.get(position).getPoints().toString()+" points");
		holder.cash.setText("$ "+categoryList.get(position).getCash().toString());
		holder.id = categoryList.get(position).getId();
		
	
		return convertView;
	}
}
