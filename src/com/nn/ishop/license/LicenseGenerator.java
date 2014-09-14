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


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;


/**
 * Generate an encrypted license
 *
 * @author nvnghia
 * @version $Revision$
 */
public class LicenseGenerator {
	PropertyUtil propUtil ;
	public LicenseGenerator(){
		propUtil = new PropertyUtil();
	}

	/**
	 * Generate ServerLicense or ClientLicense
	 *  return an encrypted license by signing the license with 
	 *  the private key file and encoding the result
	 *  in Base64
	 *  From March 10, 2008it can embedded publickey into this license
	 * @param license the unencrypted license key
	 * @param privateKeyFile the name of the file containing the private key
	 * @param publicKeyFilePath path to public key
	 * @return a Base64 representation of the encrypted license
	 * @throws LicenseException
	 */
	public static String generateLicense(String license
			, String privateKeyFilePath
			, String publicKeyFilePath
			, String licenseFilePath)
		throws LicenseException 
	{
		try 
		{
			/** Sign license message with private key file */
			String signature = signMessage(license
					, privateKeyFilePath);
			/** Merging license after encoding with the signature */
			String licenseEncoded = Base64Coder
				.encode ( license )
				+ "#" +  signature  + "#";
			EncryptionUtil utl = new EncryptionUtil();
			PublicKey pk = utl.readPublicKey(publicKeyFilePath);
			if(pk==null)throw new LicenseException("Null publickey while creating license");
			byte[] compoundBytes = new byte[licenseEncoded.getBytes().length
			                                + pk.getEncoded().length];
			byte[] licByte = licenseEncoded.getBytes();
			byte[] pkByte = pk.getEncoded();
			int licLength = licByte.length;
			int pkLength = pkByte.length;
			for(int i=0;i<licLength;i++)
			{
				compoundBytes[i] = licByte[i];
			}
			for(int i =0;i<pkLength;i++)
			{
				compoundBytes[i+licLength] = pkByte[i];
			}
			
			/** Write license to file */
			FileOutputStream fo = new FileOutputStream(
					new File(licenseFilePath));
			//fo.write(licenseEncoded.getBytes());
			fo.write(compoundBytes);
			fo.flush();
			fo.close();			
			return licenseEncoded;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new LicenseException( e.getMessage() );	
		}
	}

	private static String signMessage(String message
			, String privateKeyFile)
		throws IOException
		, NoSuchAlgorithmException
		, NoSuchProviderException
		, InvalidKeyException
		, SignatureException
		, InvalidKeySpecException 
	{
		if (message == null)
			throw new SignatureException("No Message to sign");

		EncryptionUtil signer = new EncryptionUtil();
		String signature = signer.sign(message, privateKeyFile);
		return signature;
	}


	public static void saveSignature(String signature
			, String signatureFile)
		throws IOException {
        FileOutputStream sigfos 
        = new FileOutputStream(signatureFile);
        sigfos.write(signature.getBytes());
        sigfos.close();
	}

	/**
	 * read a private key from the specified file
	 * @param URI name of the file containing the private key
	 * @return private key read from file
	 * @throws LicenseException
	 */
	public static PrivateKey readPrivateKey( String URI ) 
	throws LicenseException
	{
		try
		{
			EncryptionUtil keyUtil = new EncryptionUtil();
			PrivateKey privateKey = keyUtil.readPrivateKey( URI );
			return privateKey;
		}
		catch (Exception e)
		{
			throw new LicenseException("Unable to read Key File:" 
					+ e.getMessage());
		}
	}
	
//	@Test
//	public void testGenerateServerLicense()throws Exception{
//		Exception e1 = null;
//		String licEnc = null;
//		try{
//			
//			ConnectLicense license = new ConnectLicense("shiftTHINK liab. GmbH"
//				,LicenseUtil.CONNECT_DEMO_LICENSE_TYPE //license type
//				, 5 //namedUsers
//				, LicenseUtil.getRandomSerialNumber()
//				, 5 //concurrent users
//				, "nvnghia@shift-think.com"
//				, LicenseUtil.CONNECT_ALL_FEATURE				
//				);
//		licEnc = LicenseGenerator.generateLicense(
//				license.getLicenseKey()
//				, propUtil.getProperty("privateKeyFile"
//						,"private.key")
//				, propUtil.getProperty("licenseFile"
//						,"license.dat")
//				);
//		}
//		catch(Exception e)
//		{
//			e1 = e;
//		}
//		TestCase.assertNull(e1);
//		TestCase.assertNotNull(licEnc);
//	}


}