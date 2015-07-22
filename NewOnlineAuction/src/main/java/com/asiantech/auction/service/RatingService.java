package com.asiantech.auction.service;

import java.util.List;

import com.asiantech.auction.entity.Account;
import com.asiantech.auction.entity.Rating;

public interface RatingService {
	public static String NAME = "ratingService";
	// CRUD operations
	// Save or Update
	boolean insertRating(Rating rating);
	Rating updateRating(Rating rating); 

	// Read
	Rating getById(int id);
	

	// Delete
	void deleteById(int id);

	// Get All
	List<Rating> getAll();
	List<Rating> getAllByUser(Account account);
}
