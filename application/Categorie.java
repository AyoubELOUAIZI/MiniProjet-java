package application;

import javafx.scene.image.Image;

public class Categorie {
	int id;
	String name;
	String description;
	Image img;
	public Categorie(int id, String name, String description, Image img) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.img = img;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Image getImg() {
		return img;
	}
	public void setImg(Image img) {
		this.img = img;
	}
	
	

}
