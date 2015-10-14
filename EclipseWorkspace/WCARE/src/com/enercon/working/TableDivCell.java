package com.enercon.working;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class TableDivCell {

	public static void main(String[] args) {
		PrintWriter fos = null;
		String fileWriterPath = "D:\\Testing.html";
		/*int noOfRows = ReadingAnInteger.readAnInteger();*/
		int noOfRows = 14;
		int[] columnRequired = new int[]{4, 1, 2, 1, 4, 1, 4, 1, 4, 1, 4, 5, 2, 2};
		
		try {
			fos = new PrintWriter(new BufferedWriter(new FileWriter(
					fileWriterPath)), true);
			fos.println("<html>");
			fos.println("<head>");
			fos.println(getStyle(noOfRows, columnRequired));
			fos.println("</head>");
			fos.println("<body>");
			fos.println("\t<div class=\"divTable\">");
			
			for(int i = 0; i < noOfRows; i++){
				fos.println("\t\t<div class=\"divRow row"+ (i + 1) +"\">");
				for(int j = 1; j <= columnRequired[i]; j++){
					fos.print("\t\t\t<div class=\"divCell cell"+ j +"\">");
					fos.print((i + 1) + ":" + j);
					fos.println("</div>");
				}
				fos.println("\t\t</div>");
			}
			fos.println("\t</div>");
			fos.println("</body>");
			fos.println("</html>");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private static String getStyle(int noOfRows,int[] columnsRequired) {
		StringBuffer styleContent = new StringBuffer("<style type=\"text/css\">\n" + 
				"\t.divTable{ \n" + 
				"\t\tdisplay:  table; \n" + 
				"\t\twidth: 60%; \n" + 
				"\t\tborder:1px solid  #666666; \n" + 
				"\t\tborder-spacing:5px; \n" + 
				"\t\tborder-collapse:separate; \n" + 
				"\t} \n" + 
				" \n" + 
				"\t.divRow{ \n" + 
				"\t\tdisplay: table-row; \n" + 
				"\t\twidth: auto; \n" + 
				"\t\n} " + 
				"\n" + 
				"\t.divCell{ \n" + 
				"\t\tfloat:left;/*fix for  buggy browsers*/ \n" + 
				"\t\tdisplay:table-column; \n" + 
				"\t\twidth:auto; \n" + 
				"\t} \n");
		for(int i=0;i<noOfRows;i++){
			int widthPerCellPercentage = 100/columnsRequired[i];
			for(int j=1;j<=columnsRequired[i];j++){
				styleContent.append("\n\t.divTable .row"+(i+1)+" .cell"+j+"{\n");
				styleContent.append("\t\twidth:" + widthPerCellPercentage + "%;\n");
				styleContent.append("\t}\n");
			}
		}
		styleContent.append("</style>\n");
		return new String(styleContent);
	}
}
