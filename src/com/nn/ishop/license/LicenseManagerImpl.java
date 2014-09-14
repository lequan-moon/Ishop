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

import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * Implementation for maniputlating License data
 * @author nvnghia@cmc.com.vn
 *
 */
public class LicenseManagerImpl
    implements ILicenseManager
{
	/** Singleton instance */
	private static ILicenseManager licenseManager;	
	IShopLicense license = null;
	PropertyUtil propUtil;

    /**
     * @return singleton instance of LicenseManager.class
     */
    public static ILicenseManager getInstance(boolean initProps)
    {
        if(licenseManager == null)
            synchronized(com.nn.ishop.license.LicenseManagerImpl.class)
            {            	
                if(licenseManager == null)
                    licenseManager = new LicenseManagerImpl(initProps);                
            }
        return licenseManager;
    }

    /**
     * @param initProp true if initialize properties and vice versa
     */
    public LicenseManagerImpl(boolean initProp)
    {
    	if(initProp)
    	propUtil = new PropertyUtil();
        license = null;
    }
    
	/* 
	 * @see com.nn.ishop.licadmin.license.LicenseManager#hasValidLicense(boolean)
	 */
	public IShopLicense hasValidLicense() throws LicenseException
    {
		try{	
			license = getLicense();
			/** 
			 * TODO: 
			 * Loading last count down date in file.
			 * - With Demo license will check for expired date
			 * - With other type pass this day trial check, we may
			 *   need to check other information only.
			 */
			if(license.getEdition()
					.equals(LicenseUtil.DEMO_LICENSE_TYPE))
			updateElapsedTime();					
		}
		catch(Exception ioe)
		{
			ioe.printStackTrace();
			throw new LicenseException(ioe.getMessage());
		}
        return license;
    }
	/**
	 * Update elapsed time into a virgin key file
	 * @throws LicenseException
	 */
	private void updateElapsedTime() throws LicenseException
	{		
		/** TODO update elapsed time code go here */
		int virginRet = 0;
		try
		{
			virginRet = LicenseUtil.updateVirgin();	
			license.setTotalRemainDate(LicenseUtil.remainDays);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new LicenseException("Unhandle exception:" 
					+ e.getMessage());
		}
		switch(virginRet)
		{
			case 1:
				throw new LicenseException("System date/time has been rool back by user. Demo license expired!");
			case 2:
				throw new LicenseException("Demo license expired normally!");
			default:break;
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.nn.ishop.licadmin.ILicenseManager#getLicense(java.lang.String, java.lang.String)
	 */
	protected IShopLicense getLicense(String licPath) 
	throws LicenseException
    {
//        if(license != null)
//        	return license;
        try
        {	
            FileInputStream licfis = new FileInputStream(licPath);
			byte[] allLicBytes = new byte[licfis.available()];
			licfis.read(allLicBytes);
			licfis.close();
			// NOTICE: Ch(35) == '#'
			/** counting total lic+priv byte */
			int count = 0;
			int totalLicByte = 1;
			int licLength = allLicBytes.length;
			while (count<2)
			{
				for(int i=0;i<licLength;i++ )
				{
					if((char)allLicBytes[i]=='#')
					{
						count +=1;
						if (count == 2) break;
					}else
					{
						totalLicByte++;
					}
				}
			}
			byte[] licBytes = new byte[totalLicByte];
			for(int i=0;i<totalLicByte;i++)
			{
				licBytes[i] = allLicBytes[i];
			}
			
			/** minute '#' while calculating public key byte */
			byte[] pkBytes = new byte[allLicBytes.length
			                          - (totalLicByte + 1)];
			int pkLength = pkBytes.length;
			for(int i = 0;i < pkLength;i++)
			{
				pkBytes[i] = allLicBytes[totalLicByte+1+i];
			}
			/** Generate public key */
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(pkBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(pubKeySpec);

			String licenseString = new String(licBytes/*allLicBytes*/);
			
			// verify license
			license = LicenseVerifier.validateLicense(
						licenseString
						, publicKey);
			return license;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new LicenseException("get License Error: " 
					+ e.getMessage());
		}
    }	

    /* 
     * @see com.nn.ishop.licadmin.license.LicenseManager#getLicense(boolean)
     */
    protected IShopLicense getLicense() throws LicenseException
    {
//        if(license != null)
//        	return license;
        try
        {	
        	String licPath = propUtil.getProperty(LicenseUtil.LIC_KEY);
            FileInputStream licfis
            = new FileInputStream(licPath);
			byte[] allLicBytes = new byte[licfis.available()];
			licfis.read(allLicBytes);
			licfis.close();
			// NOTICE: Ch(35) == '#'
			/** counting total lic+priv byte */
			int count = 0;
			int totalLicByte = 1;
			int licLength = allLicBytes.length;
			while (count<2)
			{
				for(int i=0;i<licLength;i++ )
				{
					if((char)allLicBytes[i]=='#')
					{
						count +=1;
						if (count == 2) break;
					}else
					{
						totalLicByte++;
					}
				}
			}
			byte[] licBytes = new byte[totalLicByte];
			for(int i=0;i<totalLicByte;i++)
			{
				licBytes[i] = allLicBytes[i];
			}
			/** minute '#' while calculating public key byte */
			byte[] pkBytes = new byte[allLicBytes.length
			                          - (totalLicByte + 1)];
			int pkLength = pkBytes.length;
			for(int i = 0;i < pkLength;i++)
			{
				pkBytes[i] = allLicBytes[totalLicByte+1+i];
			}
			/** Generate public key */
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(pkBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(pubKeySpec);

			String licenseString = new String(licBytes/*allLicBytes*/);
			
			// verify license
			license = LicenseVerifier.validateLicense(
						licenseString
						, publicKey);
			return license;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new LicenseException("get License Error: " 
					+ e.getMessage());
		}
    }	            
	
	/**
	 * For test only
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args)
	throws Exception
	{
		LicenseManagerImpl licMng = new LicenseManagerImpl(true);
		IShopLicense cLic = licMng.hasValidLicense();
	}


	/* (non-Javadoc)
	 * @see com.nn.ishop.license.ILicenseManager#hasValidLicense(java.lang.String, java.lang.String)
	 */
	public IShopLicense hasValidLicense(String licPath)
			throws LicenseException {
		try{	
			license = getLicense(licPath);
			if(license.getEdition()
					.equals(LicenseUtil.DEMO_LICENSE_TYPE))
			updateElapsedTime();
		}
		catch(Exception ioe)
		{
			ioe.printStackTrace();
			throw new LicenseException("IO Exception while validate" +
					" license information "+ioe.getMessage());
		}
        return license;
	}


	/* (non-Javadoc)
	 * @see com.nn.ishop.licadmin.ILicenseManager#createVirginKeyForDemoLicense(long)
	 */
	public void createVirginKeyForDemoLicense(String licRepo
			, long installDate)
			throws Exception {
		LicenseUtil.createVirginKeyForDemoLicense(licRepo, installDate);
		
	}
}
