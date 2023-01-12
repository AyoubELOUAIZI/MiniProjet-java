package application;

import javafx.scene.image.Image;

public class Customer {
	int idCustomer;
	String firstName;
	String lastName;
	String phone;
	String email;
	String city;
	Image imgCustomer;
	public Customer(int idCustomer, String firstName, String lastName, String phone, String email, String city,
			Image imgCustomer) {
		super();
		this.idCustomer = idCustomer;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.city = city;
		this.imgCustomer = imgCustomer;
	}
	public int getIdCustomer() {
		return idCustomer;
	}
	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Image getImgCustomer() {
		return imgCustomer;
	}
	public void setImgCustomer(Image imgCustomer) {
		this.imgCustomer = imgCustomer;
	}
	

}
