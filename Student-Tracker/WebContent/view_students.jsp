<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<! DOCTYPE html>
<html>


<head>
	<title>Student Tracker App</title>
	
	<link type="text/css" rel="stylesheet" href="CSS/style.css">
</head>


<body>

	<div id="wrapper">
		<div id = "header">
			<h2>Foobar University</h2>
		</div>
	</div>
	
	
	
	<div id="container">
		<div id = "content">
		
		<!-- add new student button -->
		
			<!-- put new button: Add Student -->
			
			<input type="button" value="Add Student" 
				   onclick="window.location.href='add-student-form.jsp'; return false;"
				   class="add-student-button"
			/>
			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>
				<c:forEach var="Students" items="${Roster}">
				
				<!-- Set up an Update link for each student -->
				<c:url var="tempStudent" value="ListStudents">
					<c:param name="command" value="LOAD"/>
					<c:param name="StudentID" value ="${Students.id}"/>
				</c:url>
				
				<!-- Set up a Delete link for each student -->
				<c:url var="DelStudent" value="ListStudents">
					<c:param name="command" value="DELETE"/>
					<c:param name="StudentID" value ="${Students.id}"/>
				</c:url>
				
					<tr>
						<td>${Students.firstName}</td>
						<td>${Students.lastName}</td>
						<td>${Students.email}</td>
						<td> <a href="${tempStudent}">Update</a> | <a href="${DelStudent}">Delete</a> </td>
					
					</tr>
				</c:forEach>
				</table>
		</div>
	</div>




</body></html>