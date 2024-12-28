package projet.book.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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
	    @Path("/title")
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
	    @POST
	    @Path("/add")
	    @Consumes(MediaType.APPLICATION_JSON) 
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response addBook(Book book) {
	        try {
	            
	            boolean isSaved = saveBookToDatabase(book);

	            if (isSaved) {
	               
	                return Response.status(Response.Status.CREATED).entity(book).build();
	            } else {
	                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                        .entity("Failed to save the book").build();
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                    .entity("An error occurred while processing the request").build();
	        }
	    }

	   
	    private boolean saveBookToDatabase(Book book) {
	        String query = "INSERT INTO books (title, author, price, published_year) VALUES (?, ?, ?, ?)";
	        
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

	            // Set the parameters for the PreparedStatement
	            preparedStatement.setString(1, book.getTitle());
	            preparedStatement.setString(2, book.getAuthor());
	            preparedStatement.setDouble(3, book.getPrice());
	            preparedStatement.setInt(4, book.getPublishedYear());

	            int rowsAffected = preparedStatement.executeUpdate();

	            return rowsAffected > 0; // If rows are affected, return true
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }

	    
	    
	    @POST
	    @Path("/add-multiple")
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response addBooks(List<Book> books) {
	        String query = "INSERT INTO books (title, author, price, published_year) VALUES (?, ?, ?, ?)";

	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

	            // Loop through each book and add it to the batch
	            for (Book book : books) {
	                preparedStatement.setString(1, book.getTitle());
	                preparedStatement.setString(2, book.getAuthor());
	                preparedStatement.setDouble(3, book.getPrice());
	                preparedStatement.setInt(4, book.getPublishedYear());

	                preparedStatement.addBatch();  // Add to batch
	            }

	            // Execute the batch of insert statements
	            int[] rowsAffected = preparedStatement.executeBatch();

	            // If all rows were inserted successfully, return true
	            for (int row : rowsAffected) {
	                if (row == Statement.EXECUTE_FAILED) {
	                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                                   .entity("Failed to save one or more books")
	                                   .build();
	                }
	            }

	            return Response.status(Response.Status.CREATED)
	                           .entity("All books added successfully!")
	                           .build();

	        } catch (SQLException e) {
	            e.printStackTrace();
	            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                           .entity("Error while adding books")
	                           .build();
	        }
	    }
	    @DELETE
	    @Path("/delete/{id}")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response deleteBook(@PathParam("id") Long id) {
	        String query = "DELETE FROM books WHERE id = ?";

	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

	            // Set the ID of the book to be deleted
	            preparedStatement.setLong(1, id);

	            int rowsAffected = preparedStatement.executeUpdate();

	            if (rowsAffected > 0) {
	                // Book was successfully deleted
	                return Response.status(Response.Status.OK)
	                               .entity("Book with ID " + id + " deleted successfully.")
	                               .build();
	            } else {
	                // No book found with the provided ID
	                return Response.status(Response.Status.NOT_FOUND)
	                               .entity("Book with ID " + id + " not found.")
	                               .build();
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                           .entity("An error occurred while deleting the book.")
	                           .build();
	        }
	    }

	    
	    
	    
	    
	    
	    
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	


	

	
	
	
	
	

