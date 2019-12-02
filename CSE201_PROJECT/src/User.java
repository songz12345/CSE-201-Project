import java.util.ArrayList;

public class User {

	private String username, password;
	private ArrayList<String> booksCheckedOut;
	
	public User (String u, String p, ArrayList<String> b) {
		
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

	public ArrayList<String> getBooksCheckedOut() {	return booksCheckedOut;	}
	public void setBooksCheckedOut(ArrayList<String> booksCheckedOut) {	this.booksCheckedOut = booksCheckedOut;	}
	
	
	
}
