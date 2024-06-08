package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial") 
// class with http-servlet extended
public class feedbackservlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) //  'POST' method with request and response as parameter
			throws ServletException,IOException {     						// also with exception
		
		String uname = (String)req.getParameter("name");
		String bname = (String)req.getParameter("bkname");
		String msg = (String)req.getParameter("message");			// Getting the data form the html-page using method 'POST'
		
		//System.out.println("username= "+uname);	
		//System.out.println("Bookname= "+bname);	
		//System.out.println("Feedback= "+msg);						//print statement to check the data are stored 
		
		String url="jdbc:mysql://localhost:3306/feedback";           // URL for connect the mysql with jdbc
		String user="root";											// username of tthe database 
		String password="krbs01";									// password to connect the database
		
		Connection conn= null;										// variable for database connection
		PreparedStatement pstmt=null;								// variable accessed in the try block
		
		if(uname!="" && bname!="" && msg!="")						// check the data is null or Not  
		{
			try {													//data is not null, The function get into database connection to store
				Class.forName("com.mysql.cj.jdbc.Driver");			//load the driver class
																		
				conn=DriverManager.getConnection(url, user, password);	//establish a connection
				
				
				String sql="INSERT INTO feedbackdetails(NAME,BOOK_NAME,FEEDBACK) VALUES(?,?,?)";		//prepare the Sql Query to store
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, uname);
				pstmt.setString(2, bname);
				pstmt.setString(3, msg);
				
				
				
				int rs=pstmt.executeUpdate();							// Execute the SQL statement
				res.setContentType("text/html");						// Respond with a success message 
				if(rs==1) {
					res.sendRedirect("success.html");					// redirecting to the 'success.html' page
				}else {
					res.sendRedirect("unsuccess.html");					// redirecting to the 'unsuccess.html' page when error occur during the store process
				}
				
			}catch(ClassNotFoundException | SQLException e) {				// Exception handling 
				e.printStackTrace();
				throw new ServletException("DataBase Error: "+e.getMessage());	// Printing the database Error 
			}
		
		}else {																	// any one data is null it redirected to "Unsuccess.html' page 
			
			res.sendRedirect("unsuccess.html");
			
		}
	}


}
