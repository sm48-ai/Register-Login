package in.sp.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/loginForm")
public class Login extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		String myemail=req.getParameter("email1");
		String mypass=req.getParameter("pass1");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/yt_demo";
			Connection con=DriverManager.getConnection(url, "root", "8576");
			
			PreparedStatement ps=con.prepareStatement("select * from register where email=? and password=?");
			ps.setString(1, myemail);
			ps.setString(2, mypass);
			ResultSet rs= ps.executeQuery();
			if(rs.next()) {
				//out.println("<h2 style='color:green'>Welcome You login successfully</h2>");
				
				HttpSession session=req.getSession();
				session.setAttribute("session_name", rs.getString("name"));
				
				RequestDispatcher rd=req.getRequestDispatcher("/profile.jsp");
				rd.include(req, resp);
				
			}else {
				
				out.println("<h2 style='color:red'>Login id and Password Didnt match</h2>");
				RequestDispatcher rd=req.getRequestDispatcher("login.jsp");
				rd.include(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			out.println("<h2 style='color:red'>"+e.getMessage()+"Login id and Password Didnt match</h2>");
			RequestDispatcher rd=req.getRequestDispatcher("login.jsp");
			rd.include(req, resp);
		}
	}

}
