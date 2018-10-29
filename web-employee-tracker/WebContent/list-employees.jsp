<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>

<head>
<title>Employee Tracker App</title>

<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>CMIS440 COMPANY</h2>
		</div>
	</div>

	<div id="container">

		<div id="content">

			<!-- put new button: Add employee -->

			<input type="button" value="Add Employee"
				onclick="window.location.href='add-employee-form.jsp'; return false;"
				class="add-employee-button" />

			<table>

				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Gender</th>
					<th>age</th>
					<th>Action</th>
				</tr>

				<c:forEach var="tempEmployee" items="${EMPLOYEE_LIST}">

					<!-- set up a link for each employee -->
					<c:url var="tempLink" value="EmployeeControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="employeeId" value="${tempEmployee.id}" />
					</c:url>

					<!--  set up a link to delete a student -->
					<c:url var="deleteLink" value="EmployeeControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="employeeId" value="${tempEmployee.id}" />
					</c:url>

					<tr>
						<td>${tempEmployee.firstName}</td>
						<td>${tempEmployee.lastName}</td>
						<td>${tempEmployee.gender}</td>
						<td>${tempEmployee.age}</td>
						<td>${tempEmployee.email}</td>
						<td><a href="${tempLink}">Update</a> | <a
							href="${deleteLink}"
							onclick="if (!(confirm('Are you sure you want to delete this employee?'))) return false">
								Delete</a></td>
					</tr>

				</c:forEach>

			</table>

		</div>

	</div>
</body>


</html>








