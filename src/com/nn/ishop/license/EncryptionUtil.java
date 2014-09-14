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

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Provides utility functions to handle public/private key encryption.
 *
 * @author Nghia
 * @version $Revision$
 */
public class EncryptionUtil
{
	private PublicKey publicKey = null;
	private PrivateKey privateKey = null;
	
	public EncryptionUtil()
	{
	}

	/**
	* Generate a public/private key pair 
	*/
	public void generateKeys() 
		throws IOException, NoSuchAlgorithmException, NoSuchProviderException
	{
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(2048, new SecureRandom());
		KeyPair pair = keyGen.generateKeyPair();
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
	}

	/**
	* Generate a public/private key pair, and write the keys to the specified files
	* @param publicURI   name of file to store the public key in
	* @param privateURI  name of file to store the private key in
	*/
	public void generateKeys( String publicURI, String privateURI )
		throws IOException, NoSuchAlgorithmException, NoSuchProviderException
	{
		generateKeys();
		writeKeys(publicURI, privateURI);
	}

	public PublicKey getPublic()
	{
		return publicKey;
	}
	
	public PrivateKey getPrivate()
	{
		return privateKey;
	}
	
	public void writeKeys(String publicURI, String privateURI) 
	throws IOException, FileNotFoundException
	{
		writePublicKey(publicURI);
		writePrivateKey(privateURI);
	}
	
	public void writePublicKey(String URI) throws IOException, FileNotFoundException
	{
	  byte[] enckey = publicKey.getEncoded();
		/** TODO 24.01.2008 Change how to write data */
	  FileOutputStream fos = new FileOutputStream(
			  new File(URI));
      DataOutputStream dos = new DataOutputStream(fos);       
      dos.write(enckey);
      fos.close();
      dos.close();
          
//		FileOutputStream keyfos = new FileOutputStream(URI);
//		keyfos.write(enckey);
//		keyfos.close();
	}
	
	public void writePrivateKey(String URI) throws IOException, FileNotFoundException
	{
		byte[] enckey = privateKey.getEncoded();
		/** TODO 24.01.2008 Change how to write data */
		  FileOutputStream fos = new FileOutputStream(new File(URI));
	      DataOutputStream dos = new DataOutputStream(fos);       
	      dos.write(enckey);
	      fos.close();
	      dos.close();		
		
//		FileOutputStream keyfos = new FileOutputStream(URI);
//		keyfos.write(enckey);
//		keyfos.close();
	}
	
	/**
	* read public/private keys from specified files
	* @param publicURI   name of public key file
	* @param privateURI  name of private key file
	*/
	public void readKeys(String publicURI, String privateURI)
		throws IOException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException
	{
		readPublicKey(publicURI);
		readPrivateKey(privateURI);
	}

	/**
	* read public key from specified file
	* @param publicURI   name of public key file
	* @return PublicKey  public key
	*/
	public PublicKey readPublicKey(String URI) 
		throws IOException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException
	{
		FileInputStream keyfis  = new FileInputStream(new File(URI));
//		InputStream keyfis 
//		= EncryptionUtil.class.getResourceAsStream(URI);
		byte[] encKey = new byte[keyfis.available()];
		keyfis.read(encKey);
		keyfis.close();
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		publicKey = keyFactory.generatePublic(pubKeySpec);
		return publicKey;
	}
	
	/**
	* read private key from specified file
	* @param privateURI   name of private key file
	* @return PrivateKey  private key
	*/
	public PrivateKey readPrivateKey(String URI) 
		throws IOException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException
	{
		FileInputStream keyfis  = new FileInputStream(new File(URI));
//		= EncryptionUtil.class.getResourceAsStream(URI);
		byte[] encKey = new byte[keyfis.available()];
		keyfis.read(encKey);
		keyfis.close();
		PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(encKey);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		privateKey = keyFactory.generatePrivate(privKeySpec);
		return privateKey;
	}
	
	
	/**
	* sign a message using the private key
	* @param message   the message to be signed
	* @return String  the signed message encoded in Base64
	*/
	public String sign(String message) 
		throws IOException, NoSuchAlgorithmException, NoSuchProviderException,
		InvalidKeySpecException, InvalidKeyException, SignatureException
	{
		return sign(message, privateKey);
	}

	/**
	* sign a message using the private key
	* @param message   the message to be signed
	* @param message   the name of the file containing the private key
	* @return String  the signed message encoded in Base64
	*/
	public String sign(String message, String privateKeyURI) 
		throws IOException, NoSuchAlgorithmException, NoSuchProviderException,
		InvalidKeySpecException, InvalidKeyException, SignatureException, IOException
	{
		PrivateKey pk = readPrivateKey(privateKeyURI);
		return sign(message, pk);
	}

	/**
	* sign a message using the private key
	* @param message   the message to be signed
	* @param message   the private key
	* @return String   the signed message encoded in Base64
	*/
	public String sign(String message, PrivateKey privateKey) 
		throws IOException, NoSuchAlgorithmException, NoSuchProviderException,
		InvalidKeySpecException, InvalidKeyException, SignatureException
	{
		Signature rsa = Signature.getInstance("SHA1withRSA");		
		rsa.initSign(privateKey);
		/**23.01.2008 trim() message before signing*/
		rsa.update(message.getBytes());
		byte m1[] = rsa.sign();
		
		String signature = new String(Base64Coder.encode(m1));
		
		return signature;
	}

	/**
	* verify that the message was signed by the private key by using the public key
	* @param message     the message to be verified
	* @param signature   the signature generated by the private key and encoded in Base64
	* @param publicKeyURI   the name of the file containing the public key
	* @return boolean   true if the message was signed by the private key
	*/
	public boolean verify(String message, String signature, String publicKeyURI)
		throws IOException, NoSuchAlgorithmException, NoSuchProviderException,
		InvalidKeySpecException, InvalidKeyException, SignatureException
	{
		boolean ret = false;
		try{
		
			PublicKey pk = readPublicKey(publicKeyURI);
			ret = verify(message, signature, pk);
		}
		catch(Exception k)
		{
			k.printStackTrace();
			 
		}
		return ret;
	}
	
	/**
	* verify that the message was signed by the private key by using the public key
	* @param message     the message to be verified
	* @param signature   the signature generated by the private key and encoded in Base64
	* @return boolean   true if the message was signed by the private key
	*/
	public boolean verify(String message, String signature)
		throws IOException, NoSuchAlgorithmException, NoSuchProviderException,
		InvalidKeySpecException, InvalidKeyException, SignatureException
	{
		if ( publicKey == null )
			throw new InvalidKeyException("Public Key not provided.");
		return verify( message, signature, publicKey);
	}
	
	/**
	* verify that the message was signed by the private key by using the public key
	* @param message     the message to be verified
	* @param signature   the signature generated by the private key and encoded in Base64
	* @param publicKey   the public key
	* @return boolean   true if the message was signed by the private key
	*/
	public boolean verify(String message
			, String signature
			, PublicKey publicKey)
		throws IOException, NoSuchAlgorithmException, NoSuchProviderException,
		InvalidKeySpecException, InvalidKeyException, SignatureException
	{
		boolean ret = false;
		try{
			/** with RSA you can create Signature 
			 * with algorithm: SHA1withRSA, MD2withRSA, MD5withRSA
			 */
			Signature rsa = Signature.getInstance("SHA1withRSA");
			rsa.initVerify(publicKey);
			rsa.update(message.getBytes());
			
			byte sigDec[] = Base64Coder.decode(signature.toCharArray());
			ret = rsa.verify(sigDec);
		}
		catch(Exception k)
		{
			k.printStackTrace();
			 
		}
		return ret;
	}

}