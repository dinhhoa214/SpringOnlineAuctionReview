package com.asiantech.auction.service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;  
import org.springframework.stereotype.Service;

import com.asiantech.auction.entity.Bid;
import com.asiantech.auction.entity.Item; 
import com.asiantech.auction.repository.BidRepository; 
 
@Service(BidService.NAME)
public class BidServiceImpl implements BidService{
	@Resource
	BidRepository bidRepository; 
	
	@Autowired 
	ItemService itemSv; 
	
	@Autowired 
	AccountService accountSv;
	
	@Override
	public boolean insertBid(Bid bid){
		try{
			Item item = itemSv.getById(bid.getItemId().getItemId());
			bid.setItemId(item);
			String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			bid.setDate(date);
			long amountDesc = bidRepository.findFirstByItemIdOrderByAmountDesc(item).getAmount();
			item.setCurrentBid(amountDesc);
			if(bid.getAmount()==0){
				bid.setAmount(amountDesc + item.getBidIncremenet());
				item.setCurrentBid(amountDesc + item.getBidIncremenet());
			}
			if(bid.getAmount() > amountDesc){ 
				item.setCurrentBid(bid.getAmount());
			}
			bidRepository.save(bid);
			item.setBids(item.getBids()+1);
			itemSv.updateItem(item);
			return true;
		}
		catch(Exception ex){
			return false;
		}
	
		
	}


	@Override
	public Bid updateBid(Bid bid) {
		return bidRepository.save(bid);
	}


	@Override
	public List<Bid> getTop10ByItemId(Item it) {
		return bidRepository.findTop10ByItemIdOrderByAmountDesc(it);
	}


	@Override
	public Bid getById(int id) { 
		return bidRepository.findOne(id);
	}


	@Override
	public Bid getByItemOrderAmountTop(Item item) {
		return bidRepository.findFirstByItemIdOrderByAmountDesc(item);
	}


	@Override
	public void deleteById(int id) {
		bidRepository.delete(id); 
	}

	@Override
	public void deleteBidsByItem(Item item) {
		List<Bid> bidsItem = bidRepository.findByItemId(item);
		for(Bid i:bidsItem)
			bidRepository.delete(i);
	}


	@Override
	public List<Bid> getAll() {
		return bidRepository.findAll();
	}

	@Override
	public Page<Bid> getAllBidAndPagination(Pageable pageable) {
		return bidRepository.findAll(pageable);
	}
	
}
