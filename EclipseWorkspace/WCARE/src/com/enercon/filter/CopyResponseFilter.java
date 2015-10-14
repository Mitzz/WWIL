package com.enercon.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class CopyResponseFilter implements Filter {
	
	private static Logger logger = Logger.getLogger(CopyResponseFilter.class);

    public void init(FilterConfig config) throws ServletException {
        // NOOP.
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
    	HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    	
    	String url = new String(httpServletRequest.getRequestURL());
    	
    	filterUrlLogging(url);
//    	HttpSession session = httpServletRequest.getSession(false);
//    	if(session == null) logger.debug("Session is null");
//    	else logger.debug("Session is not null");

    	chain.doFilter(request, response);
        
//        session = httpServletRequest.getSession(false);
//        if(session == null) logger.debug("Session is null");
//    	else logger.debug("Session is not null");
       
    	filterUrlLogging(url);
    }
    
    private void filterUrlLogging(String url){
    	if(!(url.endsWith(".css") || url.endsWith(".js") || url.endsWith(".jpg") || url.endsWith(".gif") || url.endsWith(".GIF"))) logger.info(url);
    }
    
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
//
//        HttpServletResponseCopier responseCopier = new HttpServletResponseCopier((HttpServletResponse) response);
//
//        try {
//            chain.doFilter(request, responseCopier);
//            responseCopier.flushBuffer();
//        } finally {
//            byte[] copy = responseCopier.getCopy();
////            System.out.println(new String(copy, response.getCharacterEncoding())); // Do your logging job here. This is just a basic example.
//        }
//    }

    public void destroy() {
        // NOOP.
    }

}

class HttpServletResponseCopier extends HttpServletResponseWrapper {

    private ServletOutputStream outputStream;
    private PrintWriter writer;
    private ServletOutputStreamCopier copier;

    public HttpServletResponseCopier(HttpServletResponse response) throws IOException {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (writer != null) {
            throw new IllegalStateException("getWriter() has already been called on this response.");
        }

        if (outputStream == null) {
            outputStream = getResponse().getOutputStream();
            copier = new ServletOutputStreamCopier(outputStream);
        }

        return copier;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (outputStream != null) {
            throw new IllegalStateException("getOutputStream() has already been called on this response.");
        }

        if (writer == null) {
            copier = new ServletOutputStreamCopier(getResponse().getOutputStream());
            writer = new PrintWriter(new OutputStreamWriter(copier, getResponse().getCharacterEncoding()), true);
        }

        return writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (writer != null) {
            writer.flush();
        } else if (outputStream != null) {
            copier.flush();
        }
    }

    public byte[] getCopy() {
        if (copier != null) {
            return copier.getCopy();
        } else {
            return new byte[0];
        }
    }

}

class ServletOutputStreamCopier extends ServletOutputStream {

    private OutputStream outputStream;
    private ByteArrayOutputStream copy;

    public ServletOutputStreamCopier(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.copy = new ByteArrayOutputStream(1024);
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
        copy.write(b);
    }

    public byte[] getCopy() {
        return copy.toByteArray();
    }

}