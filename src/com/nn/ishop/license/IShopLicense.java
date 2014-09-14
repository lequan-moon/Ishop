/* ***************************************************************************/
/* File Description  : EncryptionUtil                                        */
/* File Version      : 1.0                                                   */
/* Legal Copyright   : Copyright © 2004-2010 by DONGA JSC                    */
/*                     All Rights Reserved                                   */
/* Company Name      : DONGA JSC.                                            */
/* Original Filename : EncryptionUtil.java                                   */
/* Product Version   : 1.0                                                   */
/* Product Name      : STAND ALONE LICENSE API                               */
/*****************************************************************************/
package com.nn.ishop.license;


import java.io.Serializable;

/**
 * 
 * @author nvnghia
 *
 */
public class IShopLicense  implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -46826014283311649L;

	/** License belong to company */
	private String customerName;
	
	/** 
	 * Edition or license type 
	 * Valid values are: ENT, PRO, DEMO
	 */
	private String edition = "";
	
	private int namedUsers = 0;	

	/** A serial number for a license, unique number */
	private String licenseSerial;
	
	private int totalRemainDate =0;
	
//	/** Number of concurrent users */
//	private int concurentUser = 0;
	
//	/** Email of customer contact person */
//	private String customerEmail = "";
	
	/** Feature Set */
	private String featureSet = "";
	
	
//	PropertyUtil propUtil = new PropertyUtil();
	
	/**
	 * Simple Constructor
	 * @param licenseKey coded String represent for 
	 * license discriminated by "#"
	 * @throws LicenseException
	 */
	public IShopLicense(String licenseKey)
		throws LicenseException
	{
		setLicenseKey(licenseKey);
	}
	

	/**
	 * Constructor a license with full information
	 * @param customerName
	 * @param licenseEdition
	 * @param namedUsers
	 * @param licenseSerial
	 * @param concurentUser
	 * @param customerEmail
	 * @throws LicenseException
	 */
	public IShopLicense(String customerName
			  , String licenseEdition
			  , int namedUsers
			  , String licenseSerial
//			  , int concurentUser
//			  , String customerEmail
			  , String featureSet) 
		throws LicenseException
	{
		setCustomerName(customerName);
		setEdition(licenseEdition);
		setNamedUsers(namedUsers);		
		setLicenseSerial(licenseSerial);
//		setConcurentUser(concurentUser);
//		setCustomerEmail(customerEmail);
		setFeatureSet(featureSet);
	}	

  /**
   * Pasering license string by "#" character
   * @param licenseKey
   * @throws LicenseException
   */
  public void setLicenseKey(String licenseKey)
  	throws LicenseException
  {
	// do not catch exception here, all integer string must be
	// parseable to a number
	String tokens[] = licenseKey.split("#");
	if(tokens.length != 5)
		throw new LicenseException("License is not valid");
	setCustomerName(tokens[0]);
	setEdition(tokens[1]);	
	setNamedUsers(Integer.parseInt(tokens[2]));
	setLicenseSerial(tokens[3]);
//	setConcurentUser(Integer.parseInt(tokens[4]));
//	setCustomerEmail(tokens[5]);
	setFeatureSet(tokens[4]);
  }
  public String getLicenseKey()
  {
	String dis = "#";
    return getCustomerName() + dis + getEdition() 
    + dis + String.valueOf(getNamedUsers())
    + dis + getLicenseSerial() + dis 
//    + String.valueOf(getConcurentUser())
//    + dis + getCustomerEmail() 
    + dis + getFeatureSet();
     
  }  

  public String getCustomerName()
  {
    return customerName;
  }
  
  public void setCustomerName( String customerName )
  {
  	this.customerName = customerName;
  }

	public String getLicenseSerial() {
		return licenseSerial;
	}
	
	public void setLicenseSerial(String licenseSerial) {
		this.licenseSerial = licenseSerial;
	}
	
	public String getEdition() {
		return edition;
	}
	
	public void setEdition(String edition) {
		this.edition = edition;
	}

	public int getNamedUsers() {
		return namedUsers;
	}
	
	public void setNamedUsers(int namedUsers) {
		this.namedUsers = namedUsers;
	}
	
//	public int getConcurentUser() {
//		return concurentUser;
//	}
//	
//	public void setConcurentUser(int concurentUser) {
//		this.concurentUser = concurentUser;
//	}
//	
//	public String getCustomerEmail() {
//		return customerEmail;
//	}
//	
//	public void setCustomerEmail(String customerEmail) {
//		this.customerEmail = customerEmail;
//	}


	public String getFeatureSet() {
		return featureSet;
	}


	public void setFeatureSet(String featureSet) {
		this.featureSet = featureSet;
	}


	public int getTotalRemainDate() {
		return totalRemainDate;
	}


	public void setTotalRemainDate(int totalRemainDate) {
		this.totalRemainDate = totalRemainDate;
	}
}

