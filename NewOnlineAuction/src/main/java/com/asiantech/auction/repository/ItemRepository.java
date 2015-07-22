package com.asiantech.auction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asiantech.auction.entity.Account;
import com.asiantech.auction.entity.Category;
import com.asiantech.auction.entity.Item;
import com.asiantech.auction.entity.Item.StatusBid;
 

public interface ItemRepository extends JpaRepository<Item, Integer>{
	
	List<Item> findByCategory(Category category);
	
	List<Item> findByStatusBidOrderByItemIdDesc(StatusBid stt);
	
	List<Item> findByCategoryAndStatusBid(Category category,StatusBid stt);
	
	List<Item> findByCreaterIdOrderByItemIdDesc(Account account);
	
	List<Item> findByWinnerIdOrderByItemIdDesc(Account account);
	
	List<Item> findByItemTitleContaining(String itemTitle);
	
	List<Item> findByCategoryAndItemTitleContaining(Category category, String itemTitle);
	
	List<Item> findTop6ByStatusBidAndEndedOrderByItemIdDesc(StatusBid stt, boolean endFalse);
	
	List<Item> findTop6ByStatusBidAndEndedOrderByBidsDesc(StatusBid stt, boolean endFalse);
	
	List<Item> findByEndedAndEmailWin(boolean endTrue, boolean emailFalse);
	
	List<Item> findTop6ByEndedOrderByItemIdDesc(boolean endFalse);

}
