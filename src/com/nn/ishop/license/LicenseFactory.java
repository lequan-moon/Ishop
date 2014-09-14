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
 * Generalize LicenseManager
 * use for application License verification and upgrade
 * @author nvnghia
 *
 */
public class LicenseFactory
{

    public LicenseFactory()
    {
    }

    /**
	 * @return singleton LicenseManager
	 */
	public static ILicenseManager getLicenseManager(boolean initProps)
    {
        if(licenseManager == null)
            synchronized(com.nn.ishop.license.LicenseFactory.class)
            {
                if(licenseManager == null)
                    licenseManager = LicenseManagerImpl.getInstance(initProps);
            }
        return licenseManager;
    }

    public static void setLicenseManager(ILicenseManager licManager)
    {
        licenseManager = licManager;
    }

    private static ILicenseManager licenseManager;
}
