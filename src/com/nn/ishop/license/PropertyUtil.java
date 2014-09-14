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
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import junit.framework.TestCase;

import org.junit.Test;



/**
 * Utility class to interface with the license.properties file
 * just use for server module and test case
 * @author Steve Fowler
 * @version $Revision$
 */
public class PropertyUtil {
        
	public static final String defaultPropertyFile = "cfg/application_en.properties";
	
	protected String propertyFile;
	static Properties props;	
	static PropertyUtil inst;
    public PropertyUtil()
    {
    	init(defaultPropertyFile);
    	
    }
    public static PropertyUtil getInstance(){
    	return inst;
    }
	public PropertyUtil(String propertyFile) 
	{
		init(propertyFile);
	}
	
	private void init( String propertyFile )
	{
		try {
			loadProperties(propertyFile);
			inst = this;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("error loading prop file");
		}        

	}
    
    public Properties getProperties()
    {
    	return props;
    }

	public String getProperty(String propertyName)
	{
		return props.getProperty(propertyName);
	}
	public String getProperty(String propertyName, String defaultValue)
	{
		return props.getProperty(propertyName, defaultValue);
	}
	protected void loadProperties(String propertyFile) throws IOException 
	{                 
		try {			
			props = new Properties();
				props.load(new FileInputStream(propertyFile));			
		} catch (Exception e) {
			e.printStackTrace();
		}            
                        
	}   
	public static void main( String[] args )
    {
    	Properties prop = new Properties();
 
    	try {
               //load a properties file
    		props = new Properties();
			props.load(new FileInputStream(defaultPropertyFile));
//    		prop.load(new FileInputStream(defaultPropertyFile));
 
               //get the property value and print it out            
    		System.out.println(props.getProperty("login.infor.need.login"));
    		System.out.println(props.getProperty("default.language"));
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
 
    }

	@Test
	public void propertyUtilTest() throws Exception{
		/** Init and load properties */
		PropertyUtil propUtil = new PropertyUtil();
		
		String s = propUtil.getProperty("publicKeyFile");
		TestCase.assertNotNull(s);
		TestCase.assertEquals(s, ".public.key");
		
		s = propUtil.getProperty("privateKeyFile");
		TestCase.assertNotNull(s);
		TestCase.assertEquals(s, ".private.key");
		
		s = propUtil.getProperty("licenseFile");
		TestCase.assertNotNull(s);
		TestCase.assertEquals(s, ".license.dat");
		
		s = propUtil.getProperty("signatureFile");
		TestCase.assertNotNull(s);		
		TestCase.assertEquals(s, ".signature.dat");
	}
	
}
