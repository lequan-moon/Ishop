/*****************************************************************************/
/* File Description  : CryptoToolkit                                         */
/* File Version      : 1.0                                                   */
/* Legal Copyright   : Copyright (c) 2004-2007 by shiftTHINK Ltd. Liab. Co.  */
/* Company Name      : shiftTHINK Ltd. Liab. Co.                             */
/* Original Filename : CryptoToolkit.java                                    */
/* Product Version   : 1.0                                                   */
/* Product Name      : CONNECT Project                                       */
/*****************************************************************************/

package com.nn.ishop.client.util;

 import java.io.*;

import java.security.*;
import java.security.cert.CertificateException;
import javax.crypto.*;


/**
 * <p>Title: Wrapper class for CryptoToolkit</p>
 *  <p>Description: This class implements CryptoToolkit</p>
 *  <p>Copyright: Copyright (c) 2006</p>
 *  <p>Company: shiftTHINK Ltd.</p>
 *
 * @author Michael Böni, Uris Kalatchoff, Nguyen Hung Cuong
 * @version 1.0
 */
public class CryptoToolkit 
{
  private static final String MD5 = "MD5";
  private static final String SHA1 = "SHA-1";
  private static final String SHA256 = "SHA-256";
  private static final String SHA384 = "SHA-384";
  private static final String SHA512 = "SHA-512";
  
  private static File file = new File("cfg/ks_client.jks");


    /**
     * encryptObject Encrypts any serializable object with a given cipher.
     *
     * @param obj Serializable
     * @param cipher Cipher
     *
     * @return SealedObject
     */

    public static SealedObject encryptObject(Serializable obj, Cipher cipher) {
        SealedObject so = null;

        try {
            so = new SealedObject(obj, cipher);
        } catch (IllegalBlockSizeException ex) {
            // Add proper error handling here
            ex.printStackTrace();
        } catch (IOException ex) {
            // Add proper error handling here
            ex.printStackTrace();
        }

        return so;
    }

    /**
     * decryptObject Decrypts an encrypted object and returns an object of type "serializable".
     *
     * @param sealedObj SealedObject
     * @param cipher Cipher
     *
     * @return originalObject an instance of Serializable
     */
    public static Serializable decryptObject(SealedObject sealedObj, Cipher cipher) {
        Serializable originalObject = null;

        try {
            originalObject = (Serializable) sealedObj.getObject(cipher);
        } catch (BadPaddingException ex) {
            // Add proper error handling here
            ex.printStackTrace();
        } catch (IllegalBlockSizeException ex) {
            // Add proper error handling here
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            // Add proper error handling here
            ex.printStackTrace();
        } catch (IOException ex) {
            // Add proper error handling here
            ex.printStackTrace();
        }

        return originalObject;
    }

    /**
     * generateKeyAES Generate a random 128 Bit AES key for encryption
     *
     * @return key an instance of SecretKey
     */
    public static SecretKey generateKeyAES() {
        SecretKey key = null;

        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(128);
            key = generator.generateKey();
        } catch (NoSuchAlgorithmException ex) {
            // Add proper error handling here
            ex.printStackTrace();
        }

        return key;
    }

    /**
     * getEncryptionCipher Get an encryption cipher with a given private key. 
     * This cipher is used for the encryption of objects.
     *
     * @param secretKey SecretKey
     *
     * @return cipher Cipher
     */
    public static Cipher getEncryptionCipher(SecretKey secretKey) {
        try {
            Cipher cipher = null;

            // Initialisation of cipher object for Encryption.
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            return cipher;
        } catch (InvalidKeyException ex) {
            // Add proper error handling here
            ex.printStackTrace();

            return null;
        } catch (NoSuchPaddingException ex) {
            // Add proper error handling here
            ex.printStackTrace();

            return null;
        } catch (NoSuchAlgorithmException ex) {
            // Add proper error handling here
            ex.printStackTrace();

            return null;
        }
    }

    /**
     * getDecryptionCipher Get a decryption cipher with a given private key. 
     * This cipher is used for the decryption of objects.
     *
     * @param secretKey SecretKey
     *
     * @return cipher Cipher
     */
    public static Cipher getDecryptionCipher(SecretKey secretKey) {
        try {
            Cipher cipher = null;
            // Initialisation of cipher object for Encryption.
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            return cipher;
        } catch (InvalidKeyException ex) {
            // Add proper error handling here
            ex.printStackTrace();

            return null;
        } catch (NoSuchPaddingException ex) {
            // Add proper error handling here
            ex.printStackTrace();

            return null;
        } catch (NoSuchAlgorithmException ex) {
            // Add proper error handling here
            ex.printStackTrace();

            return null;
        }
    }

    /**
     * saveSecretKey Save the key to the harddisk for later retrieval.
     *
     * @param secretKey SecretKey
     *
     * @deprecated
     */
    public static void saveSecretKey(SecretKey secretKey) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new 
            		FileOutputStream("cfg/kf_cache.ser"));
            out.writeObject(secretKey);
            out.close();
        } catch (IOException ex) {
            // Add proper error handling here
            ex.printStackTrace();
        }
    }

    /**
     * loadSecretKey Loads a saved secret key from the disk.
     *
     * @return SecretKey
     *
     * @deprecated
     */
    public static SecretKey loadSecretKey() {
        SecretKey secretKey = null;

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("cfg/kf_cache.ser"));
            secretKey = (SecretKey) in.readObject();
            in.close();
        } catch (ClassNotFoundException ex) {
            // Add proper error handling here
            ex.printStackTrace();

            return null;
        } catch (IOException ex) {
            // Add proper error handling here
            ex.printStackTrace();

            return null;
        }

        return secretKey;
    }

    /**
     * saveSecretKeyToKeystore
     *
     * @param keyAlias An alias for the key to be stored
     * @param secretKey SecretKey
     * @param password String
     */
    public static void saveSecretKeyToKeystore(String keyAlias, SecretKey secretKey, String password) 
    {
        File file = new File("cfg/ks_client.jks");
        FileInputStream in = null;
        FileOutputStream out = null;

        if (!file.exists()) {
            CryptoToolkit.createKeyStore(password);
        }

        try 
        {
            KeyStore ks = KeyStore.getInstance("JCEKS");
            in = new FileInputStream("cfg/ks_client.jks");
            ks.load(in, password.toCharArray());
            in.close();
            ks.setKeyEntry(keyAlias, secretKey, password.toCharArray(), null);

            out = new FileOutputStream("cfg/ks_client.jks");
            ks.store(out, password.toCharArray());
            out.close();
        } catch (CertificateException ex) {
            // Add proper error handling here
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            // Add proper error handling here
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            // Add proper error handling here
            ex.printStackTrace();
        } catch (IOException ex) {
            // Add proper error handling here
            ex.printStackTrace();
        } catch (KeyStoreException ex) {
            // Add proper error handling here
            ex.printStackTrace();
        }
        finally
        {
        	in = null;
            out = null;
        }
    }

    /**
     * loadSecretKeyFromKeystore
     *
     * @param keyAlias The alias for the key to be retrieved
     * @param password String
     *
     * @return SecretKey
     */
    public static SecretKey loadSecretKeyFromKeystore(
    		String keyAlias, String password) {
        KeyStore ks = null;
        SecretKey secretKey = null;
        FileInputStream in = null;
        
        try 
        {            
            if (file.exists()) 
            {
                ks = KeyStore.getInstance("JCEKS");
                in = new FileInputStream("cfg/ks_client.jks");

                ks.load(in, password.toCharArray());
                secretKey = (SecretKey) ks.getKey(keyAlias, 
                		password.toCharArray());
                in.close();
            }
        } catch (CertificateException ex) {
            // Add proper error handling here
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            // Add proper error handling here
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            // Add proper error handling here
            ex.printStackTrace();
        } catch (IOException ex) {
            // Add proper error handling here
            ex.printStackTrace();
        } catch (KeyStoreException ex) {
            // Add proper error handling here
            ex.printStackTrace();
        } catch (UnrecoverableKeyException ex) {
            // Add proper error handling here
            ex.printStackTrace();
        }
        finally
        {
        	in = null;
        }

        return secretKey;
    }

    /**
     * createKeyStore     
     * @param password password need stored
     */
    private static void createKeyStore(String password) {
        KeyStore ks = null;
        FileOutputStream out = null;

        try {
            ks = KeyStore.getInstance("JCEKS");

            out = new FileOutputStream("cfg/ks_client.jks");

            ks.load(null, password.toCharArray());
            ks.store(out, password.toCharArray());
            out.close();
        } catch (IOException ex) {
            // Add proper error handling here
            ex.printStackTrace();
        } catch (KeyStoreException ex) {
            // Add proper error handling here
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            // Add proper error handling here
            ex.printStackTrace();
        } catch (CertificateException ex) {
            // Add proper error handling here
            ex.printStackTrace();
        }
        finally
        {
        	out = null;
        	ks = null;
        }

        //return ks;
    }

    /**
     * Takes a string and returns an MD5 hash. MD5 is provided for
     * compatibility and should not be used anymore for security reasons.
     *
     * @param message String
     * @return String
     * @deprecated Please use more secure algorithms.
     */
    public static String getMD5Hash(String message)
    {
      return doHash(message, MD5);
    }

    /**
     * Takes a string and returns its SHA1 hash.
     *
     * @param message String
     * @return String
     */
    public static String getSHA1Hash(String message)
    {
      return doHash(message, SHA1);
    }

    /**
     * Takes a string and returns its SHA-256 hash.
     *
     * @param message String
     * @return String
     */
    public static String getSHA256Hash(String message)
    {
      return doHash(message, SHA256);
    }

    /**
     * Takes a string and returns its SHA-384 hash.
     *
     * @param message String
     * @return String
     */
    public static String getSHA384Hash(String message)
    {
      return doHash(message, SHA384);
    }

    /**
     * Takes a string and returns its SHA-512 hash.
     *
     * @param message String
     * @return String
     */
    public static String getSHA512Hash(String message)
    {
      return doHash(message, SHA512);
    }

    /**
     * doHash is private and does the actual digest calculations. 
     * The resulting
     * hash is hex-encoded.
     *
     * @param message String
     * @param algorithm String
     * @return String
     */
    private static String doHash(String message, String algorithm)
    {
      byte[] digest;
      MessageDigest digestAlgo = null;
      try
      {
        digestAlgo = MessageDigest.getInstance(algorithm);
      }
      catch (NoSuchAlgorithmException ex)
      {
        ex.printStackTrace();
      }
      StringBuffer strbuf = new StringBuffer();

      digestAlgo.update(message.getBytes(), 0, message.length());
      digest = digestAlgo.digest();

      strbuf.append(Util.toHexString(digest));

      String hashHEX = strbuf.toString().toLowerCase();

      return hashHEX;
    }
}
