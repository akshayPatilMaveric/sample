package sample;
import java.util.*;
import java.util.stream.IntStream;
import java.sql.*;

public class connectionClass {
	Connection c = null;
	Statement sm=null;
	ResultSet rs=null,rc=null;
	ResultSetMetaData rsmd=null;
	
	//To Create a Database connection	
	public void createConnection() {		
	      try {
	    	 Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\akshaypa\\Desktop\\database\\sampledb");
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Opened database successfully");
	   }
	
	//To Close the Database connection
	public void closeCon() {
		if(c!=null) {
			try {
				c.close();
			}catch ( Exception e ) {
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		        System.exit(0);
		}
			System.out.println("Closed database successfully");
	}
	}
	
	//To convert 1D array to 2D array
	//taken from google
	public static String[][] conTo2DArray(String[] inputArray, int chunkSize){
		return IntStream.iterate(0, i -> i + chunkSize)
				.limit((int) Math.ceil((double) inputArray.length / chunkSize))
				.mapToObj(j -> Arrays.copyOfRange(inputArray, j, Math.min(inputArray.length, j + chunkSize)))
				.toArray(String[][]::new);
		}
	
	//Run The SQL query and store results
	public String[][] runStatement(String Query) {
		String[][] QueryResults=null; 
		int Row_count =0;
		int coloumn_count=0;
		List<String> list=new ArrayList<String>(); 
		try {			
			sm=c.createStatement();			
			rc=sm.executeQuery(Query);
			rsmd=rc.getMetaData();
			//Get the Row and Column count
			coloumn_count=rsmd.getColumnCount();
			while (rc.next()){
                Row_count = rc.getInt(1);
            }
			System.out.println("Row Count: "+Row_count+" Column count: "+coloumn_count);
			
			//Fetching the column Names and adding them to list:
			String columnNames[]=new String[coloumn_count];
			for(int i=0;i<coloumn_count;i++) {
				columnNames[i]=rsmd.getColumnName((i+1));
				list.add(columnNames[i]);
			}
			// 2nd result for the Query results
			rs=sm.executeQuery(Query);
			//Getting the Query Results
			while(rs.next()){
				int i=1;
				while(i<=coloumn_count) {
					list.add(rs.getString(i));
					i++;
			}}
			//Converting the list to String Array
			String[] resAr=list.toArray(new String[0]);
			//Converting the 1D array list to 2D list as per the Query output			
			QueryResults=conTo2DArray(resAr,coloumn_count);		
		}catch( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	        System.exit(0);
		}
		return QueryResults;
		
	}
	}


