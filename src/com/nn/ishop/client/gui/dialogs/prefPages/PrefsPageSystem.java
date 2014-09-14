/*****************************************************************************/
/* File Description  : PrefsPageSystem                                       */
/* File Version      : 1.0                                                   */
/* Legal Copyright   : Copyright (c) 2004-2007 by shiftTHINK Ltd. Liab. Co.  */
/* Company Name      : shiftTHINK Ltd. Liab. Co.                             */
/* Original Filename : PrefsPageSystem.java                                  */
/* Product Version   : 1.0                                                   */
/* Product Name      : CONNECT Project 							             */
/*****************************************************************************/

package com.nn.ishop.client.gui.dialogs.prefPages;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * <p>Title: Connect Standalone Client</p>
 *  <p>Description: This class represents PrefsPageSystem</p>
 *  <p>Copyright: Copyright (c) 2004</p>
 *  <p>Company: shiftTHINK Ltd.</p>
 *
 * @author Dr. Michael B�ni
 * @version 1.0
 */
public class PrefsPageSystem extends JPanel {
    JPanel serverPanel = new JPanel();
    Border border1;
    TitledBorder titledBorder1;
    FlowLayout flowLayout1 = new FlowLayout();
    JLabel jLabel2 = new JLabel();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    Properties systemProperties;

    /**
     * Creates a new PrefsPageSystem object.
     *
     * @param systemProperties An instance of type Properties
     */
    public PrefsPageSystem(Properties systemProperties) {
        this.systemProperties = systemProperties;

        try {
            jbInit();
        } catch (Exception ex) {
//            ConnectApplicationExceptionHandler.processException(
//            		ConnectApplicationExceptionUtil.createException(ex, "",
//                    ConnectApplicationException.INFO));
        }
    }
    
    /**
     * initialize components 
     *
     * @throws Exception An instance of type Exception
     */
    void jbInit() throws Exception {
        border1 = BorderFactory.createEtchedBorder(Color.white, 
        		new Color(165, 163, 151));
        titledBorder1 = new TitledBorder(BorderFactory.
        		createEtchedBorder(Color.white, 
        				new Color(165, 163, 151)), "...");
        this.setLayout(gridBagLayout1);
        serverPanel.setBorder(titledBorder1);
        serverPanel.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel2.setVerifyInputWhenFocusTarget(true);
        jLabel2.setText("System Settings");
        this.add(jLabel2,
            new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 
            		GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 8, 0), 410, 14));
        this.add(serverPanel,
            new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, 
            		GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 191, 44));
    }
}
