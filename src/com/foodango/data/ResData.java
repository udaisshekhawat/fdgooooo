package com.foodango.data;

public class ResData {

	public int id;
	public ResData(int id, String name, String rate) {
		super();
		this.id = id;
		this.name = name;
		this.points = rate;
	}
	public String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String points;
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}

}
