package experiment;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.File.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.apache.http.impl.cookie.DateUtils;
import org.junit.Test;

public class DataBaseManagerTest {
	private DataBaseManager db;
	private LibraryClass lc;
	
	public DataBaseManagerTest() {
		db = new DataBaseManager();
		lc = new LibraryClass();
	}
	
	
	//@Test
	public void test_getQueryID() {
		//DataBaseManager db = new DataBaseManager();
		int expected_query_id = 2016091; 
		int input_query_id = 1;
		String input_date_string = "09/01/2016";//"yyyy-MM-dd"
		int output_query_id = db.getQueryID(input_query_id, input_date_string);
		System.out.println(output_query_id);
		org.junit.Assert.assertEquals(expected_query_id, output_query_id);
		
	}
	
	//@Test
	public void test_getLineToFeed() {
		Vector<String> buff = db.getLinetoFeed();
		
		Iterator it = buff.iterator();
		
		while (it.hasNext()) {
			System.out.print(it.next());
			System.out.println();
		}
		
		org.junit.Assert.assertFalse(buff.size() > 0);
	}
	
	@Test
	public void test_DBFeed() {
		boolean check = false;
		try {
			check = db.DBFeed();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		org.junit.Assert.assertFalse(check == true);
	}
	
	//@Test
	public void testConvertDateFornat() {
		String actualDateString = "09/01/2016";
		String ExpectedDateString = lc.convertDateFornat(actualDateString);
	
		org.junit.Assert.assertEquals(ExpectedDateString, actualDateString);
	}
	
	//@Test
	public void testGetLineDiffFromFiles(){
		String oldFile = System.getProperty("user.dir") + "\\RemoteExcelData\\" + "20160920";
		String newFile = System.getProperty("user.dir") + "\\RemoteExcelData\\" + "20160924";
		ArrayList<String> lines = new ArrayList<String>();
		
		try {
			lines = db.getLinesDiffFromFiles(oldFile, newFile);
			for (String ll : lines ){
				System.out.println(ll);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FileWriter kk = null;
		try {
			kk = new FileWriter(new File(System.getProperty("user.dir"),"TestLog\\testdatafile"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (String ll: lines){
			try {
				kk.write(ll + "\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try{
			kk.flush();
			kk.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//@Test
	public void general() {
		System.out.println(System.getProperty("user.dir"));
		System.out.println(System.getProperty("user.home"));
		System.out.println(LocalDate.MIN.now());
		System.out.println( "TestLog/"+ LocalDate.MIN.now().toString());
		return;
	}
	
	@Test
	public void testfindFile() {
		File kk = new File (System.getProperty("user.dir") + "//RemoteExcelData//");
		String fileName = null;
		if (kk.isDirectory()) {
			fileName = db.findFile(kk);
		}
		System.out.println(fileName);
	}

}
