import React from "react";
import { Box, Typography, Button, List, ListItem, ListItemText, IconButton } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import { useLocation, useNavigate } from "react-router-dom";

function Cart() {
  const location = useLocation();
  const navigate = useNavigate();
  const cartItems = location.state?.cartItems || []; // Get cart items from state

  const removeFromCart = (bookId) => {
    const updatedCart = cartItems.filter((item) => item.id !== bookId);
    navigate("/cart", { state: { cartItems: updatedCart } }); // Update state by navigating
  };

  const calculateTotal = () => {
    return cartItems.reduce((total, item) => total + item.price, 0).toFixed(2);
  };

  return (
    <Box
      sx={{
        padding: 3,
        minHeight: "100vh",
        backgroundColor: "#f9f9f9",
      }}
    >
      <Typography variant="h4" sx={{ marginBottom: 3, textAlign: "center" }}>
        Shopping Cart
      </Typography>
      <List>
        {cartItems && cartItems.length > 0 ? (
          cartItems.map((item) => (
            <ListItem
              key={item.id}
              sx={{
                display: "flex",
                justifyContent: "space-between",
                alignItems: "center",
                borderBottom: "1px solid #ddd",
                paddingBottom: 1,
                marginBottom: 1,
              }}
            >
              <ListItemText
                primary={item.title}
                secondary={`Price: $${item.price} | Author: ${item.author}`}
              />
             
              <IconButton color="error" onClick={() => removeFromCart(item.id)}>
                <DeleteIcon />
              </IconButton>
            </ListItem>
          ))
        ) : (
          <Typography sx={{ textAlign: "center", marginTop: 5 }}>
            No items in the cart.
          </Typography>
        )}
      </List>
      {cartItems && cartItems.length > 0 && (
        <Typography variant="h5" sx={{ marginTop: 3, textAlign: "center" }}>
          Total: ${calculateTotal()}
        </Typography>
      )}
      <Button variant="contained" color="primary" onClick={() => navigate("/client")} fullWidth sx={{ marginTop: 3 }}>
        Back to Store
      </Button>
    </Box>
  );
}

export default Cart;
