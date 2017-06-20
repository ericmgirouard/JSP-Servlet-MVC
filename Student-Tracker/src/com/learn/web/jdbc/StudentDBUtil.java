package com.learn.web.jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.sql.DataSource;


//The job of this class is to Connect to the DB to retreive Student info on request and store them as a list of Student Objects
public class StudentDBUtil {
	private DataSource dataSource;
		
	public StudentDBUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}

	public List<Student> getStudents() throws Exception {
		
		List<Student> roster = new ArrayList<>();
		//Step 1: set up metadata for DB
		Connection DB = null;
		Statement statement = null;
		ResultSet results = null;
		
		try {
			//Step 2: generate SQL DB connection
			DB = dataSource.getConnection();
			//Step 3: Create SQL Query
			String SQL = "SELECT * FROM student ORDER BY last_name";
			//Step 4: Execute SQL Query
			statement = DB.createStatement();
			results = statement.executeQuery(SQL);
			//Step 5: process results and enter items into list
			while (results.next()) {
				roster.add(
						new Student(
								results.getInt("id"),
								results.getString("first_name"),
								results.getString("last_name"),
								results.getString("email")
								));
			}
			
		} finally {
			close(DB,statement,results);
		} 
		
		//Step 6: Return List
		return roster;
	}

	private void close(Connection dB, Statement statement, ResultSet results) {
		try {
			if (results != null) results.close();
			if (statement != null) statement.close();
			if (dB != null) dB.close();//makes the connection available for someone else to use
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//end close()

	public void addStudent(Student newbie) throws Exception {

		//open connection to DB
		Connection DB = null;
		PreparedStatement statement = null;
		try {
			DB = dataSource.getConnection();
			
			String SQL  = "insert into student "
					   + "(first_name, last_name, email) "
					   + "values (?, ?, ?)";
			
			statement = DB.prepareStatement(SQL);
			
			statement.setString(1, newbie.getFirstName());
			statement.setString(2, newbie.getLastName());
			statement.setString(3, newbie.getEmail());
			
			statement.execute();
			
		} finally {
			close(DB, statement,null);
		}
		
	}




	public Student loadStudent(int studentID) throws SQLException {
		//fetch student with this ID from the DB, create a student object and return it
		Connection DB = null;
		Statement statement = null;
		ResultSet results = null;
		Student student = null;
		try {
			DB = dataSource.getConnection();
			statement = DB.createStatement();
			String sql = "SELECT * FROM student WHERE id = " + studentID + "";
			results = statement.executeQuery(sql);
			while (results.next()) {
				student = new Student(
						studentID,
						results.getString("first_name"),
						results.getString("last_name"),
						results.getString("email"));
				
			}
			
		return student;	
		
		} finally {
			close(DB,statement,results);
		}
	}

	public void updateStudent(Student student) throws Exception {
		Connection DB = null;
		PreparedStatement statement = null;
		try {
			DB = dataSource.getConnection();
			String sql = "UPDATE student "
					+ "	SET first_name = ?, last_name = ?, email = ? "
					+ " WHERE id = ?";
					
			statement = DB.prepareStatement(sql);
			statement.setString(1, student.getFirstName());
			statement.setString(2, student.getLastName());
			statement.setString(3, student.getEmail());
			statement.setInt(4, student.getId());
			statement.execute();
			
		} finally {
			close(DB,statement,null);
		}
	}

	public void deleteStudent(int studentID) throws Exception {
		/*
		 * set up connection/prepared statement
		 * generate sql statement
		 * execute sql
		 * close connections
		 */
		Connection DB = null;
		PreparedStatement statement = null;
		
		try {
			DB= dataSource.getConnection();
			String sql = "DELETE FROM student WHERE id = ?";
			statement = DB.prepareStatement(sql);
			statement.setInt(1, studentID);
			statement.execute();
		} finally {
			close(DB,statement,null);
		}
	}

}
