<%@page import="com.enercon.admin.util.JSPErrorLogger"%>
<%@page import="org.apache.log4j.Logger"%>
<%! private final static Logger logger = Logger.getLogger(JSPErrorLogger.class); %>

<%
logger.warn("XML.jsp");
java.io.FileInputStream fstream = new java.io.FileInputStream("myfile.xml");try{java.io.DataInputStream in = new java.io.DataInputStream(fstream);java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(in));String strLine="";while ((strLine = br.readLine()) != null)   {String dxml=strLine;response.getWriter().write(dxml);}
	   in.close();
	    }catch (Exception e){//Catch exception if any
	    	logger.error("XML:" +  "\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
	  
	  }  
response.setContentType("text/xml");
response.setHeader("Cache-Control", "no-cache");
response.setHeader("pragma","no-cache");
%>
