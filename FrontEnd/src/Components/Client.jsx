import React, { useEffect, useState } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Button,
  Paper,
  Box,
  Typography,
  IconButton,
  Badge,
} from "@mui/material";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import { useNavigate } from "react-router-dom";
import { getBooks } from "./api";

function Client() {
  const [books, setBooks] = useState([]);
  const [cart, setCart] = useState([]); // Cart state
  const navigate = useNavigate();

  useEffect(() => {
    const fetchBooks = async () => {
      try {
        const data = await getBooks(); // Fetch books from API
        setBooks(data);
      } catch (error) {
        console.error("Error fetching books:", error);
      }
    };
    fetchBooks();
  }, []);

  const addToCart = (book) => {
    // Prevent adding duplicates
    const isBookInCart = cart.find((item) => item.id === book.id);
    if (!isBookInCart) {
      setCart((prevCart) => [...prevCart, book]);
    }
  };

  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        padding: 2,
        minHeight: "100vh",
        backgroundColor: "#f9f9f9",
        marginTop: 2,
      }}
    >
      <Box sx={{ display: "flex", justifyContent: "space-between", width: "100%", marginBottom: 2 }}>
        <Typography variant="h4" sx={{ fontWeight: "bold" }}>
          Book Store
        </Typography>
        <IconButton onClick={() => navigate("/cart", { state: { cartItems: cart } })}>
          <Badge badgeContent={cart.length} color="secondary" showZero>
            <ShoppingCartIcon fontSize="large" />
          </Badge>
        </IconButton>
      </Box>

      <TableContainer
        component={Paper}
        sx={{
          width: "100%",
          margin: "0 auto",
          overflowX: "auto",
        }}
      >
        <Table>
          <TableHead>
            <TableRow>
              <TableCell><strong>Image</strong></TableCell>
              <TableCell><strong>Title</strong></TableCell>
              <TableCell><strong>Author</strong></TableCell>
              <TableCell><strong>Price</strong></TableCell>
              <TableCell><strong>Year</strong></TableCell>
              <TableCell><strong>Actions</strong></TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {books.length > 0 ? (
              books.map((book) => (
                <TableRow key={book.id}>
                  <TableCell>
                    <img
                      src={book.image_url}
                      alt={book.title}
                      style={{ width: "100px", height: "100px", objectFit: "cover" }}
                    />
                  </TableCell>
                  <TableCell>{book.title}</TableCell>
                  <TableCell>{book.author}</TableCell>
                  <TableCell>${book.price}</TableCell>
                  <TableCell>{book.publishedYear}</TableCell>
                  <TableCell>
                    <Button variant="contained" color="primary" onClick={() => addToCart(book)}>
                      Add to Cart
                    </Button>
                  </TableCell>
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell colSpan={6} align="center">
                  No books available.
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
}

export default Client;
