package com.project.waverr;


public class Restaurant {
	private String name;
	private String id;
	private String url;
	private String announcements;
	
	public Restaurant() {
	}
	public String getAnnouncements() {
		return announcements;
	}

	public void setAnnouncements(String announcements) {
		this.announcements = announcements;
	}

	public Restaurant(String name, String id, String url) {
		this.name = name;
		this.id = id;
		this.url = url;
	}
	

	public void setName(String name) {
		this.name = name;
	}
	public void setId(String id) {
		this.id = id;
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
