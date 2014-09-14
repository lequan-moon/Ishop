/*****************************************************************************/
/* File Description  : StoppableThread                                       */
/* File Version      : 1.0                                                   */
/* Legal Copyright   : Copyright (c) 2004-2007 by shiftTHINK Ltd. Liab. Co.  */
/* Company Name      : shiftTHINK Ltd. Liab. Co.                             */
/* Original Filename : StoppableThread.java                                  */
/* Product Version   : 1.0                                                   */
/* Product Name      : CONNECT Project 							             */
/*****************************************************************************/

package com.nn.ishop.client.util;

/**
 * <p>Title: Wrapper class for StoppableThread</p>
 *  <p>Description: This class represents StoppableThread</p>
 *  <p>Copyright: Copyright (c) 2006</p>
 *  <p>Company: shiftTHINK Ltd.</p>
 *
 * @author Uris Kalatchoff
 * @version 1.0
 */
public class StoppableThread extends Thread {
    /**
     * stop status
     */
    protected boolean stop = false;

    /**
     * stop thread
     */
    public void doStop() {
        stop = true;
    }
}
