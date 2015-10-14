package com.enercon.global.utils;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidationServlet extends HttpServlet {
    
    private ServletContext context;
    private HashMap accounts = new HashMap();
 
    @Override
	public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.context = config.getServletContext();
        accounts.put("greg","account data");
        accounts.put("duke","account data");
        // add a Japanese hiragana example "ne" "ko" (cat)
        accounts.put("\u306D\u3053","account data");
    }
    
    @Override
	public  void doGet(HttpServletRequest request, HttpServletResponse  response)
        throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String targetId = request.getParameter("id");
	    if ((targetId != null) && !accounts.containsKey(targetId.trim())) {
            response.setContentType("text/xml;charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write("<valid>true</valid>");
        } else {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write("<valid>false</valid>");
        }
    }
}
