package p2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BatchInsertion1{
	
   public static void main(String[]arg) throws SQLException {
	try {
		Class.forName("org.postgresql.Driver");
		Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/database1","postgres","root");
		con.setAutoCommit(false);
		PreparedStatement ps =con.prepareStatement("INSERT INTO tableoo values(?,?,?)");
		try {
		ps.setInt(1, 15);
		ps.setString(2,"arun");
		ps.setString(3,"mech");
		ps.addBatch();
		
		ps.setInt(1, 16);
		ps.setString(2,"kumar");
		ps.setString(3,"btech");
		ps.addBatch();
		
		ps.setInt(1, 17);
		ps.setString(2,"ben");
		ps.setString(3,"Biotech");
		ps.addBatch();
		
		ps.setInt(1, 18);
		ps.setString(2,"thiyagu");
		ps.setString(3,"cse");
		ps.addBatch();
		
		ps.executeBatch();
		
		con.commit();
		System.out.println("all add da mass poo");
		}catch(Exception e) {
		  con.rollback();
		  System.out.println("worng");
		}	finally {
			con.close();
		}
		
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

   }

}
