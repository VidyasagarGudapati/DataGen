package com.inndata.mogames;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/**
 * @author Vidya@inndata.in
 *
 */
public class Import extends HttpServlet{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req,HttpServletResponse res)  throws ServletException,IOException {
		JSONParser parser = new JSONParser();
		String json_user1 = User.getUser();
		JSONArray json_user = null;
		String final_result = null;
		String result[] = null;
		String attr_res = null;
		double fin_price;
		int ind = 2;
		JSONArray a = null;
		
		try {
			json_user = (JSONArray) parser.parse(json_user1);
			a = (JSONArray) parser.parse(new FileReader("/home/hadoop/Documents/campaign.json"));
			JSONArray jsonCampaign = a;
 			String prof[] = null;
			JSONObject profile = null;
			
			for (Object ob : json_user) {
				JSONObject item2 = (JSONObject) ob;
				profile = (JSONObject) item2.get("profile");
			}
			String pro = profile.toString();
			pro = pro.replaceAll("[\\[\\]{}\"\"]", "").replaceAll("[,]",":");
			prof = pro.split(":");
			
			for (Object o : jsonCampaign) {
				JSONObject item = (JSONObject) o;
				String campaign = (String) item.get("campaign");
				double price = (double) item.get("price");
				JSONArray targetlist = (JSONArray) item.get("targetlist");
				
				for (Object obj : targetlist) {
					JSONObject item1 = (JSONObject) obj;
					String target = (String) item1.get("target");
					
					for(int l=0;l<prof.length;l=l+2) {
						if(target.equals(prof[l])) {
							String attr = item1.get("attr_list").toString();
							attr = attr.replaceAll("[\\[\\]\"\"]", "").replaceAll("[,]",":");
							String attr1[] = attr.split(":");
							
							for(int k=0;k<attr1.length;k++) {
								if(attr1[k].equals(prof[l+1])) {
									final_result = final_result+":"+campaign+":"+price;
								}
							}
						}
					}
				}
			}
			if(final_result != null){
				result = final_result.split(":");
				fin_price = Double.parseDouble(result[2]);
				for(int n=2;n<result.length;n=n+2) {
					if(fin_price < Double.parseDouble(result[n])) {
						fin_price = Double.parseDouble(result[n]);
						ind = n;
					}
				}
				for (Object o : jsonCampaign) {
					JSONObject item1 = (JSONObject) o;
					String campaign_name = (String) item1.get("campaign");
					if(campaign_name.equals(result[ind-1])){
						attr_res = item1.toString();
					}
				}
			}
			else{
				attr_res = "No Match Found";
			}
		} catch (ParseException e1) {
				e1.printStackTrace();
		}
		
		res.setContentType("text/html");
		PrintWriter pw=res.getWriter();
		pw.println("<html><body>");
		pw.println(attr_res);
		pw.println("</br>");
		pw.println("</br>");
		pw.println("</body></html>");
		pw.close();
	}
}