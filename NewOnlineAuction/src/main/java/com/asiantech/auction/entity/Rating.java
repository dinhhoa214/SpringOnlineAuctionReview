package com.asiantech.auction.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity 
public class Rating {

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private int id;
	
	@ManyToOne
	private Account userRater;
	
	@ManyToOne
	private Account userRated;
	
	private float Rate;
	
	private String date;

	public Rating() { 
	}

	public Rating(int id, Account userRater, Account userRated, float rate, String date) {
		super();
		this.id = id;
		this.userRater = userRater;
		this.userRated = userRated;
		Rate = rate;
		this.date = date;
	}

	
	public Rating(Account userRater, Account userRated, float rate) {
		super();
		this.userRater = userRater;
		this.userRated = userRated;
		Rate = rate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Account getUserRater() {
		return userRater;
	}

	public void setUserRater(Account userRater) {
		this.userRater = userRater;
	}

	public Account getUserRated() {
		return userRated;
	}

	public void setUserRated(Account userRated) {
		this.userRated = userRated;
	}

	public float getRate() {
		return Rate;
	}

	public void setRate(float rate) {
		Rate = rate;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
}
