//Hakan Turgut
//CS 370 Project
//User.java

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;

//Interface for user to search and view data and administrator to search, view, and modify data.
public class User {
	
	private WebData Search = new WebData(); //Object for WebData class for scraping data for a URL
	private UAVision UA = new UAVision(); //Object for UAVision class that contains hash table and methods for actions

	private JFrame frame;
	private JTextField textFieldUser;
	private JPasswordField passwordField;
	
	boolean userPrivelage = false;
	boolean administratorPrivelage = false;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User window = new User();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public User() {
		UA.readBackUp();
		initialize();
	}

	/**
	 * Initialize the contents of the frame. Create tabs for users and administrators. Users can search and view data while administrators can search, view, and modify the data.
	 */
	private void initialize() {
		
		frame = new JFrame("Under Armour Vision");
		frame.setBounds(100, 100, 621, 454);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		//------------------------------------------------------------------------
		
		//Search Tab - Allows users to search for Under Armour items and view details about them (number, name, wear, price, color availability, image)
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Search", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblSearch = new JLabel("Search Under Armour Item");
		lblSearch.setBounds(211, 17, 299, 31);
		panel_1.add(lblSearch);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(21, 115, 556, 249);
		panel_1.add(scrollPane_1);
		
		//Text area to display searched results
		JTextArea textArea_2 = new JTextArea();
		scrollPane_1.setViewportView(textArea_2);
		
		//Text field to search for Under Armour items over the web
		textField = new JTextField();
		textField.setBounds(57, 60, 299, 26);
		panel_1.add(textField);
		textField.setColumns(10);
		
		//Search button
		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Search.search(textField.getText()); //Searches user input
					
					textArea_2.setText(Search.searchResultsDisplay); //Returns results of user input
					
					UA.insertSearchedData(Search.searchResults, textField.getText()); //Inserts results in to hash table
					
//					Search.createRows();
				} 
				catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(417, 60, 117, 29);
		panel_1.add(btnNewButton);
		
		//------------------------------------------------------------------------
		
		//Offline Query Tab - Allows users to view item details offline, for items that exist in the hash table. This includes number, name, wear, price, color availability, and image. It also allows users to download the image. 
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Offline", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel lblOfflineQuery = new JLabel("Offline Query ");
		lblOfflineQuery.setBounds(253, 19, 118, 27);
		panel_2.add(lblOfflineQuery);
		
		JLabel lblEnterItemNumber = new JLabel("Item Number");
		lblEnterItemNumber.setBounds(39, 59, 88, 27);
		panel_2.add(lblEnterItemNumber);
		 
		//Text field to search for items offline
		textField_7 = new JTextField();
		textField_7.setBounds(182, 58, 189, 27);
		panel_2.add(textField_7);
		textField_7.setColumns(10);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(22, 116, 300, 190);
		panel_2.add(textArea);
		
		JLabel label = new JLabel("");
		label.setBounds(334, 116, 249, 190);
		panel_2.add(label);
		
		//Displays all item information including image based on item number.
		JButton btnEnter_2 = new JButton("Enter");
		btnEnter_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(UA.offlineQuery(textField_7.getText())); //Returns item information for item number entered
				
				
//				JOptionPane.showMessageDialog(null, UA.itemList.get(textField_7.getText()).getImageURL());
				
				//Creates image using image URL for items that exist and have image URLs
				if (UA.itemList.get(textField_7.getText())!=null) {
					
					//If the item has an image URL, create the image
					if (UA.itemList.get(textField_7.getText()).getImageURL().contains(".com")){
						URL url = null;
				
						try {
							url = new URL("https://" + UA.itemList.get(textField_7.getText()).getImageURL()); //URL for image
							BufferedImage image = ImageIO.read(url); //Image created
							ImageIcon icon = new ImageIcon(image); //Image stored in to icon
							label.setIcon(icon);
						
							} catch (MalformedURLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					} //End if statement 
					
					else label.setIcon(null); //If image URL doesn't exist, set icon to null
				
				} //End if statement
				
				else label.setIcon(null); //If input for item doesn't exist, set icon to null
						
			}
		});
		btnEnter_2.setBounds(427, 59, 117, 29);
		panel_2.add(btnEnter_2);
		
		//Emails result to user if entered
		JButton btnEmailResult = new JButton("Email Result");
		btnEmailResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		btnEmailResult.setBounds(453, 348, 117, 29);
		panel_2.add(btnEmailResult);
		
		JLabel lblEnterEmail = new JLabel("Enter Email");
		lblEnterEmail.setBounds(22, 353, 88, 16);
		panel_2.add(lblEnterEmail);
		
		textField_8 = new JTextField();
		textField_8.setBounds(109, 350, 332, 21);
		panel_2.add(textField_8);
		textField_8.setColumns(10);
		
		//Downloads image for item if the button is pressed
		JButton btnDownloadImage = new JButton("Download Image");
		btnDownloadImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Downloads image for items that exist and have image URLs 
				if (UA.itemList.get(textField_7.getText())!=null) {
				
					//Downloads image if item has an image URL
					if (UA.itemList.get(textField_7.getText()).getImageURL().contains(".com")){
					
						URL url = null;
					
							try {
								url = new URL("https://" + UA.itemList.get(textField_7.getText()).getImageURL()); //URL for image
								BufferedImage image = ImageIO.read(url); //Creates image
								File outputFile = new File(UA.itemList.get(textField_7.getText()).getNumber() + ".jpg"); //Creates image file
								ImageIO.write(image, "jpg", outputFile); //Saves image to .jpg file
								System.out.println("Image Succesfully Downloaded");
							
							} catch (MalformedURLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							}
					}
				
				}
				
			}
		});
		btnDownloadImage.setBounds(404, 307, 140, 29);
		panel_2.add(btnDownloadImage);
		
		//--------------------------------------------------------------
		
		//Add/Edit Tab - Allows administrators to add and modify items in hash table
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Add/Edit", null, panel_3, null);
		panel_3.setLayout(null);
		
		JLabel lblAddeditUnderArmour = new JLabel("Add/Edit Under Armour Item");
		lblAddeditUnderArmour.setBounds(194, 17, 284, 34);
		panel_3.add(lblAddeditUnderArmour);
		
		//Textfield for item number
		textField_1 = new JTextField();
		textField_1.setBounds(54, 93, 236, 29);
		panel_3.add(textField_1);
		textField_1.setColumns(10);
		
		//Textfield for item name
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(54, 143, 236, 29);
		panel_3.add(textField_2);
		
		//Textfield for item wear
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(54, 196, 236, 29);
		panel_3.add(textField_3);
		
		//Textfield for item price
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(54, 243, 236, 29);
		panel_3.add(textField_4);
		
		//Textfield for item color availability
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(54, 290, 236, 29);
		panel_3.add(textField_5);
		
		//If all fields are filled and item number doesn't already exist in the hash table, new item will be inserted in to the hash table
		JButton btnEnter_1 = new JButton("Add Item");
		btnEnter_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					UA.addItem(textField_1.getText(), textField_2.getText(), textField_3.getText(), textField_4.getText(), textField_5.getText()); //Inserts the new item in to the hash table
			}
		});
		btnEnter_1.setBounds(146, 351, 117, 29);
		panel_3.add(btnEnter_1);
		
		JLabel lblNewLabel_3 = new JLabel("Item Number");
		lblNewLabel_3.setBounds(340, 96, 189, 23);
		panel_3.add(lblNewLabel_3);
		
		JLabel lblItemName = new JLabel("Item Name");
		lblItemName.setBounds(340, 143, 189, 23);
		panel_3.add(lblItemName);
		
		JLabel lblItemWear = new JLabel("Item Wear");
		lblItemWear.setBounds(340, 196, 189, 23);
		panel_3.add(lblItemWear);
		
		JLabel lblColorAvailability = new JLabel("Color Availability");
		lblColorAvailability.setBounds(340, 296, 189, 23);
		panel_3.add(lblColorAvailability);
		
		JLabel lblItemPrice = new JLabel("Item Price");
		lblItemPrice.setBounds(340, 249, 189, 23);
		panel_3.add(lblItemPrice);
		
		//Edits items for existing item numbers
		JButton btnEditItem = new JButton("Edit Item");
		btnEditItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				UA.editItem(textField_1.getText(), textField_2.getText(), textField_3.getText(), textField_4.getText(), textField_5.getText()); //Edits item with existing number and modifies its attributes in the hash table
				
			}
		});
		btnEditItem.setBounds(315, 351, 117, 29);
		panel_3.add(btnEditItem);
		
		//------------------------------------------------------------------------
		
		//Delete Tab - Allows administrators to delete existing items in the hash table
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("Delete", null, panel_4, null);
		panel_4.setLayout(null);
		
		JLabel lblDeleteUnderArmour = new JLabel("Delete Under Armour Item");
		lblDeleteUnderArmour.setBounds(221, 20, 224, 25);
		panel_4.add(lblDeleteUnderArmour);
		
		//Text field to input number for item that needs to be deleted
		textField_6 = new JTextField();
		textField_6.setBounds(114, 137, 178, 26);
		panel_4.add(textField_6);
		textField_6.setColumns(10);
		
		//Deletes item if the input number for it exists in the hash table
		JButton btnNewButton_2 = new JButton("Delete");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UA.deleteItem(textField_6.getText()); //Deletes item from hash table
			}
		});
		btnNewButton_2.setBounds(241, 255, 117, 29);
		panel_4.add(btnNewButton_2);
		
		JLabel lblItemNumber = new JLabel("Item Number");
		lblItemNumber.setBounds(361, 142, 131, 16);
		panel_4.add(lblItemNumber);
		
		//---------------------------------------------------------
		
		//System tab - Allows administrator to view the data existing in the hash table and the transaction log. Additionally, they can delete all of the stored data from pressing a button that deletes all of the data from the hash table and text file
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("System", null, panel_5, null);
		panel_5.setLayout(null);
		
		JLabel lblSystemMaintenance = new JLabel("View Data");
		lblSystemMaintenance.setBounds(265, 6, 74, 33);
		panel_5.add(lblSystemMaintenance);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 87, 548, 250);
		panel_5.add(scrollPane);
		
		JTextArea textArea_1 = new JTextArea();
		scrollPane.setViewportView(textArea_1);
		
		//Allows administrators to view the transaction log within the GUI. (Transaction log is also available through the transactionLog text file).
		JButton btnAdd = new JButton("View Transaction Log");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_1.setText(UA.transactionLogData);
				
			}
		});
		btnAdd.setBounds(35, 44, 254, 31);
		panel_5.add(btnAdd);
		
		//Allows administrator to view all of the data in the hash table
		JButton btnNewButton_1 = new JButton("View Backup Data");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UA.toStringRawM();
				textArea_1.setText(UA.toStringRaw); //Displays all of the data of each item in the hash table
			}
		});
		btnNewButton_1.setBounds(312, 44, 249, 31);
		panel_5.add(btnNewButton_1);
		
		//Deletes all of the storage data in the system. Removes hash table data and text file data.
		JButton btnDeleteAllData = new JButton("Delete All Stored Data");
		btnDeleteAllData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				UA.deleteAllSystemData(); //Deletes all storage data
				JOptionPane.showMessageDialog(null, "All stored data in hash table succesfully deleted");
			}
		});
		btnDeleteAllData.setBounds(234, 349, 196, 29);
		panel_5.add(btnDeleteAllData);
		
		//---------------------------------------------------------
		
		//Login Tab - Allows users and administrators to log in. Administrators will be able to modify data in the hash table, search for items, and view data. Users will only be able to search for items and view data. 
		JPanel panel_login = new JPanel();
		tabbedPane.addTab("Login", null, panel_login, null);
		
		panel_login.setLayout(null);
		
		tabbedPane.remove(panel_1);
		tabbedPane.remove(panel_2);
		tabbedPane.remove(panel_3);
		tabbedPane.remove(panel_4);
		tabbedPane.remove(panel_5);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(128, 118, 100, 24);
		panel_login.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(128, 196, 61, 16);
		panel_login.add(lblNewLabel_1);
		
		//Text field for user name
		textFieldUser = new JTextField();
		textFieldUser.setBounds(256, 117, 130, 25);
		panel_login.add(textFieldUser);
		textFieldUser.setColumns(10);
		
		//Password field for password
		passwordField = new JPasswordField();
		passwordField.setBounds(256, 193, 130, 21);
		panel_login.add(passwordField);
		
		JLabel lblNewLabel_2 = new JLabel("Login");
		lblNewLabel_2.setBounds(243, 47, 107, 33);
		panel_login.add(lblNewLabel_2);
		
		//If users and administrators type in their correct user names and passwords, they will be able to log in
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			//If the user types in the correct user name and password, the userPrivelage variable will be true	
			if (textFieldUser.getText().equals("user") && passwordField.getText().equals("0000")) 
				userPrivelage = true;
			
			//If the administrator types in the correct user name and password, the administratorPrivelage variable will be true	
			else if (textFieldUser.getText().equals("administrator") && passwordField.getText().equals("1111")) 
				administratorPrivelage = true;
			
			//If the user name doesn't with the password, you will not be logged in until you they do
			else JOptionPane.showMessageDialog(null, "Error! User name and password do not match! Please try again.");
			
			//If the administrator logs in, add these tabs
			if (administratorPrivelage==true) {
				
				System.out.println("Administrator Log In \n");
				
				
				tabbedPane.remove(panel_login);

				tabbedPane.addTab("Search", null, panel_1, null); //Search tab
				tabbedPane.addTab("Offline", null, panel_2, null); //Offline query tab
				tabbedPane.addTab("Add/Edit", null, panel_3, null); //Add/Edit tab
				tabbedPane.addTab("Delete", null, panel_4, null); //Delete tab
				tabbedPane.addTab("System", null, panel_5, null); //System tab
				tabbedPane.addTab("Login", null, panel_login, null); //Login tab
				administratorPrivelage = false;
				
				JOptionPane.showMessageDialog(null, "Administrator is now logged in.");
				
			}
			
			//If the user logs in, tabs that that only apply to administrators get removed
			else if (userPrivelage==true) {
				
				System.out.println("User Log In \n");
				
				tabbedPane.remove(panel_login);
				tabbedPane.remove(panel_3); //Remove Add/Edit tab
				tabbedPane.remove(panel_4); //Remove Delete tab
				tabbedPane.remove(panel_5); //Remove System tab

				tabbedPane.addTab("Search", null, panel_1, null); //Add search tab
				tabbedPane.addTab("Offline", null, panel_2, null); //Add offline tab
				tabbedPane.addTab("Login", null, panel_login, null); //Add login tab
				userPrivelage = false;
				
				JOptionPane.showMessageDialog(null, "User is now logged in.");
				
			}
				
			}
		});
		btnEnter.setBounds(294, 275, 117, 29);
		panel_login.add(btnEnter);
		
		//If this button is pressed, the system terminates
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					System.exit(0); //Exits system
			}
		});
		btnExit.setBounds(141, 275, 117, 29);
		panel_login.add(btnExit);
		
//		tabbedPane.remove(panel_1);
//		tabbedPane.remove(panel_2);
//		tabbedPane.remove(panel_3);
//		tabbedPane.remove(panel_4);
//		tabbedPane.remove(panel_5);
	
	} //End of Initialize Method
} //End of User.java class
