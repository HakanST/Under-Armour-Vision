//Hakan Turgut
//CS 370
//UAVision.java 

//Program Description: Program allows users to search for Under Armour items listed http://underarmour.com/. These items consist of different types of clothing, shoes, and equipment. Additionally, users will be able to view information about items like the item number, name, type of wear(t-shirt, pants, socks), type of sport it's used in, and the price. Administrators will be able to search for these items as well as add, delete, or edit them.

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable; //library for Hash Table
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

//UAVision class - Main class for program which sets up global variables and initializes the hash table. This class allows for users to search for Under Armour items, and display all of the items in the hash table. Administrators can add and delete items in to the hash table, search for items, view the transaction log.
public class UAVision {
	
	//Global Variables for attributes of current item (items consisting of clothing, shoes, equipment, etc.)
	public String itemNumber=""; //Variable for item number
	public String name=""; //Variable for name of item
	public String typeOfWear=""; //Variable for type of wear (pants, shirt, socks, etc)
	public String price=""; //Variable for how much the item is priced at
	public String numberColors=""; //Variable for number of colors for item
	
	public String toString = ""; //Variable that holds information of every item in the hash table to display it.
	public String toStringRaw = ""; //Variable that holds raw data of every item without specifying its attribute. 
	public String transactionLogData = ""; //Variable that contains all data for all transactions including the date/time.
	public boolean privelage = false; //Variable used as a key to determine the privileges of user depending on whether they're an administrator or not.
	
	public ArrayList <String> hashKeys = new ArrayList<String>(); //Keeps track of which keys in the hash table are filled
	public UAItem currentItem = new UAItem (itemNumber, name, typeOfWear, price, numberColors); //Object that will be used to temporarily hold data for each item. This object then gets added to the hash table.
	public Hashtable <String, UAItem> itemList = new Hashtable <String, UAItem>(); //Hash table to store information about each item
	
	public boolean administratorPrivelage = false; //Key used to determine that the administrator is logged in so that they can use their additional privileges that involve modifying data. 
	public boolean userPrivelage = false; //Key used to determine that the user is logged in so that they can only perform a limited amount of activities and cannot modify data. 
	
	public String[][] rowString; //2-dimensional array to hold item data and display it in the table for the GUI.
	
	//readBackUp Method - Opens text file, reads, and stores data of a particular item in to the hash table. 
	void readBackUp() {

		//Try block - Opens, reads, and stores data of text file
		try {
			
			FileReader f = new FileReader("backUpData.txt"); //Text file is opened and stored in to f
			BufferedReader b = new BufferedReader(f); 
			String line = "";
			
			//while loop - Goes through each line of text file with the condition that there are more lines
			while ((line = b.readLine())!=null) { 
				
				StringTokenizer tokens = new StringTokenizer(line,","); //Each line is tokenized by a comma
				
				currentItem= new UAItem("","","","","");
				if (tokens.hasMoreTokens()) currentItem.setNumber(tokens.nextToken()); //Inserts item # in to currentItem object
				if (itemList.get(currentItem.getNumber())!=null) continue;
				
				if (tokens.hasMoreTokens()) currentItem.setName(tokens.nextToken()); //Sets name of item
				if (tokens.hasMoreTokens()) currentItem.setWear(tokens.nextToken()); //Sets type of wear of item
				if (tokens.hasMoreTokens()) currentItem.setPrice(tokens.nextToken()); //Sets price of item
				if (tokens.hasMoreTokens()) currentItem.setNumberColors(tokens.nextToken()); //Sets number of colors of item
				
				if (tokens.hasMoreTokens()) currentItem.setImageURL(tokens.nextToken());
				
				itemList.put(currentItem.getNumber(), currentItem); //Inserts current item and data for its attributes in to hash table
				hashKeys.add(currentItem.getNumber()); //Inserts hash key in to array to keep track. 
				
				timeStamp("Inserting Backup Data ", currentItem); //This transaction gets recorded with a time stamp.
//				backUpData();
				
			} //End of while loop
			b.close(); //Closes buffer
		} //End of try block
		
		//Catch block for FileNotFoundException
		catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //End of Catch block
		
	} //End of readFile Method
	
	//offlineQuery Method - Allows users/administrators to view data without being connected to the internet. This can be used to check which items exist in the hash table and the image associated with each item. The parameter itemNumberIn refers to the item number.
	String offlineQuery(String itemNumberIn) {
		
		//Prevents users from entering blank
		if (itemNumberIn.equals("")) {
				JOptionPane.showMessageDialog(null, "You must enter the item number in its corresponding field.");
				return null;
		}//End of if statement
		
		//If item number doesn't exist, user will be notified.
		else if (itemList.get(itemNumberIn)==null) {
			JOptionPane.showMessageDialog(null, "No search result for item you've entered.");
			return null;
		}//End of if statement
		
		return itemList.get(itemNumberIn).toString(); //Returns information about item if the number exists in the hashtable.
		
	}
	
	 //addItem Method - Allows administrators to add items in to hash table. Parameters consist of item number, name, wear, price, and color availability
	 void addItem(String itemNumberIn, String nameIn, String typeOfWearIn, String priceIn, String numberColorsIn) {
		
		//No field can be left blank in order to add an item
		if (itemNumberIn.equals("")||nameIn.equals("")||typeOfWearIn.equals("")||priceIn.equals("")||numberColorsIn.equals("")) {
			
			JOptionPane.showMessageDialog(null, "Error! Fill in all fields.");
			return;
			
		} //End of if statement
		
		//
//	    for (String i : itemList.keySet()) {
//			
//			if (itemNumberIn.equals(itemList.get(i).getNumber())) {
//				JOptionPane.showMessageDialog(null, "Error! Item with number " + itemNumberIn + " already exists.");
//				return;
//			}
//			
//		}
		
		//If the item number already exists, administrator will be notified.
		if (itemList.get(itemNumberIn)!=null) {
			JOptionPane.showMessageDialog(null, "Error! Item with number " + itemNumberIn + " already exists.");
			return;
		} //End of if statement
		
		currentItem = new UAItem (null, null, null, null, null); //Object to temporarily hold data of item to be added
		
		currentItem.setNumber(itemNumberIn); //Stores item number
		currentItem.setName(nameIn); //Stores item name
		currentItem.setWear(typeOfWearIn); //Stores item wear
		currentItem.setPrice("$" + priceIn); //Stores item price
		currentItem.setNumberColors(numberColorsIn); //Stores number of colors
		
		itemList.put(currentItem.getNumber(), currentItem); //Inserts data for item in to hash table
//		hashKeys.add(currentItem.getNumber()); //Inserts key of new item in to array to keep track
		
		timeStamp("Item Added By Admin", currentItem); //Creates time stamp of activity
		JOptionPane.showMessageDialog(null, "Item with number " + currentItem.getNumber() + " succesfully added.");
		backUpData(); //Backs up data for data persistence 
				
	} //End of addItem Method
	
	//editItem method - Allows administrators to edit items. Parameters consist of item number, name, wear, price, and number of colors
	public void editItem(String itemNumberIn, String nameIn, String typeOfWearIn, String priceIn, String numberColorsIn) {
		
		//Item number field cannot be left blank
		if (itemNumberIn.equals("")) {
			JOptionPane.showMessageDialog(null, "You must enter the item number for the item you wish to edit!");
			return;
		} //End of if statement
		
		//Item desired to be edited must already exist in the hash table
		if (itemList.get(itemNumberIn)==null) {
			JOptionPane.showMessageDialog(null, "Error! Item you wish to modify doesn't exist!");
			return;
		}// End of if statement 
		
		//At least one of these fields need to be filled in
		if (nameIn.equals("")&&typeOfWearIn.equals("")&&priceIn.equals("")&&numberColorsIn.equals("")) { 
			JOptionPane.showMessageDialog(null, "You must fill in at least one more of the above fields to edit an item!");
			return;
		} //End of if statement
		
		//If the name, wear, price, or color availability tabs are not empty, then the hash table will store these new values for the attributes of the item desired to be modified.
		if (!nameIn.equals(""))
			itemList.get(itemNumberIn).setName(nameIn);
		if (!typeOfWearIn.equals(""))
			itemList.get(itemNumberIn).setWear(typeOfWearIn);
		if (!priceIn.equals(""))
			itemList.get(itemNumberIn).setPrice("$" + priceIn);
		if (!numberColorsIn.equals(""))
			itemList.get(itemNumberIn).setNumberColors(numberColorsIn);
		
		timeStamp("Item Edited by Admin", itemList.get(itemNumberIn)); //Creates time stamp of Editing activity
		JOptionPane.showMessageDialog(null, "Item " + itemNumberIn + " has succesfully been edited");
		backUpData(); //Data backed up
	
	}

	//deleteItem Method - Allows administrators to delete items from hash table. Parameter consists of item number for item that is desired to be deleted.
	public void deleteItem(String delete) {
		
		boolean success = false; //Indicates if an item was successfully deleted
		
		if (itemList.get(delete)!=null) {
			
			timeStamp("Item Removed by Admin", itemList.get(delete)); //Time stamp of activity is recorded
			itemList.remove(delete); // Item is removed from hash table
			JOptionPane.showMessageDialog(null, "Item with number " + delete + " has succesfully been deleted.");
			success = true; //sets variable to true since it has been deleted
			backUpData();
		}
		
		if (success==false) 	JOptionPane.showMessageDialog(null, "Error! Item with number " + delete + " does not exist!");	//If item doesn't exist, user will be notified
		
					
//		boolean success = false; //Indicates if an item was successfully deleted
//
//		//for loop - goes through hash table to find specified item in order to remove it
//		for (int k=0;k<hashKeys.size();k++) {
//			
//			//if statement - if hash key for specified item is found, item will be deleted
//			if(hashKeys.get(k).equals(delete)) { 
//				timeStamp("Item Removed ", itemList.get(hashKeys.get(k))); //Time stamp of activity is recorded
//				itemList.remove(hashKeys.get(k)); // Item is removed from hash table
//				hashKeys.remove(k); //Key is removed from array
//				JOptionPane.showMessageDialog(null, "Item with number " + delete + " has succesfully been deleted");
//				success = true; //sets variable to true since it has been deleted
//				break;				
//
//			} //End of if statement
//		} //End of for loop
//		
//		if (success==false) 	JOptionPane.showMessageDialog(null, "Error! Item with number " + delete + " does not exist!");	//If item doesn't exist, user will be notified
		
	} //End of deleteItem Method

	//toStringM Method - Stores data for every item in to a variable that will be used to display it. 
	public void toStringM() {
		toString = "";
		//Prints each item and its information
	    for (String i : itemList.keySet()) 
			toString += itemList.get(i).toString() + "\n";
		
//		JOptionPane.showMessageDialog(null, "Display Items: \n\n" + toString);
	} //End of Display Method
	
	//toStringRawM Method - Stores raw data of each item in to a variable 
	public void toStringRawM() {
		
		toStringRaw = ""; //Variable that stores raw data
		//Prints each item and its information
	    for (String i : itemList.keySet()) 
			toStringRaw += itemList.get(i).toStringRaw() + "\n";
		
//		JOptionPane.showMessageDialog(null, "Display Items: \n\n" + toString);
	} //End of displayRaw Method
	
	//timeStamp Method - Creates time stamp for an activity when it's called on and stores the time and activity data in to the transaction log. Parameters include name of activity and item data.
	public void timeStamp(String activity, UAItem item) {		
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //Specifies the format of the data for an activity. 
		Date date = new Date();
		
	    BufferedWriter writer = null;
		
		try {
			
	        writer = new BufferedWriter(new FileWriter("transactionLog.txt", true)); //Text file to store back up data is created and opened
		
			if (item == null) {
				transactionLogData += dateFormat.format(date) + "	 All Data From System Deleted." + "\n";
				writer.write(dateFormat.format(date) + "	All Data From System Deleted." + "\n"); //Backed up data is written in to text file.
		
			}
		
			else {
				transactionLogData += dateFormat.format(date) + "      Activity: " + activity + " | " + item.toStringTransactionLog() + "\n"; //Stores the time stamp and activity data in to the transactionLog variable. 
				writer.write(dateFormat.format(date) + "      Activity: " + activity + " | " + item.toStringTransactionLog() + "\n");
			}
		
			writer.close();  //Text file is closed
		}
		
	    catch (IOException e) {
	        System.err.println(e);
	    } 
		
	} //End of timeStamp method.
	
	//backUpData Method - Stores data in to a text file so that even when the system is terminated, data will not be lost and will be stored back in to the hash table.
	public void backUpData() {

		toStringRaw = "";
		
		//for loop - Stores data for each item in to toString variable
	    for (String i : itemList.keySet()) 
			toStringRaw += itemList.get(i).toStringRaw() + "\n";
		
	    BufferedWriter writer = null;
	    
	    //Writes data to text file
	    try {
	        writer = new BufferedWriter(new FileWriter("backUpData.txt")); //Text file to store back up data is created and opened
	        writer.write(toStringRaw); //Backed up data is written in to text file.
	        writer.close();  //Text file is closed
	        
	    } //End of try block
	    
	    catch (IOException e) {
	        System.err.println(e);
	    } 
	    
	} //End of backUpData Method
	
	//insertBackUpData Method - Gets data from text file and stores it in to the hash table when the system first starts running for persistence of data.
	public void insertBackUpData() {
		
		try {
			
			FileReader f = new FileReader("backUpData.txt"); //Text file is opened and stored in to f
			BufferedReader b = new BufferedReader(f); 
			String line = "";
			
			// while loop - Goes through each line of text file with the condition that there are more lines
			while ((line = b.readLine())!=null) { 
				
				
				StringTokenizer tokens = new StringTokenizer(line,","); //Each line is tokenized by a comma
				
				currentItem= new UAItem("","","","","");
				if (tokens.hasMoreTokens()) currentItem.setNumber(tokens.nextToken()); //Stores item number in to currentItem object
				if (tokens.hasMoreTokens()) currentItem.setName(tokens.nextToken()); //Stores name of item
				if (tokens.hasMoreTokens()) currentItem.setWear(tokens.nextToken()); //Stores type of wear of item
				if (tokens.hasMoreTokens()) currentItem.setPrice(tokens.nextToken()); //Stores price of item
				if (tokens.hasMoreTokens()) currentItem.setNumberColors(tokens.nextToken()); //Stores number of colors of item
				
				itemList.put(currentItem.getNumber(), currentItem); //Inserts current item and data for its attributes in to hash table
				
				timeStamp("Backup Data Inserted: ", currentItem);  //This activity is recorded along with its date/time.
//				backUpData();
				
			} // End of while loop
			b.close(); //Closes buffer
		} //End of try block
		
		//Catch block for FileNotFoundException
		catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //End of Catch block	
	}
	
	public void insertSearchedData(String in, String searched) {
		
//		StringTokenizer text = new StringTokenizer(in, "\n");
//		
//		while(text.hasMoreTokens()) {
//			
//			String line = text.nextToken();
//			
//			StringTokenizer tokens = new StringTokenizer(line, ",");
//			
//			currentItem= new UAItem("","","","","");
//			currentItem.setNumber(tokens.nextToken()); //Stores item number in to currentItem object
//			currentItem.setName(tokens.nextToken()); //Stores name of item
//			currentItem.setWear(tokens.nextToken()); //Stores type of wear of item
//			currentItem.setPrice(tokens.nextToken()); //Stores price of item
//			currentItem.setNumberColors(tokens.nextToken()); //Stores number of colors of item
//			currentItem.setImageURL(tokens.nextToken()); //Stores image URL
//			
//			itemList.put(currentItem.getNumber(), currentItem); //Inserts current item and data for its attributes in to hash table
//			
//			timeStamp("Searched Data Inserted: ", currentItem);  //This activity is recorded along with its date/time.
//			backUpData();
//			
//		}
		
		//--------------------------------------
		
	    BufferedWriter writer = null;
	    String line = "";
	    
	    //Writes data to text file
	    try {
	        writer = new BufferedWriter(new FileWriter("searchedData.txt")); //Text file to store back up data is created and opened
	        writer.write(in); //Backed up data is written in to text file.
	        writer.close();  //Text file is closed
	        
	    } //End of try block
	    
	    catch (IOException e) {
	        System.err.println(e);
	    } 
	    
		try {
			
			FileReader f = new FileReader("searchedData.txt"); //Text file is opened and stored in to f
			BufferedReader b = new BufferedReader(f); 
			
			// while loop - Goes through each line of text file with the condition that there are more lines
			while ((line = b.readLine())!=null) { 
				
				StringTokenizer tokens = new StringTokenizer(line,","); //Each line is tokenized by a comma
				
				currentItem= new UAItem("","","","","");
				if (tokens.hasMoreTokens()) currentItem.setNumber(tokens.nextToken()); //Stores item number in to currentItem object
//				if (itemList.get(currentItem.getNumber())!=null) continue;
				
				
				if (tokens.hasMoreTokens()) currentItem.setName(tokens.nextToken()); //Stores name of item
				if (tokens.hasMoreTokens()) currentItem.setWear(tokens.nextToken()); //Stores type of wear of item
				if (tokens.hasMoreTokens()) currentItem.setPrice(tokens.nextToken()); //Stores price of item
				if (tokens.hasMoreTokens()) currentItem.setNumberColors(tokens.nextToken()); //Stores number of colors of item
				if (tokens.hasMoreTokens()) currentItem.setImageURL(tokens.nextToken()); //Stores image URL
				
//				System.out.print("Here: " + currentItem.getImageURL() + "\n");
						
				itemList.put(currentItem.getNumber(), currentItem); //Inserts current item and its attribute data in to hash table
				
				timeStamp("Searched Results for \"" + searched + "\" Inserted", currentItem);  //This activity is recorded along with its date/time.
				backUpData(); //Data backed up for persistence 
				
			} // End of while loop
			b.close(); //Closes buffer
		} //End of try block
		
		//Catch block for FileNotFoundException
		catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //End of Catch block	
		
	}
	
	//Login Method - Checks to see if users/administrators entered to correct login name with its matching password. This allows users to proceed with the system turns on certain features/privileges depending on the type of user. Parameters are the user name and password entered.
	public void Login(String userName, String Password ){
		
			//Sets userPrivelage variable to true if a user successfully logs in with correct login name/password
			if (userName.equals("user") && Password.equals("0000")) {
				userPrivelage = true;
			}
		
			//Sets administratorPrivelage variable to true if an administrator successfully logs in with correct login name/password
			else if (userName.equals("administrator") && Password.equals("1111")) {
				administratorPrivelage = true;
			}
		
//			else JOptionPane.showMessageDialog(null, "Error! Login name does not match with password.");
		
	}//End of Login Method
	
	public void deleteAllSystemData() {
		
	    BufferedWriter writer = null;
	    
	    //Writes data to text file
	    try {
	        writer = new BufferedWriter(new FileWriter("searchedData.txt")); //Text file to store back up data is created and opened
	        writer.write(""); //Backed up data is written in to text file.
	        writer.close();  //Text file is closed
	        
	    } //End of try block
	    
	    catch (IOException e) {
	        System.err.println(e);
	    } 
	    
	    //Writes data to text file
	    try {
	    	
	        writer = new BufferedWriter(new FileWriter("backUpData.txt")); //Text file to store back up data is created and opened
	        writer.write(""); //Backed up data is written in to text file.
	        writer.close();  //Text file is closed
	        
	    } //End of try block
	    
	    catch (IOException e) {
	        System.err.println(e);
	    } 
		
	    itemList = new Hashtable <String, UAItem>();
		timeStamp("All Data Deleted from System ", null);  //This activity is recorded along with its date/time.
			
	}
	
	//createRows Method - Stores data for each item from hash table in to a 2-dimensional array to display it in the table of the GUI.
	public void createRows() {
		
		String[][] rowString = new String[itemList.keySet().size()][5]; //2-dimensional array to hold item data. Each attribute will be stored in a separate cell
		
		//for loop - goes through each item in hash table then extracts and stores each items attributes in to a 2-dimensional array. 
		 for (String i : itemList.keySet())  { int j=0;
				rowString[j][0] = itemList.get(i).getNumber(); //First cell holds item number
				rowString[j][1] = itemList.get(i).getName(); //Second cell holds item name
				rowString[j][2] = itemList.get(i).getWear(); //Third cell holds item wear
				rowString[j][3] = "$" + itemList.get(i).getPrice(); //Fourth cell holds item price
				rowString[j][4] = itemList.get(i).getnumberColors(); //fifth cell holds number of colors available for item. 
				j++;
				
		} //End of for loop	
	} //End of creatRows Method
	
}//End of UAVision class


