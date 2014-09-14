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


import java.lang.Exception;

/**
 * General Exception returned whenever there is an error in the license system
 *
 * @author Steve Fowler
 * @version $Revision$
 */
public class LicenseException extends Exception {	
	private static final long serialVersionUID = -2367770806099008428L;
	public LicenseException() {
		super();
	}
	public LicenseException(String s) {
		super(s);
	}
}