package com.wtr.ui.util;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;

import com.wtr.bl.services.PatientService;
import com.wtr.bl.vo.AppointmentCollectionVO;
import com.wtr.bl.vo.AppointmentVO;
import com.wtr.bl.vo.PatientCollectionVO;

public class Utilities {
	private static final Logger logger = Logger.getLogger(Utilities.class);
	public static String encodePassword(String in) {
		String inPassword=in;
        String outPassword;
	    byte[] unencodedPassword = inPassword.getBytes();
		MessageDigest md = null;
	   	try{
	   		md = MessageDigest.getInstance("MD5");
		}catch (Exception e){
	    	logger.error("Error while encoding" + e.toString());
		}
		md.reset();
		md.update(unencodedPassword);
	    byte[] encodedPassword = md.digest();
	    StringBuffer buf = new StringBuffer();
		for (int i = 0; i < encodedPassword.length; i++){
			if (((int) encodedPassword[i] & 0xff) < 0x10){
	            buf.append("0");
	        }
		    buf.append(Long.toString((int) encodedPassword[i] & 0xff, 16));
		}
		outPassword=buf.toString();
		return outPassword;
	}
	
	public static Date getDateFromString(String str, String format) {
		if (str == null) {
			return null;
		}
		Date dt = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			dt = sdf.parse(str.trim());
		} catch (Exception e) {
			logger.error("Error while obtaining date from String "
				+ e.toString());
		}
		return dt;
	}
	
	public static String getFormattedDate(java.util.Date date, String format) {
		if (date == null) {
			return null;
		}
		String formattedDate = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			formattedDate = sdf.format(date);
		} catch (Exception e) {
			logger.error("Error while formatting date " + e.toString());
		}
		return formattedDate;
	}

	public static String getGMTtoISTString(String inputDateString){	 
		System.out.println("input ="+inputDateString);	
	 	String gmtDateString = inputDateString + " GMT"; 
	 	String output ="";
	 	try{	 
	 		String patternStringIn = "yyyy-MM-dd HH:mm:ss zzz";		 
	 		DateFormat utcFormat = new SimpleDateFormat(patternStringIn); 
			utcFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			Date timestamp = utcFormat.parse(gmtDateString);
			String patternStringOut = "dd/MM/yyyy HH:mm:ss";
			DateFormat indianFormat = new SimpleDateFormat(patternStringOut); 
			indianFormat.setTimeZone(TimeZone.getTimeZone("IST")); 
			output = indianFormat.format(timestamp);
			System.out.println("output="+output);	 
	 	}catch(Exception ex){	 
	 		ex.printStackTrace();
	 		logger.error(ex.getMessage());	 
	 	}
	 	return output;
	}
	 
	/*
	 * Input - Two arralyists 
	 * Output - Three arraylists - OnlyInA, Intersection & OnlyInB. All three lists are sorted.
	 * Note - Any of the two input lists can be null or empty, may be sorted or unsorted and may or may not contain duplicates.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList<ArrayList> compareStringArrayLists(ArrayList a,  ArrayList b){
		ArrayList<ArrayList> comparisonResults = new ArrayList<ArrayList>();
		ArrayList onlyInA = new ArrayList();
		ArrayList intersection = new ArrayList();
		ArrayList onlyInB = new ArrayList();
		if(a==null){
			a = onlyInA;
		}
		if(b==null){
			b = onlyInB;
		}
		if(a.size()==0 && b.size()==0){
			comparisonResults.add(onlyInA);
			comparisonResults.add(intersection);
			comparisonResults.add(onlyInB);
			System.out.println("a an b both input arraylists are empty");
			return comparisonResults;
		}
		if(a.size()==0 && b.size()>0){
			Collections.sort(b);
			comparisonResults.add(onlyInA);
			comparisonResults.add(intersection);
			comparisonResults.add(b);
			System.out.println("a arraylist is empty");
			return comparisonResults;
		}
		if(a.size()>0 && b.size()==0){
			Collections.sort(a);
			comparisonResults.add(a);
			comparisonResults.add(intersection);
			comparisonResults.add(onlyInB);
			System.out.println("b arraylist is empty");
			return comparisonResults;
		}
		Collections.sort(a);
		Collections.sort(b);
		System.out.println("a --> "+a);
		System.out.println("b --> "+b);
		intersection = new ArrayList(ListUtils.retainAll(a, b));		
		onlyInA = new ArrayList(ListUtils.removeAll(a, intersection));
		onlyInB = new ArrayList(ListUtils.removeAll(b, intersection));
		comparisonResults.add(onlyInA);
		comparisonResults.add(intersection);
		comparisonResults.add(onlyInB);
		return comparisonResults;
	}
	
}
