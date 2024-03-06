<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1.0" />
    <title>Books List</title>
    <link
      href="css/bootstrap.min.css"
      rel="stylesheet"
      type="text/css" />
  </head>
  <body>
    <div class="d-block px-3 py-2 text-center text-bold skippy purple-200">
      <a
        href="#"
        class="text-white text-decoration-none">
        Library Management
      </a>
    </div>
    <div class="container">
      <table class="table table-success table-striped">
			  <td>
		      </td>
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">Book Id</th>
            <th scope="col">Book Name</th>
            <th scope="col">Author</th>
            <th scope="col">Copies Available</th>
            <th scope="col">Category</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach
            var="book"
            items="${books}"
            varStatus="theCount">
            <tr>
              <th scope="row">${theCount.count}</th>
              <td><c:out value="${book.bookId}" /></td>
              <td><c:out value="${book.bookName}" /></td>
              <td><c:out value="${book.author}" /></td>
              <td><c:out value="${book.copiesAvailable}" /></td>
              <td><c:out value="${book.category}" /></td>
              <td>
			  	<form action="BooksList" method="post">
			  	  <input type="hidden" name ="id" value="${book.bookId}">
			  	  <input type="hidden" name ="action" value="edit">
			  	  <button type="submit">Edit</button>
			  	</form>
		      </td>
			  <td>
			  	<form action="BooksList" method="post">
			  	  <input type="hidden" name ="id" value="${book.bookId}">
			  	  <input type="hidden" name ="action" value="delete">
			  	  <button type="submit">Delete</button>
			  	</form>
		      </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
    
    <h3>Add Book</h3>
    <form action="BooksList" method="post">
    	<input type="number" name ="bid" placeholder="Book Id" required="required"><br>
    	<input type="text" name ="bname" placeholder="Book Name" required="required"><br>
    	<input type="text" name ="bauthor" placeholder="Author" required="required"><br>
    	<input type="number" name ="bcopies" placeholder="Copies Available" required="required"><br>
    	<input type="text" name ="bcategory" placeholder="Category" required="required"><br>
    	<input type="hidden" name ="action" value="add">
    	<button type="submit">Add</button>
	</form>
   
  </body>
</html>
