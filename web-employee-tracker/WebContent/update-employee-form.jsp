<!DOCTYPE html>
<html>

<head>
	<title>Update Employee</title>

	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/add-student-style.css">	
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>CMIS440 COMPANY</h2>
		</div>
	</div>
	
	<div id="container">
		<h3>Update Employee</h3>
		
		<form action="EmployeeControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="UPDATE" />

			<input type="hidden" name="employeeId" value="${THE_EMPLOYEE.id}" />
			
			<table>
				<tbody>
					<tr>
						<td><label>First name:</label></td>
						<td><input type="text" name="firstName" 
								   value="${THE_EMPLOYEE.firstName}" /></td>
					</tr>

					<tr>
						<td><label>Last name:</label></td>
						<td><input type="text" name="lastName" 
								   value="${THE_EMPLOYEE.lastName}" /></td>
					</tr>
					<tr>
						<td><label>Gender:</label></td>
						<td><input type="text" name="gender" 
								   value="${THE_EMPLOYEE.lastName}" /></td>
					</tr>
					<tr>
						<td><label>Age:</label></td>
						<td><input type="text" name="age" 
								   value="${THE_EMPLOYEE.age}" /></td>
					</tr>
					

					<tr>
						<td><label>Email:</label></td>
						<td><input type="text" name="email" 
								   value="${THE_STUDENT.email}" /></td>
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
			<a href="EmployeeControllerServlet">Back to List</a>
		</p>
	</div>
</body>

</html>











