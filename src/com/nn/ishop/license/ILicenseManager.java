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

/**
 * Interface for manipulating License data
 *
 * @author Nghia
 * @version $Revision$
 */
public interface ILicenseManager
{
    /**
     * Check if the license is valid
	 * @return true if license is valid
	 * @throws LicenseException
	 */
	public IShopLicense hasValidLicense() 
	throws LicenseException;

	/**
	 * Validate a license from specific path
	 * Public key is embedded into license file
	 * @param licPath
	 * @return
	 * @throws LicenseException
	 */
	public IShopLicense hasValidLicense(String licPath)  
	throws LicenseException;

//	/**
//	 * Validate a license from specific path
//	 * @param licPath
//	 * @param pkPath
//	 * @return
//	 * @throws LicenseException
//	 * @deprecated not use any more
//	 */
//	public ConnectLicense hasValidLicense(String licPath
//			, String pkPath)  
//	throws LicenseException;
	
	/**
	 * Create virgin key file for demo license
	 * @param installDate
	 * @throws Exception
	 */
	public void createVirginKeyForDemoLicense(String licRepo,
			long installDate) throws Exception;


}
