package com.project.waverr;

public class Restaurant {
	private String name;
	private String id;
	private String url;
	
	public Restaurant(String name, String id, String url) {
		this.name = name;
		this.id = id;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}
	
	
}
