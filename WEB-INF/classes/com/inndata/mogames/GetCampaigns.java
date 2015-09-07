package com.inndata.mogames;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Random;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import com.google.gson.Gson;
/**
* @author Vidya@inndata.in
*
*/
public class GetCampaigns extends HttpServlet{ 
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
    {
    	String param1 = req.getParameter("x");
    	String param2 = req.getParameter("y");
    	String param3 = req.getParameter("z");
    	int x = Integer.parseInt(param1);
    	int y = Integer.parseInt(param2);
    	int z = Integer.parseInt(param3);
    	ArrayList<Object> finalArray = new ArrayList<Object>();
    	float minX = 0.0f;
    	float maxX = 10.0f;
    	Random rand = new Random();
    	final java.util.Random generator = new java.util.Random();
    	final int MIN = 1;
    	final int MAX = y-1;
    	
    	for ( int camp = 0 ; camp < z ; camp++){
    		LinkedHashMap<String, Object> Lh = new LinkedHashMap<String, Object>();
    		ArrayList<Object> arr1 = new ArrayList<Object>();
    		int randomNumberOfAttributes = generator.nextInt(MAX - MIN) + MIN;
    		
    		for ( int tgList = 0; tgList < randomNumberOfAttributes ; tgList++){
    			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
    			String atrAlpha = getAlpha(tgList);
    			map.put("target", "attr_" + atrAlpha);
    			HashSet<String> al=new HashSet<String>();
    			
    			for (int atrList = 0 ; atrList < x-1 ; atrList++){
    				int randAtrNum = rand.nextInt(x -1 -0) + 0;
    				al.add(atrAlpha+randAtrNum);
    			}
    			map.put("attr_list",al);
    			arr1.add(map);
    		}
    		
    		Lh.put("campaign", "campaign"+camp);
    		float finalX = rand.nextFloat() * (maxX - minX) + minX;
    		Lh.put("price", finalX);
    		Lh.put("targetlist", arr1);
    		finalArray.add(Lh);
    	}
    	
    	Gson jsonString = new Gson();
    	String json = jsonString.toJson(finalArray);
    	FileWriter file = new FileWriter("/home/hadoop/Documents/campaign.json");
    	
    	try {
    		file.write(json);
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
    		file.flush();
    		file.close();
    	}
        
    	res.setContentType("text/html");
    	PrintWriter pw=res.getWriter();
    	pw.println("<html><body>");
    	pw.println(json);
    	pw.println("</body></html>");
    	pw.close();
    }
    
    public static String getAlpha(int num) {
    	return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(num, num+1);
    }
}