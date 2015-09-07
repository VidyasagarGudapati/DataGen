package com.inndata.mogames;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import com.google.gson.Gson;

/**
* @author Vidya@inndata.in
*
*/
public class User implements Filter{
	private static int hitCount;
	
	public void  init(FilterConfig config) throws ServletException{
		// Reset hit counter.
		hitCount = 1;
	}
	
	public void  doFilter(ServletRequest request,ServletResponse response,FilterChain chain) 
			throws java.io.IOException, ServletException {
		ArrayList<Object> finalArr = new ArrayList<Object>();
		final java.util.Random generator = new java.util.Random();
		final int MIN = 1;
		final int MAX = 200;
		LinkedHashMap<String, Object> Lh1 = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> Lh2 = new LinkedHashMap<String, Object>();
		long countValue = hitCount++;
		long filter = countValue%26;
		
		if( (filter<=25) && (filter>0) ) {
			for(int i=1; i<=countValue%26;i++){
				String atrAlpha = getAlpha(i-1);
				int randomNumber = generator.nextInt(MAX - MIN) + MIN;
				Lh2.put("attr_"+atrAlpha, ""+atrAlpha+""+ randomNumber);
			}
		} else {
			for(int i=1; i<=26;i++){
				String atrAlpha = getAlpha(i-1);
				int randomNumber = generator.nextInt(MAX - MIN) + MIN;
				Lh2.put("attr_"+atrAlpha, ""+atrAlpha+""+ randomNumber);
			}
		}
		
		Lh1.put("user","u"+countValue);
		Lh1.put("profile", Lh2);
		finalArr.add(Lh1);
		
		Gson jsonString = new Gson();
		String json1 = jsonString.toJson(finalArr);
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		
		pw.println("<html><body>");
		pw.println(json1);
		pw.println("</body></html>");
		pw.close();
	}
	
	public void destroy() { 
        // This is optional step but if you like you
        // can write hitCount value in your database.
    }
	public static String getAlpha(int num) {
		return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(num, num+1);
	}
	public static String getUser(){
		ArrayList<Object> finalArr = new ArrayList<Object>();
		final java.util.Random generator = new java.util.Random();
		final int MIN = 1;
		final int MAX = 200;
		LinkedHashMap<String, Object> Lh1 = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> Lh2 = new LinkedHashMap<String, Object>();
		long countValue = hitCount++;
		long filter = countValue%26;
		
		if( (filter<=25) && (filter>0) ) {
			for(int i=1; i<=countValue%26;i++){
				String atrAlpha = getAlpha(i-1);
				int randomNumber = generator.nextInt(MAX - MIN) + MIN;
				Lh2.put("attr_"+atrAlpha, ""+atrAlpha+""+ randomNumber);
			}
		} else {
			for(int i=1; i<=26;i++){
				String atrAlpha = getAlpha(i-1);
				int randomNumber = generator.nextInt(MAX - MIN) + MIN;
				Lh2.put("attr_"+atrAlpha, ""+atrAlpha+""+ randomNumber);
			}
		}
		
		Lh1.put("user","u"+countValue);
		Lh1.put("profile", Lh2);
		finalArr.add(Lh1);
		Gson jsonString = new Gson();
		String json1 = jsonString.toJson(finalArr);
		return json1;
	}
}