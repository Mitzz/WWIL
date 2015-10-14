<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
    response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="../../WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="../../WEB-INF/struts-logic.tld" prefix="logic"%>

<%@page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="java.text.*" %>
        <%
            response.setHeader("Pragma", "no-cache");
            response.getOutputStream().flush();
            response.getOutputStream().close();
        %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateForm.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/mscript/lib/jquery-1.10.1.min.js"></script>
        
        <script type="text/javascript">
        	$(document).ready(function(){
        		$("#ScadaDataJumpUpload").submit(function(){
        			
        		});
        	});
        </script>

    </head>
    <body>
    
        <div align="center">
            <table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
                <tr width="100%">
                    <td width="100%" align="center">
                        <form action="<%=request.getContextPath()%>/ScadaDataJumpUpload.do" method="post" enctype="multipart/form-data" name = "ScadaDataJumpUpload" id = "ScadaDataJumpUpload">
                            <table align="center" border="0" cellpadding="0" cellspacing="0" width="500">
                                <tbody>
                                	<tr>
                                        <td class="newhead1"></td>
                                        <th class="headtext">Upload Scada Jump Data For WEC</th>
                                        <td>
                                        	<img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10">
                                        </td>
                                        <td class="newhead3">&nbsp;</td>
                                        <td class="newhead4">
                                        	<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="newheadl"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
                                        <td colspan="3">
                                            <img src="<%=request.getContextPath()%>/<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
                                            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                            	<tbody>
                                            		<tr>
                                            			<td bgcolor="#dbeaf5">
                                                            <table border="0" cellpadding="2" cellspacing="1" width="100%">
                                                                <tbody>
                                                                	<tr class="bgcolor">
                                                                		<td colspan="2" style="text-align: center; font-weight: bolder;color: blue;" >
                                                                			Please Refer the new excel format w.e.f. <u>31-JAN-2014</u> before uploading. 
                                                                		</td>
                                                                	</tr>
                                                                	<tr class="bgcolor"> 
                                                                        <td id="t_street_address">Select File:</td>
                                                                        <td class="bgcolor">
                                                                            <input type="file" name="excelFile" size="40" class="ctrl" />
                                                                        </td>
                                                                        
                                                                    </tr>				
                                                                    <tr class="bgcolor"> 
                                                                        <td id="t_street_address">Excel Sheet Name(Case Sensitive):</td>
                                                                        <td class="bgcolor">
                                                                            <input type="text" name="excelSheetName" size="20" class="ctrl" maxlength="20" />
                                                                        </td>
                                                                        
                                                                    </tr>		
                                                                    <tr class="bgcolor">
                                                                        <td class="bgcolor" colspan="2" align="center">
                                                                            <a href="<%=request.getContextPath()%>/Admin/ExcelFiles/WECDataWithGridDowntimeBifurcation.html" target="_blank"><span class="linkbluebold">Excel File Sample</span></a>
                                                                        </td>
                                                                        
                                                                    </tr>
                                                                    <tr class="bgcolor">
                                                                    	<logic:messagesNotPresent message="true">
                                                                        <logic:messagesNotPresent message="false">
                                                                        <td style="color:blue;text-align: center;" colspan="2">
																		</td>
																		</logic:messagesNotPresent>
																		</logic:messagesNotPresent>
                                                                        <logic:messagesPresent message="false">
                                                                        <td style="color:red;text-align: center;" colspan="2">
                                                                    		<html:messages id="err_name" property="common.file.err">
																				<bean:write name="err_name" />
																			</html:messages>
																			<html:messages id="err_name" property="common.file.err.ext">
																				<bean:write name="err_name" />
																			</html:messages>
																			<html:messages id="err_name" property="common.file.err.size">
																				<bean:write name="err_name" />
																			</html:messages>
																			<html:messages id="err_name" property="error.file.excel.sheetname">
																				<bean:write name="err_name" />
																			</html:messages>
																		</td>
																		</logic:messagesPresent>
																		<logic:messagesPresent property="successfulUploaded" message="true">
																		<td style="color:green;text-align: center;" colspan="2">
                                                                    		<html:messages id="message" property="successfulUploaded" message="true">
     																			<bean:write name="message"/>
   																			</html:messages>
																		</td>
																		</logic:messagesPresent>
																		<logic:messagesPresent property="fileDateProblem" message="true">
																		<td style="color:red;text-align: center;" colspan="2">
                                                                    		<html:messages id="message" property="fileDateProblem" message="true">
     																			<bean:write name="message"/>
   																			</html:messages>
																		</td>
																		</logic:messagesPresent>
																		<logic:messagesPresent property="dataNotUploaded" message="true">
																		<td style="color:red;text-align: center;" colspan="2">
                                                                    		<html:messages id="message" property="dataNotUploaded" message="true">
     																			<bean:write name="message"/>
   																			</html:messages>
																		</td>
																		</logic:messagesPresent>
																		<logic:messagesPresent property="fileTimeProblem" message="true">
																		<td style="color:red;text-align: center;" colspan="2">
                                                                    		<html:messages id="message" property="fileTimeProblem" message="true">
     																			<bean:write name="message"/>
   																			</html:messages>
																		</td>
																		</logic:messagesPresent>
																		<logic:messagesPresent property="excelSheetNotPresent" message="true">
																		<td style="color:red;text-align: center;" colspan="2">
                                                                    		<html:messages id="message" property="excelSheetNotPresent" message="true">
     																			<bean:write name="message"/>
   																			</html:messages>
																		</td>
																		</logic:messagesPresent>
                                                                    </tr>
                                                                </tbody>
															</table>
														</td>
													</tr>
												</tbody>
											</table>
                                            <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
                                        </td>
                                        <td class="newheadr"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
                                    </tr>
                                    <tr>
                                        <td width="10"><img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
                                        <td colspan="4" align="right" bgcolor="#006633">
                                            <table border="0" cellpadding="0" cellspacing="0">
                                                <tbody>
                                                	<tr>
                                                        <td class="btn" width="100">
                                                            <input type="Submit" name="UploadCmd" class="btnform" value="Submit" onClick="return confirmation();"/>
                                                        </td>
                                                        <td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
                                                        <td class="btn" width="100"><input name="Reset" value="Cancel" class="btnform" type="reset"></td>
                                                        <td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
                                                    </tr>
                                                </tbody>
                                         	</table>
                                        </td>
                                    </tr>
                                </tbody>
                          	</table>
                        </form>	
                    </td>		
                </tr>
            </table>
        </div>
    </body>
</html>