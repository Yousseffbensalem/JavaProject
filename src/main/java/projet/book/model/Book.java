package projet.book.model;

public class Book {
	
	
	private Long id;
	private String title;
	private String author;
	private Double price;
	private int published_year;

	
	

	
	public Book(Long id, String title, String author, Double price, int publishedyear) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.price=price;
		this.published_year = publishedyear;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getPublishedYear() {
		return published_year;
	}
	public void setPublishedYear(int publishedYear) {
		published_year = publishedYear;
	}
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}



}
