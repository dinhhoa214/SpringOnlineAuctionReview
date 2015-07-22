package com.asiantech.auction.entity;   
import javax.persistence.Column;
import javax.persistence.Entity; 
import javax.persistence.EnumType;
import javax.persistence.Enumerated; 
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;  
import javax.validation.constraints.NotNull; 
 

@Entity 
public class Account {
	public enum Role{ROLE_ADMIN,ROLE_USER}
	
	@Id   
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	 
	@NotNull 
	private String userName;
	
	@NotNull 
	@Column(unique=true)
	private String email;
	
	@NotNull 
	private String password;
	
	@Enumerated(EnumType.STRING) 
	private Role Role;
	
	@NotNull 
	private String iformation;  
	private float totalRating; 
	private String image;
	private boolean block;

	public Account() { 
	}  
	
	public int getUserId() {
		return userId;
	} 
	 
	
	public Account(int userId, String userName, String email, String password,
			com.asiantech.auction.entity.Account.Role role, String iformation,
			  float totalRating, String image, boolean block) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.password = password;
		Role = role;
		this.iformation = iformation; 
		this.totalRating = totalRating;
		this.image = image;
		this.block = block;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return Role;
	}

	public void setRole(Role role) {
		Role = role;
	}

	public String getIformation() {
		return iformation;
	}

	public void setIformation(String iformation) {
		this.iformation = iformation;
	} 

	public float getTotalRating() {
		return totalRating;
	}

	public void setTotalRating(float totalRating) {
		this.totalRating = totalRating;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isBlock() {
		return block;
	}

	public void setBlock(boolean block) {
		this.block = block;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	 
}
