import java.util.ArrayList;

public class User {

	private String username, password;
	private ArrayList<Book> booksCheckedOut;
	
	public User (String u, String p, ArrayList<Book> b) {
		
		username = u;
		password = p;
		booksCheckedOut = b;
		
	}
	
	public User () {
		
	}

	public String getUsername() {	return username;	}
	public void setUsername(String username) {	this.username = username;	}

	public String getPassword() {	return password;	}
	public void setPassword(String password) {	this.password = password;	}

	public ArrayList<Book> getBooksCheckedOut() {	return booksCheckedOut;	}
	public void setBooksCheckedOut(ArrayList<Book> booksCheckedOut) {	this.booksCheckedOut = booksCheckedOut;	}
	
	
	
}
