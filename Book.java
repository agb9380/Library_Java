package Library2;

public class Book {
	
	private String bNo; // 책 번호
	private String bTitle; // 책 제목
	private String bAuthor; // 작가
	private String bGenre; // 장르
	
	
	public Book() {
		
	}
	
	
	public Book(String bNo, String bTitle, String bAuthor, String bGenre) {
		super();
		this.bNo= bNo;
		this.bTitle = bTitle;
		this.bAuthor = bAuthor;
		this.bGenre = bGenre;
	}
	

	public String getbNO() {
		return bNo;
	}




	public void setbNO(String bNo) {
		this.bNo = bNo;
	}




	public String getbTile() {
		return bTitle;
	}




	public void setbTile(String bTile) {
		this.bTitle = bTile;
	}




	public String getbAuthor() {
		return bAuthor;
	}




	public void setbAuthor(String bAuthor) {
		this.bAuthor = bAuthor;
	}




	public String getbGenre() {
		return bGenre;
	}




	public void setbGenre(String bGenre) {
		this.bGenre = bGenre;
	}
	
	
	
	
}
