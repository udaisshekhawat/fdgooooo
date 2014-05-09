package com.foodango.data;

public class RestaurantData {

	public RestaurantData(int id, String desc, String points, String cash) {
		super();
		this.id = id;
		this.desc = desc;
		this.cash = cash;
		this.points = points;
	}
	public int id;
	
	public String desc;
	public String cash;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getCash() {
		return cash;
	}
	public void setCash(String cash) {
		this.cash = cash;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public String points;
	

}
