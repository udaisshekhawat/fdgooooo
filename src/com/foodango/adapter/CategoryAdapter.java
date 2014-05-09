package com.foodango.adapter;

import java.util.ArrayList;

import com.foodango.R;
import com.foodango.data.CategoryData;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CategoryAdapter extends BaseAdapter {
	

	private static LayoutInflater inflater = null;
    private ArrayList<CategoryData> categoryList;


	public CategoryAdapter(Activity activity,
			ArrayList<CategoryData> categoryList) {
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
		holder.text.setText(categoryList.get(position).getName().toString());
		holder.rate.setText(categoryList.get(position).getRate().toString());
		holder.id = categoryList.get(position).getId();
		
	
		return convertView;
	}
}
