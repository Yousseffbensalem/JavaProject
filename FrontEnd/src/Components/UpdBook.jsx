import React, { useEffect, useState } from 'react';
import { Box, TextField, Button } from '@mui/material';
import { useParams } from 'react-router-dom';
import { updBook, getBooks } from './api';

const UpdBook = () => {
    const { id } = useParams(); // Extract the book ID from the URL
    const [book, setBook] = useState({
        title: '',
        author: '',
        price: '',
        publishedYear: '',
        image_url: ''
    });

    // Fetch the existing book details by ID when the component mounts
    useEffect(() => {
        const fetchBookDetails = async () => {
            try {
                const books = await getBooks(); // Fetch all books
                const selectedBook = books.find((b) => b.id === parseInt(id));
                if (selectedBook) {
                    setBook(selectedBook);
                } else {
                    console.error("Book not found");
                }
            } catch (error) {
                console.error("Error fetching book details:", error);
            }
        };
        fetchBookDetails();
    }, [id]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const updatedBook = await updBook(id, book); // Call API to update book
            console.log('Updated book:', updatedBook);
            alert('Book updated successfully!');
        } catch (error) {
            console.error('Error updating book:', error);
            alert('Failed to update book.');
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setBook({
            ...book,
            [name]: value
        });
    };

    return (
        <Box
            component="form"
            sx={{
                '& .MuiTextField-root': { m: 1, width: '25ch' },
            }}
            noValidate
            autoComplete="off"
            onSubmit={handleSubmit}
        >
            <div>
                <TextField
                    required
                    id="title"
                    name="title"
                    label="Title"
                    value={book.title}
                    onChange={handleChange}
                />
                <TextField
                    required
                    id="author"
                    name="author"
                    label="Author"
                    value={book.author}
                    onChange={handleChange}
                />
                <TextField
                    required
                    id="price"
                    name="price"
                    label="Price"
                    value={book.price}
                    onChange={handleChange}
                />
                <TextField
                    required
                    id="publishedYear"
                    name="publishedYear"
                    label="Published Year"
                    value={book.publishedYear}
                    onChange={handleChange}
                />
                <TextField
                    required
                    id="image_url"
                    name="image_url"
                    label="Image URL"
                    value={book.image_url}
                    onChange={handleChange}
                />
            </div>
            <Button type="submit" variant="contained" color="primary">
                Update Book
            </Button>
        </Box>
    );
};

export default UpdBook;
