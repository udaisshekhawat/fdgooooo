package com.foodango.model;

import com.google.gson.annotations.SerializedName;

public class LoginListDetails {

	@SerializedName("UserID")
	public String UserID;
	@SerializedName("EmailID")
	public String EmailID;
	@SerializedName("PhoneNumber")
	public String PhoneNumber;
	@SerializedName("Success")
	public int Success;
}
