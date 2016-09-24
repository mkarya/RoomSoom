package experiment;

import java.util.HashMap;
import java.util.Map;

public class ColumnPosition {
	protected static Map<String,Integer> dbValue = new HashMap<String,Integer> ();
	protected static String[] query_status_db = {"query_date","query_time","source","querier_name","calling_status","status",
			"querier_number","sharing","location_asked","teritory","budget","Food","remark","follow_up_call_date",
			"call_by","call_date","call_time","category","number_of_seat_required","querier_email",
			"Company_institution_name","sms_whatapp_text_send_status","number_circulated",
			"assigned_sales_person","allot_date","allot_time","followup_call","sales_team_feedback",
			"feedback_date","feedback_time","converted","reason_for_not_conversion","Converted_By",
			"property_address","booking_date"};
	
	protected static String[] query_status_db02 = {"query_date","source","querier_name","calling_status", 
			"category","querier_number","querier_email","number_of_seat_required","location_asked",	
			"budget","Company_institution_name","call_by","call_date",
			"remark","followup_call","booking_date","converted","property_address" };
	
/*	public ColumnPosition() {
		dbValue.put("query_id", 0);
		dbValue.put("query_date",1);
		dbValue.put("source",2);
		dbValue.put("querier_name",3);
		dbValue.put("calling_status",4); 
		dbValue.put("category",5);
		dbValue.put("querier_number",6);
		dbValue.put("querier_email",7);
		dbValue.put("number_of_seat_required",8);
		dbValue.put("location_asked",9);	
		dbValue.put("budget",10);
		dbValue.put("Company_institution_name",11);
		dbValue.put("call_by",12);
		dbValue.put("call_date",13);
		dbValue.put("Particular-Action",14);
		dbValue.put("remark",15);
		dbValue.put("followup_call",16);
		dbValue.put("booking_date",17);
		dbValue.put("converted",18);
		dbValue.put("property_address",19);
	}*/
	
	public ColumnPosition() {
		dbValue.put("query_id", 0); 						//a
		dbValue.put("query_date",1);						//b
		dbValue.put("query_time",2);						//c
		dbValue.put("source",3);							//d
		dbValue.put("querier_name",4);						//e
		dbValue.put("calling_status",5);					//f
		dbValue.put("status",5);							//g
		dbValue.put("querier_number",7);					//h
		dbValue.put("sharing",8);							//i
		dbValue.put("location_asked",9);					//j
		dbValue.put("teritory",10);							//k
		dbValue.put("budget", 11);		
		dbValue.put("Food", 12);//l
		dbValue.put("category", 13);
		dbValue.put("remark",14);//m
		dbValue.put("follow_up_call_date",15);				//n
		dbValue.put("call_by",16);							//o
		dbValue.put("call_date",17);						//p
		dbValue.put("call_time",18);						//q
		dbValue.put("number_of_seat_required",19);			//s
		dbValue.put("querier_email",20);					//t
		dbValue.put("Company_institution_name",21);			//u
		dbValue.put("sms_whatapp_text_send_status",22);		//v	
		dbValue.put("number_circulated",23);				//w				
		dbValue.put("assigned_sales_person",24);			//x
		dbValue.put("allot_date",25);						//y
		dbValue.put("allot_time",26);						//z
		dbValue.put("followup_call",27);					//aa
		dbValue.put("sales_team_feedback",28);				//ab
		dbValue.put("feedback_date",29);					//ac
		dbValue.put("feedback_time",30);					//ad
		dbValue.put("converted",31);						//AE
		dbValue.put("reason_for_not_conversion",32);		//AF	
		dbValue.put("Converted_By",33);						//ag
		dbValue.put("property_address",34);					//ah
		dbValue.put("booking_date",35);						//ae
	}
	
}
