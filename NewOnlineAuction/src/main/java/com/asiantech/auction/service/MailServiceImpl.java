package com.asiantech.auction.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service(MailService.NAME)
public class MailServiceImpl implements MailService {

	@Autowired
	JavaMailSender mailSender; 

	private String to;
	private String subject;
	private String msg;
	
	

	public void sendMail() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);
	}

	@Override
	public void run() {
		this.sendMail();

	}

	@Override
	public void newEmail(String to, String subject, String msg) {

		this.to = to;
		this.subject = subject;
		this.msg = msg;
	}

	@Override
	public void newEmailForRegister(String email, String pass) {
		this.to = "dinhhoa.vta@gmail.com";
		this.subject = "Xac nhan dang ky thanh cong";
		this.msg = "Hello to Online Auction. \nYou can login with Email : "
					   + email + " - Password: " + pass + 
					   "\n ---- Thanks!";
	}

	@Override
	public void newEmailForAdminActiveItem(int itemid, String email, float totalRating) {
		this.to = "dinhhoa.vta@gmail.com";
		this.subject = "Yeu cau Active Item dau gia";
		this.msg = "Hello to Admin. \nI want you to Active Item with ID : "
	    		   	+ itemid + " \n ---- Thanks! \nSeller: " + email + 
	    		   	" - Rating: " + totalRating;
	}

	@Override
	public void newEmailForAdminDeleteItem(int itemid) {
		this.to = "dinhhoa.vta@gmail.com";
		this.subject = "Yeu cau xoa item dau gia";
		this.msg = "Hello to Admin. \nI want to delete Item with ID : "
	    		   + itemid + " \n ---- Thanks! ";
	}

	@Override
	public void newEmailForUserActiveItem(String email, String title, long price, int itemid, String codeActive) {
		this.to = "dinhhoa.vta@gmail.com";
		this.subject = "Xac nhan yeu cau dang ky item dau gia";
		this.msg = "Hello "+ email +". \nIf you want to active Item: "
				   + title + " with price: " + price + 
				   "\nPlease click link: http://localhost:8080/NewOnlineAuction/user/active?itemId=" + itemid +
				   "&codeActive="+ codeActive +" \n ---- Thanks! ";
	}

}
