//Hakan Turgut
//CS 370 Project
//UAItem.java 

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

//UAItem Class - Stores contents for each Under Armour item. 
public class UAItem {
	
	public String Number = ""; //Stores item number
	public String Name = ""; //Stores Name of item
	public String Wear = ""; //Stores type of wear for item
	public String Price = ""; //Stores price of item
	public String numberColors =""; //Stores number of colors of item
	public static BufferedImage image = null; //Stores the image of the item
	public String imageURL = " "; //Stores the image URL
	public String URL = ""; //Stores the URL of the item
	
	/**
	 * Stores values of particular Under Armour item
	 * 
	 * @param Number - Item number to be stored
	 * @param Name - Item name to be stored
	 * @param Wear - Type of wear for item to be stored
	 * @param Price - Price of item to be stored
	 * @param Colors - Number of colors to be stored
	 * @param Image - Image of item to be stored
	 */
	public UAItem(String Number, String Name, String Wear, String Price, String Colors) {
		this.Number = Number; //Stores number
		this.Name = Name; //Stores name
		this.Wear = Wear; //Stores wear
		this.Price = Price; //Stores price
		this.numberColors = Colors; //Stores number of colors
	} // End of UAItem
	
	
	/**
	 * gentNumber() - Gets item number
	 * @return returns item number
	 */
	public String getNumber() {
		return this.Number;	
	}

	/**
	 * getName() - Gets item name
	 * @return returns item name
	 */
	public String getName() {
		return this.Name;
	}
	
	/**
	 * getWear() - Gets item wear
	 * @return returns item wear
	 */
	public String getWear() {
		return this.Wear;
	}
	
	/**
	 * getPrice() - Gets item price
	 * @return returns item price
	 */
	public String getPrice() {
		return this.Price;
	}
	
	/**
	 * getnumberColors() - Gets number of colors that item has
	 * @return returns number of colors item has
	 */
	public String getnumberColors() {
		return this.numberColors;
	}
	
	/**
	 * setNumber Method - Sets item number
	 * @param Number - Item number to be stored
	 */
	public void setNumber(String Number) {
		this.Number = Number;	
	}

	/**
	 * setName Method - Sets item name
	 * @param Name - Item name to be stored
	 */
	public void setName(String Name) {
		this.Name = Name;
	}
	
	/**
	 * setWear Method - Sets item wear
     * @param Wear - Item wear to be stored
	 */
	public void setWear(String Wear) {
		this.Wear = Wear;
	}
	
	/**
	 * setPrice Method - Sets item price
	 * @param Price - Item price to be stored
	 */
	public void setPrice(String Price) {
		this.Price = Price;
	}
	
	/**
	 * setNumberColors Method - Sets number of colors for item
	 * @param numberColors - Number of colors of item to be stored
	 */
	public void setNumberColors(String numberColors) {
		this.numberColors = numberColors;
	}
	
	/**
	 * getImageURL() - Gets Image URL
	 * @return returns image URL
	 */
	public String getImageURL() {
		return this.imageURL;
	}
	
	/**
	 * setImageURL Method - Sets image URL of item
	 * @param imageURL - Image URL of item
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	
	/**
	 * getImageURL() - Gets URL
	 * @return returns URL
	 */
	public String getURL() {
		return this.URL;
	}
	
	/**
	 * setURL Method - Sets URL of item
	 * @param URL - URL of item
	 */
	public void setURL(String URL) {
		this.URL = URL;
	}
	
	/**
	 * toString() - Returns all data of item
	 */
	public String toString() {
		return ("Item Number: " + Number + "\n" + "Name: " + Name + "\n" + "Type of Wear: " + Wear + "\n" + "Price: " + Price + "\n" + "Number of Colors: " + numberColors + "\n");		
	}
	
	/**
	 * toString() - Returns all raw data of item
	 */
	public String toStringRaw() {
		return ( Number  + "," + Name + "," + Wear + "," + Price + "," + numberColors + "," + imageURL);		
	}
	
	/**
	 * toString() - Returns all data of item in a transaction log format
	 */
	public String toStringTransactionLog() {
		return ("Item #: " + Number + " | " + "Name: " + Name + " | " + "Wear: " + Wear + " | " + "Price: " + Price + " | " + "Colors: " + numberColors);		
	}
	
} //End of UAItem class
