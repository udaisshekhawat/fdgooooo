package com.foodango.data;

import com.google.gson.annotations.SerializedName;

public class DishesDetails {
	@SerializedName("dish_name")
	public String dish_name;
	@SerializedName("dish_id")
	public String dish_id;
	@SerializedName("dish_description")
	public String dish_description;
	@SerializedName("dish_cost")
	public String dish_cost;
	@SerializedName("is_chef_special")
	public String is_chef_special;
	@SerializedName("is_today_spl")
	public String is_today_spl;
	@SerializedName("dish_url")
	public String dish_url;
	@SerializedName("cat_id")
	public String cat_id;
	@SerializedName("res_id")
	public String res_id;
}
