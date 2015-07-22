package com.asiantech.auction.entity; 
import javax.persistence.Entity;  
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;  
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull; 
 

@Entity 
public class Bid {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private int numberIndex; 
	
	@NotNull 
	private long amount; 
	
	@ManyToOne
	private Item itemId;
	
	@ManyToOne 
	private Account bidderId;
	
	private String Date;

	public Bid() { 
	} 
	
	 

	public Bid(long amount, Item itemId, Account bidderId) {
		super();
		this.amount = amount;
		this.itemId = itemId;
		this.bidderId = bidderId;
	}



	public Bid(long amount, Item itemId, Account bidderId, String date) {
		super(); 
		this.amount = amount;
		this.itemId = itemId;
		this.bidderId = bidderId;
		Date = date;
	}



	public int getNumberIndex() {
		return numberIndex;
	}

	public void setNumberIndex(int numberIndex) {
		this.numberIndex = numberIndex;
	} 

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	} 
	public Item getItemId() {
		return itemId;
	} 
	public Account getBidderId() {
		return bidderId;
	}

	public void setBidderId(Account bidderId) {
		this.bidderId = bidderId;
	}

	public void setItemId(Item itemId) {
		this.itemId = itemId;
	} 
}
