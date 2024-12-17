package projet.book.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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
			String query = "SELECT id, title, author FROM books"; 
			ResultSet resultSet = statement.executeQuery(query); 
			while (resultSet.next()) { 
				Long id = resultSet.getLong("id");
				String title = resultSet.getString("title"); 
				String author = resultSet.getString("author"); 
				Double price= resultSet.getDouble("price");
				int publishedyear=resultSet.getInt("publishedyear");
				
				books.add(new Book(id, title, author,price,publishedyear)); } 
			return Response.ok(books).build(); 
			} catch (SQLException e) { e.printStackTrace(); 
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error").build(); } }
		
	}

	
	
	
	
	

