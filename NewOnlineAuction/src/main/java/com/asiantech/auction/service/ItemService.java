package com.asiantech.auction.service;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asiantech.auction.entity.Account;
import com.asiantech.auction.entity.Category;
import com.asiantech.auction.entity.Item;
public interface ItemService {
	public static String NAME = "itemService";
	// CRUD operations
	// Save or Update
	Item insertItem(Item item);
	Item updateItem(Item item);
	void sentEmailForActive(int id);
	boolean activeItem(int id, String code);
	void inActiveItem(int id);
	void setEndedItem(int id);

	// Read
	Item getById(int id);

	// Delete
	void deleteById(int id);
	
	// Sent email delete
	
	void sentEmailDeleteById(int id);

	// Get All
	List<Item> getAll();
	List<Item> getAllByStatusActive();
	List<Item> getAllByUserId(Account account);
	List<Item> getAllByUserIdWin(Account account);
	List<Item> getAllByCategory(Category cate);
	List<Item> getAllByCategoryAndStatusActive(Category cate);
	List<Item> getNewTop6Item();
	List<Item> getHotTop6Item();
	List<Item> getEndTop6Item();
	List<Item> getAllItemEndForMail();

	// Get All and Paging
	Page<Item> getAllItemAndPagination(Pageable pageable); 
	
	// Search
	List<Item> searchItemByTitleAndCategories(String title, List<String> categories);
}
