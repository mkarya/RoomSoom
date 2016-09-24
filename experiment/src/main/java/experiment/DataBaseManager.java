package experiment;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.google.api.client.util.DateTime;

public class DataBaseManager extends ColumnPosition{
	static private Connection conn = null;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	//static final String DB_URL = "jdbc:mysql://roomsoomdb.chov4e4ad1c2.us-west-2.rds.amazonaws.com:3306?";
	static final String DB_URL = "jdbc:mysql://52.38.202.203:3306";
	private boolean CoonectionDone = false;
	private LibraryClass lc;
	
	DataBaseManager() {
		lc = new LibraryClass();
	}
	
	
	public void setupConnection() {
		try{
			Class.forName(JDBC_DRIVER);
			
			if (this.CoonectionDone != true) {
				conn = DriverManager.getConnection(DB_URL,"mkarya","Ruby2016!");
				CoonectionDone = true;
			}
			
		}
		catch (SQLException se) {
			se.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getLinetoFeed() {
		String name  = lc.getTodayDataFileName();
		ArrayList<String> buff = null;
		File ff = new File(name);
		if( ff.exists() ) {
			buff = new ArrayList<String> ();
			BufferedReader bw = null;
			try {
				bw = new BufferedReader(new InputStreamReader( new FileInputStream(ff)));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String line;
			try {
				while ((line = bw.readLine()) != null) {
					buff.add(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}		
		return buff;
	}
	
	@SuppressWarnings("null")
	public ArrayList<String> getLinesDiffFromFiles(String oldfile01, String newfile02) throws IOException {
        File f1 = new File(oldfile01);// OUTFILE
        File f2 = new File(newfile02);// INPUT
        FileReader fR1 = null;
        FileReader fR2 = null;
        
        ArrayList<String> buff = new ArrayList<String>();

        try{
        	fR1 = new FileReader(f1);
        	fR2 = new FileReader(f2);
        } catch (IOException e) {
        	e.printStackTrace();
        	return null;
        }

        BufferedReader reader1 = new BufferedReader(fR1);
        BufferedReader reader2 = new BufferedReader(fR2);

        String line1 = new String();
        String line2 = new String();
        
        while ((line2 = reader2.readLine()) != null) {
        	
        	if ((line1 = reader1.readLine()) == null){
        		line1 = new String();
        	}
        	
            if (!line1.isEmpty() && line2.isEmpty()) {
            	buff.add(line1); 
            }
            else if (!line2.isEmpty() && line1.isEmpty()) {
            	buff.add(line2); 
            }
            else if (!line2.equalsIgnoreCase(line1)) {
            	buff.add(line2); 
            }
        }
        reader1.close();
        reader2.close();
        
        return buff;
	}
	
	/**
	 * @return
	 * @throws SQLException
	 */
	public boolean DBFeed() throws SQLException {;
		this.setupConnection();
		PreparedStatement pstm = null;
		@SuppressWarnings("static-access")
		File databaseFeedResult = new File (System.getProperty("user.dir"), "TestLog/"+ LocalDate.MIN.now().toString());
		if (!databaseFeedResult.exists()){
			try {
				databaseFeedResult.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
		PrintStream ps = null;
		try {
			ps = new PrintStream(databaseFeedResult);
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		
		String oldFile = System.getProperty("user.dir") + "\\RemoteExcelData\\" + "20160920";
		String newFile = System.getProperty("user.dir") + "\\RemoteExcelData\\" + "20160924";
		
		ArrayList<String> buff = new ArrayList<String>();
		//buff = this.getLinetoFeed();
		try {
			buff = this.getLinesDiffFromFiles(oldFile, newFile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (int tcounter = 2; tcounter < buff.size(); tcounter ++) {
			
			String[] row = buff.get(tcounter).split(",");
			String tempS = (String) row[dbValue.get("query_id")];
			int temp_qid = Integer.parseInt(tempS.trim());	
            int qID = getQueryID(temp_qid, (String) row[dbValue.get("query_date")]);
			int query_date_number = dbValue.get("query_date");
			row[query_date_number] = lc.convertDateFornat(row[query_date_number]);

            String sql_insert = "INSERT INTO RoomSoom.query_status (id) value('" + qID + "')";
            String sql_update = "update RoomSoom.query_status set ";
            for (int counter = 0; counter < (query_status_db.length - 1) ; counter++){
            	String item = query_status_db[counter];
            	int number = dbValue.get(item);
            	try{
	            	if (row[number].isEmpty() != true ) {
	            		sql_update = sql_update +  " " + item + "  = '" + row[number] + "',";
	            	}
            	} catch (ArrayIndexOutOfBoundsException e) {
	            	e.printStackTrace(ps);
	            }
	            
            }
            sql_update = sql_update + " WHERE id = " + qID + "";
            sql_update = sql_update.replaceFirst(", WHERE id", "  WHERE id");
           
            System.out.println(sql_insert);
            System.out.println(sql_update);

            try {
	            pstm = (PreparedStatement) conn.prepareStatement(sql_insert);
	            pstm.execute();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(ps);
			} 
            try {
	            pstm = (PreparedStatement) conn.prepareStatement(sql_update);
	            pstm.execute(); 
            } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(ps);
			}
                  
		}
            conn.close();
            return true;
	}
	
	public int getQueryID(int queryID, String date) {
	    //assuming date format ("MM/dd/yyyy"); 

        String[] xx = date.split("/");
        String sqID = xx[2] + xx[0] + Integer.toString(queryID);
        return Integer.parseInt(sqID);
	}
	
	public Date getSQLformattedDate(String idate){
		Date dd = null;
		return dd;
	}

}
