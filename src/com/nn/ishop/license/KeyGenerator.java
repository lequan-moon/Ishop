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
import java.security.PrivateKey;
import java.security.PublicKey;


/**
 * @author nvnghia
 *
 */
public class KeyGenerator
{
	private PublicKey publicKey;
	private PrivateKey privateKey;
	EncryptionUtil keygen;
	
	/**
	 * Constructor
	 */
	public KeyGenerator()
    {
		keygen = new EncryptionUtil();
    }

    /**
     * Create a public/private key pair 
     * and save the keys to the specified files
     * Not allow to overwrite old key pairs
	 * if administrator want to create new key pairs
	 * , firstly removing the old one
	 * @param publicURI file name to store public key in
	 * @param privateURI file name to store private key in
	 * @throws LicenseException
	 */
	public void createKeys(String publicURI, String privateURI)
    	throws LicenseException
    {
    	try 
    	{
    		/** 
    		 * Not allow to overwrite old key pairs
    		 * if administrator want to create new key pairs
    		 * , firstly removing the old one
    		 */
    		File pubFile 
    		= new File(publicURI);
    		if(pubFile.exists())
    		throw new LicenseException("Public key already exist in: " + publicURI );
    		
    		File priFile = new File(privateURI);
    		if (priFile.exists())
    			throw new LicenseException("Private key already exist in: "+ privateURI);
    		
			keygen.generateKeys( publicURI, privateURI );
			publicKey = keygen.getPublic();
			privateKey = keygen.getPrivate();
    	}
    	catch (Exception e)
    	{
    		throw new LicenseException ( e.getMessage() );
    	}
    }

	/**
	 * Read public/private keys from specified files
	 * 
	 * @param publicURI name of file containing the public key
	 * @param privateURI name of file containing the private key
	 * @throws LicenseException
	 */
	public void readKeys( String publicURI, String privateURI ) throws LicenseException
	{
		try
		{
			keygen.readKeys( publicURI, privateURI );
			publicKey = keygen.getPublic();
			privateKey = keygen.getPrivate();
		}
		catch (Exception e)
		{
			throw new LicenseException("Unable to read Key Files: " + e.getMessage());
		}
	}

	/**
	 * read a public key from a file
	 * 
	 * @param URI name of file containing the key
	 * @throws LicenseException
	 */
	public void readPublicKey( String URI ) throws LicenseException
	{
		try
		{
			publicKey = keygen.readPublicKey( URI );
		}
		catch (Exception e)
		{
			throw new LicenseException("Unable to read Key File:" + e.getMessage());
		}
	}

	/**
	 * read a private key from a file
	 * 
	 * @param URI name of file containing the key
	 * @throws LicenseException
	 */
	public void readPrivateKey( String URI ) throws LicenseException
	{
		try
		{
			privateKey = keygen.readPrivateKey( URI );
		}
		catch (Exception e)
		{
			throw new LicenseException("Unable to read Key File:" + e.getMessage());
		}
	}

	public PublicKey getPublic()
	{
		return publicKey;
	}
	
	public PrivateKey getPrivate()
	{
		return privateKey;
	}
	
	public String getPublicString()
	{
		return publicKey.toString();
	}
	public String getPrivateString()
	{
		return privateKey.toString();
	}

//	@Test
//    public void testGenerateClientKeys() throws Exception
//    {
//		Exception commEx = null;
//		PropertyUtil propUtil = new PropertyUtil();
//		
//		String publicURI = propUtil.getProperty(
//				"clientPublicKeyFile"
//				, ".client_public.key");
//		String pivateURI = propUtil.getProperty(
//				"clientPrivateKeyFile"
//				, ".client_private.key");
//		
//    	try {    	
//    		KeyGenerator keygen = new KeyGenerator();
//			keygen.createKeys( publicURI, pivateURI );			
//    	}
//    	catch ( Exception e)
//    	{
//    		e.printStackTrace();
//    		commEx = e;
//    	}
//    	
//    	TestCase.assertNull(commEx);
//    }
//	@Test
//    public void testGenerateServerKeys() throws Exception
//    {
//		Exception commEx = null;
//		PropertyUtil propUtil = new PropertyUtil();
//		
//		String publicURI = propUtil.getProperty(
//				"serverPublicKeyFile"
//				, ".server_public.key");
//		String pivateURI = propUtil.getProperty(
//				"serverPrivateKeyFile"
//				, ".server_private.key");
//		
//    	try {    	
//    		KeyGenerator keygen = new KeyGenerator();
//			keygen.createKeys( publicURI, pivateURI );			
//    	}
//    	catch ( Exception e)
//    	{
//    		e.printStackTrace();
//    		commEx = e;
//    	}
//    	
//    	TestCase.assertNull(commEx);
//    }
//
//	@Test
//	public void testReadPublicKey(){
//		Exception commEx = null;
//		PropertyUtil propUtil = new PropertyUtil();
//		String publicURI = propUtil.getProperty(
//				"clientPublicKeyFile"
//				, ".client_public.key");
//	 	try {    	
//    		KeyGenerator keygen = new KeyGenerator();
//			keygen.readPublicKey(publicURI);			
//    	}
//    	catch ( Exception e)
//    	{
//    		e.printStackTrace();
//    		commEx = e;
//    	}		
//    	TestCase.assertNull(commEx);
//	}
//	@Test
//	public void testReadPrivateKey(){
//		Exception commEx = null;
//		PropertyUtil propUtil = new PropertyUtil();
//		String privateURI = propUtil.getProperty(
//				"clientPrivateKeyFile"
//				, ".client_private.key");
//	 	try {    	
//    		KeyGenerator keygen = new KeyGenerator();
//			keygen.readPrivateKey(privateURI);			
//    	}
//    	catch ( Exception e)
//    	{
//    		e.printStackTrace();
//    		commEx = e;
//    	}		
//    	TestCase.assertNull(commEx);
//	}	
}
