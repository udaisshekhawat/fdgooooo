package com.foodango.data;



import java.util.LinkedList;

import com.google.gson.annotations.SerializedName;

public class GetDishes {

	@SerializedName("status")
	public String status;
	@SerializedName("record_count")
	public int record_count;
	@SerializedName("records")
	public LinkedList<DishesDetails> dishdetails;
	
	
	
}
