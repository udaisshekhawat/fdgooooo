package com.foodango.data;

public class CategoryData {

	public int id;
	public CategoryData(int id, String name, String rate) {
		super();
		this.id = id;
		this.name = name;
		this.rate = rate;
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
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String rate;

}
