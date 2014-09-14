/*****************************************************************************/
/* File Description  : CredentialSetter                                      */
/* File Version      : 1.0                                                   */
/* Legal Copyright   : Copyright (c) 2004-2007 by shiftTHINK Ltd. Liab. Co.  */
/* Company Name      : shiftTHINK Ltd. Liab. Co.                             */
/* Original Filename : CredentialSetter.java                                 */
/* Product Version   : 1.0                                                   */
/* Product Name      : CONNECT Project 							             */
/*****************************************************************************/

package com.nn.ishop.client.util;

/**
 * <p>Title: Class implements CredentialSetter</p>
 *  <p>Description: This class implements CredentialSetter</p>
 *  <p>Copyright: Copyright (c) 2006</p>
 *  <p>Company: shiftTHINK Ltd.</p>
 *
 * @author Uris Kalatchoff
 * @version 1.0
 */
public class CredentialSetter {
    String username = "";
    String password = "";
    boolean okPressed = false;

    /**
     * Creates a new CredentialSetter object.
     */
    public CredentialSetter() {
    }

    /**
     * get username
     *
     * @return username username
     */
    public String getUsername() {
        return username;
    }

    /**
     * get password
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * set username
     *
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * set password
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * get OkPressed
     *
     * @return okPressed 
     */
    public boolean getOkPressed() {
        return okPressed;
    }

    /**
     * set OkPressed
     *
     * @param okPressed 
     */
    public void setOkPressed(boolean okPressed) {
        this.okPressed = okPressed;
    }
}
