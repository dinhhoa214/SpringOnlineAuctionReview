package com.asiantech.auction.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.asiantech.auction.entity.Account;
import com.asiantech.auction.entity.Bid;
import com.asiantech.auction.entity.Category;
import com.asiantech.auction.entity.Item;
import com.asiantech.auction.entity.Item.StatusBid;
import com.asiantech.auction.repository.ItemRepository;
 
@Service(ItemService.NAME)
@Transactional(propagation=Propagation.SUPPORTS)
public class ItemServiceImpl implements ItemService{
	@Resource
	ItemRepository itemRepository; 
	
	@Autowired 
	AccountService accountSv; 
	
	@Autowired
	BidService bidSv;
	
	@Autowired 
	CategoryService categorySv; 
	
	@Autowired
	MailService mailSv;

	@Override
	public Item insertItem(Item item) {
		item.setBids(1);
		item.setStatusBid(StatusBid.I);
		item.setCurrentBid(item.getMiniumBid());
		if(item.getItemImage()==null)
			item.setItemImage("image.item/default.jpg");
		itemRepository.save(item);
		// Gui email yeu cau active cho Admin
		mailSv.newEmailForAdminActiveItem(item.getItemId(), item.getCreaterId().getEmail() , item.getCreaterId().getTotalRating());
		Thread t = new Thread(mailSv);
		t.start();
		return item;
	}

	@Override
	public Item updateItem(Item item) {
		return itemRepository.save(item);
	}
	@Override
	public void sentEmailForActive(int id) {
		Item item = getById(id);
		item.setCodeActive(RandomStringUtils.randomAlphanumeric(12));
		itemRepository.save(item);
		mailSv.newEmailForUserActiveItem(item.getCreaterId().getEmail(),
				item.getItemTitle(), item.getMiniumBid(), id, item.getCodeActive());
		Thread t = new Thread(mailSv);
		t.start();
	}

	@Override
	public boolean activeItem(int id, String code) {
		Item item = getById(id);
		if(!item.getCodeActive().equalsIgnoreCase(code))
			return false;
		item.setStatusBid(StatusBid.A);
		if(item.getBids()==1){
			Bid bidfirst = new Bid(item.getMiniumBid(), item, item.getCreaterId());
			bidSv.updateBid(bidfirst);
		}
		itemRepository.save(item);
		return true;
	}

	@Override
	public void inActiveItem(int id) {
		Item item = getById(id);
		item.setStatusBid(StatusBid.I);
		itemRepository.save(item);
	}

	@Override
	public void setEndedItem(int id) {
		Item item = getById(id);
		item.setEnded(true);
		// Lay User dau gia cao nhat
		Bid top1 = bidSv.getByItemOrderAmountTop(item);
		item.setWinnerId(top1.getBidderId());
		itemRepository.save(item);
	}

	@Override
	public List<Item> getAllByCategory(Category cate) {
		return itemRepository.findByCategory(cate); 
	}

	@Override
	public List<Item> getAllByCategoryAndStatusActive(Category cate) {
		return itemRepository.findByCategoryAndStatusBid(cate, StatusBid.A);
	}

	@Override
	public Item getById(int id) {
		return itemRepository.findOne(id);
	}

	@Override
	public void deleteById(int id) {
		Item item = itemRepository.findOne(id);
		bidSv.deleteBidsByItem(item);
		itemRepository.delete(item);
	}

	@Override
	public void sentEmailDeleteById(int id) {
		mailSv.newEmailForAdminDeleteItem(id);
		Thread t = new Thread(mailSv);
		t.start();
	}

	@Override
	public List<Item> getAll() {
		return itemRepository.findAll();
	}
	
	@Override
	public List<Item> getAllByStatusActive() { 
		return itemRepository.findByStatusBidOrderByItemIdDesc(StatusBid.A);
	}

	@Override
	public List<Item> getAllByUserId(Account account) {
		
		return itemRepository.findByCreaterIdOrderByItemIdDesc(account);
	}

	@Override
	public List<Item> getAllByUserIdWin(Account account) {
		return itemRepository.findByWinnerIdOrderByItemIdDesc(account);
	}

	@Override
	public Page<Item> getAllItemAndPagination(Pageable pageable) {
		return itemRepository.findAll(pageable);
	}
	
	

	@Override
	public List<Item> getNewTop6Item() {
		return itemRepository.findTop6ByStatusBidAndEndedOrderByItemIdDesc(StatusBid.A, false);
	}

	@Override
	public List<Item> getHotTop6Item() {
		return itemRepository.findTop6ByStatusBidAndEndedOrderByBidsDesc(StatusBid.A, false);
	}

	@Override
	public List<Item> getEndTop6Item() {
		return itemRepository.findTop6ByEndedOrderByItemIdDesc(true);
	}

	@Override
	public List<Item> getAllItemEndForMail() {
		return itemRepository.findByEndedAndEmailWin(true, false);
	}

	@Override
	public List<Item> searchItemByTitleAndCategories(String title, List<String> categories) {
		List<Item> items = new ArrayList<Item>();
		if(categories.get(0).equals("All"))
			return itemRepository.findByItemTitleContaining(title);
		for(String cate:categories)
			items.addAll(itemRepository.findByCategoryAndItemTitleContaining(categorySv.getByName(cate), title));
		return items;
	}

	@Scheduled(fixedRate=5000)
	private void sendMailWhenAuctionEnd(){
		System.out.println(getAllItemEndForMail().size() + " email was sent.");
		for(Item i:getAllItemEndForMail()){
			mailSv.newEmail("dinhhoa.vta@gmail.com", "Congratulation!!! You is winner", 
					"Item: " + i.getItemTitle() + " - with Price: " + i.getCurrentBid() +
					"\n ---- Thanks! ");
			i.setEmailWin(true);
			itemRepository.save(i);
			Thread t = new Thread(mailSv);
			t.start();
		}
		System.out.println("-------");
	}
	
	
	
}
