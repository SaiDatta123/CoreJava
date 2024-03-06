package com.wipro.controller;

import java.io.IOException;

import com.wipro.model.Book;
import com.wipro.service.MySqlService;
import com.wipro.service.impl.MySqlServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/BooksList")
public class BookListServlet extends HttpServlet {
	
	private static final long serialVersionUID = 4824519493477840594L;
	private MySqlService mySqlService;

	@Override
	public void init() throws ServletException {
		super.init();
		mySqlService = new MySqlServiceImpl();

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("books", mySqlService.findAllBooks());
		req.getRequestDispatcher("book_list.jsp")
		.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if(action.equalsIgnoreCase("delete")) {
			mySqlService.deleteBook(Integer.parseInt(req.getParameter("id")));
			req.getRequestDispatcher("index.jsp").forward(req, resp);
		}
		if(action.equalsIgnoreCase("edit")) {
			Book book = mySqlService.getBookById(Integer.parseInt(req.getParameter("id")));
			req.setAttribute("book", book);
			req.getRequestDispatcher("edit.jsp").forward(req, resp);
		}
		if(action.equalsIgnoreCase("update")) {
			Book book = new Book();
			System.out.println("IN UPDATE ACTION PAGE");
			book.setBookId(Integer.parseInt(req.getParameter("bid")));
			book.setBookName(req.getParameter("bname"));
			book.setAuthor(req.getParameter("bauthor"));
			book.setCopiesAvailable(Integer.parseInt(req.getParameter("bcopies")));
			book.setCategory(req.getParameter("bcategory"));
			System.out.println("BOOK FROM UPDATE SCREEN : " + book.toString());
			mySqlService.updateBook(book);
			req.getRequestDispatcher("update.jsp").forward(req, resp);
		}
		if(action.equalsIgnoreCase("add")) {
			Book book = new Book();
			book.setBookId(Integer.parseInt(req.getParameter("bid")));
			book.setBookName(req.getParameter("bname"));
			book.setAuthor(req.getParameter("bauthor"));
			book.setCopiesAvailable(Integer.parseInt(req.getParameter("bcopies")));
			book.setCategory(req.getParameter("bcategory"));
			
			mySqlService.addBook(book);
			req.getRequestDispatcher("add.jsp").forward(req, resp);
		}
		
	}
}
