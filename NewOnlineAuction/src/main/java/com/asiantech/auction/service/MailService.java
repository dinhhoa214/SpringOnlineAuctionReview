package com.asiantech.auction.service;

public interface MailService extends Runnable{
	public static String NAME = "mailService";
	
	void newEmail(String to, String subject, String msg);
	
	void newEmailForRegister(String email, String pass);
	
	void newEmailForAdminActiveItem(int itemid, String email, float totalRating);
	
	void newEmailForAdminDeleteItem(int itemid);
	
	void newEmailForUserActiveItem(String email, String title, long price, int itemid, String codeActive);
}
