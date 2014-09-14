/* ***************************************************************************/
/* File Description  : EncryptionUtil                                        */
/* File Version      : 1.0                                                   */
/* Legal Copyright   : Copyright � 2004-2010 by DONGA JSC                    */
/*                     All Rights Reserved                                   */
/* Company Name      : DONGA JSC.                                            */
/* Original Filename : EncryptionUtil.java                                   */
/* Product Version   : 1.0                                                   */
/* Product Name      : STAND ALONE LICENSE API                               */
/*****************************************************************************/
package com.nn.ishop.license;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;

public final class LicenseUtil {
	public static final String DEMO_LICENSE_TYPE="DEMO";
	public static final String ENT_LICENSE_TYPE="ENT";
	public static final String PRO_LICENSE_TYPE="PRO";
	
	//ALL, STD, PB, CONSUMER, ATOC, HRM
	public static final String ALL_FEATURE = "All";
	public static final String STD_FEATURE = "STD";
	public static final String PB_FEATURE = "PB";
	public static final String CONSUMER_FEATURE = "CONSUMER";
	public static final String ATOC_FEATURE = "ATOC";
	public static final String HRM_FEATURE = "HRM";
	
	public static final String VIRGIN_FILE_NAME= "virgin.dat";
	
	public static final String LIC_KEY = "licenseFile";
	public static final String PK_KEY = "publicKeyFile";
	public static final String PR_KEY = "privateKeyFile";
	public static String virginPath = null;
	/** DEMO license time fund in second: days*hours*minutes*seconds */
	public static final long DEMO_TIME_FUND = 30*24*60*60 ;
	public static int remainDays = 0;
	
    /**
     * Generate product random serial number
     * @return XXXXXX-XXXXXX-XXXXXX-XXXXXX-XXXXXX-XXXXXX 
     * String represent for random generated serial number 
     * @throws Exception
     */
    protected static String getRandomSerialNumber() throws Exception
    {
    	String ret = "";
    	for (int i=0;i<6;i++)
    	{
    		if(i<5) ret += String.valueOf((int)(Math.random() * 1000000)) + "-";
    		else
    			ret += String.valueOf((int)(Math.random() * 1000000));
    	}
    	return ret;
    	
    }
	/**
	 * convert a byte[] array to readable string format.
	 * 
	 * @param in
	 *            byte[] buffer to convert to string format
	 * 
	 * @return result String buffer in String format
	 */
	public static String toHexString(byte[] in) {
		byte ch = 0x00;
		int i = 0;
	
		if ((in == null) || (in.length <= 0)) {
			return null;
		}
	
		String[] pseudo = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"A", "B", "C", "D", "E", "F" }; // Hex order
		StringBuffer out = new StringBuffer(in.length * 2);
	
		while (i < in.length) {
			ch = (byte) (in[i] & 0xF0); // Strip off high nibble
			ch = (byte) (ch >>> 4); // shift the bits down
			ch = (byte) (ch & 0x0F); // must do this is high order bit is on!
			out.append(pseudo[(int) ch]); // convert the nibble to a String
											// Character
			ch = (byte) (in[i] & 0x0F); // Strip off low nibble
			out.append(pseudo[(int) ch]); // convert the nibble to a String
											// Character
			i++;
		}
	
		String rslt = new String(out);
	
		return rslt;
	}
	
	/**
	 * Initialize a virgin key file for DEMO license
	 * do this while installing
	 * @param installDate long present for installed date (in second)
	 * @param licRepo path to repository of license, for example: $INSTALL_PATH/license
	 * @throws Exception
	 */
	protected static void createVirginKeyForDemoLicense(String licRepo, long installDate) throws Exception	
	{
		long elapsedTime = 30*24*60*60;// second
		String timeFund = String.valueOf(elapsedTime);//in millisecond
		String installedDate = String.valueOf(installDate);
		
		installedDate = Base64Coder.encode(installedDate);
		timeFund = Base64Coder.encode(timeFund);
		
		String virginKey = installedDate + "#" + timeFund;
		
		File f = new File(licRepo +"/"+ VIRGIN_FILE_NAME);
		
		FileOutputStream fo = new FileOutputStream(f);
		
		fo.write(virginKey.getBytes());
		fo.flush();
		fo.close();		
	}
	
	/**
	 * Update virgin key file for count down elapsed time
	 * <li> Read virgin file and parse content
	 * <li> Calculate virgin data, update elapsed time
	 * <li> Write new virgin data back to file 
	 * @param path path to virgin key file
	 * @return
	 * <li> 0 if success
	 * <li> 1 if user roll-back system date/time
	 * <li> 2 30 days try-out expired
	 * @throws Exception 
	 */
	protected static int updateVirgin()throws Exception{
		/** TODO read virgin key from file */
		int ret = 0;
		String virginStr = readVirginFileName();
		if (virginStr == null || virginStr.equals(""))
			throw new Exception(" Invalid content of virgin key");
		String[] virginTokens = virginStr.split("#");
		String installedDate  = virginTokens[0];
		String timeFund       = virginTokens[1];
		
		long longInstalledDate = Long.parseLong(
				Base64Coder.decode(installedDate));
		long remainTimeFund = Long.parseLong(
				Base64Coder.decode(timeFund));
		if(remainTimeFund <= 0 )ret = 2;
		
		long currentVirginDate = DEMO_TIME_FUND*1000 
		- remainTimeFund*1000 + longInstalledDate;
		long curSysDate = System.currentTimeMillis();
		
				
		/** If not follow clock wise, return false for stop using SIC*/
		if (curSysDate < currentVirginDate - 12*60*60*1000)ret = 1;
		
		
		/** Calculate new time fund*/
		
		remainTimeFund = (30*24*60*60) 
		- (curSysDate - longInstalledDate)/1000;
		if(remainTimeFund <= 0 )ret = 2;
		
		remainDays = (int)remainTimeFund/24/60/60;
		/** Re-encode timeFund with new value */
		timeFund = Base64Coder.encode(String.valueOf(remainTimeFund));
		virginStr = installedDate + "#" + timeFund;		
		
		/** TODO write virgin key from file */
		writeVirginFile(virginStr);
		return ret;
	}
	
	/**
	 * Read content of virgin key file from specific path
	 * @param fromPath virgin key file path
	 * @return String represent content of the file
	 * @throws IOException
	 */
	private static String readVirginFileName() throws Exception
	{
		String ret = "";
		String fromPath = getVirginPath();
		File f = new File(fromPath);
		if(!f.exists())
			createVirginKeyForDemoLicense(f.getParent(), System.currentTimeMillis());
		FileInputStream fi = new FileInputStream(f);
		int totalByte = fi.available();
		byte[] bytes = new byte[totalByte];
		int readBytes = fi.read(bytes);
		if ( readBytes > 0 )
		{
			ret = new String(bytes);
		}
		fi.close();
		return ret;
	}
	
	/**
	 * Write encoded content to file
	 * @param toPath path to save virgin key file
	 * @param content encoded content string
	 * @throws IOException
	 */
	private static void writeVirginFile(String content) throws Exception
	{
		String toPath = getVirginPath();
		File f = new File(toPath);
		FileOutputStream fo = new FileOutputStream(f);
		fo.write(content.getBytes());
		fo.flush();//flush to device
		fo.close();
	}
	private static String getVirginPath() throws Exception
	{
		if(virginPath != null )return virginPath;
		
		PropertyUtil propUtl = new PropertyUtil();
		String licPath = propUtl.getProperty(LIC_KEY);
		File licFile = new File(licPath);
		
		File licRepo = licFile.getAbsoluteFile().getParentFile();
		try {
			if (licRepo.exists()) {
				virginPath = licRepo.getAbsolutePath() + "/" + VIRGIN_FILE_NAME;
			}
		} catch (Exception e) {
			SystemMessageDialog.showMessageDialog(null, "Error creating: "+ licFile.getAbsolutePath(), SystemMessageDialog.SHOW_OK_OPTION);
		}
		return virginPath;
	}
	/////////// To run JUnit test pls copy conf folder
	/////////// from root project folder to src folder ///////////
//	@Test
	public void testCreateVirgin() throws Exception{
		LicenseUtil.createVirginKeyForDemoLicense("E:\\My_Projects\\SIC\\SIC-Projects\\SIC_Retailer\\license\\",System.currentTimeMillis());
	}
	public static void main(String[] args) throws Exception
	{
		LicenseUtil.createVirginKeyForDemoLicense("E:\\My_Projects\\SIC-Projects\\SIC_Watch\\license",System.currentTimeMillis());
	}
//	
//	@Test
//	public void testUpdateVirgin() throws Exception
//	{
//		int ret = LicenseUtil.updateVirgin();
//		if (ret !=0 )
//		{
//		}
//	}
	
}
