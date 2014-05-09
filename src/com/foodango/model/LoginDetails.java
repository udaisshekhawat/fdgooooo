package com.foodango.model;

import java.util.LinkedList;

import com.google.gson.annotations.SerializedName;

public class LoginDetails {

	@SerializedName("appstore_app_items")
	public LinkedList<LoginListDetails> appstore_app_items;
}
