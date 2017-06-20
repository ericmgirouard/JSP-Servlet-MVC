package com.learn.web.jdbc;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


@WebServlet("/ListStudents")
public class ListStudents extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private StudentDBUtil studentDBUtil;


    @Resource(name="jdbc/web_student_tracker")
    private DataSource dataSource;
   
    
	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			studentDBUtil = new StudentDBUtil(dataSource);
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//read the command parameter: 
			String command = request.getParameter("command");
			if (command == null) command = "LIST";
			//route to appropriate method
			switch (command) {
			case "LIST":
				listStudents(request,response);
				break;
				
			case "ADD":
				addStudent(request,response);
				break;
				
			case "LOAD":
				loadStudent(request,response);
				break;
			
			case "UPDATE":
				updateStudent(request,response);
				break;
			
			case "DELETE":
				deleteStudent(request,response);
				break;
				
			default:
				listStudents(request,response);
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}


	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		 * fetch student ID from request object
		 * call delete method in DBUtil with that student ID
		 */
		int studentID = Integer.parseInt(request.getParameter("StudentID"));
		studentDBUtil.deleteStudent(studentID);
		listStudents(request,response);
	}



	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//fetch the student's fields from request object, generate SQL update statement with the new/old values. execute it
		int id = Integer.parseInt(request.getParameter("StudentID"));
		String first = request.getParameter("firstName");
		String last = request.getParameter("lastName");
		String email = request.getParameter("email");
		Student student = new Student (id,first,last,email);
		studentDBUtil.updateStudent(student);
		listStudents(request,response);
	}



	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		//Pre-load current student info Into a JSP form page where they can update the records later
		//fetch student info 
		int StudentID = Integer.parseInt(request.getParameter("StudentID"));
		Student student = studentDBUtil.loadStudent(StudentID);
		//pass data to new JSP page
		RequestDispatcher dispatch =  request.getRequestDispatcher("/update-student.jsp");
		request.setAttribute("Student", student);
		dispatch.forward(request,response);
	}



	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//Gather info about the student
		String firstname = request.getParameter("firstName");
		String lastname = request.getParameter("lastName");
		String email = request.getParameter("email");
		//create student object
		Student newbie = new Student(firstname,lastname,email);
		//add the student to the DB via Util
		studentDBUtil.addStudent(newbie);
		//return to list students
		listStudents(request,response);
	}



	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html");
		
		List<Student> roster = studentDBUtil.getStudents();
		
		if (roster.size() != 0) {
			RequestDispatcher RD = request.getRequestDispatcher("/view_students.jsp");
			request.setAttribute("Roster", roster);
			RD.forward(request, response);
		} else {
			//no data to display, call a JSP page to let them know that, and create new students if they like
			RequestDispatcher RD = request.getRequestDispatcher("/no_students.jsp");
			RD.forward(request, response);
		}
		
	}

}
