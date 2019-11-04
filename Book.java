
public class Book {

	private String title, author, date, subject, description, checkedOutBy, frontPage;
	private boolean checkedOut;
	
	public Book (String t, String a, String d, String s, String desc, 
			boolean cO, String cOB, String fp) {
		
		title = t;
		author = a;
		date = d;
		subject = s;
		description = desc;
		checkedOut = cO;
		checkedOutBy = cOB;
		frontPage = fp;
		
	}
	
	public Book () {
		
	}

	public String getTitle() {	return title;	}
	public void setTitle(String title) {	this.title = title;	}

	public String getAuthor() {	return author;	}
	public void setAuthor(String author) {	this.author = author;	}

	public String getDate() {	return date;	}
	public void setDate(String date) {	this.date = date;	}

	public String getSubject() {	return subject;	}
	public void setSubject(String subject) {	this.subject = subject;	}

	public String getDescription() {	return description;	}
	public void setDescription(String description) {	this.description = description;	}

	public String getCheckedOutBy() {	return checkedOutBy;	}
	public void setCheckedOutBy(String checkedOutBy) {	this.checkedOutBy = checkedOutBy;	}

	public String getFrontPage() {	return frontPage;	}
	public void setFrontPage(String frontPage) {	this.frontPage = frontPage;	}
	
	public boolean isCheckedOut() {	return checkedOut;	}
	public void setCheckedOut(boolean checkedOut) {	this.checkedOut = checkedOut;	}
	
	public String toString() {
		
		return this.getTitle() + ", by " + this.getAuthor();
		
		
	}
	
	public String printAllInfo() {
		return this.getTitle() + ", " + this.getAuthor() + ", " + this.getDate() + ", " + this.getSubject() + ", " + this.getDescription()
					+ ", " + this.isCheckedOut() + "," + this.getCheckedOutBy();
	}
	
	
}
