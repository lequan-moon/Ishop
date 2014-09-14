/*****************************************************************************/
/* File Description  : Identifiable                                          */
/* File Version      : 1.0                                                   */
/* Legal Copyright   : Copyright (c) 2004-2007 by shiftTHINK Ltd. Liab. Co.  */
/* Company Name      : shiftTHINK Ltd. Liab. Co.                             */
/* Original Filename : Identifiable.java                                     */
/* Product Version   : 1.0                                                   */
/* Product Name      : CONNECT Project                                       */
/*****************************************************************************/

package com.nn.ishop.client.util;

/**
 * <p>Title: Identifiable</p>
 *  <p>Description: This interface represents Identifiable items/values (items
 *     which have an associated id)</p>
 *  <p>Copyright: Copyright (c) 2006</p>
 *  <p>Company: shiftTHINK Ltd.</p>
 *
 * @author Uris Kalatchoff
 * @version 1.0
 */
public interface Identifiable {

    /**
    * get id of Identifiable
    *
    * @return id id of Identifiable
    */
   public String getId();

   /**
    * set value for Identifiable
    *
    * @param value value of Identifiable
    */
   public void setValue(String value);

   /**
    * get value of Identifiable
    *
    * @return value value of Identifiable
    */
   public String getValue();
}