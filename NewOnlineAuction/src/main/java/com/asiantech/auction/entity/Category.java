package com.asiantech.auction.entity; 
import javax.persistence.Entity;  
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;  
import javax.validation.constraints.NotNull; 
import javax.validation.constraints.Size;
 

@Entity 
public class Category {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private int Id;
	
	@NotNull
	@Size(min = 3, max = 10, message = "Category name must be between 3 and 10 characters long.") 
	private String name;

	public Category() { 
	}

	public Category(int id, String name) { 
		Id = id;
		this.name = name;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	 
}
