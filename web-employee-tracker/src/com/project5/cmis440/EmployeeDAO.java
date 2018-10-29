package com.project5.cmis440;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class EmployeeDAO {

	private DataSource dataSource;

	public EmployeeDAO(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
	public List<Employee> getEmployees() throws Exception {
		
		List<Employee> employees = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			// get a connection
			myConn = dataSource.getConnection();
			
			// create sql statement
			String sql = "select * from employee order by last_name";
			
			myStmt = myConn.createStatement();
			
			// execute query
			myRs = myStmt.executeQuery(sql);
			
			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String gender = myRs.getString("gender");
				int age = myRs.getInt("age");
				String email = myRs.getString("email");
				
				// create new employee object
				Employee tempEmployee = new Employee(id, firstName, lastName,gender,age, email);
				
				// add it to the list of employees
				employees.add(tempEmployee);				
			}
			
			return employees;		
		}
		finally {
			// close JDBC objects
			close(myConn, myStmt, myRs);
		}		
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

		try {
			if (myRs != null) {
				myRs.close();
			}
			
			if (myStmt != null) {
				myStmt.close();
			}
			
			if (myConn != null) {
				myConn.close();   // doesn't really close it ... just puts back in connection pool
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public void addEmployee(Employee theEmployee) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// get db connection
			myConn = dataSource.getConnection();
			
			// create sql for insert
			String sql = "insert into employee"
					   + "(first_name, last_name, gender,age, email) "
					   + "values (?, ?, ?,?,?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			// set the param values for the employee
			myStmt.setString(1, theEmployee.getFirstName());
			myStmt.setString(2, theEmployee.getLastName());
			myStmt.setString(3, theEmployee.getGender());
			myStmt.setInt(4, theEmployee.getAge());
			myStmt.setString(5, theEmployee.getEmail());
			
			// execute sql insert
			myStmt.execute();
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public Employee getEmployee(String theEmployeeId) throws Exception {

		Employee theEmployee = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int employeeId;
		
		try {
			// convert employee id to int
			employeeId = Integer.parseInt(theEmployeeId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to get selected student
			String sql = "select * from employee where id=?";
			
			// create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, employeeId);
			
			// execute statement
			myRs = myStmt.executeQuery();
			
			// retrieve data from result set row
			if (myRs.next()) {
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String gender = myRs.getString("gender");
				int age = myRs.getInt("age");
				String email = myRs.getString("email");
				
				
				// use the employeeId during construction
				theEmployee = new Employee(employeeId, firstName, lastName,gender,age, email);
			}
			else {
				throw new Exception("Could not find employee id: " + employeeId);
			}				
			
			return theEmployee;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	public void updateEmployee(Employee theEmployee) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();
			
			// create SQL update statement
			String sql = "update employee "
						+ "set first_name=?, last_name=?, gender=?,age=?,email=? "
						+ "where id=?";
			
			// prepare statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setString(1, theEmployee.getFirstName());
			myStmt.setString(2, theEmployee.getLastName());
			myStmt.setString(3, theEmployee.getGender());
			myStmt.setInt(4, theEmployee.getAge());
			myStmt.setString(5, theEmployee.getEmail());
			myStmt.setInt(6, theEmployee.getId());
		
			
			
			// execute SQL statement
			myStmt.execute();
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public void deleteEmployee(String theEmployeeId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// convert student id to int
			int employeeId = Integer.parseInt(theEmployeeId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to delete employee
			String sql = "delete from employee where id=?";
			
			// prepare statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, employeeId);
			
			// execute sql statement
			myStmt.execute();
		}
		finally {
			// clean up JDBC code
			close(myConn, myStmt, null);
		}	
	}
}















