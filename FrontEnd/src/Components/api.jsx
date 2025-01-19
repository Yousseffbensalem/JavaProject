import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8082/book/webapi',
  headers: {
    'Content-Type': 'application/json',
  },
});
export const getBooks = async () => {
  try {
      const response = await api.get('/books');
      return response.data;
  } catch (error) {
      console.error('Error fetching books:', error);
      throw error;
  }
};
export const deleteBook = async (id) => {
  try {
    const response = await api.delete(`books/delete/${id}`); 
    return response.data;
  } catch (error) {
    console.error("Error deleting book:", error);
  }
};
export const addBook = async (book) => {
  try {
    const response = await api.post(`books/add`, book); 
    return response.data;
  } catch (error) {
    console.error("Error adding book:", error);
   
  }
};
export const updBook = async (id, book) => {
  try {
      const response = await api.put(`/books/update/${id}`, book);
      return response.data;
  } catch (error) {
      console.error("Error updating book:", error);
      throw error;
  }
};




export default api;
