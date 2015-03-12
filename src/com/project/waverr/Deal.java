package com.project.waverr;


public class Deal {
	
	int ID;
	String details;
	int percentageDiscount;
	int amountDiscount;
	String freebie;
	String canvasText;
	int minimumAmount;
	DateTime start;
	DateTime end;
	String cuisine;
	String dealID;
	double distanceFromUser;
	
	String restaurantID;
	String restaurantName;
	String restaurantNumber;
	String restaurantAddress;
	String restaurantCoordinates;
	String restaurantFinePrint;
	
	public String getRestaurantCoordinates() {
		return restaurantCoordinates;
	}
	public void setRestaurantCoordinates(String restaurantCoordinates) {
		this.restaurantCoordinates = restaurantCoordinates;
	}
	public String getRestaurantFinePrint() {
		return restaurantFinePrint;
	}
	public void setRestaurantFinePrint(String restaurantFinePrint) {
		this.restaurantFinePrint = restaurantFinePrint;
	}
	public String getRestaurantNumber() {
		return restaurantNumber;
	}
	public void setRestaurantNumber(String restaurantNumber) {
		this.restaurantNumber = restaurantNumber;
	}
	public String getRestaurantAddress() {
		return restaurantAddress;
	}
	public void setRestaurantAddress(String restaurantAddress) {
		this.restaurantAddress = restaurantAddress;
	}

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
	public void setStartDateTime(DateTime dateTime) {
		start = dateTime;
	}
	public DateTime getStartDateTime() {
		return start;
	}
	public void setEndDateTime(DateTime dateTime) {
		end = dateTime;
	}
	public DateTime getEndDateTime() {
		return end;
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
