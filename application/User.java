package application;

import javafx.scene.image.Image;

public class User {
	int id;
	String userName;
	String passWord;
	String TypeUser;
	Image userImg;
	String SellerName;
	
	
	public Image getUserImg() {
		return userImg;
	}

	public User(int id, String userName, String passWord, String typeUser, Image userImg, String sellerName) {
		super();
		this.id = id;
		this.userName = userName;
		this.passWord = passWord;
		TypeUser = typeUser;
		this.userImg = userImg;
		SellerName = sellerName;
	}

	public void setUserImg(Image userImg) {
		this.userImg = userImg;
	}
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
	public User() {
		
	}
	
	

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getTypeUser() {
		return TypeUser;
	}
	public void setTypeUser(String typeUser) {
		TypeUser = typeUser;
	}
	public String getSellerName() {
		return SellerName;
	}
	public void setSellerName(String sellerName) {
		SellerName = sellerName;
	}
	
}
