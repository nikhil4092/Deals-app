package com.project.waverr;

import java.sql.Time;
import java.util.Date;

public class Deal {
	
	int ID;
	String restaurantID;
	String restaurantName;
	String details;
	int percentageDiscount;
	int amountDiscount;
	String freebie;
	String canvasText;
	int minimumAmount;
	Date startDate;
	Date endDate;
	Time startTime;
	Time endTime;
	String cuisine;
	String dealID;
	double distanceFromUser;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getRestaurantID() {
		return restaurantID;
	}
	public void setRestaurantID(String restaurantID) {
		this.restaurantID = restaurantID;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public int getPercentageDiscount() {
		return percentageDiscount;
	}
	public void setPercentageDiscount(int percentageDiscount) {
		this.percentageDiscount = percentageDiscount;
	}
	public int getAmountDiscount() {
		return amountDiscount;
	}
	public void setAmountDiscount(int amountDiscount) {
		this.amountDiscount = amountDiscount;
	}
	public String getFreebie() {
		return freebie;
	}
	public void setFreebie(String freebie) {
		this.freebie = freebie;
	}
	public String getCanvasText() {
		return canvasText;
	}
	public void setCanvasText(String canvasText) {
		this.canvasText = canvasText;
	}
	public int getMinimumAmount() {
		return minimumAmount;
	}
	public void setMinimumAmount(int minimumAmount) {
		this.minimumAmount = minimumAmount;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Time getStartTime() {
		return startTime;
	}
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	public Time getEndTime() {
		return endTime;
	}
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
	public String getCuisine() {
		return cuisine;
	}
	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}
	public String getDealID() {
		return dealID;
	}
	public void setDealID(String dealID) {
		this.dealID = dealID;
	}
	
	public void setDistanceFromUser(double distance) {
		this.distanceFromUser = distance;
	}
	
	public double getDistanceFromUser() {
		return distanceFromUser;
	}
}
