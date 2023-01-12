package application;

import javafx.scene.image.Image;

public class Product {
	//SELECT `idProduct`, `NameProduct`, `discription`, `QteOnStock`, `Price`, `img1`, `img2`, `img3`, `idCat` FROM `products` WHERE 1
	int idProduct;
	String NameProduct;
	String discription;
	int QteOnStock;
	float Price;
	Image img1;
	Image img2;
	Image img3;
	int idCat;
	String Categorie;
	
	public Product(int idProduct, String nameProduct, String discription, int qteOnStock, float price, Image img1,
			Image img2, Image img3, int idCat, String categorie) {
		super();
		this.idProduct = idProduct;
		NameProduct = nameProduct;
		this.discription = discription;
		QteOnStock = qteOnStock;
		Price = price;
		this.img1 = img1;
		this.img2 = img2;
		this.img3 = img3;
		this.idCat = idCat;
		Categorie = categorie;
	}
	
	
	public String getCategorie() {
		return Categorie;
	}


	public void setCategorie(String categorie) {
		Categorie = categorie;
	}


	public int getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}
	public String getNameProduct() {
		return NameProduct;
	}
	public void setNameProduct(String nameProduct) {
		NameProduct = nameProduct;
	}
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public int getQteOnStock() {
		return QteOnStock;
	}
	public void setQteOnStock(int qteOnStock) {
		QteOnStock = qteOnStock;
	}
	public float getPrice() {
		return Price;
	}
	public void setPrice(float price) {
		Price = price;
	}
	public Image getImg1() {
		return img1;
	}
	public void setImg1(Image img1) {
		this.img1 = img1;
	}
	public Image getImg2() {
		return img2;
	}
	public void setImg2(Image img2) {
		this.img2 = img2;
	}
	public Image getImg3() {
		return img3;
	}
	public void setImg3(Image img3) {
		this.img3 = img3;
	}
	public int getIdCat() {
		return idCat;
	}
	public void setIdCat(int idCat) {
		this.idCat = idCat;
	}
	
	
}
