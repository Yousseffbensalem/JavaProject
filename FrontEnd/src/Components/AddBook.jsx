import { TextField, Button, Box } from "@mui/material";
import { addBook } from "./api";

function AddBookForm() {
  const onSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const book = Object.fromEntries(formData.entries());
   await addBook(book);
  };
  return (
    <Box
      component="form"
      sx={{
        display: "flex",
        flexDirection: "column",
        gap: 2,
        width: "50%",
        margin: "0 auto",
      }}
      onSubmit={onSubmit}
    >
      <TextField label="Title" name="title" required />
      <TextField label="Author" name="author" required />
      <TextField label="Price" name="price" type="number" required />
      <TextField label="Year" name="year" type="number" required />
      <Button type="submit" variant="contained" color="primary">
        Add Book
      </Button>
    </Box>
  );
}

export default AddBookForm;
