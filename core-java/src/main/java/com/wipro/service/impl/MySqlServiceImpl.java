package com.wipro.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.wipro.model.Book;
import com.wipro.service.DatabaseConnection;
import com.wipro.service.MySqlService;

public class MySqlServiceImpl implements MySqlService {

	private static Connection connection;

	static {
		try {
			connection = DatabaseConnection.getConnection();
			initDb();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void initDb() throws SQLException {
		try {

			// Clear data from the books table
			clearTable("books");

			System.out.println("Table data cleared successfully.");

			System.out.println("Generating sample data");
			int[] bookNumbers = { 12, 45, 35, 78 };
			String[] bookNames = { "Core Java", "Adventure Book", "Mt.Everest Hiking", "Spring Framework" };
			String[] authorNames = { "Debasis Samantha", "Tom Holland", "Robert Downey", "Arnold" };
			int[] copiesAvailable = { 150, 120, 200, 180 };
			String[] categories = { "Technology", "Adventure", "Travel", "Technology" };

			String booksInsertQuery = "INSERT INTO books (book_id, book_name, author, copies_available, category) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement booksInsertStatement = connection.prepareStatement(booksInsertQuery);
			for (int i = 0; i < bookNumbers.length; i++) {
				booksInsertStatement.setInt(1, bookNumbers[i]);
				booksInsertStatement.setString(2, bookNames[i]);
				booksInsertStatement.setString(3, authorNames[i]);
				booksInsertStatement.setInt(4, copiesAvailable[i]);
				booksInsertStatement.setString(5, categories[i]);
				booksInsertStatement.addBatch();
			}
			booksInsertStatement.executeBatch();

			// Closing resources
			booksInsertStatement.close();
			// connection.close();

			System.out.println("Sample Books inserted successfully.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Method to clear data from a table
	public static void clearTable(String tableName) throws SQLException {
		String deleteQuery = "DELETE FROM " + tableName;
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate(deleteQuery);
		}
	}

	@Override
	public List<Book> findAllBooks() {

		List<Book> books = new ArrayList<>();

		String query = "select * from books";

		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

			while (resultSet.next()) {

				Book book = new Book();
				book.setBookId(resultSet.getInt("book_id"));
				book.setBookName(resultSet.getString("book_name"));
				book.setAuthor(resultSet.getString("author"));
				book.setCopiesAvailable(resultSet.getInt("copies_available"));
				book.setCategory(resultSet.getString("category"));

				books.add(book);

			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return books;
	}

	@Override
	public void addBook(Book book) {

		try (PreparedStatement query = connection.prepareStatement("INSERT INTO books VALUES (?, ?, ?, ?, ?)");) {
			query.setInt(1, book.getBookId());
			query.setString(2, book.getBookName());
			query.setString(3, book.getAuthor());
			query.setInt(4, book.getCopiesAvailable());
			query.setString(5, book.getCategory());

			query.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void deleteBook(int bookId) {

		try (PreparedStatement query = connection.prepareStatement("DELETE FROM books WHERE book_id = ?");) {
			query.setInt(1, bookId);
			query.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public Book getBookById(int bookId) {
		Book book = new Book();

		try (PreparedStatement query = connection.prepareStatement("select * FROM books WHERE book_id = ?");) {
			query.setInt(1, bookId);
			ResultSet resultSet = query.executeQuery();

			while (resultSet.next()) {
				book.setBookId(resultSet.getInt("book_id"));
				book.setBookName(resultSet.getString("book_name"));
				book.setAuthor(resultSet.getString("author"));
				book.setCopiesAvailable(resultSet.getInt("copies_available"));
				book.setCategory(resultSet.getString("category"));
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return book;
	}

	@Override
	public void updateBook(Book book) {
		
		try (PreparedStatement query = connection.prepareStatement("update books set book_name=? , author=? , copies_available=? , "
				+ "category=? where book_id =? ");) {
			
			query.setString(1, book.getBookName());
			query.setString(2, book.getAuthor());
			query.setInt(3, book.getCopiesAvailable());
			query.setString(4, book.getCategory());
			
			query.setInt(5, book.getBookId());

			query.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
