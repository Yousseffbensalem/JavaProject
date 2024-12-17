package projet.book.model;

public class Book {
	
	
	private Long id;
	private String Title;
	private String Author;
	private Double Price;
	private int PublishedYear;
	private int fdsfs;
	
	

	
	public Book(Long id, String title, String author, Double price, int publishedyear) {
		super();
		this.id = id;
		Title = title;
		Author = author;
		Price=price;
		PublishedYear = publishedyear;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getAuthor() {
		return Author;
	}
	public void setAuthor(String author) {
		Author = author;
	}
	public int getPublishedYear() {
		return PublishedYear;
	}
	public void setPublishedYear(int publishedYear) {
		PublishedYear = publishedYear;
	}
	public Double getPrice() {
		return Price;
	}

	public void setPrice(Double price) {
		this.Price = price;
	}



}
