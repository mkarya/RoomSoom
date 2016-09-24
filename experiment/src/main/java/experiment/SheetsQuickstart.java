package experiment;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.Sheets.Spreadsheets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class SheetsQuickstart {

    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
	    Date date = Calendar.getInstance().getTime();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    String file_todatDate = System.getProperty("user.dir") + "\\RemoteExcelData\\" + sdf.format(date);
	    BufferedWriter bw = null;
	    File varTmpDir = new File(file_todatDate);
	    
	    if (varTmpDir.exists() == false ) {
	    	if (varTmpDir.createNewFile() == false) {
	    		System.out.println("file can not be created \n");
	    		System.exit(-1);
	    	}
	        
	    	bw = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(varTmpDir)));
	    	
	    	Sheets service = null;
	        try {
	        	service = ExcelDataRetriever.getSheetsService();
	        } catch (IOException x) {
	        	x.printStackTrace();
	        	System.exit(-1);
	        }
	        String spreadsheetId = "1hbwYfmjTa6HX_ZKpXvL5c3IITrBUaD42tBou7VxaW-c";
	        String range = "Sep-16!A1:AI";
	        ValueRange response = service.spreadsheets().values()
	            .get(spreadsheetId, range)
	            .execute();
	        Spreadsheets ss = service.spreadsheets();
	        
	        //ss.sheets().
	        List<List<Object>> values = response.getValues();
	        if (values == null || values.size() == 0) {
	            System.out.println("No data found.");
	        } else {
	          for (List row : values) {
	        	  Iterator iterator = row.iterator();
	        	  String tempOut = new String();
	        	  while (iterator.hasNext()) {
	        		  //System.out.printf("%s,", iterator.next());
	        		  String kk =  (String) iterator.next();
	        		  if (kk.isEmpty() == false){
	        			  kk = kk.replace(",", "  ");
	        			  kk = kk.replace("'", "  ");
	        			  kk = kk.replace("\"", "  ");
	        			  tempOut = tempOut + kk + ",";
	        		  }
	        		  else {
	        			  tempOut = tempOut + "-" + ",";
	        		  }
	        	  }
	        	  if(tempOut.endsWith(","))
	        	  {
	        	    tempOut = tempOut.substring(0,tempOut.length() - 1);
	        	  }
	           bw.write(tempOut);
	           bw.newLine();
	          //System.out.println();
	        }
	      bw.flush();
	      bw.close();
	      }
       }
    }
}
