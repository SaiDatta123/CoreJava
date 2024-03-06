<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>Books List</title>
</head>
<body>

<h3>Update Book</h3>
    <form action="BooksList" method="post">
    	Book Id : <input type="number" value="${book.bookId}" disabled="disabled"><br>
    	Book Name : <input type="text" name ="bname" value="${book.bookName}" ><br>
    	Author : <input type="text" name ="bauthor" value="${book.author}" ><br>
    	Copies Available : <input type="number" name ="bcopies" value="${book.copiesAvailable}"><br>
    	Category: <input type="text" name ="bcategory" value="${book.category}"><br>
    	<input type="hidden" name ="bid" value="${book.bookId}">
    	<input type="hidden" name ="action" value="update"><br>
    	<button type="submit">Update</button>
	</form>


</body>
</html>
