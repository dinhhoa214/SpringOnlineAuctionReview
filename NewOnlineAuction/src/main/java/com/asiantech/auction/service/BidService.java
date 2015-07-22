package com.asiantech.auction.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asiantech.auction.entity.Bid;
import com.asiantech.auction.entity.Item;

public interface BidService {
	public static String NAME = "bidService";
	// CRUD operations
	// Save or Update
	boolean insertBid(Bid bid);
	Bid updateBid(Bid bid);

	// Read
	Bid getById(int id);
	Bid getByItemOrderAmountTop(Item item);

	// Delete
	void deleteById(int id);
	void deleteBidsByItem(Item item);

	// Get All
	List<Bid> getAll();
	List<Bid> getTop10ByItemId(Item item);

	// Get All and Paging
	Page<Bid> getAllBidAndPagination(Pageable pageable); 
}
