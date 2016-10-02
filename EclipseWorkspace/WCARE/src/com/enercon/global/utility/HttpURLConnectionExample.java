package com.enercon.global.utility;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;

public class HttpURLConnectionExample {
	private final static Logger logger = Logger.getLogger(HttpURLConnectionExample.class);
	
	private final String USER_AGENT = "Mozilla/5.0";
	private String url;
	private Map<String, String> requestParams;
	
	public HttpURLConnectionExample(String url, Map<String, String> requestParams) {
		this.url = url;
		this.requestParams = requestParams;
	}

	public String sendRequest() {
		String urlParameters = new String("");
		
		for (String paramName : requestParams.keySet()) {
			urlParameters += urlParameters + paramName + "=" + requestParams.get(paramName) + "&";
		}
		logger.debug(urlParameters);
		URL obj = null;
		HttpURLConnection con = null;
		BufferedReader in = null;
		String inputLine;
		StringBuffer response = new StringBuffer();
		try {
			obj = new URL(url + "?" + urlParameters);
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} catch (IOException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		return response.toString();
		

	}
	
	// HTTP POST request
	private void sendPost(String url, Map<String, String> requestParam) throws Exception {

		
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		
		
		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
//		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		System.out.println(response.toString());

	}

}
