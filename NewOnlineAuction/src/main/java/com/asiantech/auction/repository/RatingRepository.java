package com.asiantech.auction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asiantech.auction.entity.Account;
import com.asiantech.auction.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Integer>{

	Rating findByUserRaterAndUserRated(Account accountRater,Account accountRated);
	
	List<Rating> findByUserRated(Account accountRated);
}
