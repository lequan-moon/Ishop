/*****************************************************************************/
/* File Description  : HelpSystem                                            */
/* File Version      : 1.0                                                   */
/* Legal Copyright   : Copyright (c) 2004-2007 by shiftTHINK Ltd. Liab. Co.  */
/* Company Name      : shiftTHINK Ltd. Liab. Co.                             */
/* Original Filename : HelpSystem.java                                       */
/* Product Version   : 1.0                                                   */
/* Product Name      : CONNECT Project 							             */
/*****************************************************************************/

package com.nn.ishop.client.util;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.net.URL;

/**
 * <p>Title: Wrapper class for HelpSystem</p>
 *  <p>Description: This class implements HelpSystem</p>
 *  <p>Copyright: Copyright (c) 2006</p>
 *  <p>Company: shiftTHINK Ltd.</p>
 *
 * @author Uris Kalatchoff
 * @version 1.0
 */
public class HelpSystem {
    /**
     * Creates a new HelpSystem object.
     */
    public HelpSystem() {}
    
    /**
     * launch HelpTopic by id
     *
     * @param ID id of help topic
     */
    public static void LaunchHelpTopic(String ID)
    {
//      String urlString = "file:help/connect_1.0_user_handbook.hs";
//      HelpSet hs = new HelpSet();
//      HelpBroker hb = null;
//      //ClassLoader loader = HelpSystem.class.getClassLoader();
//      
//      try
//      {
//        URL helpSetURL = new URL(urlString);
//        hs = new HelpSet(null, helpSetURL);
//        
//        hb = hs.createHelpBroker();
//
//        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
//        Point center = new Point( (int) screen.getWidth() / 2 -
//                                 (int) hb.getSize().getWidth() / 2,
//                                 (int) screen.getHeight() / 2 -
//                                 (int) hb.getSize().getHeight() / 2);
//
//        hb.setLocation(center);
//        hb.setCurrentID("welcometoshiftthinkconnect");
//        hb.setDisplayed(true);
//      }
//      catch (Exception ee)
//      {
//      	ConnectApplicationExceptionHandler.processException(
//  	    		ConnectApplicationExceptionUtil.createException(ee, 
//  	    				"HelpSet", 
//  					ConnectApplicationException.INFO)
//  		);     	
//      }
//      finally
//      {
//    	  hs = null;
//    	  hb = null;
//      }      
    }
}
