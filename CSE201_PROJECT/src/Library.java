import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;

public class Library {
	final static int MAX_BOOK_NUMBER = 100;
	
	private static JFrame jf;
	private JPanel subjectPanel;
	private JLabel frontPage, icon, descripTitle, background;
	private JButton search, login, signup, borrow;
	private ImageIcon bookImg, backgroundImg;
	private JTextField text;
	private JTextArea area, description;
	private JList<String> list;
	public static ArrayList<Book> lib;
	public static ArrayList<User> users;
	public static String searchBuffer;
    public User currentUser;
    public String subject = "";
    
	public Library() {
		jf = new JFrame();
		subjectPanel = new JPanel();
		search = new JButton();
		login = new JButton();
		borrow = new JButton();
		signup = new JButton();
		area = new JTextArea();
		description = new JTextArea();
		list = new JList<String>();
		text = new JTextField();
		currentUser = new User();
		init();
	}
	
	/**
	 * load the book information
	 */
	private static void loadBooks() {
		Scanner in;
		String line;
		int index = 0;
		try {
			in = new Scanner(new File("database/BookInfo.txt"));
			// remove the first line
			in.nextLine();
			while (in.hasNextLine()) {
				line = in.nextLine();
				if (!line.isEmpty()) {
			 	    String[] info = new String[6];
				    info = line.split("/");
				    // load the date to the lib
				    lib.add(new Book(info[0], info[1], info[2], info[3], info[4], false, " ", "imgs/"+info[5]));
			        index++;
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * load the user information
	 */
	private static void loadUsers() {
		Scanner in;
		String line;
		try {
			in = new Scanner(new File("database/users.txt"));
			// remove the first line
			in.nextLine();
			while (in.hasNextLine()) {
				line = in.nextLine();
				if (!line.isEmpty()) {
					User user = new User();
					ArrayList<String> borrowedBooks = new ArrayList<String>();
			 	    String[] info = line.split("/");
				    // load the user date to the users
				    user.setUsername(info[0]);
				    user.setPassword(info[1]);
				    for (int i = 2; i <= info.length; i++) {
				    		if (i >= info.length) {
				    			break;
				    		} else {
				    			borrowedBooks.add(info[i]);
				    		}
				    }
				    user.setBooksCheckedOut(borrowedBooks);
				    users.add(user);
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Check if the search key is in the library. If it exist, return the index
	 * otherwise return -1
	 * @param key
	 * @return
	 */
	public static int inLibrary(String key) {
		int i = 0;
		// search for the book by the title
		for (Book s : lib) {
			if (s.getTitle().contains(key)) {
				return i;
			} else {
				i++;
			}
		}
		return -1;
	}
	
	/**
	 * Retrive the book information from the library as a string based on the key
	 * e.g.
	 * "Ancient Greek philosophical school/Errin Crutsinger/1975/philosophy
	 * / the Ancient Greek Philosophical Schools
	 * representative.\n"
	 * 
	 * @param key
	 * @return
	 */
	public Book getBookInfo(String key) {
		for (Book b : lib) {
			if (key.compareTo(b.getTitle()) == 0) {
				return b;	
			}
		}
		return null;
	}
	
	/**
	 * 
	 */
	public void search() {
		searchBuffer = text.getText();
		// if didn't find the book in the library
		// pop up a error window for user
		int index = inLibrary(searchBuffer);
		if (index < 0) {
			JFrame errWindow = new JFrame();
			errWindow.setTitle("Error");
			errWindow.setLayout(null);
			JLabel errMsg = new JLabel("Sorry, we didn't find your book!");
			errMsg.setFont(new Font("Serif", Font.PLAIN, 20));
			errMsg.setBounds(40, 0, 300, 100);
			errWindow.add(errMsg);
			errWindow.setSize(350, 125);
			errWindow.setLocationRelativeTo(null);
			errWindow.setResizable(false);
			errWindow.setVisible(true);
			errWindow.toFront();
		} else {
			// if match given subject
			if (!lib.get(index).getSubject().equals(subject) && subject != "") {
				JFrame errWindow = new JFrame();
				errWindow.setTitle("Error");
				errWindow.setLayout(null);
				JLabel errMsg = new JLabel("Sorry, we didn't find your book under this subject!");
				errMsg.setFont(new Font("Serif", Font.PLAIN, 20));
				errMsg.setBounds(40, 0, 450, 100);
				errWindow.add(errMsg);
				errWindow.setSize(500, 125);
				errWindow.setLocationRelativeTo(null);
				errWindow.setResizable(false);
				errWindow.setVisible(true);
				errWindow.toFront();
			} else {
				// change description for found book
				description.setText(lib.get(index).getDescription());
//				descripTitle.setBounds(350, 300, 150, 50);
//				description.setBounds(210, 350, 390, 150);
				// change front page for found book
				bookImg = new ImageIcon(lib.get(index).getFrontPage());
				Image img = bookImg.getImage().getScaledInstance(400, 500, Image.SCALE_SMOOTH);
				frontPage.setIcon(new ImageIcon(img));
			}
		}
	}
	
	/**
	 * update the user information after borrowing one or more books
	 */
	public void updateUser(User user) {
		for (User u : users) {
			// if the name matches, update the user information
			if (u.getUsername().equals(user.getUsername())) {
				u.setBooksCheckedOut(user.getBooksCheckedOut());
			}
		}
			try {
				FileWriter fw = new FileWriter("database/users.txt", false);
				fw.write("username            password                borrowed-books\n");
				for (User a : users) {
					fw.write(a.getUsername() + "/" + a.getPassword() + "/" + a.getBooksCheckedOut() + "\n");
				}
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	}
	
	/**
	 * Borrowing, set the book unavailable
	 * @param book
	 * @return
	 */
	public boolean borrowBook(Book book) {
		String username = Login_System.username;
		String password = Login_System.password;
		boolean isLogged = Login_System.isLogged;
		
		currentUser.setUsername(username);
		currentUser.setPassword(password);
		// if user has logged in
		if (isLogged) {
			// if the book has been borrowed
			if (book.isCheckedOut()) { 
				JFrame errWindow = new JFrame();
				errWindow.setTitle("Error");
				errWindow.setLayout(null);
				JLabel errMsg = new JLabel("Sorry, this book is unavailable right now!");
				errMsg.setFont(new Font("Serif", Font.PLAIN, 20));
				errMsg.setBounds(40, 0, 300, 100);
				errWindow.add(errMsg);
				errWindow.setSize(350, 125);
				errWindow.setLocationRelativeTo(null);
				errWindow.setResizable(false);
				errWindow.setVisible(true);
				errWindow.toFront();
				return false;
			} else { // if the book hasn't been borrowed
				// mark book as unavailable and add the book to user's borrowlist
				if (!isLogged) {
					JFrame errWindow = new JFrame();
					errWindow.setTitle("Error");
					errWindow.setLayout(null);
					JLabel errMsg = new JLabel("Sorry, You need to login first!");
					errMsg.setFont(new Font("Serif", Font.PLAIN, 20));
					errMsg.setBounds(40, 0, 300, 100);
					errWindow.add(errMsg);
					errWindow.setSize(350, 125);
					errWindow.setLocationRelativeTo(null);
					errWindow.setResizable(false);
					errWindow.setVisible(true);
					errWindow.toFront();
					return false;
				} else {
					book.setCheckedOut(true);
					book.setCheckedOutBy(currentUser.getUsername());
					// append the new book user borrowed to its profile
					updateUser(currentUser);
					// pop up new window
					JFrame newWindow = new JFrame();
					newWindow.setTitle("Successs");
					newWindow.setLayout(null);
					JLabel newMsg = new JLabel("Okay, this book is yours now!");
					newMsg.setFont(new Font("Serif", Font.PLAIN, 20));
					newMsg.setBounds(40, 0, 300, 100);
					newWindow.add(newMsg);
					newWindow.setSize(350, 125);
					newWindow.setLocationRelativeTo(null);
					newWindow.setResizable(false);
					newWindow.setVisible(true);
					newWindow.toFront();
					return true;
				}
			}
		} else {  // if the user hasn't logged in
			JFrame errWindow = new JFrame();
			errWindow.setTitle("Error");
			errWindow.setLayout(null);
			JLabel errMsg = new JLabel("Sorry, please login first!");
			errMsg.setFont(new Font("Serif", Font.PLAIN, 20));
			errMsg.setBounds(40, 0, 300, 100);
			errWindow.add(errMsg);
			errWindow.setSize(350, 125);
			errWindow.setLocationRelativeTo(null);
			errWindow.setResizable(false);
			errWindow.setVisible(true);
			errWindow.toFront();
			return false;
		}
	}
	
	/**
	 * Return the book and set the book available again
	 * @param book
	 * @return
	 */
	public boolean returnBook(Book book) {
		book.setCheckedOut(false);
		book.setCheckedOutBy(null);
		currentUser.getBooksCheckedOut().remove(book.getTitle());
		return true;
	}
	
	/**
	 * Add the book with its related information
	 * @param info
	 * @return
	 */
	public boolean addBook(String info) {
		String[] infos = new String[6];
		// seperate the info to 6 parts
	    infos = info.split("/");
	    Book newbook = new Book(infos[0], infos[1], infos[2], infos[3], infos[4], false, " ", infos[5]);
	    // if the book is already exists in the library
	    if (inLibrary(newbook.getTitle()) != -1) {
	    	return false;
	    } else {
	    	lib.add(newbook);
			return true;
	    }
	}
	
	/**
	 * Remove the book from the library
	 * @param book
	 * @return
	 */
	public boolean removeBook(Book book) {
		lib.remove(book);
		return true;
	}
	
//	/**
//	 * Issue a fine to a user with its login id, return the fine amount
//	 * @param id
//	 * @return
//	 */
//	public int issueAFine(String id) {
//		int fineAmount = 0;
//		
//		return fineAmount;
//	}
//	
//	/**
//	 * Ban a user if s/he didn't return the book over a month.
//	 * @param id
//	 * @return
//	 */
//	public boolean banAUser(String id) {
//		return true;
//	}
	
	/**
	 * initiation of GUI
	 */
	private void init() {
		jf.setTitle("Librademic");
		jf.setLayout(null);
		// book list for drop-down list
//		ArrayList<String> books = new ArrayList<String>();
		text.setText("");
		// buttons
		search.setText("Search");
		// after press the search button, the text the user has typed would be
		// stored in searchBuffer to future use.
		search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});
		login.setText("Log in");
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// load the user information
				Login_System.main();
			}
		});
		signup.setText("Sign up");
		signup.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// trigger the create account class and ask user to create an account
				CreateAccount.main();
			}
		});
		
		borrow.setText("Borrow");
		borrow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Book borrowed = new Book();
				borrowBook(borrowed);
			}
		});
		// description part
		description.setWrapStyleWord(true);
		description.setLineWrap(true);
		description.setEditable(false);
		
		// buttons
		JRadioButton rButton1 = new JRadioButton("Chemestry       ");
		rButton1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				subject = rButton1.getText();
				subject = subject.trim();
			}
		});
		JRadioButton rButton2 = new JRadioButton("Alchemy         ");
		rButton2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				subject = rButton2.getText();
				subject = subject.trim();
			}
		});
		JRadioButton rButton3 = new JRadioButton("Physics         ");
		rButton3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				subject = rButton3.getText();
				subject = subject.trim();
			}
		});
		JRadioButton rButton4 = new JRadioButton("Math            ");
		rButton4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				subject = rButton4.getText();
				subject = subject.trim();
			}
		});
		JRadioButton rButton5 = new JRadioButton("English         ");
		rButton5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				subject = rButton5.getText();
				subject = subject.trim();
			}
		});
		JRadioButton rButton6 = new JRadioButton("Spanish         ");
		rButton6.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				subject = rButton6.getText();
				subject = subject.trim();
			}
		});
		JRadioButton rButton7 = new JRadioButton("Chinese         ");
		rButton7.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				subject = rButton7.getText();
				subject = subject.trim();
			}
		});
		JRadioButton rButton8 = new JRadioButton("Computer Science");
		rButton8.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				subject = rButton8.getText();
				subject = subject.trim();
			}
		});
		JRadioButton rButton9 = new JRadioButton("Biology         ");
		rButton9.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				subject = rButton9.getText();
				subject = subject.trim();
			}
		});
		// put the button in one group so that only one can be 
		// choose at the same time.
		ButtonGroup bg = new ButtonGroup();
		bg.add(rButton1);
		bg.add(rButton2);
		bg.add(rButton3);
		bg.add(rButton4);
		bg.add(rButton5);
		bg.add(rButton6);
		bg.add(rButton7);
		bg.add(rButton8);
		bg.add(rButton9);
		
		// add icon
		ImageIcon iconImg = new ImageIcon("imgs/icon.jpeg");
		icon = new JLabel(iconImg);
		//add background
		backgroundImg = new ImageIcon("imgs/background.jpg");
		Image img1 = backgroundImg.getImage().getScaledInstance(1100, 600, Image.SCALE_SMOOTH);
		background = new JLabel(new ImageIcon(img1));
		// initial book's front page
		bookImg = new ImageIcon("imgs/default1.jpg");
		Image img = bookImg.getImage().getScaledInstance(400, 500, Image.SCALE_SMOOTH);
		frontPage = new JLabel(new ImageIcon(img));
		// add description part
		descripTitle = new JLabel("Description");
		
		icon.setBounds(30, 5, iconImg.getIconWidth(), iconImg.getIconHeight());
		icon.setSize(300, 45);
		background.setBounds(0, 0, 1100, 600);
		frontPage.setBounds(650, 60, 400, 500);
		search.setBounds(30, 50, 170, 50);
		login.setBounds(900, 20, 80, 30);
		signup.setBounds(810, 20, 80, 30);
		borrow.setBounds(990, 20, 80, 30);
		text.setBounds(210, 50, 390, 50);
		area.setBounds(30, 150, 470, 450);
		list.setBounds(215, 100, 380, 150);
		subjectPanel.setBounds(30, 150, 170, 350);
		descripTitle.setBounds(350, 300, 150, 50);
		description.setBounds(210, 350, 390, 150);
		
		// subject buttons
		subjectPanel.add(rButton1);
		subjectPanel.add(rButton2);
		subjectPanel.add(rButton3);
		subjectPanel.add(rButton4);
		subjectPanel.add(rButton5);
		subjectPanel.add(rButton6);
		subjectPanel.add(rButton7);
		subjectPanel.add(rButton8);
		subjectPanel.add(rButton9);
		
		// add components to jframe
		jf.add(subjectPanel);
		jf.add(search);
		jf.add(login);
		jf.add(signup);
		jf.add(borrow);
		jf.add(text);
//		jf.add(list);
		jf.add(frontPage);
		jf.add(icon);
		jf.add(description);
		jf.add(descripTitle);
//		jf.add(background);
		
		 // set jframe properties
		jf.setSize(1100, 600);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		lib = new ArrayList<Book>();
		users = new ArrayList<User>();
		searchBuffer = new String();
		new Library();
		loadBooks();
		loadUsers();
		jf.setVisible(true);
	}
}
