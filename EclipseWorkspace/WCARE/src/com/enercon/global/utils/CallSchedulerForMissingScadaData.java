package com.enercon.global.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.enercon.customer.dao.CustomerDao;
import com.enercon.customer.util.CustomerUtil;
import com.enercon.global.utility.DateUtility;

public class CallSchedulerForMissingScadaData {
	
	private String todaysDate = GlobalUtils.todaysDateInGivenFormat("dd/MM/yyyy");
	
	private String[] receiverEmailIDs = {"missing.scadadata@windworldindia.com"};/*"mithul.bhansali@windworldindia.com""Vinay.Singh@windworldindia.com"*/

	private static final String senderEmailID = "WindWorldCare@windworldindia.com";
	
	public void callTimer() throws Exception {
		callScheduleForMissingScadaData(DateUtility.getYesterdayDateInGivenStringFormat("dd/MM/yyyy"));
		
		System.out.println("Testing 1 - Send Http GET request");
		
	}

	/*Must be in the format dd/mm/yyyy*/
	public void callScheduleForMissingScadaData(String date) throws Exception {
		
		String cls = "TableRow1";
		StringBuffer msg = new StringBuffer();
		List missingScadaDataList = new ArrayList();
		CustomerUtil custUtils = new CustomerUtil();
		CustomerDao custDAO = new CustomerDao();
		
		/*Must be in the format dd/mm/yyyy*/
		String missingScadaDataDate = date;
		
		ArrayList<String> stateIDs = custDAO.getStateID();
		ArrayList<String> stateNames = custDAO.getStateNames();
		
		/*ArrayList for Storing Column Data*/
		ArrayList<String> stateName = new ArrayList<String>();
		ArrayList<String> areaName = new ArrayList<String>();
		ArrayList<String> siteName = new ArrayList<String>();
		ArrayList<String> locationNo = new ArrayList<String>();
		ArrayList<String> plantNo = new ArrayList<String>();
		ArrayList<String> wecName = new ArrayList<String>();
		ArrayList<String> technicalNo = new ArrayList<String>();
		ArrayList<String> missingDays = new ArrayList<String>();
		
		ArrayList<Integer> totalWECStateWise = new ArrayList<Integer>();
		for(String stateID:stateIDs){
			missingScadaDataList = (List) custUtils.getMissingScadaDataReport(stateID, "", "", missingScadaDataDate);
			//System.out.println("------------------1--------------------------");
			//GlobalUtils.displayVectorMember(missingScadaDataList);
			if (missingScadaDataList.size() > 0) {
				for (int i = 0; i < missingScadaDataList.size(); i++) {
					Vector v = new Vector();
					v = (Vector) missingScadaDataList.get(i);
					/*System.out.println("---------1---------2--------------------------");
					GlobalUtils.displayVectorMember(v);*/
					stateName.add(v.get(0).toString());
					areaName.add(v.get(1).toString());
					siteName.add(v.get(2).toString());
					locationNo.add(v.get(3).toString());
					plantNo.add(v.get(4).toString());
					wecName.add(v.get(5).toString());
					missingDays.add(v.get(6).toString());
					technicalNo.add(v.get(7).toString());
				}
			}
			totalWECStateWise.add(missingScadaDataList.size());
			missingScadaDataList.clear();
		}
		
		int totalWECs = 0;
		for (Integer integer : totalWECStateWise) {
			totalWECs += integer;
		}
		if(totalWECs >= 0){
			msg.append("<!DOCTYPE html>\n");
			msg.append("<html>\n");
			msg.append("<head>\n");
				msg.append("\t<title>Missing Scada Data</title>\n");
				msg.append("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
				msg.append("\t<style type=\"text/css\">\n");
					msg.append("\t\t.test{ \n");
						msg.append("\t\t\tbackground: LightGreen;\n");
						msg.append("\t\t\ttext-align: center;\n");
						msg.append("\t\t\tfont-weight: bolder;\n");
					msg.append("\t\t}\n");
				msg.append("\t</style>\n");
			msg.append("</head>\n");
			msg.append("<body>\n");
			msg.append("<table cellSpacing=0 cellPadding=0 width=\"90%\" border=1>\n");
				msg.append("\t<tr class=TableTitleRow>\n");
					msg.append("\t\t<td colspan='2' style = 'background: LightGreen;text-align: center;font-weight: bolder;' width=\"100%\">Missing SCADA Data for "+ missingScadaDataDate + ":Total WECs Statewise</td>\n");
				msg.append("\t</tr>\n");
				msg.append("\t<tr class=TableTitleRow>\n");
					msg.append("\t\t<td class = \"test\" width=\"50%\">State Name</td>\n");
					msg.append("\t\t<td class = \"test\" width=\"50%\">Total WECs</td>\n");
				msg.append("\t</tr>\n");
				
			for (int i = 0; i < stateNames.size() ; i++) {
				msg.append("\t<tr class=TableTitleRow>\n");
					msg.append("\t\t<td style = 'text-align: center;' width=\"50%\">"+ stateNames.get(i) +"</td>\n");
					msg.append("\t\t<td style = 'text-align: center;' width=\"50%\">"+ totalWECStateWise.get(i) +"</td>\n");
				msg.append("\t</tr>\n");
			}
				msg.append("\t<tr class=TableTitleRow>\n");
					msg.append("\t\t<td class = \"test\" width=\"50%\">Grand Total</td>\n");
					msg.append("\t\t<td class = \"test\" width=\"50%\">" + totalWECs + "</td>\n");
				msg.append("\t</tr>\n");
				
			msg.append("</table>\n");
			
			
			if(totalWECs <= 0){
				
			}
			else{
				msg.append("<br>\n");	
				msg.append("<table cellSpacing=0 cellPadding=0 width=\"90%\" border=1>\n");
					msg.append("\t<tr class=TableTitleRow>\n");
						msg.append("\t\t<td colspan='9' style = 'background: LightGreen;text-align: center;font-weight: bolder;' width=\"100%\">Missing SCADA Data for "+ missingScadaDataDate + ":All WECs Details Statewise</td>\n");
					msg.append("\t</tr>\n");
					msg.append("\t<tr class=TableTitleRow>\n");
						msg.append("\t\t<td class = \"test\" width=\"5%\">Sr No.</td>\n");
						msg.append("\t\t<td class = \"test\" width=\"15%\">State Name</td>\n");
						msg.append("\t\t<td class = \"test\" width=\"15%\">Area Name</td>\n");
						msg.append("\t\t<td class = \"test\" width=\"15%\">Site Name</td>\n");
						msg.append("\t\t<td class = \"test\" width=\"10%\">Location No</td>\n");
						msg.append("\t\t<td class = \"test\" width=\"10%\">Plant No</td>\n");
						msg.append("\t\t<td class = \"test\" width=\"15%\">WEC Name</td>\n");
						msg.append("\t\t<td class = \"test\" width=\"10%\">Missing Days</td>\n");
						msg.append("\t\t<td class = \"test\" width=\"15%\">Technical No</td>\n");
					msg.append("\t</tr>\n");
			
			//System.out.println(stateName.size());
			
			
				for (int i = 0; i < stateName.size(); i++) {
					int rem = 1;
					rem = i % 2;
		
					if (rem == 0)
						cls = "TableRow2";
					else
						cls = "TableRow1";
		
					msg.append("\t<tr class=" + cls + ">\n");
						msg.append("\t\t<td style = 'text-align:center;'>" + (i+1) + "</td>\n");
						msg.append("\t\t<td>" + stateName.get(i) + "</td>\n");
						msg.append("\t\t<td>" + areaName.get(i) + "</td>\n");
						msg.append("\t\t<td>" + siteName.get(i) + "</td>\n");
						msg.append("\t\t<td align=\"center\">" + locationNo.get(i) + "</td>\n");
						msg.append("\t\t<td align=\"center\">" + plantNo.get(i) + "</td>\n");
						msg.append("\t\t<td>" + wecName.get(i) + "</td>\n");
						msg.append("\t\t<td  align=\"center\">" + missingDays.get(i) + "</td>\n");
						msg.append("\t\t<td align=\"center\">" + technicalNo.get(i) + "</td>\n");
					msg.append("\t</tr>\n");
				}
				msg.append("</table>\n");
			}
			
			msg.append("</body>\n");
			msg.append("</html>\n");
			
			String Remarks="Message Not Sent Due To some Problem";
			boolean flag=false;  
			   
			SendMail sm =new SendMail();
			
			String subject= "Missing Scada Data for " + missingScadaDataDate + ":Total WECs Statewise (Report as per data availability in SCADA database @ 10:00 hours on " +  todaysDate + ")";
			
		    for (int i = 0; i < receiverEmailIDs.length; i++) {
		    	flag=sm.sendMail(receiverEmailIDs[i],senderEmailID,subject,new String(msg));
			}
		    
		    Remarks="Message Sent Sucessfully";
		}
	}
}
