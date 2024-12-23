package projet.book.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import projet.book.connection.DatabaseConnection;
import projet.book.model.Book;

@Path("/books")
public class BookController {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON) 
	public Response getAllBooks() { 
		List<Book> books = new ArrayList<>(); 
		try (Connection connection = DatabaseConnection.getConnection(); 
				Statement statement = connection.createStatement()) 
		{ 
			String query = "SELECT id, title, author , price,published_year FROM books"; 
			ResultSet resultSet = statement.executeQuery(query); 
			while (resultSet.next()) { 
				Long id = resultSet.getLong("id");
				String title = resultSet.getString("title"); 
				String author = resultSet.getString("author"); 
				Double price= resultSet.getDouble("price");
				int published_year=resultSet.getInt("published_year");
				
				books.add(new Book(id, title, author,price,published_year)); } 
			return Response.ok(books).build(); 
			} catch (SQLException e) { e.printStackTrace(); 
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error").build(); } }
	


	    @GET
	    @Path("/author")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response getBookByAuthor(@QueryParam("author") String author) {
	        List<Book> books = new ArrayList<>();
	        String query = "SELECT id, title, author, price, published_year FROM books WHERE author = ?";

	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	             
	            preparedStatement.setString(1, author); // Bind the title parameter to prevent SQL injection
	            
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                while (resultSet.next()) {
	                    Long id = resultSet.getLong("id");
	                    String title = resultSet.getString("title");
	                    String bookauthor = resultSet.getString("author");
	                    Double price = resultSet.getDouble("price");
	                    int publishedYear = resultSet.getInt("published_year");

	                    books.add(new Book(id, title, bookauthor, price, publishedYear));
	                }
	            }
	            return Response.ok(books).build(); // Return the list of books
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                           .entity("Database error occurred: " + e.getMessage())
	                           .build();
	        }
	    }
	    @GET
	    @Path("/author")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response getBookByTile(@QueryParam("title") String title) {
	        List<Book> books = new ArrayList<>();
	        String query = "SELECT id, title, author, price, published_year FROM books WHERE title = ?";

	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	             
	            preparedStatement.setString(1, title); // Bind the title parameter to prevent SQL injection
	            
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                while (resultSet.next()) {
	                    Long id = resultSet.getLong("id");
	                    String booktitle = resultSet.getString("title");
	                    String author = resultSet.getString("author");
	                    Double price = resultSet.getDouble("price");
	                    int publishedYear = resultSet.getInt("published_year");

	                    books.add(new Book(id, booktitle, author, price, publishedYear));
	                }
	            }
	            return Response.ok(books).build(); // Return the list of books
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                           .entity("Database error occurred: " + e.getMessage())
	                           .build();
	        }
	    }
	    
	    
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	


	

	
	
	
	
	

