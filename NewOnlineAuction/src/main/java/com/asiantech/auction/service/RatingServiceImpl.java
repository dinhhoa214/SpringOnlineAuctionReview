package com.asiantech.auction.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.asiantech.auction.entity.Account;
import com.asiantech.auction.entity.Rating; 
import com.asiantech.auction.repository.RatingRepository;
@Service(RatingService.NAME)
@Transactional(propagation=Propagation.SUPPORTS)
public class RatingServiceImpl implements RatingService	{

	@Resource
	RatingRepository ratingRepository; 
	
	@Autowired
	AccountService accountSv;
	
	@Override
	public boolean insertRating(Rating rating) {
		if(ratingRepository.findByUserRaterAndUserRated(rating.getUserRater(),rating.getUserRated())!=null)
			return false;
		String date = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date());
		rating.setDate(date);
		ratingRepository.save(rating);
		accountSv.updateRating(rating.getUserRated());
		return true;
	}

	@Override
	public Rating updateRating(Rating rating) {
		return ratingRepository.save(rating);
	}

	@Override
	public Rating getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Rating> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rating> getAllByUser(Account account) {
		// TODO Auto-generated method stub
		return ratingRepository.findByUserRated(account);
		
	}

	
}
