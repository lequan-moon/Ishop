package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class TestCPUInfo {

	 public static String getMotherboardSN() {
		  String result = "";
		    try {
		      File file = File.createTempFile("realhowto",".vbs");
		      file.deleteOnExit();
		      FileWriter fw = new java.io.FileWriter(file);

		      String vbs =
		         "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
		        + "Set colItems = objWMIService.ExecQuery _ \n"
		        + "   (\"Select * from Win32_BaseBoard\") \n"
		        + "For Each objItem in colItems \n"
		        + "    Wscript.Echo objItem.SerialNumber \n"
		        + "    exit for  ' do the first cpu only! \n"
		        + "Next \n";

		      fw.write(vbs);
		      fw.close();
		      Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
		      BufferedReader input =
		        new BufferedReader
		          (new InputStreamReader(p.getInputStream()));
		      String line;
		      while ((line = input.readLine()) != null) {
		         result += line;
		      }
		      input.close();
		    }
		    catch(Exception e){
		        e.printStackTrace();
		    }
		    return result.trim();
		  }

		  public static void main(String[] args){
		    String cpuId = TestCPUInfo.getMotherboardSN();
		    System.out.println(cpuId);
//		    javax.swing.JOptionPane.showConfirmDialog((java.awt.Component)
//		         null, cpuId, "Motherboard serial number",
//		         javax.swing.JOptionPane.DEFAULT_OPTION);
		  }

}
