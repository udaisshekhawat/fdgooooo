package com.foodango.adapter;

import java.util.ArrayList;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.foodango.R;
import com.foodango.data.DishesDetails;
import com.foodango.data.GetDishes;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DishDisplayAdapter extends BaseAdapter {
	

	private static LayoutInflater inflater = null;
    private LinkedList<DishesDetails> dishesDetails;

	public DishDisplayAdapter(Activity activity,
			LinkedList<DishesDetails> dishesDetails) {
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
		this.dishesDetails = dishesDetails;
	}

	public int getCount() {
		return dishesDetails.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public class ViewHolder {
		TextView text;
		TextView rate;
		int id;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// View vi=convertView;
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.menu_item, null);
			holder = new ViewHolder();
			holder.text = (TextView) convertView.findViewById(R.id.menutitle);
			holder.rate = (TextView) convertView.findViewById(R.id.itemrate);
			holder.id = position;
			convertView.setTag(holder);

		} else
		holder = (ViewHolder) convertView.getTag();
		
		holder.text.setText(dishesDetails.get(position).dish_name);
		holder.rate.setText(dishesDetails.get(position).dish_cost);
		holder.id =  Integer.parseInt(dishesDetails.get(position).dish_id);
		
	
		return convertView;
	}

	
}
