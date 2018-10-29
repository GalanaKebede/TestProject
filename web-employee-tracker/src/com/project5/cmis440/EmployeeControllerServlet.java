package com.project5.cmis440;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.servlet.RequestDispatcher;


/**
 * Servlet implementation class EmployeeControllerServlet
 */
@WebServlet("/EmployeeControllerServlet")
public class EmployeeControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EmployeeDAO employeeDAO;
	
	@Resource(name="jdbc/web_employee_tracker")
	private DataSource dataSource;

	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		//create employee dao .... and pass in the conn pool / datasource
		try {
			employeeDAO = new EmployeeDAO(dataSource);
		}
		catch(Exception exc) {
			throw new ServletException(exc);
		}
	}
	


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");
			
			// if the command is missing, then default to listing students
			if (theCommand == null) {
				theCommand = "LIST";
			}
			
			// route to the appropriate method
			switch (theCommand) {
			
			case "LIST":
				listEmployees(request, response);
				break;
				
			case "ADD":
				addEmployee(request, response);
				break;
				
			case "LOAD":
				loadEmployee(request, response);
				break;
				
			case "UPDATE":
				updateEmployee(request, response);
				break;
			
			case "DELETE":
				deleteEmployee(request, response);
				break;
				
			default:
				listEmployees(request, response);
			}
				
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
		
	}

	private void deleteEmployee(HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		// read employee id from form data
		String theEmployeeId = request.getParameter("employeeId");
		
		// delete employee from database
	employeeDAO.deleteEmployee(theEmployeeId);
		
		// send them back to "list employees" page
		listEmployees(request, response);
	}

	private void updateEmployee(HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		// read employee info from form data
		int id = Integer.parseInt(request.getParameter("employeeId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		int age = Integer.parseInt(request.getParameter("age"));
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		
		// create a new student object
		Employee theEmployee = new Employee (id, firstName, lastName,gender,age, email);
		
		// perform update on database
		employeeDAO.updateEmployee(theEmployee);
		
		// send them back to the "list students" page
		listEmployees(request, response);
		
	}

	private void loadEmployee(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {

		// read student id from form data
		String theEmployeeId = request.getParameter("employeeId");
		
		// get student from database (db util)
		Employee theEmployee = employeeDAO.getEmployee(theEmployeeId);
		
		// place student in the request attribute
		request.setAttribute("THE_EMPLOYEE", theEmployee);
		
		// send to jsp page: update-student-form.jsp
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/update-employee-form.jsp");
		dispatcher.forward(request, response);		
	}

	private void addEmployee(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read employee info from form data
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String gender = request.getParameter("gender");
		int age = Integer.parseInt(request.getParameter("age"));
		String email = request.getParameter("email");		
		
		// create a new employee object
		Employee theEmployee = new Employee (firstName, lastName,gender,age, email);
		
		// add the employee to the database
		employeeDAO.addEmployee(theEmployee);
				
		// send back to main page (the student list)
		listEmployees(request, response);
	}

	private void listEmployees(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {

		// get students from db util
		List<Employee> employees = employeeDAO.getEmployees();
		
		// add students to the request
		request.setAttribute("EMPLOYEE_LIST", employees);
				
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-employees.jsp");
		dispatcher.forward(request, response);
	}

}
