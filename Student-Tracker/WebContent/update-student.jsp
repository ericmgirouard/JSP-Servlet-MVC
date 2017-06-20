<! DOCTYPE html>
<html>

<head>
	<title>Student Tracker App</title>
	
	<link type="text/css" rel="stylesheet" href="CSS/style.css">
	<link type="text/css" rel="stylesheet" href="CSS/add-student-style.css">	
	
</head>


	<div id="wrapper">
		<div id = "header">
			<h2>Foobar University</h2>
		</div>
	</div>
	
	
	<div id="container">
		<h3>Update Student</h3>
		
		<form action="ListStudents" method="GET">
		
			<input type="hidden" name="command" value="UPDATE" />
			<input type="hidden" name="StudentID" value="${Student.id}" />
			
			<table>
				<tbody>
					<tr>
						<td><label>First name:</label></td>
						<td><input type="text" name="firstName" 
								value = "${Student.firstName}"/></td>
						
					</tr>

					<tr>
						<td><label>Last name:</label></td>
						<td><input type="text" name="lastName" 
								value = "${Student.lastName}"/></td>
					</tr>

					<tr>
						<td><label>Email:</label></td>
						<td><input type="text" name="email" 
								value = "${Student.email}"/></td>
					</tr>
					
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Save" class="save" /></td>
					</tr>
					
				</tbody>
			</table>
		</form>
		
		<div style="clear: both;"></div>
		
		<p>
			<a href="ListStudents">Back to List</a>
		</p>
	</div>


<body>





</body>
</html>