import java.util.Scanner;
import java.io.Console;
import java.sql.*;

public class DockerConnectMySQL {
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://10.0.10.3:3306/db";

   static final String USER = "WTruskowski";
   static final String PASS = "1234";
   
   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   try{
      Class.forName("com.mysql.jdbc.Driver");

      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);

      stmt = conn.createStatement();
	  
	  String sqlDrop;
	  sqlDrop = "DROP TABLE IF EXISTS  table_name;";
	  stmt.executeUpdate(sqlDrop);
	  
	  String sqlCreate;
	  sqlCreate = "CREATE TABLE table_name (id int AUTO_INCREMENT,name varchar(255),kind varchar(255), PRIMARY KEY(id));";
	  stmt.executeUpdate(sqlCreate);
	  
	  String sqlInsert;
	  sqlInsert = "INSERT INTO table_name (id, name, kind)"+
		"VALUES (null, 'Reksio', 'pies'),"+
		"(null,'Klakier','kot');";
	  stmt.executeUpdate(sqlInsert);
	  
	  String sqlSelect;
      sqlSelect = "SELECT * FROM table_name";
	  
	  Scanner input = new Scanner(System.in);
	  
	  boolean loop = true;
	  while(loop){
		  System.out.println("1 aby wyswietlić");
		  System.out.println("2 aby dodac");
		  System.out.println("3 aby zakonczyc");
		  
		  int key = input.nextInt();
		  
		  switch(key){
			case 1:
				ResultSet rs = stmt.executeQuery(sqlSelect);

				  while(rs.next()){
					 int id  = rs.getInt("id");
					 String name = rs.getString("name");
					 String kind = rs.getString("kind");

					 System.out.println("ID: " + id+", Name: " + name+", Kind: " + kind);
				  }
				  rs.close();
				break;
			case 2:

				System.out.println("podaj gatunek");
				String kindI = input.next();
				System.out.println("podaj Imie");
				String nameI = input.next();
				sqlInsert = "INSERT INTO table_name "+
					"VALUES (null,'"+nameI+"','"+kindI+"')";
				stmt.executeUpdate(sqlInsert);
				break;
			case 3:
				loop=false;
				break;
			default:
				System.out.println("dupa");
		  }
	  }
      
      stmt.close();
      conn.close();
   }catch(SQLException se){
      se.printStackTrace();
   }catch(Exception e){
      e.printStackTrace();
   }finally{
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }
   }
 }
}
