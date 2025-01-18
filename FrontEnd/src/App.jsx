import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import { AppBar, Toolbar, Typography, Button, Container, Box } from "@mui/material";
import BookList from "./Components/BookList"; // Your existing component
import AddBook from "./Components/AddBook"; // Your existing component
import Client from './Components/Client';
import Cart from './Components/Cart';

function App() {
  return (
    <Router>
      <AppBar position="static" sx={{ backgroundColor: "#1976d2", width: "100%" }}>

      <Toolbar>
    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
    Menu
    </Typography>
    <Box sx={{ display: "flex", gap: 2 }}>
    <Button component={Link} to="/books" sx={{ color: "#fff" }}>
      Book List
    </Button>
    <Button component={Link} to="/add-book" sx={{ color: "#fff" }}>
      Add Book
    </Button>
    <Button component={Link} to="Client" sx={{ color: "#fff" }}>
     Client
    </Button>
  </Box>
</Toolbar>
</AppBar>
      <Container sx={{ marginTop: 0 }}>
        <Routes>
          <Route path="/books" element={<BookList />} />
          <Route path="/add-book" element={<AddBook />} />
          <Route path="Client" element={<Client/>}/>
          <Route path="/cart" element={<Cart />} />
        </Routes>
      </Container>
    </Router>
  );
}

export default App;



