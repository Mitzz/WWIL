<%java.io.FileInputStream fstream = new java.io.FileInputStream("myfile.xml");try{java.io.DataInputStream in = new java.io.DataInputStream(fstream);java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(in));String strLine="";while ((strLine = br.readLine()) != null)   {String dxml=strLine;response.getWriter().write(dxml);}
	   in.close();
	    }catch (Exception e){//Catch exception if any
	  System.err.println("Error: " + e.getMessage());
	  }  
response.setContentType("text/xml");
response.setHeader("Cache-Control", "no-cache");
response.setHeader("pragma","no-cache");
%>
