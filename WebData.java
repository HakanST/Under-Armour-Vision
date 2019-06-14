//Hakan Turgut
//CS 370 Project
//WebData.java

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

//WebData Class - Extracts HTML data for a particular URL based on what the user searched. This data is then stored as a text. Attribute data for items associated with the search is then extracted and stored in the the searchResults variable.
public class WebData {
	
	private static String webpage = null; //Contains the URL data
	public String searchResults = ""; //Contains data for every item associated with the search
	public String searchResultsDisplay = ""; //Contains data for every item associated with with a cleaner look for displaying
	
	String[][]  row;
	
	//BufferedReader - Returns HTML contents of URL. Parameter consists of the url. 
	public static BufferedReader read(String url) throws Exception {

			return new BufferedReader(new InputStreamReader(new URL(url).openStream()));

		} //End of BufferedReader
	
	//search Method - Takes a search input and creates the URL for that search. The URL will then be scraped of its HTML code which will be stored as a string. Certain parts of the HTML code will then be extracted and stored in to the attributes of each item in the hash table. The parameter consists of the search input. 
	public void search(String input) throws Exception {
		
		String inputSearch = ""; //Variable to be concatenated to the URL
		webpage = "https://www.underarmour.com/en-us/search?q="; //Base URL
		BufferedReader b;
		
		//If the search is empty, there will be no search results
		if (input.equals(null) || input.equals(" ") || input.equals("")) {		
			JOptionPane.showMessageDialog(null, "No Search Results");
			return;
		} //End of if statement
		
		//If there is only one word in the search parameter, then this will immediately get concatenated to the URL.
		if (input.split(" ").length == 1) {
			inputSearch = input;
			
			//Sports related searches don't have a general template for their URL. As a result of this, we will use the whole URL without making any concatenations.
			if (input.equalsIgnoreCase("basketball")) b = read("https://www.underarmour.com/en-us/basketball/g/3fy?iid=nav"); //Basketball
			else if (input.equalsIgnoreCase("soccer")) b = read("https://www.underarmour.com/en-us/mens/soccer/g/39hj?iid=nav"); //Soccer
			else if (input.equalsIgnoreCase("tennis")) b = read("https://www.underarmour.com/en-us/tennis/g/3hy?iid=nav"); //Tennis
			else if (input.equalsIgnoreCase("golf")) b = read("https://www.underarmour.com/en-us/golf/g/3gm?iid=dbo"); //Golf
			else if (input.equalsIgnoreCase("football")) b = read("https://www.underarmour.com/en-us/football/g/3gj?iid=nav"); //Football
			else if (input.equalsIgnoreCase("hockey")) b = read("https://www.underarmour.com/en-us/hockey/g/3gv?iid=dbo"); //hockey
			else if (input.equalsIgnoreCase("shoes")) b = read("https://www.underarmour.com/en-us/footwear/g/3r?iid=dbo"); //shoes
			else b = read(webpage + inputSearch); //Reads url
		} //End of if statement
		
		//If there are multiple words in the input parameter, then we will need to add a "+" at the end of each word in order to concatenate at the end of the URL.
		else {
			
		String[] searchSplit = input.split(" "); //Input is split on spaces to be replaced by "+"
		
		inputSearch = searchSplit[0];
		
		//Adds a "+" to replace the spaces. 
		for(int i=1; i<searchSplit.length; i++)
			inputSearch+="+" + searchSplit[i];
		
			b = read(webpage + inputSearch); //The input word can now be concatenated to the URL. It is then scraped and stored in to b. 
		}
		
//		JOptionPane.showMessageDialog(null, "Search is: " + inputSearch);
		
		String line = b.readLine(); //Contains a line for HTML code. 
		String vitalLine = ""; //Contains data with the important information we need
		
		int count=0;
		
		//Looks for the line that contains all of the data for each item. All other lines can be disregarded.
		while ((line = b.readLine())!=null) {

			if (line.contains("<div id=\"grid-container\"><div data-reactid")) { //The important line will contain this text
				vitalLine = line; //vitalLine will hold the important text.
				count++;	
				if (count == 3) break;
			} //End of if statement
		} //End of while loop
		
//		System.out.println(vitalLine);
		
		String[] split = vitalLine.split("data-pid=\""); //Each item contains one occurrence of this
		
		searchResults = "";
		searchResultsDisplay = "";
		
		//Extracts the attributes for each item for the search
		for (int i=1; i<split.length-1; i++) {
		
			//Extracts the item number 
			searchResultsDisplay += ("Item Number: " + split[i].substring(0,7) + "\n");
			searchResults += split[i].substring(0,7) + ",";
			
			//Extracts the item name 
			if (split[i].contains("name\">")) {
				int from = split[i].indexOf("name\">"); //Starting position
				int to = split[i].indexOf("</div><div class=\"title sub\""); //Ending position
				searchResultsDisplay += ("Item Name: " + split[i].substring(from+6, to) + "\n"); //Stores the text located between starting and ending position
				searchResults += split[i].substring(from+6, to) + ","; //Stores the text located between starting and ending position
			} //End of if
			
			//Extracts the item name 
			else if (split[i].contains("UA ")) {
				int from = split[i].indexOf("UA "); //Starting position
				int to = split[i].indexOf("</div><div class=\"title sub\""); //Ending position
				searchResultsDisplay += ("Item Name: " + split[i].substring(from, to-1) + "\n"); //Stores the text located between starting and ending position
				searchResults += split[i].substring(from, to-1) + ","; //Stores the text located between starting and ending position
			} //End of if
			else searchResults += " ,"; //If it doesn't contain either of the two above, it stores a space
		
			//Extracts the wear 
			if (split[i].contains("$cat\">")) {
				int to; //Ending position
				int from = split[i].indexOf("$cat\">"); //Starting position
				if (split[i].contains("</div><div class=\"price\"")) to = split[i].indexOf("</div><div class=\"price\""); 
				else to = split[i].indexOf("</div><div class=\"price");
				searchResultsDisplay += ("Item Wear: " + split[i].substring(from+6, to) + "\n"); //Stores the text located between starting and ending position
				searchResults += split[i].substring(from+6, to) + ","; //Stores the text located between starting and ending position
			} //End of if
			else searchResults += " ,";
			
			//Extracts the price
			if (split[i].contains(">$")) {
				int to; //Ending position
				int from = split[i].indexOf(">$"); //Starting position
				if (split[i].contains("</span><span class=\"price-orig\"")) to = split[i].indexOf("</span><span class=\"price-orig\"");
				else to = split[i].indexOf("/span></p></div>");
				
				if ((to-from)>0) {
				searchResultsDisplay += ("Item Price: " + split[i].substring(from+1, to-1) + "\n"); //Stores the text located between starting and ending position
				searchResults += split[i].substring(from+1, to-1) + ","; //Stores the text located between starting and ending position
				} //End of if
				else searchResults += " ,";
				
			} //End of if
			else searchResults += " ,";
		
			//Extracts the color availability
			if (split[i].contains("Colors Available")) {
				int from = split[i].indexOf("Colors Available"); //Starting position
				int to = split[i].indexOf("Colors Available"); //Ending position
				searchResultsDisplay += ("Item Colors Availability: " + split[i].substring(from-3, to) + "\n"); //Stores the text located between starting and ending position
				searchResults += split[i].substring(from-3, to) + ","; //Stores the text located between starting and ending position
			} //End of if
			else { 
				searchResultsDisplay += ("Item Colors Availability: 1" + "\n");		
				searchResults += "1" + ",";
			} //End of else
		
			// Extracts image URL
			if (split[i].contains("<img src=\"")) {
				int from = split[i].indexOf("<img src=\""); //Starting position
				int to = split[i].indexOf("\" alt="); //Ending position
//				searchResultsDisplay += ("Item Image Data: " + split[i].substring(from+12, to));
				searchResults += split[i].substring(from+12, to); //Stores the text located between starting and ending position
//				System.out.print(split[i].substring(from+12, to) + "\n");					
			} //End of if
			
			else searchResults += " ,";
		
			searchResultsDisplay += ("\n\n"); //All attribute data for current item is extracted so we insert space to space out items.
			searchResults += "\n"; //All attribute data for current item is extracted so we insert space to space out items.
				
		} //End of for loop
//		System.out.println(searchResultsDisplay);
//		System.out.println(inputSearch);
		
		if(searchResults.equals("")) JOptionPane.showMessageDialog(null, "No Search Results For: " + input); //If no data is stored in to searchResults variables, then it means there are no search results. User gets notified about this. 
		
	} //End of search method
	
//	void createRows(){
//		
//		try {
//			String line = "";
//			int c = 0;
//			int i=0;
//			FileReader f = new FileReader("searchedData.txt"); //Text file is opened and stored in to f
//			BufferedReader br = new BufferedReader(f); 
//			
//			// while loop - Goes through each line of text file with the condition that there are more lines
//			while ((line = br.readLine())!=null) 
//				c++;
//			
//			row = new String[c][6];
//			
//			
//			while ((line = br.readLine())!=null) {
//				String[] splits = line.split(",");
//				System.out.println(splits.length);
//				row[i][0] = splits[0];
//				row[i][1] = splits[2];
//				row[i][2] = splits[3];
//				row[i][3] = splits[4];
//				row[i][4] = splits[5];
//				row[i][5] = splits[6];
//				i++;
//			}
//
//			br.close(); //Closes buffer
//		} //End of try block
//		
//		//Catch block for FileNotFoundException
//		catch ( IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} //End of Catch block	
//		
//		JOptionPane.showMessageDialog(null, row[1][4]);
//		
//		
//	}
} //End of WebData Class